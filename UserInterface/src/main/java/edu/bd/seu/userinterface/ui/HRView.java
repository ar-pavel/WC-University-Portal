package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
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
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import edu.bd.seu.userinterface.model.Employee;
import edu.bd.seu.userinterface.service.EmployeeService;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vaadin.flow.component.icon.VaadinIcon.DASHBOARD;


@Route("HR")
public class HRView extends AppLayout {

    private Map<Tab, Component> tab2Workspace;
    private Tabs tabs;
    private Grid<Employee> employeeGrid;
    private Dialog dialog;

    private EmployeeService employeeService;
    private List<Employee> employees;
    private Binder<Employee> employeeBinder;

    public HRView(EmployeeService employeeService) {
        this.employeeService = employeeService;
        employees = employeeService.getEmployees();
        init();

        Header header = new Header();
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

    }

    private void init() {
        employeeBinder = new Binder<>();
        dialog = new Dialog();
        employeeBinder = new Binder<>();
        tab2Workspace = new HashMap<>();
        employeeGrid = new Grid<>();

    }

    private Tab dashBoard() {
        Span label = new Span("Dashboard");
        Icon icon  = DASHBOARD.create();
        Tab  tab   = new Tab(new HorizontalLayout(icon,label));
        tab2Workspace.put(tab, dashboardView());
        return tab;
    }

    private VerticalLayout dashboardView() {
        VerticalLayout dashbaLayout = new VerticalLayout();
        H1 h1 = new H1("I will add it latter! ");
        dashbaLayout.add(h1);
        Button addEmployee = new Button("Add Employee", VaadinIcon.PLUS_CIRCLE_O.create());
        addEmployee.getStyle().set("float", "right");
        setEmployeeGrid();
        addEmployee.addClickListener(buttonClickEvent ->dialog.open() );
        dashbaLayout.add(addEmployee, employeeGrid);
        createEmployee();
        return dashbaLayout;
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

    private Tab logout() {
        Span label = new Span("LogOut");
        Button logout = new Button("LogOut",VaadinIcon.SIGN_OUT.create());
        logout.addClickListener(buttonClickEvent -> logout.getUI().ifPresent(ui -> ui.navigate("")));
        Tab tab = new Tab(logout);
        tab2Workspace.put(tab, new VerticalLayout());
        return tab;
    }

    private void setEmployeeGrid(){

        employeeGrid
                .addColumn(Employee::getId)
                .setWidth("300px")
                .setFlexGrow(1)
                .setHeader("Employee ID");
        employeeGrid
                .addColumn(Employee::getName)
                .setWidth("300px")
                .setFlexGrow(1)
                .setHeader("Name");

        employeeGrid
                .addColumn(Employee::getRole)
                .setFlexGrow(1)
                .setHeader("Designation");

//        employeeGrid
//                .addComponentColumn(this::getEditColumn)
//                .setWidth("60px")
//                .setFlexGrow(0);

        employeeGrid.setItems(employees);

    }

    private void createEmployee(){
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Confirm", event -> {
            Notification.show("Confirmed!");
//            List<Course> courses = new ArrayList<>();
//            Employee employee = new Employee("0000000000000", "Demo", "demo@seu.edu.bd", LocalDate.of(1996, 10, 12), 43, LocalDate.now(), "BScInCSE", 0.0, 0, courses);
            Employee employee = new Employee();
            try {
                employeeBinder.writeBean(employee);
                Notification.show(employee.toString());
                Employee savedEmployee = employeeService.insertEmployee(employee);
                employeeGrid.setItems(employeeService.getEmployees());
                Notification.show("Saved " + savedEmployee.getName());
                dialog.close();
            }catch (HttpClientErrorException.Conflict errorException) {
//                    employeeService.updateEmployee()
            }catch (ValidationException e) {
//                System.err.println("*Days " + DAYS.between(LocalDate.now(), employee.getDob()));
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


        TextField idField = new TextField("Employee ID", "5 characters long");
        TextField nameField = new TextField("Employee Name", "Full name");
        TextField emailField = new TextField("Employee Email", "email");

        Select<String> select = new Select<>();
        select.setLabel("Designation");
        select.setItems("DeputyRegister(Academic)", "DeputyRegister(HumanResource)", "Coordinator", "Officer(Admission)", "Officer(Examination)");


        FormLayout formLayout = new FormLayout();
        formLayout.add(idField, nameField, emailField,select);

        dialog.add(formLayout,confirmButton,cancelButton);

        employeeBinder
                .forField(idField)
                .asRequired()
                .withValidator(id -> id.length() == 5, "ID must be 5 characters only")
                .bind(Employee::getId, Employee::setId);
        employeeBinder
                .forField(nameField)
                .asRequired()
                .withValidator(name -> name.length() >= 3, "Names should be at least 3 letters long")
                .withValidator(name -> name.length() <= 20, "Names cannot be more than 20 letters long")
                .withValidator(name -> !name.contains("_"), "Names cannot have underscores")
                .bind(Employee::getName, Employee::setName);
        employeeBinder
                .forField(select)
                .asRequired()
                .bind(Employee::getRole, Employee::setRole);
        employeeBinder
                .forField(emailField)
                .asRequired()
                .withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .bind(Employee::getEmail, Employee::setEmail);


    }

    private Component getEditColumn(Employee employee) {
        Button button = new Button();
        button.setIcon(VaadinIcon.EDIT.create());
        button.addClickListener(event -> {
            employeeBinder.readBean(employee);
            dialog.open();
        });
        return button;
    }


}
