
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Advanced Dashboard</title>
    <style>
        :root {
            --primary-color: #4a90e2;
            --secondary-color: #50e3c2;
            --background-color: #f4f7f9;
            --text-color: #333;
            --card-bg: #ffffff;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--background-color);
            color: var(--text-color);
            line-height: 1.6;
        }

        .dashboard {
            display: flex;
            min-height: 100vh;
        }

        .sidebar {
            width: 250px;
            background: linear-gradient(to bottom, var(--primary-color), var(--secondary-color));
            color: white;
            padding: 2rem;
        }

        .logo {
            font-size: 1.5rem;
            font-weight: bold;
            margin-bottom: 2rem;
        }

        .nav-links {
            list-style: none;
        }

        .nav-links li {
            margin-bottom: 1rem;
        }

        .nav-links a {
            color: white;
            text-decoration: none;
            display: flex;
            align-items: center;
        }

        .nav-links a:hover {
            opacity: 0.8;
        }

        .icon {
            margin-right: 10px;
            font-size: 1.2rem;
        }

        .main-content {
            flex: 1;
            padding: 2rem;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
        }

        .search-bar {
            display: flex;
            align-items: center;
            background-color: var(--card-bg);
            border-radius: 20px;
            padding: 0.5rem 1rem;
        }

        .search-bar input {
            border: none;
            background: transparent;
            margin-left: 0.5rem;
        }

        .user-info {
            display: flex;
            align-items: center;
        }

        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 1rem;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }

        .stat-card {
            background-color: var(--card-bg);
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .stat-card h3 {
            font-size: 0.9rem;
            color: #888;
            margin-bottom: 0.5rem;
        }

        .stat-card .value {
            font-size: 1.8rem;
            font-weight: bold;
        }

        .chart-container {
            background-color: var(--card-bg);
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .invites-list {
            background-color: var(--card-bg);
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .invite-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem 0;
            border-bottom: 1px solid #eee;
        }

        .invite-item:last-child {
            border-bottom: none;
        }

        .btn {
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn-accept {
            background-color: var(--secondary-color);
            color: white;
        }

        .btn-decline {
            background-color: #ff6b6b;
            color: white;
        }

        .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
<div class="dashboard">
    <aside class="sidebar">
        <div class="logo">DevSync</div>
        <ul class="nav-links">
            <li><a href="#"><span class="icon">📊</span>Dashboard</a></li>
            <li><a href="alldemand"><span class="icon">🔔</span>Notifications</a></li>
        </ul>
    </aside>
    <main class="main-content">
        <header class="header">
            <div class="search-bar">
                <span class="icon">🔍</span>
                <input type="text" placeholder="Search...">
            </div>
            <div class="user-info">
                <img src="https://via.placeholder.com/40" alt="User Avatar" class="user-avatar">
                <span>John Doe</span>
            </div>
        </header>
        <section class="stats-grid">
            <div class="stat-card">
                <h3>Total Tasks</h3>
                <div class="value">${taskCount}</div>
            </div>
            <div class="stat-card">
                <h3>Completed Tasks</h3>
                <div class="value">${completeTaskCount}</div>
            </div>
            <div class="stat-card">
                <h3>Total Token</h3>
                <div class="value">${tokenCount}</div>
            </div>
            <div class="stat-card">
                <h3>Tokens Used</h3>
                <div class="value">${usedTokenCount}</div>
            </div>
        </section>
        <section class="chart-container">
            <h2>Task Completion Trend</h2>
            <!-- Placeholder for a chart -->
            <div style="width: 100%; height: 300px; background-color: #eee; display: flex; justify-content: center; align-items: center;">
                Chart Placeholder
            </div>
        </section>
        <section class="invites-list">
            <h2>Task Replacement Invitations</h2>
            <div class="invite-item">
                <div>
                    <strong>Alice Johnson</strong> invited to replace: <em>Project X Review</em>
                </div>
                <div>
                    <button class="btn btn-accept">Accept</button>
                    <button class="btn btn-decline">Decline</button>
                </div>
            </div>
            <div class="invite-item">
                <div>
                    <strong>Bob Smith</strong> invited to replace: <em>Client Meeting Prep</em>
                </div>
                <div>
                    <button class="btn btn-accept">Accept</button>
                    <button class="btn btn-decline">Decline</button>
                </div>
            </div>
            <div class="invite-item">
                <div>
                    <strong>Carol White</strong> invited to replace: <em>Quarterly Report</em>
                </div>
                <div>
                    <button class="btn btn-accept">Accept</button>
                    <button class="btn btn-decline">Decline</button>
                </div>
            </div>
        </section>
    </main>
</div>
</body>
</html>
