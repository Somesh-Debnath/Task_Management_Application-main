package com.service.task.service;

import com.service.task.model.TaskDetails;

import java.util.List;

public interface TaskDetailsService {

    public TaskDetails createTask(TaskDetails taskDetails);

    public TaskDetails getTaskById(Long taskId);

    public TaskDetails deleteTaskDetails(Long taskId);

    public TaskDetails updateTask(TaskDetails taskDetails, Long taskId);

    public List<TaskDetails> getAllTasks();

    public List<TaskDetails> getTasksOfTeacher(Long empId);

    public List<Long> getTaskIdsOfTeacher(Long empId);
}
