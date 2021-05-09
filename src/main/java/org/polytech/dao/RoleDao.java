package main.java.org.polytech.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import main.java.org.polytech.achraf.entities.Role;
import main.java.org.polytech.achraf.entities.User;
import main.java.org.polytech.achraf.utils.HibernateUtils;

public class RoleDao implements IDao<Role>{

	SessionFactory factory = HibernateUtils.getSessionFactory();

	private static RoleDao instance;

	public static RoleDao getInstance() {
		if (instance == null)
			instance = new RoleDao();
		return instance;
	}

	private RoleDao() {
		factory = HibernateUtils.getSessionFactory();
	}
	
	@Override
	public void insert(Role t) {
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
	public Role findById(Integer id) {
		// TODO Auto-generated method stub
		Session session;
		try {
			session = factory.getCurrentSession();
		} catch (HibernateException ex) {
			session = factory.openSession();
		}
		try {
			session.getTransaction().begin();
			String sql = "Select e from Role e Where e.id = :id";
			Query<Role> query = session.createQuery(sql);
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

	public Role findByLabel(String label) {
		// TODO Auto-generated method stub
		Session session;
		try {
			session = factory.getCurrentSession();
		} catch (HibernateException ex) {
			session = factory.openSession();
		}
		try {
			session.getTransaction().begin();
			String sql = "Select e from Role e Where e.label = :label";
			Query<Role> query = session.createQuery(sql);
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
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		Session session;
		List<Role> result;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			String sql = "Select e from Role e";
			Query<Role> query = session.createQuery(sql);
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
	public void delete(Role t) {
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
	public void update(Role t) {
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
