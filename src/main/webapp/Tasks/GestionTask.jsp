<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gestion Tasks</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 18px;
            text-align: left;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #333;
            color: white;
        }

        tr:hover {
            background-color: #f2f2f2;
        }

        .btn {
            padding: 10px 20px;
            font-size: 16px;
            margin: 5px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
        }

        .btn-add {
            background-color: #4CAF50;
            color: white;
        }

        .btn-update {
            background-color: #FFC107;
            color: white;
        }

        .btn-delete {
            background-color: #F44336;
            color: white;
        }

        .btn:hover {
            opacity: 0.8;
        }

        .action-buttons {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .add-task-btn {
            text-align: center;
            margin: 20px 0;
        }

        .add-task-btn button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            font-size: 18px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
        }

        .add-task-btn button:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Gestion Tasks</h1>

    <div class="add-task-btn">
        <button type="button" onclick="location.href='add'">Add Task</button>
    </div>

    <table>
        <thead>
        <tr>
            <th>Task ID</th>
            <th>Task Title</th>
            <th>Creation Date</th>
            <th>Assigned To</th>
            <th>Created By</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty tasksList}">
                <c:forEach items="${tasksList}" var="task">
                    <tr>
                        <td><c:out value="${task.id}" /></td>
                        <td><c:out value="${task.title}" /></td>
                        <td><c:out value="${task.creationDate}" /></td>
                        <td><c:out value="${task.assignedTo.name}" /></td>
                        <td><c:out value="${task.createdBy.name}" /></td>
                        <td>
                            <div class="action-buttons">
                                <button class="btn btn-update" type="button" onclick="location.href='updateTask.jsp?id=${task.id}'">Update</button>
                                <button class="btn btn-delete" type="button" onclick="location.href='deleteTask.jsp?id=${task.id}'">Delete</button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="6">No Tasks found</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>
</body>
</html>
