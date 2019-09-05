package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import edu.bd.seu.userinterface.model.Student;
import edu.bd.seu.userinterface.service.StudentService;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vaadin.flow.component.icon.VaadinIcon.DASHBOARD;
import static java.time.temporal.ChronoUnit.DAYS;

@Route("admission-officer")
public class AdmissionOfficer extends AppLayout {
    private Map<Tab, Component> tab2Workspace;
    private Tabs tabs;
    private Grid<Student> studentGrid;
    private Dialog dialog;

    private StudentService studentService;
    private List<Student> students;
    private Binder<Student> studentBinder;

    public AdmissionOfficer(StudentService studentService) {
        this.studentService = studentService;
        students = studentService.getStudents();
        studentBinder = new Binder<>();
        dialog = new Dialog();
        studentBinder = new Binder<>();


        studentGrid = new Grid<>();
        Header header = new Header();
        Footer footer = new Footer();
        addToNavbar(new DrawerToggle());
        tab2Workspace = new HashMap<>();

        tabs = new Tabs(dashBoard(), user(), logout());

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
        H1 h1 = new H1("This is the Dashboard & IDK what to do with this! -_- ");
        dashbaLayout.add(h1);
        Button addStudent = new Button("Add Student", VaadinIcon.PLUS_CIRCLE_O.create());
        addStudent.getStyle().set("float", "right");
        setStudentGrid();
        addStudent.addClickListener(buttonClickEvent ->dialog.open() );
        dashbaLayout.add(addStudent, studentGrid);
        createStudent();
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
        Image image = new Image("https://pmcvariety.files.wordpress.com/2019/04/joker-trailer.jpg?w=1000","joker");
        image.setHeight("300px");
        profilePage.add(image);
        return profilePage;
    }

    private Tab logout() {
        Span label = new Span("LogOut");
        Button logout = new Button("LogOut",VaadinIcon.SIGN_OUT.create());
        logout.addClickListener(buttonClickEvent -> logout.getUI().ifPresent(ui -> ui.navigate("")));
        Tab tab = new Tab(logout);
        tab2Workspace.put(tab, new VerticalLayout());
        return tab;
    }

    private void setStudentGrid(){

        studentGrid
                .addColumn(Student::getId)
                .setWidth("300px")
                .setFlexGrow(1)
                .setHeader("Student ID");
        studentGrid
                .addColumn(Student::getName)
                .setWidth("300px")
                .setFlexGrow(1)
                .setHeader("Name");
        studentGrid
                .addColumn(Student::getBatch)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setHeader("Batch");
        studentGrid
                .addColumn(Student::getProgram)
                .setFlexGrow(1)
                .setHeader("Program");
        studentGrid
                .addColumn(Student::getDob)
                .setFlexGrow(1)
                .setHeader("Date of Birth");
        studentGrid
                .addColumn(Student::getDateOfAdmission)
                .setFlexGrow(1)
                .setHeader("Admission Date");
//        studentGrid
//                .addComponentColumn(this::getEditColumn)
//                .setWidth("60px")
//                .setFlexGrow(0);

        studentGrid.setItems(students);

    }

    private void createStudent(){
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Confirm", event -> {
            Notification.show("Confirmed!");
//            List<Course> courses = new ArrayList<>();
//            Student student = new Student("0000000000000", "Demo", "demo@seu.edu.bd", LocalDate.of(1996, 10, 12), 43, LocalDate.now(), "BScInCSE", 0.0, 0, courses);
            Student student = new Student();
            try {
                studentBinder.writeBean(student);
                Notification.show(student.toString());
                Student savedStudent = studentService.insertStudent(student);
                studentGrid.setItems(studentService.getStudents());
                Notification.show("Saved " + savedStudent.getName());
                dialog.close();
            }catch (HttpClientErrorException.Conflict errorException) {
//                    studentService.updateStudent()
            }catch (ValidationException e) {
//                System.err.println("*Days " + DAYS.between(LocalDate.now(), student.getDob()));
                Notification.show(e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                Notification.show(e.getMessage());
            }

        });

        confirmButton.addThemeNames(Lumo.DARK);
        Button cancelButton = new Button("Cancel", event -> {
            Notification.show("Cancelled!");
            dialog.close();
        });
        cancelButton.addThemeNames(Material.LIGHT);


        TextField idField = new TextField("Student ID", "13 digit ID");
        TextField nameField = new TextField("Student Name", "Full name");
        TextField emailField = new TextField("Student Email", "email");
        TextField programField = new TextField("Program Name", "BScInCSE,BScInEE,..");
        TextField batchField = new TextField("Batch", "Current Batch No.");
        DatePicker datePicker = new DatePicker("Date of Birth");
        DatePicker admissionDatePicker = new DatePicker("Date of Admission",LocalDate.now());

        FormLayout formLayout = new FormLayout();
        formLayout.add(idField, nameField, emailField,datePicker,programField,admissionDatePicker,batchField);
        dialog.add(formLayout,confirmButton,cancelButton);

        studentBinder
                .forField(idField)
                .asRequired()
                .withValidator(id -> id.length() == 13, "ID must be 13 digit number only")
//                .withValidator(id -> Pattern.matches("\\d{4}-\\d{4}", id), "ID must contain number only")
                .bind(Student::getId, Student::setId);
        studentBinder
                .forField(nameField)
                .asRequired()
                .withValidator(name -> name.length() >= 3, "Names should be at least 3 letters long")
                .withValidator(name -> name.length() <= 20, "Names cannot be more than 20 letters long")
                .withValidator(name -> !name.contains("_"), "Names cannot have underscores")
                .bind(Student::getName, Student::setName);
        studentBinder
                .forField(datePicker)
                .asRequired()
                .withValidator(dob -> DAYS.between(dob, LocalDate.now()) > 16 * 365, "Students should be at least 16 years old!")
                .bind(Student::getDob, Student::setDob);
        studentBinder
                .forField(emailField)
                .asRequired()
                .withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .bind(Student::getEmail, Student::setEmail);
        studentBinder
                .forField(batchField)
                .asRequired()
                .withValidator(s -> s.length() <= 2, "Input valid semester ID if you love ur job")
                .withConverter(new StringToLongConverter("Batch must be a number"))
                .bind(Student::getBatch, Student::setBatch);
        studentBinder
                .forField(programField)
                .asRequired()
                .bind(Student::getProgram, Student::setProgram);;


    }

    private Component getEditColumn(Student student) {
        Button button = new Button();
        button.setIcon(VaadinIcon.EDIT.create());
        button.addClickListener(event -> {
            studentBinder.readBean(student);
            dialog.open();
        });
        return button;
    }


}
