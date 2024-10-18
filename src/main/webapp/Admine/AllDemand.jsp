
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Task Management</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f0f4f8;
      margin: 0;
      padding: 20px;
      color: #333;
    }
    .container {
      max-width: 1000px;
      margin: 0 auto;
      background-color: #ffffff;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    h1 {
      color: #2c3e50;
      margin-bottom: 20px;
    }
    table {
      width: 100%;
      border-collapse: separate;
      border-spacing: 0;
    }
    th, td {
      padding: 12px 15px;
      text-align: left;
      border-bottom: 1px solid #e0e0e0;
    }
    th {
      background-color: #f8f9fa;
      font-weight: 600;
      color: #2c3e50;
      text-transform: uppercase;
      font-size: 0.9em;
      letter-spacing: 0.5px;
    }
    tr:last-child td {
      border-bottom: none;
    }
    .btn {
      padding: 8px 12px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-weight: 500;
      transition: background-color 0.3s, transform 0.1s;
    }
    .btn:hover {
      transform: translateY(-1px);
    }
    .btn-accept {
      background-color: #4CAF50;
      color: white;
      margin-right: 8px;
    }
    .btn-accept:hover {
      background-color: #45a049;
    }
    .btn-reject {
      background-color: #f44336;
      color: white;
    }
    .btn-reject:hover {
      background-color: #da190b;
    }
    .task-title {
      font-weight: 500;
      color: #3498db;
    }
    .due-date {
      font-weight: 500;
      color: #e67e22;
    }
    @media (max-width: 768px) {
      .container {
        padding: 15px;
      }
      table, thead, tbody, th, td, tr {
        display: block;
      }
      thead tr {
        position: absolute;
        top: -9999px;
        left: -9999px;
      }
      tr {
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
        overflow: hidden;
      }
      td {
        border: none;
        position: relative;
        padding-left: 50%;
      }
      td:before {
        position: absolute;
        top: 6px;
        left: 6px;
        width: 45%;
        padding-right: 10px;
        white-space: nowrap;
        content: attr(data-label);
        font-weight: 600;
      }
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Task Management</h1>
  <table>
    <thead>
    <tr>
      <th>Task Title</th>
      <th>Assigned To</th>
      <th>Due Date</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
 <c:choose>
  <c:when test="${not empty demandList}">
    <c:forEach var="demand" items="${demandList}">
      <tr>
        <td data-label="Task Title"><span class="task-title">${demand.title}</span></td>
        <td data-label="Assigned To">${demand.assignedTo.name}</td>
        <td data-label="Due Date"><span class="due-date">${demand.dueDate}</span></td>
        <td data-label="Actions">
          <button class="btn btn-accept" onclick="location.href='acceptInvit?task_id=${demand.id}&user_id=${demand.assignedTo.id}'">Accept</button>
          <button class="btn btn-reject" onclick="location.href='rejectInvit'">Reject</button>
        </td>
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr>
      <td colspan="4" style="text-align: center;">No tasks found</td>
    </tr>
  </c:otherwise>
</c:choose>
    </tbody>
  </table>
</div>
</body>
</html>