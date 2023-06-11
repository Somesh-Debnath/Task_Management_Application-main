package com.service.task.service;

import com.service.task.client.StudentClient;
import com.service.task.client.TeacherClient;
import com.service.task.enums.StatusType;
import com.service.task.model.TaskAssigned;
import com.service.task.model.TaskDetails;
import com.service.task.repository.TaskAssignedRepository;
import com.service.task.repository.TaskDetailsRepository;
import com.service.task.request.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskServiceImple implements TaskDetailsService, TaskAssignedService {

    @Autowired
    private TaskDetailsRepository taskDetailsRepository;

    @Autowired
    private TaskAssignedRepository taskAssignedRepository;

    @Autowired
    private TeacherClient teacherClient;

    @Autowired
    private StudentClient studentClient;

    @Override
    public TaskDetails createTask(TaskDetails taskDetails) {
        if(taskDetails.getDescription().isEmpty() || taskDetails.getName().isEmpty() || taskDetails.getEmployeeId()==-1){
            throw new RuntimeException("Input field(s) not provided");
        }
        return taskDetailsRepository.save(taskDetails);
    }

    @Override
    public TaskDetails getTaskById(Long taskId) {
        return taskDetailsRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("cannot find task with id :"+taskId));
    }

    @Override
    public TaskDetails deleteTaskDetails(Long taskId) {
        TaskDetails taskDetails = getTaskById(taskId);
        taskDetailsRepository.deleteById(taskId);
        return taskDetails;
    }

    @Override
    public TaskDetails updateTask(TaskDetails taskDetails, Long taskId) {
        if(taskId<=0){
            throw new NoSuchElementException("Cannot find task");
        }
        TaskDetails newTaskDetails = getTaskById(taskId);
        newTaskDetails.setTaskId(taskId);
        if(taskDetails.getName()!=null)
            newTaskDetails.setName(taskDetails.getName());
        if(taskDetails.getDescription()!=null)
            newTaskDetails.setDescription(taskDetails.getDescription());

        return taskDetailsRepository.save(newTaskDetails);
    }

    @Override
    public List<TaskDetails> getAllTasks() {
        return taskDetailsRepository.findAll();
    }

    @Override
    public List<TaskDetails> getTasksOfTeacher(Long empId) {
        if(empId<1){
            throw new NoSuchElementException("Cannot find teacher");
        }
        return taskDetailsRepository.getTasksOfTeacher(empId);
    }

    @Override
    public List<Long> getTaskIdsOfTeacher(Long empId) {
        if(empId<1){
            throw new NoSuchElementException("Cannot find teacher");
        }
        return taskDetailsRepository.getTaskIdsOfTeacher(empId);
    }

    @Override
    public List<Long> getStudentsByTaskId(Long taskId) {
        if(taskId<1){
            throw new NoSuchElementException("Cannot find task");
        }
        return taskAssignedRepository.getStudentsOfTask(taskId);
    }

    @Override
    public List<Map<String,Object>> getTasksOfStudent(Long rollNum) {
        if(rollNum<=0){
            throw new NoSuchElementException("Cannot find student");
        }
        List<Long> taskIds= taskAssignedRepository.getTasksOfStudent(rollNum);
        List<Map<String,Object>> tasks= new ArrayList<>();
        for(long id : taskIds){
            TaskDetails taskDetails=getTaskById(id);
            StatusType status=checkTaskStatus(id, rollNum);
            HashMap<String,Object> map=taskDetails.taskDetailsAsMap();
            map.put("teacher",teacherClient.readTeacherById(taskDetails.getEmployeeId()).name());
            map.put("status",status);
            tasks.add(map);
        }
        return tasks;
    }

    @Override
    public List<Long> getTaskIdsOfStudent(Long rollNum) {
        if(rollNum<=0){
            throw new NoSuchElementException("Cannot find student");
        }
        return taskAssignedRepository.getTasksOfStudent(rollNum);
    }

    @Override
    public List<TaskAssigned> getTasksStatus() {
        return taskAssignedRepository.findAll();
    }

    @Override
    public String deleteStudentFromTask(Long rollNumber, Long taskId) {
        if(rollNumber<=0){
            throw new NoSuchElementException("Cannot find student");
        }
        if(taskId<=0){
            throw new NoSuchElementException("Cannot find task");
        }
        try {
            taskAssignedRepository.deleteStudentFromTask(taskId, rollNumber);
            return "Success";
        } catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public void deleteTaskById(Long taskId) {
        if(taskId<=0){
            throw new NoSuchElementException("Cannot find task");
        }
        taskAssignedRepository.deleteTaskById(taskId);
    }

    @Override
    public String deleteAllStudentTasks(Long rollNum) {
        if(rollNum<=0){
            throw new NoSuchElementException("Cannot find student");
        }
        try {
            taskAssignedRepository.deleteAllStudentTasks(rollNum);
            return "Success";
        } catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public String addStudentToTask(Long taskId, Long rollNum) {
        if(rollNum<=0){
            throw new NoSuchElementException("Cannot find student");
        }
        if(taskId<=0){
            throw new NoSuchElementException("Cannot find task");
        }
        try {
            TaskAssigned task=new TaskAssigned();
            task.setTaskId(taskId);
            task.setStudentRollNum(rollNum);
            task.setStatus(StatusType.DUE);
            taskAssignedRepository.save(task);
            return "Success";
        } catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public StatusType checkTaskStatus(Long taskId, Long rollNum) {
        if(rollNum<=0){
            throw new NoSuchElementException("Cannot find student");
        }
        if(taskId<=0){
            throw new NoSuchElementException("Cannot find task");
        }
        return taskAssignedRepository.checkTaskStatus(taskId,rollNum);
    }

    @Override
    public String changeTaskStatus(Long taskId, Long rollNum, StatusType status) {
        if(rollNum<=0){
            throw new NoSuchElementException("Cannot find student");
        }
        if(taskId<=0){
            throw new NoSuchElementException("Cannot find task");
        }
        if(status!=StatusType.DUE && status!=StatusType.IN_PROGRESS && status!=StatusType.CANCELLED && status!=StatusType.COMPLETED){
            throw new RuntimeException("Invalid status");
        }
        taskAssignedRepository.changeTaskStatus(status.name(),taskId,rollNum);
        return "Changed status to : "+status.name();
    }

    @Override
    public String addManyStudentsToTask(Long taskId, List<Long> rollNums) {
        if(taskId<=0){
            throw new NoSuchElementException("Cannot find task");
        }
        try {
            for (long rollNum : rollNums) {
                TaskAssigned task = new TaskAssigned();
                task.setTaskId(taskId);
                task.setStudentRollNum(rollNum);
                task.setStatus(StatusType.DUE);
                taskAssignedRepository.save(task);
            }
            return "Success";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
}
