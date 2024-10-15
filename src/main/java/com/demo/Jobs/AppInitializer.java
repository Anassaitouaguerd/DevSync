package com.demo.Jobs;

import com.demo.Jobs.ScheduledJobs;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.quartz.SchedulerException;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ScheduledJobs.startJobs();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup code if needed
    }
}