package com.service.student.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.*;

import java.util.List;
import java.util.Map;

@HttpExchange
public interface TaskClient {
    @DeleteExchange("/tasks/deleteAllStudentTasks")
    public void deleteAllStudentTasks(@RequestParam Long rollNum);

    @GetExchange("/tasks/getTaskIdsOfStudent")
    public List<Long> getTaskIdsOfStudent(@RequestParam Long rollNum);

    @GetExchange("/tasks/getTasksOfStudent")
    public List<Map<String,Object>> getTasksOfStudent(@RequestParam Long rollNum);

}
