package com.service.student.service;

import com.service.student.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StudentService {
    public Student createStudent(Student student);

    public Student getStudent(Long rollNum);

    public List<Student> getAllStudents();

    public List<HashMap<String,Object>> getStudentsOfTeacher(long empId);

    public Student deleteStudent(Long rollNum);

    public Student updateStudent(Student student, Long rollNum);

    public String getCoordinator(Long rollNum);

    public String addTeacher(Long stuId,Long empId);

    public String deleteTeacherOfStudent(Long stuId);

    public String deleteTeachersWithId(Long empId);

    public List<Map<String,Object>> getTasksOfStudent(Long rollNum);

    List<Student> jsonImport(List<Student> teachers);
}
