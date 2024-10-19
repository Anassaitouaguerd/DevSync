package com.demo.Servlets;

import com.demo.entity.User;
import com.demo.service.TaskService;
import com.demo.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Dashboard", value = "/Dashboard/*")
public class Dashboard extends HttpServlet {
    private TaskService taskService;
    private TokenService tokenService;

    public void init() throws ServletException {
        super.init();
        taskService = new TaskService();
        tokenService = new TokenService();
    }
    private static final Logger LOGGER = Logger.getLogger(Dashboard.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        LOGGER.info("Received request with pathInfo: " + pathInfo);

        try {
            switch (pathInfo) {
                case "/":
                    Long taskCount = taskService.getTaskCount();
                    Long completeTaskCount = taskService.getCompleteTaskCount();
                    Long tokenCount = tokenService.getTokenCount();
                    Long usedTokenCount = tokenService.getUsedTokenCount();
                    request.setAttribute("taskCount", taskCount);
                    request.setAttribute("completeTaskCount", completeTaskCount);
                    request.setAttribute("tokenCount", tokenCount);
                    request.setAttribute("usedTokenCount", usedTokenCount);
                    request.getRequestDispatcher("/Admine/Dashboard.jsp").forward(request, response);
                    break;
                case "/alldemand":
                    Object userObj = request.getSession()
                                            .getAttribute("user");
                    User user = (User) userObj;
                    request.setAttribute("demandList", taskService.displayTasksHasPanding(user));
                    request.getRequestDispatcher("/Admine/AllDemand.jsp").forward(request, response);
                    break;
                case "acceptInvit":
                    String taskId = request.getParameter("task_id");
                    String userId = request.getParameter("user_id");
                    taskService.acceptInvitation(Long.parseLong(taskId) , Long.parseLong(userId));
                    response.sendRedirect(request.getContextPath() + "/Dashboard/alldemand");
                    break;
                default:
                    LOGGER.warning("Unknown path: " + pathInfo);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while processing request", e);
            throw e;
        }
    }
}