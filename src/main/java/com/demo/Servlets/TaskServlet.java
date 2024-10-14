package com.demo.Servlets;

import com.demo.entity.Tag;
import com.demo.entity.Task;
import com.demo.entity.User;
import com.demo.service.TagService;
import com.demo.service.TaskService;
import com.demo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MultipartConfig
@WebServlet(name = "TaskServlet", value = "/Task/*")
public class TaskServlet extends HttpServlet {
    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        super.init();
        taskService = new TaskService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        response.getWriter().println(pathInfo);
        List<Tag> tags = new TagService().displayTags();
        switch (pathInfo) {
            case "/display-All-Tasks":
                Object userObj = request.getSession().getAttribute("user");
                User user = (User) userObj;
                List<Task> tasks = taskService.displayTasks(user);
                request.setAttribute("tasksList", tasks);
                request.getRequestDispatcher("/Tasks/GestionTask.jsp").forward(request, response);
                break;
            case "/add":
                List<User> users = new UserService().displayUsers();
                request.setAttribute("tagsList", tags);
                request.setAttribute("usersList", users);
                request.getRequestDispatcher("/Tasks/AddTask.jsp").forward(request, response);
                break;
            case "/updateTask":
                String taskId = request.getParameter("id");
                Task task = taskService.getTaskById(Long.parseLong(taskId));
                List<User> usersList = new UserService().displayUsers();
                request.setAttribute("task", task);
                request.setAttribute("usersList", usersList);
                request.setAttribute("tagsList", new TagService().displayTags());
                request.getRequestDispatcher("/Tasks/UpdateTask.jsp").forward(request, response);
                break;
            case "/delete":
                request.getRequestDispatcher("/DeleteTask.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String pathInfo = req.getPathInfo();
        res.setContentType("application/json");
        switch (pathInfo) {
            case "/add":
                handleAddTask(req, res);
                break;
            case "/update":
                handleUpdateTask(req, res);
                break;
            case "/complete":
                handleCompleteTask(req, res);
                break;
            case "/delete":
                handleDeleteTask(req, res);
                break;
        }
    }

    private void handleAddTask(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String dueDate = req.getParameter("dueDate");

        String assignedTo;
        User currentUser = (User) req.getSession().getAttribute("user");
        if (req.getParameter("assignedTo") != null) {
            assignedTo = req.getParameter("assignedTo");
            if (!assignedTo.equals(currentUser.getId().toString())) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write("You can only assign additional tasks to yourself");
                return;
            }
        } else {
            assignedTo = currentUser.getId().toString();
        }

        String createdBy = currentUser.getId().toString();
        String[] tagsArray = req.getParameterValues("Tag");
        List<String> tags = tagsArray != null ? Arrays.asList(tagsArray) : new ArrayList<>();

        try {
            taskService.createTask(title, description, LocalDateTime.parse(dueDate), Long.parseLong(assignedTo), Long.parseLong(createdBy), tags);
            res.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write(e.getMessage());
        }
    }

    private void handleUpdateTask(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String taskId = req.getParameter("id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String dueDate = req.getParameter("dueDate");
        User currentUser = (User) req.getSession().getAttribute("user");
        String assignedTo = req.getParameter("assignedToUP");
        if (assignedTo == null) {
            assignedTo = currentUser.getId().toString();
        }
        String[] tagsArray = req.getParameterValues("Tag");
        List<String> tags = tagsArray != null ? Arrays.asList(tagsArray) : new ArrayList<>();

        try {
            taskService.updateTask(Long.parseLong(taskId), title, description, LocalDateTime.parse(dueDate), Long.parseLong(assignedTo), currentUser.getId(), tags);
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write(e.getMessage());
        }
    }

    private void handleCompleteTask(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String taskId = req.getParameter("id");
        try {
            taskService.markTaskAsCompleted(Long.parseLong(taskId));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write(e.getMessage());
        }
    }

    private void handleDeleteTask(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String taskId = req.getParameter("id");
        User currentUser = (User) req.getSession().getAttribute("user");
        try {
            taskService.deleteTask(Long.parseLong(taskId), currentUser.getId());
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalStateException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write(e.getMessage());
        }
    }
}