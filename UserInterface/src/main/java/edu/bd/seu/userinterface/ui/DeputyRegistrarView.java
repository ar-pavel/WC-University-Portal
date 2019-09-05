package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;

import static com.vaadin.flow.component.icon.VaadinIcon.DASHBOARD;


@Route("deputy-registrar")
public class DeputyRegistrarView extends AppLayout {
    private Map<Tab, Component> tab2Workspace;
    private Tabs tabs;

    public DeputyRegistrarView() {
        Header header = new Header();
        Footer footer = new Footer();
        addToNavbar(new DrawerToggle());
        tab2Workspace = new HashMap<>();

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
        H1 h1 = new H1("This is the Dashboard");
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
        Image image = new Image("https://pmcvariety.files.wordpress.com/2019/04/joker-trailer.jpg?w=1000","joker");
        profilePage.add(image);
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
        VerticalLayout convocationdLayout= new VerticalLayout();

        return convocationdLayout;
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
}
