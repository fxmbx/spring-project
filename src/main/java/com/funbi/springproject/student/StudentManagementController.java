package com.funbi.springproject.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private static final List<Student> STUDENTS = Arrays.asList(
        new Student(1,"james bond"),
        new Student(2,"007 bond"),
        new Student(3,"john snow")
    ); 

    @GetMapping
    public List<Student> getAllStudents(){
        return STUDENTS;
    }
    @PostMapping("/register")
    public void registerNewStudents(@RequestBody() Student request){
        int id = STUDENTS.get(STUDENTS.size()-1).getStudentId()+ 1;
        var stud = new Student(id,request.getStudentName());
        STUDENTS.add(stud);

    }
    @DeleteMapping(path="{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println("Deleted");
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId, Student student){
        System.out.println(String.format("%s %s", studentId, student));
    }



}
