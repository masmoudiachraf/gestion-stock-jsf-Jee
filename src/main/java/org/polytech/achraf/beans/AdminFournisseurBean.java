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
import main.java.org.polytech.achraf.entities.Fournisseur;
import main.java.org.polytech.dao.FounisseurDao;

@Named
@ViewScoped
@SessionScoped
@Proxy(lazy = false)
public class AdminFournisseurBean {

	List<Fournisseur> fournisseurList = new ArrayList<Fournisseur>();
	private Fournisseur addedFournisseur = new Fournisseur();
	private Fournisseur selectedFournisseur = new Fournisseur();

	public AdminFournisseurBean() {
		fournisseurList = FounisseurDao.getInstance().findAll();
	}

	public void ajouterFournisseur() {
		Fournisseur fournisseur = new Fournisseur();
		fournisseur.setLabel(addedFournisseur.getLabel());
		FounisseurDao.getInstance().insert(fournisseur);
		PrimeFaces.current().executeScript("PF('manageProductDialog').hide();");
		if (fournisseurList == null)
			fournisseurList = new ArrayList<Fournisseur>();
		fournisseurList.add(fournisseur);
		addMessage(FacesMessage.SEVERITY_INFO, "Fournisseur Ajouté", "Un fournisseur a été ajouté avec success");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-fournisseurs");

	}

	public void deleteFournisseur() {
		FounisseurDao.getInstance().delete(selectedFournisseur);
		fournisseurList = FounisseurDao.getInstance().findAll();
		addMessage(FacesMessage.SEVERITY_INFO, "Fournisseur supprimé", "Un fournisseur a été supprimé avec success");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-fournisseurs");
	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		PrimeFaces.current().ajax().update("form:messages");
	}

	public void showInvalitTransaction() {
		addMessage(FacesMessage.SEVERITY_ERROR, "Transaction n'est pas autorisé", "Stock indisponible");
	}

	public List<Fournisseur> getFournisseurList() {
		return fournisseurList;
	}

	public void setFournisseurList(List<Fournisseur> fournisseurList) {
		this.fournisseurList = fournisseurList;
	}

	public Fournisseur getAddedFournisseur() {
		return addedFournisseur;
	}

	public void setAddedFournisseur(Fournisseur addedFournisseur) {
		this.addedFournisseur = addedFournisseur;
	}

	public Fournisseur getSelectedFournisseur() {
		return selectedFournisseur;
	}

	public void setSelectedFournisseur(Fournisseur selectedFournisseur) {
		this.selectedFournisseur = selectedFournisseur;
	}
	
	
	
}
