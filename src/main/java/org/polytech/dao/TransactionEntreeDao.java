package main.java.org.polytech.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import main.java.org.polytech.achraf.beans.SessionUtils;
import main.java.org.polytech.achraf.entities.Article;
import main.java.org.polytech.achraf.entities.TransactionEntree;
import main.java.org.polytech.achraf.utils.HibernateUtils;

public class TransactionEntreeDao  implements IDao<TransactionEntree> {

	SessionFactory factory;
	private static TransactionEntreeDao instance;

	public static TransactionEntreeDao getInstance() {
		if (instance == null)
			instance = new TransactionEntreeDao();
		return instance;
	}

	private TransactionEntreeDao() {
		factory = HibernateUtils.getSessionFactory();
	}

	@Override
	public void insert(TransactionEntree t) {
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
	public TransactionEntree findById(Integer id) {
		// TODO Auto-generated method stub
		Session session;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			String sql = "Select e from TransactionEntree e Where e.id = :id";
			Query<TransactionEntree> query = session.createQuery(sql);
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
	public synchronized List<TransactionEntree> findAll() {
		Session session;
		 List<TransactionEntree> result;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			String sql = "Select e from TransactionEntree e";
			Query<TransactionEntree> query = session.createQuery(sql);
			result =  query.getResultList();
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

	public synchronized List<TransactionEntree> findAllMyTransactions() {
		Session session;
		 List<TransactionEntree> result;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			String sql = "Select e from TransactionEntree e WHERE e.user=:user";
			Query<TransactionEntree> query = session.createQuery(sql);
			query.setParameter("user", SessionUtils.getUser());
			result =  query.getResultList();
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
	public void delete(TransactionEntree t) {
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
	public void update(TransactionEntree t) {
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

	public void insertTransactionAndUpdateArticle(TransactionEntree t, Article article) {
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