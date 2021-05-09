package main.java.org.polytech.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import main.java.org.polytech.achraf.entities.Categorie;
import main.java.org.polytech.achraf.utils.HibernateUtils;
public class CategorieDao implements IDao<Categorie>{

	SessionFactory factory = HibernateUtils.getSessionFactory();

	private static CategorieDao instance;

	public static CategorieDao getInstance() {
		if (instance == null)
			instance = new CategorieDao();
		return instance;
	}

	private CategorieDao() {
		factory = HibernateUtils.getSessionFactory();
	}
	
	@Override
	public void insert(Categorie t) {
		// TODO Auto-generated method stub
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
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public Categorie findById(Integer id) {
		// TODO Auto-generated method stub
		Session session;
		try {
			session = factory.getCurrentSession();
		} catch (HibernateException ex) {
			session = factory.openSession();
		}
		try {
			session.getTransaction().begin();
			String sql = "Select e from Categorie e Where e.id = :id";
			Query<Categorie> query = session.createQuery(sql);
			query.setParameter("id", id);
			return query.getSingleResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}

	public Categorie findByLabel(String label) {
		// TODO Auto-generated method stub
		Session session;
		try {
			session = factory.getCurrentSession();
		} catch (HibernateException ex) {
			session = factory.openSession();
		}
		try {
			session.getTransaction().begin();
			String sql = "Select e from Categorie e Where e.label = :label";
			Query<Categorie> query = session.createQuery(sql);
			query.setParameter("label", label);
			return query.getSingleResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
	}
	
	@Override
	public List<Categorie> findAll() {
		// TODO Auto-generated method stub
		Session session;
		List<Categorie> result;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			String sql = "Select e from Categorie e";
			Query<Categorie> query = session.createQuery(sql);
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
	public void delete(Categorie t) {
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
	public void update(Categorie t) {
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

}
