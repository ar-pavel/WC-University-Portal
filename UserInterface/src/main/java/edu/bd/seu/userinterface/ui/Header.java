package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class Header extends HorizontalLayout {

    public Header( ) {
        super();
        Image wcuLogo = new Image("https://www.wcupa.edu/_services/STU/ramsEyeView/images/wcuSeal.png", "WCU Logo");
        wcuLogo.setHeight("60px");
        Button home = new Button(wcuLogo);

        Span userInfo = new Span();
        Label userName = new Label("UserName");
        Label userRole = new Label("UserRole");
//        VerticalLayout layout = new VerticalLayout();
//        layout.add(userName,userRole);
        userInfo.add(userRole,userName);

//        userInfo.getStyle().set("display", "inline-block");
//        userInfo.getStyle().set("margin-top", "0px");
        userInfo.getStyle().set("position", "relative");
        userInfo.getStyle().set("top", "0px");

        add(home,userInfo);
    }

}