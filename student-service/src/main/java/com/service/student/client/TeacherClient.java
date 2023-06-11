package com.service.student.client;

import com.service.student.request.Teacher;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface TeacherClient {

    @GetExchange("/teachers/getTeacherById")
    public Teacher readTeacherById(@RequestParam Long empId);

}
