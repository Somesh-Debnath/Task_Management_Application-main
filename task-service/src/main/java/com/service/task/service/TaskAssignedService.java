package com.service.task.service;

import com.service.task.enums.StatusType;
import com.service.task.model.TaskAssigned;
import com.service.task.model.TaskDetails;
import org.springframework.scheduling.config.Task;

import java.util.List;
import java.util.Map;

public interface TaskAssignedService {

    public List<Long> getStudentsByTaskId(Long taskId);

    public List<Map<String,Object>> getTasksOfStudent(Long rollNum);

    public List<Long> getTaskIdsOfStudent(Long rollNum);

    public List<TaskAssigned> getTasksStatus();

    public String deleteStudentFromTask(Long rollNumber, Long taskId);

    public void deleteTaskById(Long taskId);

    public String deleteAllStudentTasks(Long rollNum);

    public String addStudentToTask(Long taskId, Long rollNum);

    public StatusType checkTaskStatus(Long taskId, Long rollNum);

    public String changeTaskStatus(Long taskId, Long rollNum, StatusType status);

    public String addManyStudentsToTask(Long taskId, List<Long> rollNums);
}
