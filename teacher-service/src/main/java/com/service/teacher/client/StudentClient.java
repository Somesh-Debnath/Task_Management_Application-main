package com.service.teacher.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;

import java.util.HashMap;
import java.util.List;

@HttpExchange
public interface StudentClient {

    @GetExchange("/students/getStudentsOfTeacher")
    public List<HashMap<String,Object>> getStudentsOfTeacher(@RequestParam Long empId);

    @PatchExchange("/students/removeTeacherWithId")
    public String removeTeacherWithId(@RequestParam Long empId);
}
