package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class Header extends VerticalLayout {

    public Header( ) {
        super();

        Div leftLogo = new Div();
        Image wcuLogo = new Image("https://www.wcupa.edu/_services/STU/ramsEyeView/images/wcuSeal.png", "WCU Logo");
        wcuLogo.setHeight("56px");
        wcuLogo.getStyle().set("margin-top", "10px");
        leftLogo.add(wcuLogo);

//        Div userInfo = new Div();
        Label userName = new Label("UserName");
        Label userRole = new Label("UserRole");
        userName.getStyle().set("margin-top", "0px");
        userRole.getStyle().set("margin-top", "0px");
//        userInfo.add(userName,userRole);

//        rightInfoBar.getStyle().set("float", "right");
//        leftLogo.getStyle().set("float", "left");

        add(leftLogo,userName, userRole);
//        getStyle().set("display", "block");
//        setAlignSelf(Alignment.CENTER, rightInfoBar);
    }

}