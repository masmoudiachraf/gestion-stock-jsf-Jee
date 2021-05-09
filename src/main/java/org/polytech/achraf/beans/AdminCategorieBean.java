package main.java.org.polytech.achraf.beans;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.annotations.Proxy;
import org.primefaces.PrimeFaces;
import main.java.org.polytech.achraf.entities.Categorie;
import main.java.org.polytech.dao.CategorieDao;

@Named
@ViewScoped
@SessionScoped
@Proxy(lazy = false)
public class AdminCategorieBean {
	List<Categorie> categorieList = new ArrayList<Categorie>();
	private Categorie addedCategorie = new Categorie();
	private Categorie selectedCategorie = new Categorie();

	public AdminCategorieBean() {
		categorieList = CategorieDao.getInstance().findAll();
	}

	public void ajouterCategorie() {
		Categorie categorie = new Categorie();
		categorie.setLabel(addedCategorie.getLabel());
		CategorieDao.getInstance().insert(categorie);
		PrimeFaces.current().executeScript("PF('manageProductDialog').hide();");
		if (categorieList == null)
			categorieList = new ArrayList<Categorie>();
		categorieList.add(categorie);
		addMessage(FacesMessage.SEVERITY_INFO, "Categorie Ajouté", "Une categorie a été ajouté avec success");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");

	}

	public void deleteCategorie() {
		CategorieDao.getInstance().delete(selectedCategorie);
		categorieList = CategorieDao.getInstance().findAll();
		addMessage(FacesMessage.SEVERITY_INFO, "Categorie supprimé", "Une categorie a été supprimé avec success");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		PrimeFaces.current().ajax().update("form:messages");
	}

	public List<Categorie> getCategorieList() {
		return categorieList;
	}

	public void setCategorieList(List<Categorie> categorieList) {
		this.categorieList = categorieList;
	}

	public Categorie getAddedCategorie() {
		return addedCategorie;
	}

	public void setAddedCategorie(Categorie addedCategorie) {
		this.addedCategorie = addedCategorie;
	}

	public Categorie getSelectedCategorie() {
		return selectedCategorie;
	}

	public void setSelectedCategorie(Categorie selectedCategorie) {
		this.selectedCategorie = selectedCategorie;
	}
	
	

}
