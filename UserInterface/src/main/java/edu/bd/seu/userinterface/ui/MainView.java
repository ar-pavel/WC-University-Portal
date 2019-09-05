package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

//@Route("main")
public class MainView extends VerticalLayout {
    public MainView() {
        Header header = new Header();
      //  add(header);
        getStyle().set("display", "block");
        Div body = new Div();
        AppLayout mainLayout = new AppLayout();
//        mainLayout.addToNavbar(header);
        Tabs tabs = new Tabs(new Tab("Dashboard"), new Tab("Profile"), new Tab("Convocation info"));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        mainLayout.addToDrawer(tabs);
        body.add(mainLayout);
        add(body);

    }
}
