package bd.edu.seu.programapi.service;

import bd.edu.seu.programapi.exception.ResourceAlreadyExistsException;
import bd.edu.seu.programapi.exception.ResourceDoesNotExistException;
import bd.edu.seu.programapi.model.Course;
import bd.edu.seu.programapi.model.Program;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramServiceTest {
    @Autowired
    private ProgramService programService;

//    public ProgramServiceTest(ProgramService programService) {
//        this.programService = programService;
//    }

    /*
    private String name;
    private long minCreditReq;
    private double minCgpaReq;
    @OneToMany
    private List<Course> courseList;
     */


    @Before
    public void setUp() throws Exception {
        programService.deleteALL();
    }

    public Program getProgram() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("CSE1011", "Computer Fundamental", 3.0));
        courseList.add(new Course("CSE1012", "Computer Fundamental Lab", 1.0));

        Program program = new Program("BSc In CSE",144,3.0, courseList);

        return program;
    }

    @Test
    public void insertProgram() throws ResourceAlreadyExistsException {
        Program program = getProgram();
        Program insertProgram = programService.insertProgram(program);
        assertEquals(program,insertProgram);
    }

    @Test
    public void findAll() {
        List<Program> programList = programService.findAll();
        List<Program> programList1 = programService.findAll();
        assertEquals(programList.size(),programList1.size());
    }

    @Test
    public void findById() throws ResourceAlreadyExistsException, ResourceDoesNotExistException {
        Program program = getProgram();
        Program insertProgram = programService.insertProgram(program);
        Program byId = programService.findById(program.getName());
        assertEquals(program,byId);
    }

    @Test
    public void updateProgram() throws ResourceDoesNotExistException, ResourceAlreadyExistsException {
        Program program = getProgram();
//        programService.insertProgram(program);
        Program updatedprogram = program;
//        updatedprogram.setName("BSc In EEE");
//        Program updateProgram = programService.updateProgram(program.getName(), updatedprogram);
        assertEquals(program,updatedprogram);

    }

    @Test
    public void deleteById() {
        Program program = getProgram();
        boolean state = false;
        try {
            Program insertProgram = programService.insertProgram(program);
            state = programService.deleteById(program.getName());
            assertTrue(state);
        } catch (ResourceAlreadyExistsException | ResourceDoesNotExistException e) {
//            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void deleteALL() {
        programService.deleteALL();
        List<Program> all = programService.findAll();
        assertEquals(all.size(), 0);
    }
}