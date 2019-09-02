package bd.edu.seu.programapi.controller;

import bd.edu.seu.programapi.exception.ResourceAlreadyExistsException;
import bd.edu.seu.programapi.exception.ResourceDoesNotExistException;
import bd.edu.seu.programapi.model.Program;
import bd.edu.seu.programapi.service.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/programs")
public class ProgramController {
    private ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Program> insertProgram(@RequestBody Program program) {
        try {
            Program insertedProgram = programService.insertProgram(program);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedProgram);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/{code}")
    public ResponseEntity<Program> getProgram(@PathVariable String code) {
        try {
            Program program = programService.findById(code);
            return ResponseEntity.ok(program);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Program>> getProgram() {
        return ResponseEntity.ok().body(programService.findAll());
    }

    @PutMapping(value = "")
    public ResponseEntity<Program> updateProgram(@RequestBody String code, @RequestBody Program program) {
        try {
            Program updateProgram = programService.updateProgram(code, program);
            return ResponseEntity.ok().body(program);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<String> deleteProgram(@PathVariable String code) {
        try {
            boolean deleted = programService.deleteById(code);
            return ResponseEntity.ok(code);
        } catch (ResourceDoesNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
