package com.service.teacher.request;


import java.util.List;

public record Student(Long rollNumber, String name, String email, String contact, Long coordinator) {
}
