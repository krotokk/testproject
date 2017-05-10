package testproject;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Created by kirill on 26.04.17.
 */

@Named
@SessionScoped
public class authorizationCDI implements Serializable{
    private String login;
    private String password;
    private String password2;
    private boolean userCreated;
    private boolean userLogged;

    @EJB
    private authorizationEJB usersEJB;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String createUser(){
        if(!password.equals(password2)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwords are different!", "Please Try Again!"));
            return "createUser";
        }
        userCreated = usersEJB.createUser(login, password);
        if(userCreated){
            return "authpage";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login is used!", "Please Try Again!"));
        return "createUser";
    }
    public String authorization(){
        userLogged = usersEJB.checkUser(login, password);
        if(userLogged){
            HttpSession session = Util.getSession();
            session.setAttribute("login", login);
            return "mainpage";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login!", "Please Try Again!"));
        return "authpage";
    }
}
