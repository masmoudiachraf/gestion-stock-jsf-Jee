package main.java.org.polytech.achraf.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.hibernate.annotations.Proxy;
import org.primefaces.PrimeFaces;
import main.java.org.polytech.achraf.entities.Article;
import main.java.org.polytech.achraf.entities.TransactionEntree;
import main.java.org.polytech.achraf.entities.TransactionSortie;
import main.java.org.polytech.dao.ArticleDao;
import main.java.org.polytech.dao.TransactionEntreeDao;
import main.java.org.polytech.dao.TransactionSortieDao;

@Named
@ViewScoped
@SessionScoped
@Proxy(lazy=false)
public class AdminBean implements Serializable{
	
	private ArticleDao articleDao;
	
	private String selectedArticle;
	
	public List<Article> articlesList;
	
	public TransactionSortie addedTransaction = new TransactionSortie();
	
	public int quantite;
	
	public List<TransactionSortie> transactionSortiList;
	
	public TransactionSortie selectedTransaction;
	
	public TransactionSortie deletedTransaction;
	
	public List<TransactionEntree> transactionEntreeList;
	
	public TransactionEntree addedTransactionEntree = new TransactionEntree();
	
	public TransactionEntree selectedTransactionEntree;
	
	public TransactionEntree deletedTransactionEntree;
	
	public AdminBean() { 
	    // Do your stuff here.
		selectedTransaction = new TransactionSortie();
		selectedTransactionEntree = new TransactionEntree();
		transactionEntreeList = TransactionEntreeDao.getInstance().findAll();
	}
	
	public void ajouterTransactionSortie() {
		Article article = ArticleDao.getInstance().findByLabel(selectedArticle);
		if(article == null)
			addMessage(FacesMessage.SEVERITY_ERROR, "Transaction n'est pas autorisé", "Article n'est pas trouvé");
		else {
			if(article.getStock() - addedTransaction.getQuantite() < 0 )
				showInvalitTransaction();	
			else {
				TransactionSortie transactionSortie = new TransactionSortie();
				transactionSortie.setArticle(article);
				transactionSortie.setDate(addedTransaction.getDate());
				transactionSortie.setQuantite(addedTransaction.getQuantite());
				transactionSortie.setUser(SessionUtils.getUser());
				article.setStock(article.getStock() - transactionSortie.getQuantite());
				TransactionSortieDao.getInstance().insertTransactionAndUpdateArticle(transactionSortie, article);
				PrimeFaces.current().executeScript("PF('manageProductDialog').hide();");
				transactionSortiList.add(transactionSortie);
				addMessage(FacesMessage.SEVERITY_INFO, "Transaction Ajouté", "Une transaction a été ajouté avec success");
				PrimeFaces.current().ajax().update("form:messages", "form:dt-transactions");
			}
		}
        
	}
	
	public void updateTransactionSortie() {
		Article article = ArticleDao.getInstance().findByLabel(selectedTransaction.getArticle().getLabel());
		if(article == null)
			addMessage(FacesMessage.SEVERITY_ERROR, "Transaction n'est pas autorisé", "Article n'est pas trouvé");
		else {
			if(article.getStock() - selectedTransaction.getQuantite() < 0 )
				showInvalitTransaction();	
			else {
				TransactionSortieDao.getInstance().update(selectedTransaction);
				PrimeFaces.current().executeScript("PF('manageProductDialog').hide();");
				transactionSortiList = TransactionSortieDao.getInstance().findAll();
				addMessage(FacesMessage.SEVERITY_INFO, "Transaction modifié", "Une transaction a été modifié avec success");
				PrimeFaces.current().ajax().update("form:messages", "form:dt-transactions");
			}
		}
        
	}
	
	public void deleteTransaction() {
		TransactionSortieDao.getInstance().delete(deletedTransaction);
		deletedTransaction.getArticle().setStock(deletedTransaction.getArticle().getStock() + deletedTransaction.getQuantite());
		ArticleDao.getInstance().update(deletedTransaction.getArticle());
		transactionSortiList = TransactionSortieDao.getInstance().findAll();
		addMessage(FacesMessage.SEVERITY_INFO, "Transaction supprimé", "Une transaction a été supprimé avec success");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-transactions");
	}
	
	
	
	public void ajouterTransactionEntree() {
		Article article = ArticleDao.getInstance().findByLabel(selectedArticle);
		if(article == null)
			addMessage(FacesMessage.SEVERITY_ERROR, "Transaction n'est pas autorisé", "Article n'est pas trouvé");
		else {
			TransactionEntree transactionEntree = new TransactionEntree();
			transactionEntree.setArticle(article);
			transactionEntree.setDate(addedTransactionEntree.getDate());
			transactionEntree.setQuantite(addedTransactionEntree.getQuantite());
			transactionEntree.setUser(SessionUtils.getUser());
			article.setStock(article.getStock() + transactionEntree.getQuantite());
			TransactionEntreeDao.getInstance().insertTransactionAndUpdateArticle(transactionEntree, article);
			PrimeFaces.current().executeScript("PF('manageProductDialog').hide();");
			if(transactionEntreeList == null) 
				transactionEntreeList = new ArrayList<TransactionEntree>();
			transactionEntreeList.add(transactionEntree);
			addMessage(FacesMessage.SEVERITY_INFO, "Transaction Ajouté", "Une transaction a été ajouté avec success");
			PrimeFaces.current().ajax().update("form:messages", "form:dt-transactions-entree");
		}
        
	}
	
	public void updateTransactionEntree() {
		Article article = ArticleDao.getInstance().findByLabel(selectedTransaction.getArticle().getLabel());
		if(article == null)
			addMessage(FacesMessage.SEVERITY_ERROR, "Transaction n'est pas autorisé", "Article n'est pas trouvé");
		else {
			if(article.getStock() - selectedTransaction.getQuantite() < 0 )
				showInvalitTransaction();	
			else {
				TransactionEntreeDao.getInstance().update(selectedTransactionEntree);
				PrimeFaces.current().executeScript("PF('manageProductDialog').hide();");
				transactionEntreeList = TransactionEntreeDao.getInstance().findAll();
				addMessage(FacesMessage.SEVERITY_INFO, "Transaction modifié", "Une transaction a été modifié avec success");
				PrimeFaces.current().ajax().update("form:messages", "form:dt-transactions-entree");
			}
		}
        
	}
	
	public void deleteTransactionEntree() {
		TransactionEntreeDao.getInstance().delete(deletedTransactionEntree);
		deletedTransactionEntree.getArticle().setStock(deletedTransactionEntree.getArticle().getStock() - deletedTransactionEntree.getQuantite());
		ArticleDao.getInstance().update(deletedTransactionEntree.getArticle());
		transactionSortiList = TransactionSortieDao.getInstance().findAll();
		addMessage(FacesMessage.SEVERITY_INFO, "Transaction supprimé", "Une transaction a été supprimé avec success");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-transactions-entree");
	}
	
	public List<String> completeText(String query) {
		articlesList = ArticleDao.getInstance().findAll();
        String queryLowerCase = query.toLowerCase();
        List<String> countryList = new ArrayList<>();
        for (Article country : articlesList) {
            countryList.add(country.getLabel());
        }

        return countryList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
        PrimeFaces.current().ajax().update("form:messages");
    }

    public void showInvalitTransaction() {
        addMessage(FacesMessage.SEVERITY_ERROR, "Transaction n'est pas autorisé", "Stock indisponible");
    }
    
	public ArticleDao getArticleDao() {
		return articleDao;
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public List<Article> getArticlesList() {
		return this.articlesList;
	}

	public void setArticlesList(List<Article> articlesList) {
		this.articlesList = articlesList;
	}

	public TransactionSortie getAddedTransaction() {
		return addedTransaction;
	}

	public void setAddedTransaction(TransactionSortie addedTransaction) {
		this.addedTransaction = addedTransaction;
	}

	public String getSelectedArticle() {
		return selectedArticle;
	}

	public void setSelectedArticle(String selectedArticle) {
		this.selectedArticle = selectedArticle;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public List<TransactionSortie> getTransactionSortiList() {
		return TransactionSortieDao.getInstance().findAll();
	}

	public void setTransactionSortiList(List<TransactionSortie> transactionSortiList) {
		this.transactionSortiList = transactionSortiList;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public TransactionSortie getSelectedTransaction() {
		return selectedTransaction;
	}

	public void setSelectedTransaction(TransactionSortie selectedTransaction) {
		this.selectedTransaction = selectedTransaction;
	}

	public TransactionSortie getDeletedTransaction() {
		return deletedTransaction;
	}

	public void setDeletedTransaction(TransactionSortie deletedTransaction) {
		this.deletedTransaction = deletedTransaction;
	}

	public List<TransactionEntree> getTransactionEntreeList() {
		return TransactionEntreeDao.getInstance().findAll();
	}

	public void setTransactionEntreeList(List<TransactionEntree> transactionEntreeList) {
		this.transactionEntreeList = transactionEntreeList;
	}

	public TransactionEntree getAddedTransactionEntree() {
		return addedTransactionEntree;
	}

	public void setAddedTransactionEntree(TransactionEntree addedTransactionEntree) {
		this.addedTransactionEntree = addedTransactionEntree;
	}

	public TransactionEntree getSelectedTransactionEntree() {
		return selectedTransactionEntree;
	}

	public void setSelectedTransactionEntree(TransactionEntree selectedTransactionEntree) {
		this.selectedTransactionEntree = selectedTransactionEntree;
	}

	public TransactionEntree getDeletedTransactionEntree() {
		return deletedTransactionEntree;
	}

	public void setDeletedTransactionEntree(TransactionEntree deletedTransactionEntree) {
		this.deletedTransactionEntree = deletedTransactionEntree;
	}
	
	
	
}
