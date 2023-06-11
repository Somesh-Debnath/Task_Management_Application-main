package com.service.teacher.client;

import com.service.teacher.request.TaskDetails;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.*;

import java.util.List;

@HttpExchange
public interface TaskClient {

    @GetExchange("/tasks/getTasksOfTeacher")
    public List<TaskDetails> getTasksOfTeacher(@RequestParam Long empId);
}
