package com.service.task.controller;

import com.service.task.enums.StatusType;
import com.service.task.model.TaskDetails;
import com.service.task.service.TaskAssignedService;
import com.service.task.service.TaskDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskDetailsService taskDetailsService;

    private final TaskAssignedService taskAssignedService;

    @Autowired
    public TaskController(TaskDetailsService service, TaskAssignedService taskAssigned){
        taskDetailsService =service;
        taskAssignedService=taskAssigned;
    }

    @GetMapping("/getTaskById")
    public TaskDetails getTaskById(@RequestParam Long taskId){
        return taskDetailsService.getTaskById(taskId);
    }

    @GetMapping("/getAllTasks")
    public List<TaskDetails> getAllTasks(){
        return taskDetailsService.getAllTasks();
    }

    @PostMapping("/createTask")
    public TaskDetails createTask(@RequestBody TaskDetails taskDetails, @RequestParam List<Long> stuRollNums){
        TaskDetails t= taskDetailsService.createTask(taskDetails);
        taskAssignedService.addManyStudentsToTask(taskDetails.getTaskId(), stuRollNums);
        return t;
    }

    @DeleteMapping("/deleteTaskById")
    public TaskDetails deleteTaskById(@RequestParam Long taskId){
        TaskDetails task= taskDetailsService.deleteTaskDetails(taskId);
        taskAssignedService.deleteTaskById(taskId);
        return task;
    }

    @PutMapping("/updateTask")
    public TaskDetails updateTask(@RequestBody TaskDetails taskDetails, @RequestParam Long taskId){
        return taskDetailsService.updateTask(taskDetails, taskId);
    }

    @PostMapping("/addStudentsToTask")
    public String addStudentsToTask(@RequestParam Long taskId, @RequestParam List<Long> stuIds){
         return taskAssignedService.addManyStudentsToTask(taskId,stuIds);
    }

    @PostMapping("/addStudentToTask")
    public String addStudentToTask(@RequestParam Long taskId, @RequestParam Long stuId){
        return taskAssignedService.addStudentToTask(taskId,stuId);
    }

    @DeleteMapping("/deleteStudentFromTask")
    public String deleteStudentFromTask(@RequestParam Long taskId, @RequestParam Long rollNum){
        return taskAssignedService.deleteStudentFromTask(rollNum,taskId);
    }

    @DeleteMapping("/deleteAllStudentTasks")
    public String deleteAllStudentTasks(@RequestParam Long rollNum){
        return taskAssignedService.deleteAllStudentTasks(rollNum);
    }

    @GetMapping("/getTasksOfTeacher")
    public List<TaskDetails> getTasksOfTeacher(@RequestParam Long empId){
        return taskDetailsService.getTasksOfTeacher(empId);
    }

    @GetMapping("/getTaskIdsOfTeacher")
    public List<Long> getTaskIdsOfTeacher(@RequestParam Long empId){
        return taskDetailsService.getTaskIdsOfTeacher(empId);
    }

    @GetMapping("/getTasksOfStudent")
    public List<Map<String,Object>> getTasksOfStudent(@RequestParam Long rollNum){
        return taskAssignedService.getTasksOfStudent(rollNum);
    }

    @GetMapping("/getTaskIdsOfStudent")
    public List<Long> getTaskIdsOfStudent(@RequestParam Long rollNum){
        return taskAssignedService.getTaskIdsOfStudent(rollNum);
    }

    @GetMapping("/checkTaskStatus")
    public StatusType checkTaskStatus(@RequestParam Long taskId, @RequestParam Long rollNum){
        return taskAssignedService.checkTaskStatus(taskId, rollNum);
    }

    @PatchMapping("/changeTaskStatus")
    public String changeTaskStatus(@RequestParam Long taskId, @RequestParam Long rollNum, @RequestParam String status){
        return taskAssignedService.changeTaskStatus(taskId, rollNum, StatusType.valueOf(status));
    }
}
