package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import edu.bd.seu.userinterface.model.*;
import edu.bd.seu.userinterface.service.ConvocationService;
import edu.bd.seu.userinterface.service.StudentService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vaadin.flow.component.icon.VaadinIcon.DASHBOARD;

@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@Route("student")
public class StudentView  extends AppLayout {

    private Convocation convocation;

    private Grid<StudentGuest> studentGrid;
    private Grid<Course> courseGrid;
    private ConvocationService convocationService;

    private Student student;
    private StudentService studentService;

    private Map<Tab, Component> tab2Workspace;
    private Tabs tabs;

    public StudentView(HttpSession httpSession, StudentService studentService, ConvocationService convocationService) {
        this.convocationService = convocationService;
        this.studentService = studentService;
        studentMaker();
        studentGrid = new Grid<>();
        tab2Workspace = new HashMap<>();
        courseGrid = new Grid<>();
        convocation = new Convocation();


        Header header = new Header(httpSession);
        LoginToken loginToken = header.getLoginToken();
        System.err.println(loginToken);

        Footer footer = new Footer();
        addToNavbar(new DrawerToggle());

//        studentMaker();
         student = studentService.getStudent(loginToken.getUsername());
//        System.err.println(student1);
            if(student.getCourseList()==null) {
                List<Course> courses = new ArrayList<>();
                student.setCourseList(courses);
            }
//        student = studentService.getStudent(loginToken.getUsername());
        convocation = new Convocation();
//        convocation = convocationService.getConvocation(student.getId());
//        Student serviceStudent = studentService.getStudent("2016100000003");
//        System.err.println(serviceStudent);

        tabs = new Tabs(dashBoard(), user(), convocation(),logout());
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            Component component = tab2Workspace.get(selectedTab);
            setContent(component);
        });
        addToDrawer(header,tabs,footer);
        setContent(tab2Workspace.get(tabs.getSelectedTab()));

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
        H1 h1 = new H1("Welcome To WC University Student Portal");
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

        HorizontalLayout up = new HorizontalLayout();
        up.setWidthFull();
        HorizontalLayout down = new HorizontalLayout();
        down.setWidthFull();

        Label as = new Label("Application Status ");
        TextField applicationStatus = new TextField(null,"Not applied");
        applicationStatus.setEnabled(false);

        Label ps = new Label("Payment Status ");
        TextField paymentStatus = new TextField(null,"Not paid");
        paymentStatus.setEnabled(false);

        Dialog makePay = makePayment();

        Button ConfirmPayment = new Button("Confirm Payment", VaadinIcon.MONEY_DEPOSIT.create());
        ConfirmPayment.addClickListener(buttonClickEvent -> makePay.open());
        if(convocation.getStudentId() != null) {
            applicationStatus.setEnabled(false);
            applicationStatus.setValue("Application is on pending");
        }

        if(convocation.getPaymentStatus()!= null) {
            ConfirmPayment.setEnabled(false);
            paymentStatus.setValue("Paid");
            applicationStatus.setValue("Application Confirmed");

        }


//        Button addMember = new Button("Add Member", VaadinIcon.USERS.create());
//        addMember.addClickListener(buttonClickEvent -> addMem.open());
//        addMember.getStyle().set("float", "right");

        Button applyForConvocation = new Button("Apply for convocation", VaadinIcon.DIPLOMA_SCROLL.create());
        applyForConvocation.addClickListener(buttonClickEvent ->{
            H3 label = new H3("You are applying for convocation 2k20 ");
            Dialog dialog = new Dialog();

            dialog.setCloseOnEsc(false);
            dialog.setCloseOnOutsideClick(false);

            Button confirmButton = new Button("Confirm", event -> {
                convocation.setConfirmationStatus("Applied");
                applicationStatus.setValue(convocation.getConfirmationStatus());
                Notification.show("Confirmed!");
                dialog.close();
            });
            confirmButton.addThemeNames(Lumo.DARK);
            Button cancelButton = new Button("Cancel", event -> {
                Notification.show("Cancelled!");
                dialog.close();
            });
            cancelButton.addThemeNames(Material.LIGHT);

            dialog.add(label,confirmButton, cancelButton);
            dialog.open();
        } );
//        setStudentGrid();

        if(student.getCgpa()==null || student.getCgpa()<3.2 || student.getCompletedCredit()<20){
            Notification.show("You are not eligible for Convocation 2K20").setPosition(Notification.Position.BOTTOM_STRETCH);
            applyForConvocation.setEnabled(false);
            ConfirmPayment.setEnabled(false);
        }

        up.add(as,applicationStatus);
        down.add(ps,paymentStatus);
        convocationLayout.add(up,applyForConvocation,down,ConfirmPayment);

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
        Select<String> acocount = new Select<>();
        acocount.setItems("Visa Card", "Nexus Pay", "Rocket", "Bkash");
        acocount.setRequiredIndicatorVisible(true);
        TextField accName = new TextField("Account Holder Name", "Account Holder Name");
        TextField accNo = new TextField("Acc no ", "Card Number/ Acc. Number");
        TextField trID = new TextField("Transaction ID", "Transaction ID");
        registration.add(acocount,accName,accNo,trID);
        Dialog dialog = new Dialog();
        trID.setRequired(true);

        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);


        Button confirmButton = new Button("Confirm", event -> {
            convocation.setPaymentStatus("Confirmed");
            convocation.setId(trID.getValue());
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
