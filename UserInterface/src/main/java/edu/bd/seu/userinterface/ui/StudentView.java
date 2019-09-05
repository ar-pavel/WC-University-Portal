package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import edu.bd.seu.userinterface.model.Course;
import edu.bd.seu.userinterface.model.Student;
import edu.bd.seu.userinterface.model.StudentGuest;
import edu.bd.seu.userinterface.service.StudentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vaadin.flow.component.icon.VaadinIcon.DASHBOARD;

@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@Route("student")
public class StudentView  extends AppLayout {

    private Grid<StudentGuest> studentGrid;
    private Grid<Course> courseGrid;

    private Student student;
    private StudentService studentService;

    private Map<Tab, Component> tab2Workspace;
    private Tabs tabs;

    public StudentView() {

        studentMaker();

        studentGrid = new Grid<>();
        tab2Workspace = new HashMap<>();
        courseGrid = new Grid<>();

        Header header = new Header();
//        header.getElement().getAttribute()
        Footer footer = new Footer();
        addToNavbar(new DrawerToggle());

//        Student serviceStudent = studentService.getStudent("2016100000003");
//        System.err.println(serviceStudent);

        tabs = new Tabs(dashBoard(), user(), convocation(),logout());
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addSelectedChangeListener(event -> {
            final Tab selectedTab = event.getSelectedTab();
            final Component component = tab2Workspace.get(selectedTab);
            setContent(component);
        });
        addToDrawer(header,tabs,footer);
    }

    private Tab dashBoard() {
        Span label = new Span("Dashboard");
        Icon icon  = DASHBOARD.create();
        Tab  tab   = new Tab(new HorizontalLayout(icon,label));
        tab2Workspace.put(tab, deshboardView());
        return tab;
    }

    private VerticalLayout deshboardView() {
        VerticalLayout dashbaLayout = new VerticalLayout();
        H1 h1 = new H1("This is the Dashboard & IDK what to do with it! -_- ");
        dashbaLayout.add(h1);
        return dashbaLayout;
    }

    private Tab user() {
        Span label = new Span("Proile");
        Icon icon  = VaadinIcon.USER.create();
        Tab  tab   = new Tab(new HorizontalLayout(icon,label));
        tab2Workspace.put(tab, getProfileView());
        return tab;
    }

    private VerticalLayout getProfileView(){
        VerticalLayout profilePage = new VerticalLayout();
        Label name = new Label("Name : " + student.getName());
        Label id = new Label("Student ID : " + student.getId());
        Label dob = new Label("Date Of Birth : " + student.getDob().toString());
        Label creditCom = new Label("Credit Completed : " + student.getCompletedCredit());
        Label cgpa = new Label("CGPA : " + student.getCgpa());

        FormLayout layout = new FormLayout();
        layout.add(name,id,dob,cgpa,creditCom);
        setCourseGrid();
        profilePage.add(layout,courseGrid);
        return profilePage;
    }

    private Tab convocation() {
        Span label = new Span("Convocation");
        Icon icon  = DASHBOARD.create();
        Tab  tab   = new Tab(new HorizontalLayout(icon,label));
        tab2Workspace.put(tab, convocationdView());
        return tab;
    }

    private VerticalLayout convocationdView() {
        VerticalLayout convocationLayout = new VerticalLayout();

        Dialog addMem = addMember();
        Dialog makePaym = makePayment();

        Button ConfirmPayment = new Button("Confirm Payment", VaadinIcon.MONEY_DEPOSIT.create());
        ConfirmPayment.addClickListener(buttonClickEvent -> makePaym.open());

        Button addMember = new Button("Add Member", VaadinIcon.USERS.create());
        addMember.addClickListener(buttonClickEvent -> addMem.open());
        addMember.getStyle().set("float", "right");

        setStudentGrid();
        convocationLayout.add(ConfirmPayment,addMember,studentGrid);

        return convocationLayout;
    }

    private Tab logout() {
        Span label = new Span("LogOut");
        Icon icon  = DASHBOARD.create();
        Button logout = new Button("LogOut",VaadinIcon.SIGN_OUT.create());
        logout.addClickListener(buttonClickEvent -> logout.getUI().ifPresent(ui -> ui.navigate("")));
        Tab tab = new Tab(logout);

        tab2Workspace.put(tab, new VerticalLayout());
        return tab;
    }

    private Dialog makePayment(){
        FormLayout registration = new FormLayout();
        TextField Name = new TextField("Bkash Ac. Name", "01XXXXXXXXXX");
        TextField trID = new TextField("Transaction ID", "BKash transaction ID");
        registration.add(Name,trID);
        Dialog dialog = new Dialog();

        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Label messageLabel = new Label();

        Button confirmButton = new Button("Confirm", event -> {
            Notification.show("Confirmed!");
            dialog.close();
        });
        confirmButton.addThemeNames(Lumo.DARK);
        Button cancelButton = new Button("Cancel", event -> {
            Notification.show("Cancelled!");
            dialog.close();
        });
        cancelButton.addThemeNames(Material.LIGHT);

        dialog.add(registration,confirmButton, cancelButton);
        return dialog;
    }

    private Dialog addMember(){

        FormLayout registration = new FormLayout();
        TextField Name = new TextField("Name", "name ");
        TextField Gender = new TextField("Gender", "Male/Female");
        TextField Relation = new TextField("Relation", "relation");
        registration.add(Name,Gender,Relation);
        Dialog dialog = new Dialog();

        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Confirm", event -> {
            Notification.show("Confirmed!");
            dialog.close();
        });
        confirmButton.addThemeNames(Lumo.DARK);
        Button cancelButton = new Button("Cancel", event -> {
            Notification.show("Cancelled!");
            dialog.close();
        });
        cancelButton.addThemeNames(Material.LIGHT);

        dialog.add(registration,confirmButton, cancelButton);
        return dialog;
    }

    private void setStudentGrid(){
        studentGrid
                .addColumn(StudentGuest::getName)
                .setWidth("300px")
                .setFlexGrow(0)
                .setHeader("Guest Name");
        studentGrid
                .addColumn(StudentGuest::getGender)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setHeader("Guest Gender");
        studentGrid
                .addColumn(StudentGuest::getRelation)
                .setFlexGrow(1)
                .setHeader("Relationship");

    }

    private void setCourseGrid(){
        courseGrid.setRowsDraggable(true);
        courseGrid
                .addColumn(Course::getCode)
                .setFlexGrow(1)
                .setHeader("Course Code");
        courseGrid
                .addColumn(Course::getTitle)
                .setFlexGrow(1)
                .setHeader("Course Title");
        courseGrid
                .addColumn(Course::getCredit)
                .setFlexGrow(1)
                .setHeader("Credit");

        courseGrid.setItems(student.getCourseList());
        System.err.println(student.getCourseList());
    }

    private void studentMaker(){
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("CSE1011","Programming Language I (C)",3.0));
        courses.add(new Course("CSE1012","Programming Language I (C) lab",1.0));
        courses.add(new Course("CSE1013","Computer Fundamentals",3.0));
        courses.add(new Course("CSE1021","Discrete Mathematics",3.0));
        courses.add(new Course("CSE1033","Data Structure",3.0));
        courses.add(new Course("CSE1034","Data Structure Lab",1.0));
        courses.add(new Course("CSE2013","Digital Logic Design",3.0));
        courses.add(new Course("CSE2014","Digital Logic Design Lab",1.0));

        student = new Student("2016100000003", "Tanvir Ahmed", "2016100000003.seu.edu.bd", LocalDate.of(1996, 10, 12), 43, LocalDate.of(2016,04,10),"BScInCSE", 3.94, 112, courses);

    }

}
