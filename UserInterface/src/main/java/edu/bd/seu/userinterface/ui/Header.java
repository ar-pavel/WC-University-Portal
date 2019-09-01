package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Header extends HorizontalLayout {
    public Header() {
        super();

        Image img = new Image("https://www.wcupa.edu/_services/STU/ramsEyeView/images/wcuSeal.png", "Vaadin Logo");
        img.setHeight("64px");
        Image logout = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsEwQsF_vzf2D3AQGBljwWGTEwcD3aEVNPxhemNE2s2snWzx9o", "log_out");
        logout.setHeight("44px");

        logout.addClickListener(imageClickEvent -> Notification.show("You are Logged out now"));

        logout.getStyle().set("margin-left", "auto");
        logout.getStyle().set("margin-right", "10px");



        add(img,logout);
//        setAlignSelf(Alignment.END, logout);
    }
}
