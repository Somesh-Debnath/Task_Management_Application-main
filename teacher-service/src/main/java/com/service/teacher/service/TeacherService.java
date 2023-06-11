package com.service.teacher.service;

import com.service.teacher.model.Teacher;
import com.service.teacher.request.Student;
import com.service.teacher.request.TaskDetails;

import java.util.HashMap;
import java.util.List;

public interface TeacherService {
    public Teacher createTeacher(Teacher teacher);

    public Teacher readTeacher(Long empId);

    public List<Teacher> getAllTeachers();

    public Teacher deleteTeacher(Long empId);

    public Teacher updateTeacher(Teacher teacher, Long empId);

    public List<HashMap<String,Object>> getStudentsOfTeacher(Long empId);

    public List<TaskDetails> getTasksOfTeacher(Long empId);

    List<Teacher> jsonImport(List<Teacher> teachers);
}
