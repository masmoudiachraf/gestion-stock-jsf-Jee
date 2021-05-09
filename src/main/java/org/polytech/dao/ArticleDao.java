package main.java.org.polytech.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import main.java.org.polytech.achraf.entities.Article;
import main.java.org.polytech.achraf.utils.HibernateUtils;

public class ArticleDao implements IDao<Article> {

	SessionFactory factory;

	private static ArticleDao instance;

	public static ArticleDao getInstance() {
		if (instance == null)
			instance = new ArticleDao();
		return instance;
	}

	private ArticleDao() {
		factory = HibernateUtils.getSessionFactory();
	}

	@Override
	public void insert(Article t) {
		Session session = factory.openSession();
		try {

			session.getTransaction().begin();
			// Using persist(..)
			// Now 'e' is managed by Hibernate.
			// it has Persistent status.
			// No action at this time with DB.
			session.persist(t);

			// At this step the data is pushed to the DB.
			// Execute Insert statement.
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	@Override
	public Article findById(Integer id) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		Article article = null;
		try {
			session.getTransaction().begin();
			String sql = "Select e from Article e Where e.id = :id";
			Query<Article> query = session.createQuery(sql);
			query.setParameter("id", id);
			article = query.getSingleResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

		return article;
	}

	public Article findByLabel(String label) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		Article article = null;
		try {
			session.getTransaction().begin();
			String sql = "Select e from Article e Where e.label = :label";
			Query<Article> query = session.createQuery(sql);
			query.setParameter("label", label);
			article = query.getSingleResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return article;
	}

	public List<Article> findArticleStartWith(String string) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		List<Article> result = null;
		try {
			session.getTransaction().begin();
			String sql = "Select e from Article e Where LOWER(e.label) like ':query%'";
			Query<Article> query = session.createQuery(sql);
			query.setParameter("query", string.toLowerCase());
			result = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

		return result;
	}

	@Override
	public List<Article> findAll() {
		Session session = factory.openSession();
		List<Article> result = null;
		try {
			session.getTransaction().begin();
			String sql = "Select e from Article e";
			Query<Article> query = session.createQuery(sql);
			result = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		
		return result;
	}

	@Override
	public void delete(Article t) {
		Session session = factory.openSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.delete(t);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	@Override
	public void update(Article t) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.update(t);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

}
