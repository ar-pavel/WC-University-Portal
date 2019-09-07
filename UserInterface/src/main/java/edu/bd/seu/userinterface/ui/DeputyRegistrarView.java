package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import edu.bd.seu.userinterface.model.Course;
import edu.bd.seu.userinterface.model.Program;
import edu.bd.seu.userinterface.service.ProgramService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vaadin.flow.component.icon.VaadinIcon.DASHBOARD;


@Route("deputy-registrar")
public class DeputyRegistrarView extends AppLayout {

    private Map<Tab, Component> tab2Workspace;
    private Tabs tabs;
    private H4 programName;

    private Dialog dialog;
    private Grid<Program> programGrid;
    private Grid<Course> offeredCourseGrid;
    private Binder<Program> programBinder;

    private List<Program> programs;
    private String progName;
    private ProgramService programService;
    private Dialog courseDialog;
    private Binder<Course> courseBinder;
    private Program program;

    public DeputyRegistrarView(ProgramService programService) {
        this.programService = programService;
        Header header = new Header();
        Footer footer = new Footer();
        addToNavbar(new DrawerToggle());
        tab2Workspace = new HashMap<>();
        dialog = new Dialog();
        courseDialog = new Dialog();
        programGrid = new Grid<>();
        programBinder = new Binder<>();
        courseBinder = new Binder<>();
        offeredCourseGrid = new Grid<>();
        programName = new H4();
        program = new Program();
        programs = programService.getPrograms();

        tabs = new Tabs(dashBoard(), user(), logout());
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addSelectedChangeListener(event -> {
            final Tab selectedTab = event.getSelectedTab();
            final Component component = tab2Workspace.get(selectedTab);
            setContent(component);
        });
        addToDrawer(header,tabs,footer);
        setContent(tab2Workspace.get(tabs.getSelectedTab()));

        programGrid.addItemClickListener(programItemClickEvent -> {
            List<Course> courses = programService.getProgram(programItemClickEvent.getItem().getName()).getCourseList();
            progName = programItemClickEvent.getItem().getName();
            program = programItemClickEvent.getItem();
            System.err.println(program);
            programName.setText("by Department of " +progName);
            if (courses == null) {
                offeredCourseGrid.setItems();
            } else {
                offeredCourseGrid.setItems(courses);
            }
            System.err.println(program);

        });

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
        H3 h1 = new H3("Programs Offered by WC University");
        dashLayout.add(h1);

        Button addCourse = new Button("Add Course", VaadinIcon.FILE_ADD.create());

        addCourse.addClickListener(buttonClickEvent ->courseDialog.open() );

        Button addProgram = new Button("Add Program", VaadinIcon.PLUS_CIRCLE_O.create());

        addProgram.getStyle().set("float", "right");

        setOfferedCourseGrid();
        setProgramGrid();
        addProgram.addClickListener(buttonClickEvent ->dialog.open() );
        dashLayout.add(addProgram, programGrid);

        HorizontalLayout title = new HorizontalLayout();
        H4 rightText = new H4("Courses Offered");
        Div blank = new Div();
        title.setWidthFull();
        title.add(rightText, programName, blank);

        dashLayout.add(title,offeredCourseGrid,addCourse);
        createProgram();
        createCourse();
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
        offeredCourseGrid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

    }

    private void createProgram() {
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Confirm", event -> {
            Notification.show("Confirmed!");
//            List<Course> courses = new ArrayList<>();
//            Program program = new Program("0000000000000", "Demo", "demo@seu.edu.bd", LocalDate.of(1996, 10, 12), 43, LocalDate.now(), "BScInCSE", 0.0, 0, courses);
            Program program = new Program();
            try {
                programBinder.writeBean(program);
                Notification.show(program.toString());
                Program savedProgram = programService.insertProgram(program);
                programGrid.setItems(programService.getPrograms());
                Notification.show("Saved " + savedProgram.getName());
                dialog.close();
            }catch (HttpClientErrorException.Conflict errorException) {
//                    programService.updateProgram()
            }catch (ValidationException e) {
//                System.err.println("*Days " + DAYS.between(LocalDate.now(), program.getDob()));
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


        TextField nameField = new TextField("Program Name", "BSc in CSE, BBA, BA Architect");
        TextField minCreditField = new TextField("Minimum Credit Requirement", "Required Credit");
        TextField minCgpaField = new TextField("Minimum Cgpa Requirement", "Required Cgpa");

        Select<String> select = new Select<>();
        select.setLabel("Designation");
        select.setItems("DeputyRegister(Academic)", "DeputyRegister(HumanResource)", "Coordinator", "Officer(Admission)", "Officer(Examination)");


        FormLayout formLayout = new FormLayout();
        formLayout.add(nameField, minCreditField, minCgpaField);

        dialog.add(formLayout,confirmButton,cancelButton);

        programBinder
                .forField(minCreditField)
                .asRequired()
                .withConverter(new StringToDoubleConverter("Must be a number/double value"))
                .bind(Program::getMinCreditReq, Program::setMinCreditReq);
        programBinder
                .forField(minCgpaField)
                .asRequired()
                .withConverter(new StringToDoubleConverter("Must be a number/double value"))
                .bind(Program::getMinCgpaReq, Program::setMinCgpaReq);
        programBinder
                .forField(nameField)
                .asRequired()
                .withValidator(name -> name.length() >= 2, "Program name should be at least 3 letters long")
                .withValidator(name -> name.length() <= 20, "Program name cannot be more than 20 letters long")
                .withValidator(name -> !name.contains("_"), "Program names cannot have underscores")
                .bind(Program::getName, Program::setName);  programBinder
                .forField(nameField)
                .asRequired()
                .withValidator(name -> name.length() >= 2, "Program name should be at least 3 letters long")
                .withValidator(name -> name.length() <= 20, "Program name cannot be more than 20 letters long")
                .withValidator(name -> !name.contains("_"), "Program names cannot have underscores")
                .bind(Program::getName, Program::setName);
//        programBinder
//                .forField(select)
//                .asRequired()
//                .bind(Program::getRole, Program::setRole);
//        programBinder
//                .forField(emailField)
//                .asRequired()
//                .withValidator(new EmailValidator("This doesn't look like a valid email address"))
//                .bind(Program::getEmail, Program::setEmail);


    }

    private void createCourse() {
        courseDialog.setCloseOnEsc(false);
        courseDialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Confirm", event -> {
            Notification.show("Confirmed!");
            Course course = new Course();

            try {
                courseBinder.writeBean(course);
                Notification.show(course.toString());
                program = programService.getProgram(progName);
                List<Course> courseList = new ArrayList<>();
                if(program.getCourseList()!=null)
                    courseList.addAll(program.getCourseList());
                courseList.add(course);
                program.setCourseList(courseList);

                System.err.println(program);
                System.err.println(course);

                programService.updateProgram(program);

                offeredCourseGrid.setItems(program.getCourseList());
                programGrid.setItems(programService.getPrograms());
                Notification.show("Saved " + course.getTitle());
                courseDialog.close();

            }catch (ValidationException e) {
                Notification.show(e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                Notification.show(e.getMessage());
            }
            courseBinder.readBean(new Course());
        });

        confirmButton.addThemeNames(Lumo.DARK);
        Button cancelButton = new Button("Cancel", event -> {
            Notification.show("Cancelled!");
            courseDialog.close();
        });
        cancelButton.addThemeNames(Material.LIGHT);


        TextField codeField = new TextField("Course Code", "i.e. CSE1011");
        TextField titleField = new TextField("Course Title", "i.e. Computer Fundamental");
/*
private String code;
    private String title;
    private Double credit;
 */

        Select<Double> select = new Select<>();
        select.setLabel("Credit Hour");
        select.setItems(1.0,3.0);


        FormLayout formLayout = new FormLayout();
        formLayout.add(codeField,titleField,select);

        courseDialog.add(formLayout,confirmButton,cancelButton);

        courseBinder
                .forField(codeField)
                .asRequired()
                .withValidator(s -> s.length() ==7, "Code must be 7 digits long")
                .bind(Course::getCode, Course::setCode);
        courseBinder
                .forField(titleField)
                .asRequired()
                .bind(Course::getTitle, Course::setTitle);
        courseBinder
                .forField(select)
                .asRequired()
                .bind(Course::getCredit, Course::setCredit);


    }


    private void setProgramGrid() {
        programGrid
                .addColumn(Program::getName)
                .setWidth("300px")
                .setFlexGrow(1)
                .setHeader("Program Name");
        programGrid
                .addColumn(Program::getMinCreditReq)
                .setFlexGrow(1)
                .setHeader("Minimum Credit Requirement");

        programGrid
                .addColumn(Program::getMinCgpaReq)
                .setFlexGrow(1)
                .setHeader("Minimum Cgpa Requirement");

//        programGrid
//                .addComponentColumn(this::getEditColumn)
//                .setWidth("60px")
//                .setFlexGrow(0);

        programGrid.setItems(programs);
        programGrid.addThemeVariants(
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
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
}
