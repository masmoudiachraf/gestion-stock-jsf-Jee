package main.java.org.polytech.achraf.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.annotations.Proxy;
import org.primefaces.PrimeFaces;
import main.java.org.polytech.achraf.entities.Role;
import main.java.org.polytech.achraf.entities.User;
import main.java.org.polytech.dao.RoleDao;
import main.java.org.polytech.dao.UserDao;

@Named
@ViewScoped
@SessionScoped
@Proxy(lazy=false)
public class AdminUsersBean {

	List<User> usersList = new ArrayList<User>();
	List<Role> rolesList = new ArrayList<Role>();
	private User addedUser = new User();
	private String selectedRole;
	private User selectedUser = new User();
	
	public AdminUsersBean () {
		usersList = UserDao.getInstance().findAll();
		rolesList = RoleDao.getInstance().findAll();
	}

	public List<String> completeText(String query) {
        String queryLowerCase = query.toLowerCase();
        List<String> countryList = new ArrayList<>();
        for (Role role : rolesList) {
            countryList.add(role.getLabel());
        }

        return countryList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
	
	public void ajouterUser() {
		Role role = RoleDao.getInstance().findByLabel(selectedRole);
		if(role == null)
			addMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'ajout", "Role n'est pas trouvé");
		else {
			User user = new User();
			user.setNom(addedUser.getNom());
			user.setPrenom(addedUser.getPrenom());
			user.setPassword(addedUser.getUsername());
			user.setUsername(addedUser.getUsername());
			user.setMail(addedUser.getMail());
			user.setRole(role);
			UserDao.getInstance().insert(user);
			PrimeFaces.current().executeScript("PF('manageProductDialog').hide();");
			if(usersList == null) 
				usersList = new ArrayList<User>();
			usersList.add(user);
			addMessage(FacesMessage.SEVERITY_INFO, "Transaction Ajouté", "Une transaction a été ajouté avec success");
			PrimeFaces.current().ajax().update("form:messages", "form:dt-utilisateurs");
		}
        
	}
	
	
	public void deleteUser() {
		UserDao.getInstance().delete(selectedUser);
		usersList = UserDao.getInstance().findAll();
		addMessage(FacesMessage.SEVERITY_INFO, "Utilisateur supprimé", "Un utilisateur a été supprimé avec success");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-utilisateurs");
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
        PrimeFaces.current().ajax().update("form:messages");
    }

    public void showInvalitTransaction() {
        addMessage(FacesMessage.SEVERITY_ERROR, "Transaction n'est pas autorisé", "Stock indisponible");
    }
    
	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

	public User getAddedUser() {
		return addedUser;
	}

	public void setAddedUser(User addedUser) {
		this.addedUser = addedUser;
	}

	public String getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	
	
}
