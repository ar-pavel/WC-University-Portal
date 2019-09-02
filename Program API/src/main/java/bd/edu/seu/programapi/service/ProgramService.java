package bd.edu.seu.programapi.service;

import bd.edu.seu.programapi.exception.ResourceAlreadyExistsException;
import bd.edu.seu.programapi.exception.ResourceDoesNotExistException;
import bd.edu.seu.programapi.model.Program;
import bd.edu.seu.programapi.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {
    private ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public Program insertProgram(Program program) throws ResourceAlreadyExistsException {
        Optional<Program> optionalProgram = programRepository.findById(program.getName());
        if (optionalProgram.isPresent()) {
            throw new ResourceAlreadyExistsException(program.getName());
        } else {
            return programRepository.save(program);
        }
    }

    public List<Program> findAll() {
        return new ArrayList<>((Collection<? extends Program>) programRepository.findAll());
    }

    public Program findById(String name) throws ResourceDoesNotExistException {
        Optional<Program> optionalProgram = programRepository.findById(name);
        if (optionalProgram.isPresent()) {
            return optionalProgram.get();
        } else throw new ResourceDoesNotExistException(name);
    }

    public Program updateProgram(String name, Program program) throws ResourceDoesNotExistException {
        Optional<Program> optionalProgram = programRepository.findById(name);
        if (optionalProgram.isPresent()) {
            program.setName(name);
            return programRepository.save(program);
        } else {
            throw new ResourceDoesNotExistException(name);
        }
    }

    public boolean deleteById(String name) throws ResourceDoesNotExistException {
        Optional<Program> optionalProgram = programRepository.findById(name);
        optionalProgram.ifPresent(program -> programRepository.deleteById(name));
        optionalProgram.orElseThrow(() -> new ResourceDoesNotExistException(name));
        return true;
    }

    public void deleteALL() {
        programRepository.deleteAll();
    }
}
