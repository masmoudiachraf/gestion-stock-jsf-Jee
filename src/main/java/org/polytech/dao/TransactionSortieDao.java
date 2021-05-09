package main.java.org.polytech.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import main.java.org.polytech.achraf.beans.SessionUtils;
import main.java.org.polytech.achraf.entities.Article;
import main.java.org.polytech.achraf.entities.TransactionSortie;
import main.java.org.polytech.achraf.utils.HibernateUtils;

public class TransactionSortieDao implements IDao<TransactionSortie> {

	SessionFactory factory;
	private static TransactionSortieDao instance;

	public static TransactionSortieDao getInstance() {
		if (instance == null)
			instance = new TransactionSortieDao();
		return instance;
	}

	private TransactionSortieDao() {
		factory = HibernateUtils.getSessionFactory();
	}

	@Override
	public void insert(TransactionSortie t) {
		Session session;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
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
		}finally {
            if (session.isOpen()){
                session.close();
            }
        }

	}

	@Override
	public TransactionSortie findById(Integer id) {
		// TODO Auto-generated method stub
		Session session;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			String sql = "Select e from TransactionSortie e Where e.id = :id";
			Query<TransactionSortie> query = session.createQuery(sql);
			query.setParameter("id", id);
			return query.getSingleResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally {
            if (session.isOpen()){
                session.close();
            }
        }
	}

	@Override
	public synchronized List<TransactionSortie> findAll() {
		Session session;
		List<TransactionSortie> result;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			String sql = "Select e from TransactionSortie e";
			Query<TransactionSortie> query = session.createQuery(sql);
			result = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally {
            if (session.isOpen()){
                session.close();
            }
        }
		return result;
	}

	public synchronized List<TransactionSortie> findAllMyTransactions() {
		Session session;
		List<TransactionSortie> result;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			String sql = "Select e from TransactionSortie e WHERE e.user=:user";
			Query<TransactionSortie> query = session.createQuery(sql);
			query.setParameter("user", SessionUtils.getUser());
			result = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally {
            if (session.isOpen()){
                session.close();
            }
        }
		return result;
	}
	
	@Override
	public void delete(TransactionSortie t) {
		// TODO Auto-generated method stub
		Session session;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.delete(t);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}finally {
            if (session.isOpen()){
                session.close();
            }
        }
	}

	@Override
	public void update(TransactionSortie t) {
		// TODO Auto-generated method stub
		Session session;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.update(t);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}finally {
            if (session.isOpen()){
                session.close();
            }
        }
	}

	public void insertTransactionAndUpdateArticle(TransactionSortie t, Article article) {
		Session session;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			// Using persist(..)
			// Now 'e' is managed by Hibernate.
			// it has Persistent status.
			// No action at this time with DB.
			session.persist(t);

			session.update(article);
			// At this step the data is pushed to the DB.
			// Execute Insert statement.
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		}finally {
            if (session.isOpen()){
                session.close();
            }
        }

	}
	
}
