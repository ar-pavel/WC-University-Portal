package bd.edu.seu.programapi.service;

import bd.edu.seu.programapi.model.Course;
import bd.edu.seu.programapi.model.Program;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProgramServiceTest {
    private ProgramService programService;

    public ProgramServiceTest(ProgramService programService) {
        this.programService = programService;
    }

    @Test
    public void insertProgram() {

    }

    @Test
    public void findAll() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void updateProgram() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void deleteALL() {
        programService.deleteALL();
        List<Program> all = programService.findAll();
        assertEquals(all.size(), 0);
    }
}