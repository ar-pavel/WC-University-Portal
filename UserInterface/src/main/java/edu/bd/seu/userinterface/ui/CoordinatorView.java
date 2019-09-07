package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import edu.bd.seu.userinterface.model.Course;
import edu.bd.seu.userinterface.model.Student;
import edu.bd.seu.userinterface.service.ProgramService;
import edu.bd.seu.userinterface.service.StudentService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vaadin.flow.component.icon.VaadinIcon.DASHBOARD;

@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@Route("coordinator")
public class CoordinatorView extends AppLayout {
    private StudentService studentService;
    private ProgramService programService;

    private Grid<Course> courseGrid;
    private Grid<Course> offeredCourseGrid;
    private Grid<Student> studentGrid;
    private Map<Tab, Component> tab2Workspace;
    private Tabs tabs;
    private Dialog dialog;
    private List<Student> students;
    private List<Course> courses;
    private Student student;



    public CoordinatorView(HttpSession httpSession, StudentService studentService, ProgramService programService) {
        this.studentService = studentService;
        this.programService = programService;
        init();

        Header header = new Header(httpSession);
        Footer footer = new Footer();

        tabs = new Tabs(dashBoard(), user(), logout());
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addSelectedChangeListener(event -> {
            final Tab selectedTab = event.getSelectedTab();
            final Component component = tab2Workspace.get(selectedTab);
            setContent(component);
        });

        addToNavbar(new DrawerToggle());
        addToDrawer(header,tabs,footer);
        setContent(tab2Workspace.get(tabs.getSelectedTab()));

        studentGrid.addItemClickListener(studentItemClickEvent -> {
            Student student = new Student();
            courseGrid.setItems();
//            List<Course> courseList = studentService.getStudent(studentItemClickEvent.getItem().getId()).getCourseList();
            student = studentItemClickEvent.getItem();
            if (student.getCourseList() == null) {
                courseGrid.setItems();
            } else {
                courseGrid.setItems(student.getCourseList());
            }
            offeredCourseGrid.setItems(programService.getProgram(student.getProgram()).getCourseList());
        });
        offeredCourseGrid.addItemClickListener(courseItemClickEvent -> {
            Dialog dialog = new Dialog();
            H3 warning = new H3("Do you want to add this course? ");
            Button confirmButton = new Button("Confirm", event -> {

//                Course eventItem = courseItemClickEvent.getItem();
//                offeredCourseGrid.

                Notification.show("Confirmed!");
                dialog.close();
            });
            confirmButton.addThemeNames(Lumo.DARK);
            Button cancelButton = new Button("Cancel", event -> {
                Notification.show("Cancelled!");
                dialog.close();
            });
            cancelButton.addThemeNames(Material.LIGHT);
            dialog.add(warning,confirmButton,cancelButton);
            dialog.open();
        });

    }

    private void init() {
        dialog = new Dialog();
        tab2Workspace = new HashMap<>();
        courseGrid = new Grid<>();
        offeredCourseGrid = new Grid<>();
    }

    private Tab dashBoard() {
        Span label = new Span("Dashboard");
        Icon icon  = DASHBOARD.create();
        Tab  tab   = new Tab(new HorizontalLayout(icon,label));
        tab2Workspace.put(tab, dashboardView());
        return tab;
    }

    private VerticalLayout dashboardView() {
        VerticalLayout dashLayout = new VerticalLayout();
        H1 h1 = new H1("Student List");
        dashLayout.add(h1);
        Button addCourse = new Button("Add Course", VaadinIcon.FILE_ADD.create());
        addCourse.getStyle().set("float", "right");
        setStudentGrid();
        setCourseGrid();
        setOfferedCourseGrid();
        VerticalLayout left = new VerticalLayout();
        VerticalLayout right = new VerticalLayout();
        H4 leftText = new H4("Course Passed");
        H4 rightText = new H4("Course Offered");
        left.add(leftText, courseGrid);
        right.add(rightText,offeredCourseGrid);
        HorizontalLayout twoMid = new HorizontalLayout();
        twoMid.setWidthFull();
        twoMid.add(left,right);
        addCourse.addClickListener(buttonClickEvent ->dialog.open() );
        dashLayout.add( studentGrid, twoMid);
        return dashLayout;
    }

    private void setOfferedCourseGrid() {
        offeredCourseGrid
                .addColumn(Course::getCode)
                .setWidth("150px")
                .setFlexGrow(0)
                .setHeader("Course CODE");
        offeredCourseGrid
                .addColumn(Course::getTitle)
                .setWidth("250px")
                .setFlexGrow(0)
                .setHeader("Course Title");
        offeredCourseGrid
                .addColumn(Course::getCredit)
                .setWidth("150px")
                .setFlexGrow(0)
                .setHeader("Credit Hours");
        offeredCourseGrid.addThemeVariants( GridVariant.LUMO_ROW_STRIPES);

    }

    private Tab user() {
        Span label = new Span("Profile");
        Icon icon  = VaadinIcon.USER.create();
        Tab  tab   = new Tab(new HorizontalLayout(icon,label));
        tab2Workspace.put(tab, getProfileView());
        return tab;
    }

    private VerticalLayout getProfileView(){
        VerticalLayout profilePage = new VerticalLayout();
        Image image = new Image("https://pmcvariety.files.wordpress.com/2019/04/joker-trailer.jpg?w=1000","joker");
        image.setHeight("300px");
        profilePage.add(image);
        return profilePage;
    }

    private void setStudentGrid(){
        studentGrid = new Grid<>();

        studentGrid
                .addColumn(Student::getId)
                .setWidth("50px")
                .setFlexGrow(1)
                .setHeader("Student ID");
        studentGrid
                .addColumn(Student::getName)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setHeader("Name");
        studentGrid
                .addColumn(Student::getCgpa)
                .setWidth("50px")
                .setFlexGrow(1)
                .setHeader("Cgpa");
        studentGrid
                .addColumn(Student::getBatch)
                .setWidth("50px")
                .setFlexGrow(1)
                .setHeader("Batch");
//        studentGrid
//                .addComponentColumn(student -> getAddCourseColumn())
//                .setWidth("50px")
//                .setFlexGrow(1);
        studentGrid.addThemeVariants(
                GridVariant.LUMO_ROW_STRIPES);
        studentGrid.setItems(studentService.getStudents());
    }

    public void setCourseGrid(){
        courseGrid
                .addColumn(Course::getCode)
                .setWidth("150px")
                .setFlexGrow(0)
                .setHeader("Course CODE");
        courseGrid
                .addColumn(Course::getTitle)
                .setWidth("250px")
                .setFlexGrow(0)
                .setHeader("Course Title");
        courseGrid
                .addColumn(Course::getCredit)
                .setWidth("150px")
                .setFlexGrow(0)
                .setHeader("Credit Hours");
        courseGrid.addThemeVariants(
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
//        courseGrid.setItems(programService.getPrograms(s));
    }

    private Tab logout() {
        Span label = new Span("LogOut");
        Button logout = new Button("LogOut",VaadinIcon.SIGN_OUT.create());
        logout.addClickListener(buttonClickEvent -> logout.getUI().ifPresent(ui -> ui.navigate("")));
        Tab tab = new Tab(logout);
        tab2Workspace.put(tab, new VerticalLayout());
        return tab;
    }

    private Component getAddCourseColumn() {
        return new Button("Add Course", VaadinIcon.FILE_ADD.create());
    }
}

