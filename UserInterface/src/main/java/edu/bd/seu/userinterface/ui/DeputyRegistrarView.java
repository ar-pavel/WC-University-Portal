package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Route("deputy-registrar")
public class DeputyRegistrarView extends VerticalLayout {
    public DeputyRegistrarView() {
        Tab dashboard = new Tab(new HorizontalLayout(VaadinIcon.DASHBOARD.create(), new Label("Dashboard")));
        Tab profile = new Tab(new HorizontalLayout(VaadinIcon.USER.create(), new Label("Profile")));
        Tab modify = new Tab(new HorizontalLayout(VaadinIcon.SCREWDRIVER.create(), new Label("Change System")));
        Button logout = new Button("LogOut",VaadinIcon.SIGN_OUT.create());
        Tab logOut = new Tab(logout);

        VerticalLayout dashboardPage = new VerticalLayout();
        VerticalLayout profilePage = new VerticalLayout();
        VerticalLayout modifyPage = new VerticalLayout();

        profilePage.getStyle().set("margin-left", "270px");
        modifyPage.getStyle().set("margin-left", "270px");
        dashboardPage.getStyle().set("margin-left", "270px");
        Image image = new Image("https://pmcvariety.files.wordpress.com/2019/04/joker-trailer.jpg?w=1000","joker");
        profilePage.add(image);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(dashboard, dashboardPage);
        tabsToPages.put(profile, profilePage);
        tabsToPages.put(modify, modifyPage);

        Tabs tabs = new Tabs();
        tabs.add(dashboard,profile,modify,logOut);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        Div pages = new Div(dashboardPage, profilePage, modifyPage);
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
        modifyPage.setVisible(false);

        add(mainLayout,dashboardPage,profilePage,modifyPage);
        getStyle().set("display", "block");
    }

    private VerticalLayout systemChange(){
        VerticalLayout mainLayout = new VerticalLayout();



        return mainLayout;
    }
}
