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
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        response.getWriter().println(pathInfo);
        switch (pathInfo) {
            case "/display-All-Tasks":
                List<Task> tasks = new TaskService().displayTasks();
                request.setAttribute("tasksList", tasks);
                request.getRequestDispatcher("/Tasks/GestionTask.jsp").forward(request, response);
                break;
            case "/add":
                List<Tag> tags = new TagService().displayTags();
                List<User> users = new UserService().displayUsers();
                request.setAttribute("tagsList", tags);
                request.setAttribute("usersList", users);
                request.getRequestDispatcher("/Tasks/AddTask.jsp").forward(request, response);
                break;
            case "/update":
                request.getRequestDispatcher("/UpdateTask.jsp").forward(request, response);
                break;
            case "/delete":
                request.getRequestDispatcher("/DeleteTask.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        res.setContentType("application/json");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String dueDate = req.getParameter("dueDate");

        String assignedTo;
        if (req.getParameter("assignedTo") != null) {
            assignedTo = req.getParameter("assignedTo");
        } else {
            Object userObj = req.getSession().getAttribute("user");
            if (userObj != null && userObj instanceof User) {
                User user = (User) userObj;
                assignedTo = String.valueOf(user.getId());
            } else {
                assignedTo = "default_id";
            }
        }
        String createdBy = req.getParameter("createdBy");
            String[] tagsArray = req.getParameterValues("Tag");
        List<String> tags = tagsArray != null ? Arrays.asList(tagsArray) : new ArrayList<>();
        new TaskService().createTask(title, description, LocalDateTime.parse(dueDate), Long.parseLong(assignedTo), Long.parseLong(createdBy),tags);
        res.setStatus(HttpServletResponse.SC_CREATED);
    }

}
