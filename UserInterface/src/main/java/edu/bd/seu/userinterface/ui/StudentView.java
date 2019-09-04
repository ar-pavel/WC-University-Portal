package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("student")
public class StudentView  extends VerticalLayout {
    public StudentView() {
        Header header = new Header();
        AppLayout appLayout = new AppLayout();

        add(header,appLayout);
    }
}
