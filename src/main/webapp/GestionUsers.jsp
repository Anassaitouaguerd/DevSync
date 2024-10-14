<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DevSync Users</title>
    <style>
        .table {
            width: 100%;
            border-collapse: collapse;
        }
        .table th, .table td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        .table th {
            background-color: #f2f2f2;
            text-align: left;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            outline: none;
            color: #fff;
            background-color: #4CAF50;
            border: none;
            border-radius: 5px;
            box-shadow: 0 4px #999;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn:active {
            background-color: #45a049;
            box-shadow: 0 2px #666;
            transform: translateY(2px);
        }
        .mt-3 {
            margin-top: 1rem;
        }
    </style>
</head>
<body>
<div class="container">
    page
    <c:if test="${not empty sessionScope.user.name}">
        <h2>Hello, <c:out value="${sessionScope.user.name}" />!</h2>
    </c:if>
    <h2>All Users</h2>
    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Address</th>
            <th>Is Manager</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty usersList}">
                <c:forEach items="${usersList}" var="user">
                    <tr>
                        <td><c:out value="${user.name}" /></td>
                        <td><c:out value="${user.email}" /></td>
                        <td><c:out value="${user.adresse}" /></td>
                        <td><c:out value="${user.manager ? 'Manager' : 'Not Manager'}" /></td>
                        <c:if test="${not empty sessionScope.user.manager}">
                            <c:if test="${sessionScope.user.manager eq 'true'}">
                                <td>
                                <button class="btn btn-primary" style="background-color: #007bff; border-color: #007bff; padding: 0.375rem 0.75rem;">
                                    <a href="update-user?email=${user.email}" style="color: white; text-decoration: none;">Update</a>
                                </button>
                                <button class="btn btn-danger" style="background-color: #dc3545; border-color: #dc3545; padding: 0.375rem 0.75rem;">
                                    <a href="delete-user?email=${user.email}" style="color: white; text-decoration: none;">Delete</a>
                                </button>
                                </td>
                            </c:if>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="4">No users found</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
    <c:if test="${not empty sessionScope.user.manager}">
        <c:if test="${sessionScope.user.manager eq 'true'}">
            <a href="add">
                <button class="btn btn-primary mt-3">
                Add New User
                </button>
            </a>
        </c:if>
    </c:if>

        <button class="btn btn-primary mt-3" onclick="location.href='/Task/display-All-Tasks'">
            Go to Tasks
        </button>

</div>
</body>
</html>