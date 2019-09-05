package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import edu.bd.seu.userinterface.model.Student;
import edu.bd.seu.userinterface.model.StudentGuest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@Route("student")
public class StudentView  extends VerticalLayout {
    private Grid<StudentGuest> studentGrid;
    public StudentView() {
        studentGrid = new Grid<>();

        Tab dashboard = new Tab(new HorizontalLayout(VaadinIcon.DASHBOARD.create(), new Label("Dashboard")));
        Tab profile = new Tab(new HorizontalLayout(VaadinIcon.USER.create(), new Label("Profile")));
        Tab convocation = new Tab(new HorizontalLayout(VaadinIcon.ACADEMY_CAP.create(), new Label("Convocation")));
        Button logout = new Button("LogOut",VaadinIcon.SIGN_OUT.create());
        Tab logOut = new Tab(logout);

        VerticalLayout dashboardPage = new VerticalLayout();
        VerticalLayout profilePage = new VerticalLayout();
        VerticalLayout convocationPage = convocationForm();

        profilePage.getStyle().set("margin-left", "270px");
        convocationPage.getStyle().set("margin-left", "270px");
        dashboardPage.getStyle().set("margin-left", "270px");
        Image image = new Image("https://pmcvariety.files.wordpress.com/2019/04/joker-trailer.jpg?w=1000","joker");
        profilePage.add(image);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(dashboard, dashboardPage);
        tabsToPages.put(profile, profilePage);
        tabsToPages.put(convocation, convocationPage);

        Tabs tabs = new Tabs();
        tabs.add(dashboard,profile,convocation,logOut);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        Div pages = new Div(dashboardPage, profilePage, convocationPage);
        logout.addClickListener(buttonClickEvent -> logout.getUI().ifPresent(ui -> ui.navigate("")));

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
        convocationPage.setVisible(false);

        add(mainLayout,dashboardPage,profilePage,convocationPage);
        getStyle().set("display", "block");

    }

    private VerticalLayout convocationForm(){
        VerticalLayout convocation = new VerticalLayout();

        Dialog addMem = addMember();
        Dialog makePaym = makePayment();

        Button ConfirmPayment = new Button("Confirm Payment", VaadinIcon.MONEY_DEPOSIT.create());
        ConfirmPayment.addClickListener(buttonClickEvent -> makePaym.open());

        Button addMember = new Button("Add Member", VaadinIcon.USERS.create());
        addMember.addClickListener(buttonClickEvent -> addMem.open());
        addMember.getStyle().set("float", "right");

        setStudentGrid();
        convocation.add(ConfirmPayment,addMember,studentGrid);

        return convocation;
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


    private void setStudentGrid(){
        studentGrid
                .addColumn(StudentGuest::getName)
                .setWidth("150px")
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

}
