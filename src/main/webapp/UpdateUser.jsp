<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Update User</title>
  <style>
    .mb-3 {
      margin-bottom: 1rem;
    }
    .form-label {
      display: block;
      margin-bottom: 0.5rem;
    }
    .form-control {
      width: 50%;
      padding: 0.375rem 0.75rem;
    }
    .form-check-input {
      margin-right: 0.3rem;
    }
    .btn-primary {
      background-color: #007bff;
      border-color: #007bff;
      padding: 0.375rem 0.75rem;
    }
    .btn{
      cursor: pointer;
    }
  </style>
</head>
<body>
<form action="/user/update" method="POST">
  <input type="hidden" name="_method" value="PUT">
  <input type="hidden" name="id" value="<c:out value="${user.id}" />">
  <div class="mb-3" style="margin-bottom: 1rem;">
    <label for="name" class="form-label">Name</label>
    <input type="text" class="form-control" id="name" value="<c:out value="${user.name}" />" name="name" required style="width: 50%; padding: 0.375rem 0.75rem;">
  </div>
  <div class="mb-3" style="margin-bottom: 1rem;">
    <label for="email" class="form-label">Email</label>
    <input type="email" class="form-control" id="email" name="email" value="<c:out value="${user.email}" />" required style="width: 50%; padding: 0.375rem 0.75rem;">
  </div>
  <div class="mb-3" style="margin-bottom: 1rem;">
    <label for="password" class="form-label">Password</label>
    <input type="password" class="form-control" id="password" name="password" required style="width: 50%; padding: 0.375rem 0.75rem;">
  </div>
  <div class="mb-3" style="margin-bottom: 1rem;">
    <label for="adresse" class="form-label">Address</label>
    <input type="text" class="form-control" id="adresse" value="<c:out value="${user.adresse}" />" name="adresse" required style="width: 50%; padding: 0.375rem 0.75rem;">
  </div>
  <div class="mb-3 form-check">
    <input type="checkbox" class="form-check-input" id="manager" name="manager"
    ${user.isManager ? 'checked' : ''}>
    <label class="form-check-label" for="manager" style="margin-left: 0.3rem;">Is Manager</label>
  </div>
  <button type="submit" class="btn btn-primary" style="background-color: #007bff; border-color: #007bff; padding: 0.375rem 0.75rem;">Update</button>
</form>
</body>
</html>
