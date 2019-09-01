package bd.edu.seu.programapi.repository;

import bd.edu.seu.programapi.model.Program;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends CrudRepository<Program, String> {
}
