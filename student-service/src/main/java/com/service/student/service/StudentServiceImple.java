package com.service.student.service;

import com.service.student.client.TaskClient;
import com.service.student.client.TeacherClient;
import com.service.student.model.Student;
import com.service.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImple implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherClient teacherClient;

    @Autowired
    private TaskClient taskClient;

    @Override
    public Student createStudent(Student student) {
        if(student.getContact().isEmpty() || student.getName().isEmpty() || student.getEmail().isEmpty()){
            throw new RuntimeException("Input field(s) not provided");
        }
        if(studentRepository.checkUnique(student.getContact()).isEmpty()){
            throw new RuntimeException("Already present in database");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long rollNum) {
        return studentRepository.findById(rollNum).orElseThrow(() -> new NoSuchElementException("Cannot find student with roll number: "+rollNum));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<HashMap<String,Object>> getStudentsOfTeacher(long empId) {
        List<HashMap<String,Object>> students=new ArrayList<>();
        for(Student student : studentRepository.getStudentsOfTeacher(empId)){
            HashMap<String,Object> map=student.studentDetailsAsMap();
            map.put("coordinator",teacherClient.readTeacherById(empId).name());
            students.add(map);
        }
        return students;
    }

    @Override
    public Student deleteStudent(Long rollNum) {
        Student student=getStudent(rollNum);
        taskClient.deleteAllStudentTasks(rollNum);
        studentRepository.deleteById(rollNum);
        return student;
    }

    @Override
    public Student updateStudent(Student student, Long rollNum) {
        Student newStudent = getStudent(rollNum);
        newStudent.setRollNumber(rollNum);
        if(student.getName()!=null)
            newStudent.setName(student.getName());
        if(student.getEmail()!=null)
            newStudent.setEmail(student.getEmail());
        if(student.getContact()!=null)
            newStudent.setContact(student.getContact());
        return studentRepository.save(newStudent);
    }

    @Override
    public String getCoordinator(Long rollNum) {
        if(rollNum<=0){
            throw new NoSuchElementException("Cannot find student");
        }
        try {
            Long empId= getStudent(rollNum).getCoordinator();
            String name= teacherClient.readTeacherById(empId).name();
            if(name.isEmpty()){
                return "Teacher Not assigned";
            }
            return "Coordinator : " +name;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String addTeacher(Long stuId, Long empId) {
        if(empId<=0){
            throw new RuntimeException("Cannot add invalid coordinator to student");
        }
        if(stuId<=0){
            throw new NoSuchElementException("Cannot find student");
        }
        try{
            studentRepository.updateTeacher(stuId, empId);
            return "Success";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String deleteTeacherOfStudent(Long stuId) {
        if (stuId<=0){
            throw new NoSuchElementException("Invalid Student Roll Number");
        }
        try {
            getStudent(stuId);
            studentRepository.updateTeacher(stuId, -1L);
            return "Success";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String deleteTeachersWithId(Long empId) {
        if(empId<=0){
            throw new RuntimeException("Cannot DELETE invalid coordinator from student");
        }
        try {
            studentRepository.deleteTeachersWithId(empId);
            return "Success";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public List<Map<String,Object>> getTasksOfStudent(Long rollNum) {
        if(rollNum<=0){
            throw new NoSuchElementException("Cannot find student");
        }
        return taskClient.getTasksOfStudent(rollNum);
    }
    @Override
    public List<Student> jsonImport(List<Student> students) {
        students.forEach(
                student -> {
                    student.setCoordinator((long)2);
                }
        );
        return studentRepository.saveAll(students);
    }
}
