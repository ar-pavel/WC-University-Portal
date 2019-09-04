package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;

@Route("")
public class LgoinView extends LoginOverlay {
    public LgoinView() {

        Image logo = new Image("https://i.paste.pics/6LL9D.png", "Logo");
        logo.setHeight("90px");
        setTitle(logo);
        setDescription("www.wcu.edu.bd");

        addLoginListener(loginEvent -> getUI().ifPresent(ui -> ui.navigate("home")));
//        setForgotPasswordButtonVisible(false);
        setOpened(true);

    }
}
