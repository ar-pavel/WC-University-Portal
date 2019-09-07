package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import edu.bd.seu.userinterface.model.LoginToken;
import edu.bd.seu.userinterface.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;


public class Header extends VerticalLayout {

    private LoginToken loginToken;


    public Header(HttpSession httpSession) {
        super();

        loginToken = (LoginToken) httpSession.getAttribute("user");

        System.out.println("Header " + loginToken);

//        Div userInfo = new Div();
        Label userName = new Label("UserName");
        Label userRole = new Label("UserRole");
//        userName.getStyle().set("margin-top", "0px");
        userName.getStyle().set("margin-top", "20px");
        userRole.getStyle().set("margin-top", "0px");
//        userInfo.add(userName,userRole);

//        rightInfoBar.getStyle().set("float", "right");
//        leftLogo.getStyle().set("float", "left");


        Div leftLogo = new Div();
        Image wcuLogo = new Image("https://www.wcupa.edu/_services/STU/ramsEyeView/images/wcuSeal.png", "WCU Logo");
        wcuLogo.setHeight("56px");
        wcuLogo.getStyle().set("margin-top", "10px");
        leftLogo.add(wcuLogo);


        if(loginToken != null){
            userName.setText(loginToken.getName());
            userRole.setText(loginToken.getRole());
        }

        add(leftLogo,userName, userRole);
//        getStyle().set("display", "block");
//        setAlignSelf(Alignment.CENTER, rightInfoBar);
    }
    public LoginToken getLoginToken() {
        return loginToken;
    }

}