package main.java.org.polytech.achraf.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Proxy(lazy=false)
public class Transaction {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer id;
	protected LocalDateTime date = LocalDateTime.now();
	protected Integer quantite;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	protected Article article;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	protected User user;
	
	public Transaction() {
		
	}
	
	public Transaction(Article article) {
		this.article = article;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Integer getQuantite() {
		return quantite;
	}
	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
