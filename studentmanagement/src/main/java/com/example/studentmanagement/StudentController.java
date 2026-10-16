package com.example.studentmanagement;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final ArrayList<Student> students = new ArrayList<>();

    // POST: Add a student
    @PostMapping
    public String addStudent(@RequestBody Student student) {

        for (Student s : students) {
            if (s.getId() == student.getId()) {
                return "Student ID already exists!";
            }
        }

        students.add(student);
        return "Student added successfully!";
    }

    // GET: Get all students
    @GetMapping
    public ArrayList<Student> getAllStudents() {
        return students;
    }

    // GET: Get student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {

        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

    // DELETE: Delete student by ID
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                students.remove(i);
                return "Student deleted successfully!";
            }
        }

        return "Student not found!";
    }
}