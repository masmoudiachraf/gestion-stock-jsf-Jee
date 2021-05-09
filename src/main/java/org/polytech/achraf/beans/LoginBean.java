package main.java.org.polytech.achraf.beans;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.hibernate.annotations.Proxy;

import main.java.org.polytech.achraf.entities.User;
import main.java.org.polytech.dao.UserDao;


@Named
@ViewScoped
@SessionScoped
@Proxy(lazy = false)
public class LoginBean {
	private String username;
	private String password;
	
	public String login() {
		System.out.println("username : "+username);
		System.out.println("password : "+password);
		User loggedUser = UserDao.getInstance().login(username, password);
		if(loggedUser != null) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("user", loggedUser);
			return loggedUser.getRole().getLabel().toLowerCase();
		}
		else {
			showIncorrectCredentials();
			return null;
		}
			
	}
	
	public void logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		 try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showIncorrectCredentials() {
        addMessage(FacesMessage.SEVERITY_ERROR, "Username ou mot de passe incorrect", "Username ou mot de passe incorrect");
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
