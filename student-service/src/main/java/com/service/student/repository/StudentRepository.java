package com.service.student.repository;

import com.service.student.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "select coordinator from student where roll_number = :rollNum",nativeQuery = true)
    public Long findCoordinator(@Param("rollNum") Long rollNum);

    @Modifying
    @Transactional
    @Query(value = "update student set coordinator = :empId where roll_number = :rollNum",nativeQuery = true)
    public void updateTeacher(@Param("rollNum")Long rollNum, @Param("empId") Long empId);

    @Modifying
    @Transactional
    @Query(value = "update student set coordinator = -1 where coordinator = :empId",nativeQuery = true)
    public void deleteTeachersWithId(@Param("empId") Long empId);

    @Query(value = "select * from student where coordinator = :empId",nativeQuery = true)
    public List<Student> getStudentsOfTeacher(@Param("empId") Long empId);

    @Query(value = "select name from teacher where contact= contact",nativeQuery = true)
    public String checkUnique(@Param("contact") String contact);
}
