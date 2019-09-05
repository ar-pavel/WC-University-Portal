package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Footer extends VerticalLayout {
    public Footer() {
        super();
        Label developerInfo = new Label("Developed By a frustrated gUy!");
        developerInfo.getStyle().set("margin-top", "375px");
        add(developerInfo);
    }
}
