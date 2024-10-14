package com.demo.Jobs;

import com.demo.entity.Task;
import com.demo.entity.Token;
import com.demo.repository.TaskRepository;
import com.demo.repository.TokenRepository;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

public class ScheduledJobs implements Job {

    private TaskRepository taskRepository = new TaskRepository();
    private TokenRepository tokenRepository = new TokenRepository();

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String jobType = dataMap.getString("jobType");

        if ("markOverdueTasks".equals(jobType)) {
            markOverdueTasks();
        } else if ("doubleTokens".equals(jobType)) {
            doubleTokensForUnansweredRequests();
        }
    }

    private void markOverdueTasks() {
        List<Task> overdueTasks = taskRepository.getOverdueTasks();
        for (Task task : overdueTasks) {
            task.setCompleted(false);
            taskRepository.updateTask(task);
        }
    }

    private void doubleTokensForUnansweredRequests() {
        List<Token> tokensToDouble = tokenRepository.getTokensForUnansweredRequests();
        for (Token token : tokensToDouble) {
            token.setCount(token.getCount() * 2);
            tokenRepository.updateToken(token);
        }
    }

    public static void startJobs() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // Job for marking overdue tasks
        JobDetail markOverdueTasksJob = JobBuilder.newJob(ScheduledJobs.class)
                .withIdentity("markOverdueTasks", "group1")
                .usingJobData("jobType", "markOverdueTasks")
                .build();

        Trigger markOverdueTasksTrigger = TriggerBuilder.newTrigger()
                .withIdentity("markOverdueTasksTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(24)
                        .repeatForever())
                .build();

        // Job for doubling tokens
        JobDetail doubleTokensJob = JobBuilder.newJob(ScheduledJobs.class)
                .withIdentity("doubleTokens", "group1")
                .usingJobData("jobType", "doubleTokens")
                .build();

        Trigger doubleTokensTrigger = TriggerBuilder.newTrigger()
                .withIdentity("doubleTokensTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(12)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(markOverdueTasksJob, markOverdueTasksTrigger);
        scheduler.scheduleJob(doubleTokensJob, doubleTokensTrigger);

        scheduler.start();
    }
}