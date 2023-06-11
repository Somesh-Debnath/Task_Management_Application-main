package com.service.teacher.request;

public record TaskDetails(Long taskId, String name, String description, Long employeeId) {
}
