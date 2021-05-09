package main.java.org.polytech.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import main.java.org.polytech.achraf.entities.Categorie;
import main.java.org.polytech.achraf.entities.Fournisseur;
import main.java.org.polytech.achraf.entities.Role;
import main.java.org.polytech.achraf.utils.HibernateUtils;

public class FounisseurDao implements IDao<Fournisseur>{

	SessionFactory factory = HibernateUtils.getSessionFactory();

	private static FounisseurDao instance;

	public static FounisseurDao getInstance() {
		if (instance == null)
			instance = new FounisseurDao();
		return instance;
	}

	private FounisseurDao() {
		factory = HibernateUtils.getSessionFactory();
	}
	
	@Override
	public void insert(Fournisseur t) {
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
	public Fournisseur findById(Integer id) {
		// TODO Auto-generated method stub
		Session session;
		try {
			session = factory.getCurrentSession();
		} catch (HibernateException ex) {
			session = factory.openSession();
		}
		try {
			session.getTransaction().begin();
			String sql = "Select e from Fournisseur e Where e.id = :id";
			Query<Fournisseur> query = session.createQuery(sql);
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
	
	public Fournisseur findByLabel(String label) {
		// TODO Auto-generated method stub
		Session session;
		try {
			session = factory.getCurrentSession();
		} catch (HibernateException ex) {
			session = factory.openSession();
		}
		try {
			session.getTransaction().begin();
			String sql = "Select e from Fournisseur e Where e.label = :label";
			Query<Fournisseur> query = session.createQuery(sql);
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
	public List<Fournisseur> findAll() {
		// TODO Auto-generated method stub
		Session session;
		List<Fournisseur> result;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			String sql = "Select e from Fournisseur e";
			Query<Fournisseur> query = session.createQuery(sql);
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
	public void delete(Fournisseur t) {
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
	public void update(Fournisseur t) {
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
