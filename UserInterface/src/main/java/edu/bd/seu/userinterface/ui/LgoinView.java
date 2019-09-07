package edu.bd.seu.userinterface.ui;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;
import edu.bd.seu.userinterface.model.LoginToken;
import edu.bd.seu.userinterface.service.AuthService;

import javax.servlet.http.HttpSession;

@Route("")
public class LgoinView extends LoginOverlay {
    public LgoinView(AuthService authService, HttpSession httpSession) {

        Image logo = new Image("https://i.paste.pics/6LL9D.png", "Logo");
        logo.setHeight("90px");
        setTitle(logo);
        setDescription("This is a dummy University Portal of an imaginary university named 'WC University'");

//        addLoginListener(loginEvent -> getUI().ifPresent(ui -> ui.navigate("home")));

        addLoginListener(event -> {
            LoginToken loginToken = authService.authenticate(event.getUsername(), event.getPassword());

            setDescription(loginToken.toString());

            switch (loginToken.getRole()) {
                case "Student":
                    httpSession.setAttribute("user", loginToken);
                    getUI().ifPresent(ui -> ui.navigate("student"));
                    break;
                case "DeputyRegister(Academic)":
                    httpSession.setAttribute("user", loginToken);
                    getUI().ifPresent(ui -> ui.navigate("deputy-registrar"));
                    break;
                case "DeputyRegister(HumanResource)":
                    httpSession.setAttribute("user", loginToken);
                    getUI().ifPresent(ui -> ui.navigate("HR"));
                    break;
                case "Officer(Admission)":
                    httpSession.setAttribute("user", loginToken);
                    getUI().ifPresent(ui -> ui.navigate("admission-officer"));
//                    getUI().ifPresent(ui -> ui.navigate(loginToken.getRole()));
                    break;
                case "Coordinator":
                    httpSession.setAttribute("user", loginToken);
                    getUI().ifPresent(ui -> ui.navigate("coordinator"));
                    break;

                case "Officer(Examination)":
                    httpSession.setAttribute("user", loginToken);
                    getUI().ifPresent(ui -> ui.navigate(" "));
                    break;

                default:
                    setError(true);
                    setVisible(true);
                    getUI().ifPresent(ui -> ui.navigate(""));
                    break;

            }
        });

        setAction("");

//        setForgotPasswordButtonVisible(false);
        setOpened(true);

    }
}
