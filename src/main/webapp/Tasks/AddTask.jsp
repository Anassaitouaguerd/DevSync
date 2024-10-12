<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add Task</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      margin: 0;
      padding: 20px;
    }

    .container {
      max-width: 600px;
      margin: auto;
      background: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }

    h2 {
      text-align: center;
      color: #333;
    }

    .form-group {
      margin-bottom: 15px;
    }

    label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
      color: #555;
    }

    input[type="text"],
    input[type="date"],
    input[type="datetime-local"],
    textarea,
    .multi-select-wrapper {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 14px;
      box-sizing: border-box;
    }

    textarea {
      resize: vertical;
    }

    .multi-select-wrapper {
      position: relative;
      padding: 0;
    }

    .multi-select-dropdown {
      display: none;
      position: absolute;
      top: 100%;
      left: 0;
      right: 0;
      background: #fff;
      border: 1px solid #ccc;
      border-top: none;
      border-radius: 0 0 4px 4px;
      max-height: 200px;
      overflow-y: auto;
      z-index: 1000;
    }

    .multi-select-dropdown label {
      display: block;
      padding: 8px 10px;
      cursor: pointer;
      transition: background-color 0.2s;
    }

    .multi-select-dropdown label:hover {
      background-color: #f0f0f0;
    }

    .multi-select-dropdown input[type="checkbox"] {
      margin-right: 10px;
    }

    .multi-select-header {
      padding: 10px;
      cursor: pointer;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .multi-select-header::after {
      content: '▼';
      font-size: 12px;
    }

    .multi-select-wrapper.open .multi-select-dropdown {
      display: block;
    }

    .multi-select-wrapper.open .multi-select-header::after {
      content: '▲';
    }

    .selected-options {
      display: flex;
      flex-wrap: wrap;
      gap: 5px;
      padding: 5px;
    }

    .selected-option {
      background-color: #e0e0e0;
      padding: 2px 8px;
      border-radius: 12px;
      font-size: 12px;
      display: inline-flex;
      align-items: center;
    }

    .selected-option .remove {
      margin-left: 5px;
      cursor: pointer;
      font-weight: bold;
    }

    .btn {
      background-color: #5cb85c;
      color: white;
      padding: 10px 15px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 16px;
      transition: background-color 0.3s;
      display: block;
      width: 100%;
    }

    .btn:hover {
      background-color: #4cae4c;
    }

    @media (max-width: 600px) {
      .container {
        padding: 15px;
      }

      .btn {
        font-size: 14px;
      }
    }
  </style>
</head>
<body>
<div class="container">
  <h2>Add New Task</h2>
  <form action="add" method="POST">
    <div class="form-group">
      <label for="taskName">Task Name</label>
      <input type="text" id="taskName" name="title" required>
    </div>
    <div class="form-group">
      <label for="description">Description</label>
      <textarea id="description" name="description" rows="4" required></textarea>
    </div>
    <div class="form-group">
      <label for="dueDate">Due Date</label>
      <input type="datetime-local" id="dueDate" name="dueDate" required>
    </div>
    <c:if test="${not empty sessionScope.user.manager}">
      <c:if test="${sessionScope.user.manager eq 'true'}">
        <div class="form-group">
          <label for="assignedTo">Assigned To</label>
          <select id="assignedTo" name="assignedTo" required>
            <c:forEach items="${usersList}" var="user">
              <option value="${user.id}">${user.name}</option>
            </c:forEach>
          </select>
        </div>
      </c:if>
    </c:if>

    <div class="form-group" style="display: none">
      <label for="createdBy">Created By</label>
      <input type="number" id="createdBy" name="createdBy" value="${sessionScope.user.id}" required>
    </div>
    <div class="form-group">
      <label>Tags</label>
      <div class="multi-select-wrapper">
        <div class="multi-select-header">Select Tags</div>
        <div class="selected-options"></div>
        <div class="multi-select-dropdown">
          <c:choose>
            <c:when test="${not empty tagsList}">
              <c:forEach items="${tagsList}" var="tag">
                <label>
                  <input type="checkbox" name="Tag" value="${tag.id}"> ${tag.name}
                </label>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <label>
                <input type="checkbox" name="Tag" value="Not-found" disabled> Not Found
              </label>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
    <div class="form-group">
      <label for="creationDate">Creation Date</label>
      <input type="date" id="creationDate" name="creationDate" required>
    </div>
    <button type="submit" class="btn">Add Task</button>
  </form>
</div>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    const multiSelect = document.querySelector('.multi-select-wrapper');
    const header = multiSelect.querySelector('.multi-select-header');
    const dropdown = multiSelect.querySelector('.multi-select-dropdown');
    const checkboxes = dropdown.querySelectorAll('input[type="checkbox"]');
    const selectedOptions = multiSelect.querySelector('.selected-options');

    header.addEventListener('click', function() {
      multiSelect.classList.toggle('open');
    });

    checkboxes.forEach(function(checkbox) {
      checkbox.addEventListener('change', updateSelectedOptions);
    });

    function updateSelectedOptions() {
      selectedOptions.innerHTML = '';
      checkboxes.forEach(function(checkbox) {
        if (checkbox.checked) {
          const option = document.createElement('span');
          option.classList.add('selected-option');
          option.textContent = checkbox.parentNode.textContent.trim();
          const removeBtn = document.createElement('span');
          removeBtn.classList.add('remove');
          removeBtn.textContent = '×';
          removeBtn.addEventListener('click', function(e) {
            e.stopPropagation();
            checkbox.checked = false;
            updateSelectedOptions();
          });
          option.appendChild(removeBtn);
          selectedOptions.appendChild(option);
        }
      });
    }

    document.addEventListener('click', function(e) {
      if (!multiSelect.contains(e.target)) {
        multiSelect.classList.remove('open');
      }
    });

    let form = document.querySelector('form');
    form.addEventListener('submit', function(e) {
      e.preventDefault(); // Prevent the default form submission

      // Create a FormData object
      const formData = new FormData(form);

      // Get all selected tags
      const selectedTag = [];
      checkboxes.forEach(function(checkbox) {
        if (checkbox.checked) {
          selectedTag.push(checkbox.value);
        }
      });


      // Send the form data using AJAX
      fetch(form.action, {
        method: form.method,
        body: formData,
      })
              .then(response => {
                if (!response.ok) {
                  throw new Error('Network response was not ok');
                }
                return window.location.href = '/Task/display-All-Tasks';
              })
              .then(data => {
                console.log('Success:', data);
                // Handle successful submission
                // Change the URL to a success page or the task list page
                // window.location.href = '/tasks'; // Replace with your desired URL
              })
              .catch(error => {
                console.error('Error:', error);
                // Handle errors (e.g., show an error message)
              });
    });
  });
</script>
</body>
</html>
