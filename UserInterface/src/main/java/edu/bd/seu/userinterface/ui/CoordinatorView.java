package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import edu.bd.seu.userinterface.model.Course;
import edu.bd.seu.userinterface.model.Student;
import edu.bd.seu.userinterface.service.StudentService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@Route("coordinator")
public class CoordinatorView extends VerticalLayout {
    private StudentService studentService;

    private Grid<Course> courseGrid;
    private Grid<Student> studentGrid;



    public CoordinatorView(StudentService studentService) {
        this.studentService = studentService;

        Tab dashboard = new Tab(new HorizontalLayout(VaadinIcon.DASHBOARD.create(), new Label("Dashboard")));
        Tab profile = new Tab(new HorizontalLayout(VaadinIcon.USER.create(), new Label("Profile")));
        Tab course = new Tab(new HorizontalLayout(VaadinIcon.BOOK.create(), new Label("Courses")));
        Tab student = new Tab(new HorizontalLayout(VaadinIcon.USERS.create(), new Label("Students")));
        Button logout = new Button("LogOut",VaadinIcon.SIGN_OUT.create());
        Tab logOut = new Tab(logout);

        VerticalLayout dashboardPage = new VerticalLayout();
        VerticalLayout profilePage = new VerticalLayout();
        VerticalLayout coursePage = new VerticalLayout();
        VerticalLayout studentPage = setStudentPage();

        profilePage.getStyle().set("margin-left", "270px");
        dashboardPage.getStyle().set("margin-left", "270px");
        studentPage.getStyle().set("margin-left", "270px");
        coursePage.getStyle().set("margin-left", "270px");

        Image image = new Image("https://pmcvariety.files.wordpress.com/2019/04/joker-trailer.jpg?w=1000","joker");
        profilePage.add(image);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(dashboard, dashboardPage);
        tabsToPages.put(profile, profilePage);
        tabsToPages.put(course, coursePage);
        tabsToPages.put(student, studentPage);

        Tabs tabs = new Tabs();
        tabs.add(dashboard,profile,course,student,logOut);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        Div pages = new Div(dashboardPage, profilePage, coursePage, studentPage);
        logout.addClickListener(buttonClickEvent -> {
            Notification.show("You are loggedOut");
            logout.getUI().ifPresent(ui -> ui.navigate(""));
        });

        Set<Component> pagesShown = Stream.of(dashboardPage)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        Header header = new Header();
        AppLayout mainLayout = new AppLayout();
        mainLayout.addToNavbar(new DrawerToggle());

        Footer footer = new Footer();
        mainLayout.addToDrawer(header,tabs,footer);

        dashboardPage.setVisible(true);
        profilePage.setVisible(false);
        coursePage.setVisible(false);
        studentPage.setVisible(false);

        add(mainLayout,dashboardPage,profilePage,coursePage,studentPage);
        getStyle().set("display", "block");

    }

    private VerticalLayout setStudentPage(){
        VerticalLayout verticalLayout = new VerticalLayout();
        studentGrid = new Grid<>();
        setStudentGrid();
        verticalLayout.add(studentGrid);
        return verticalLayout;
    }
    private VerticalLayout setCoursePage(){
        VerticalLayout verticalLayout = new VerticalLayout();
        courseGrid = new Grid<>();
        verticalLayout.add(courseGrid);
        return verticalLayout;
    }
    private VerticalLayout setDashboardPage(){
        VerticalLayout verticalLayout = new VerticalLayout();
        return verticalLayout;
    }

    public void setCourseGrid(){

        courseGrid
                .addColumn(Course::getCode)
                .setWidth("150px")
                .setFlexGrow(0)
                .setHeader("Course CODE");
        courseGrid
                .addColumn(Course::getTitle)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setHeader("Course Title");
        courseGrid
                .addColumn(Course::getCredit)
                .setFlexGrow(1)
                .setHeader("Date of Birth");


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
                .setHeader("Student Name");
        studentGrid
                .addColumn(Student::getDob)
                .setWidth("50px")
                .setFlexGrow(1)
                .setHeader("Date of Birth");
        studentGrid
                .addColumn(Student::getBatch)
                .setWidth("50px")
                .setFlexGrow(1)
                .setHeader("Batch");
        studentGrid
                .addComponentColumn(student -> getAddCourseColumn())
                .setWidth("50px")
                .setFlexGrow(1);

        studentGrid.setItems(studentService.getStudents());
    }

    private Component getAddCourseColumn() {
        return new Button("Add Course", VaadinIcon.FILE_ADD.create());
    }
}

