package bd.edu.seu.programapi.repository;

import bd.edu.seu.programapi.model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, String> {
}
