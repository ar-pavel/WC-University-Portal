package edu.bd.seu.userinterface.service;

import edu.bd.seu.userinterface.model.Course;
import edu.bd.seu.userinterface.model.Program;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramServiceTest {
    @Autowired
    ProgramService programService;

    @Test
    public void insertProgram() {
        Program program = new Program("ABC", 150, 3.5, null);
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

    @Test
    public void updateProgram() {
        Program program = new Program("ABC", 150, 3.5, null);
        List<Course> courses = new ArrayList<>();
        program.setCourseList(courses);
        programService.updateProgram(program);
    }
}