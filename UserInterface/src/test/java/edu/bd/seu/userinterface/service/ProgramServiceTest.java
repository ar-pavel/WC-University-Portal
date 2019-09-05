package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Program;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramServiceTest {
    @Autowired
    private ProgramService programService;
    @Test
    public void insertProgram() {
        Program program = new Program();
        Program insertProgram = programService.insertProgram(program);
        System.out.println(insertProgram);
    }

    @Test
    public void getProgram() {
        Program program = programService.getProgram("");
        System.out.println(program);
    }

    @Test
    public void getPrograms() {
        List<Program> programs = programService.getPrograms();
        programs.forEach(System.out::println);
    }
}