package in.studentpackage;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByBranchIgnoreCaseAndYear(String branch, int year);

    List<Student> findByCgpaGreaterThanEqual(double cgpa);

}