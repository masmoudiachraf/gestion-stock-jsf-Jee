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
import main.java.org.polytech.achraf.entities.Article;
import main.java.org.polytech.achraf.entities.Categorie;
import main.java.org.polytech.achraf.entities.Fournisseur;
import main.java.org.polytech.dao.ArticleDao;
import main.java.org.polytech.dao.CategorieDao;
import main.java.org.polytech.dao.FounisseurDao;

@Named
@ViewScoped
@SessionScoped
@Proxy(lazy = false)
public class AdminArticleBean {
	
	List<Article> articleList = new ArrayList<Article>();
	List<Fournisseur> fournisseurList = new ArrayList<Fournisseur>();
	List<Categorie> categorieList = new ArrayList<Categorie>();
	private Article addedArticle = new Article();
	private String selectedFournisseur;
	private String selectedCategorie;
	private Article selectedArticle = new Article();
	
	public AdminArticleBean () {
	}

	public List<String> completeTextFournisseur(String query) {
		fournisseurList = FounisseurDao.getInstance().findAll();
        String queryLowerCase = query.toLowerCase();
        List<String> fournisseurs = new ArrayList<>();
        for (Fournisseur fournisseur : fournisseurList) {
        	fournisseurs.add(fournisseur.getLabel());
        }

        return fournisseurs.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
	
	public List<String> completeTextCategorie(String query) {
		categorieList = CategorieDao.getInstance().findAll();
        String queryLowerCase = query.toLowerCase();
        List<String> categories = new ArrayList<>();
        for (Categorie categorie : categorieList) {
        	categories.add(categorie.getLabel());
        }

        return categories.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
	
	public void ajouterArticle() {
		Fournisseur fournisseur = FounisseurDao.getInstance().findByLabel(selectedFournisseur);
		Categorie categorie = CategorieDao.getInstance().findByLabel(selectedCategorie);
		if(fournisseur == null || categorie == null)
			addMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'ajout", "Fournisseur ou Categorie et même les deux non trouvé");
		else {
			Article article = new Article();
			article.setLabel(addedArticle.getLabel());
			article.setStock(addedArticle.getStock());
			article.setCategorie(categorie);
			article.setFournisseur(fournisseur);
			
			ArticleDao.getInstance().insert(article);
			PrimeFaces.current().executeScript("PF('manageProductDialog').hide();");
			if(articleList == null) 
				articleList = new ArrayList<Article>();
			articleList.add(article);
			addMessage(FacesMessage.SEVERITY_INFO, "Article Ajouté", "Un article a été ajouté avec success");
			PrimeFaces.current().ajax().update("form:messages", "form:dt-articles");
		}
        
	}
	
	
	public void deleteArticle() {
		ArticleDao.getInstance().delete(selectedArticle);
		articleList = ArticleDao.getInstance().findAll();
		addMessage(FacesMessage.SEVERITY_INFO, "Utilisateur supprimé", "Un article a été supprimé avec success");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-articles");
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
        PrimeFaces.current().ajax().update("form:messages");
    }

	public List<Article> getArticleList() {
		return ArticleDao.getInstance().findAll();
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public List<Fournisseur> getFournisseurList() {
		return FounisseurDao.getInstance().findAll();
	}

	public void setFournisseurList(List<Fournisseur> fournisseurList) {
		this.fournisseurList = fournisseurList;
	}

	public List<Categorie> getCategorieList() {
		return CategorieDao.getInstance().findAll();
	}

	public void setCategorieList(List<Categorie> categorieList) {
		this.categorieList = categorieList;
	}

	public Article getAddedArticle() {
		return addedArticle;
	}

	public void setAddedArticle(Article addedArticle) {
		this.addedArticle = addedArticle;
	}

	public String getSelectedFournisseur() {
		return selectedFournisseur;
	}

	public void setSelectedFournisseur(String selectedFournisseur) {
		this.selectedFournisseur = selectedFournisseur;
	}

	public String getSelectedCategorie() {
		return selectedCategorie;
	}

	public void setSelectedCategorie(String selectedCategorie) {
		this.selectedCategorie = selectedCategorie;
	}

	public Article getSelectedArticle() {
		return selectedArticle;
	}

	public void setSelectedArticle(Article selectedArticle) {
		this.selectedArticle = selectedArticle;
	}

    
	
}
