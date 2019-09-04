package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("main")
public class MainView extends AppLayout {
    public MainView() {
        Header head = new Header();
        Image img = new Image("https://www.wcupa.edu/_services/STU/ramsEyeView/images/wcuSeal.png", "Vaadin Logo");
        img.setHeight("55px");
//        addToNavbar(head);
        addToNavbar(new DrawerToggle(), head);
        Tabs tabs = new Tabs(new Tab("Dashboard"), new Tab("Profile"), new Tab("Convocation info"));

        
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);
    }
}
