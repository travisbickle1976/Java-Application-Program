package in.studentpackage;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // GET ALL STUDENTS
    @GetMapping
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // GET STUDENT BY ID
    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(
            @PathVariable int studentId) {

        return repository.findById(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ADD STUDENT
    @PostMapping
    public ResponseEntity<?> addStudent(
            @RequestBody Student student) {

        if (repository.existsById(student.getStudentId())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Student already exists"));
        }

        Student savedStudent = repository.save(student);

        return ResponseEntity.ok(savedStudent);
    }

    // UPDATE STUDENT
    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(
            @PathVariable int studentId,
            @RequestBody Student updatedStudent) {

        return repository.findById(studentId)
                .map(student -> {

                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setBranch(updatedStudent.getBranch());
                    student.setYear(updatedStudent.getYear());
                    student.setCgpa(updatedStudent.getCgpa());

                    Student savedStudent = repository.save(student);

                    return ResponseEntity.ok(savedStudent);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE STUDENT
    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(
            @PathVariable int studentId) {

        if (!repository.existsById(studentId)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(studentId);

        return ResponseEntity.ok(
                Map.of("message", "Student deleted successfully"));
    }

    // SEARCH STUDENTS
    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam String branch,
            @RequestParam int year) {

        return repository.findByBranchIgnoreCaseAndYear(branch, year);
    }

    // GET TOPPER STUDENTS
    @GetMapping("/topper")
    public List<Student> getTopperStudents(
            @RequestParam double cgpa) {

        return repository.findByCgpaGreaterThanEqual(cgpa);
    }
}