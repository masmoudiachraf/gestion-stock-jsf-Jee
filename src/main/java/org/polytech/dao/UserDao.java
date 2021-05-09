package main.java.org.polytech.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import main.java.org.polytech.achraf.entities.User;
import main.java.org.polytech.achraf.utils.HibernateUtils;

public class UserDao implements IConnectionDao, IDao<User> {

	SessionFactory factory = HibernateUtils.getSessionFactory();

	private static UserDao instance;

	public static UserDao getInstance() {
		if (instance == null)
			instance = new UserDao();
		return instance;
	}

	private UserDao() {
		factory = HibernateUtils.getSessionFactory();
	}

	@Override
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		User user = null;
		try {
			session.getTransaction().begin();

			String sql = "Select e from User e WHERE e.username = :username AND e.password = :password";
			Query<User> query = session.createQuery(sql);
			query.setParameter("username", username);
			query.setParameter("password", password);
			user = query.getSingleResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return user;
	}

	@Override
	public void insert(User t) {
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
	public User findById(Integer id) {
		// TODO Auto-generated method stub
		Session session;
		try {
			session = factory.getCurrentSession();
		} catch (HibernateException ex) {
			session = factory.openSession();
		}
		try {
			session.getTransaction().begin();
			String sql = "Select e from User e Where e.id = :id";
			Query<User> query = session.createQuery(sql);
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

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		Session session;
		List<User> result;
        try {
            session = factory.getCurrentSession();
        } catch (HibernateException ex) {
            session = factory.openSession();
        }
		try {
			session.getTransaction().begin();
			String sql = "Select e from User e";
			Query<User> query = session.createQuery(sql);
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
	public void delete(User t) {
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
	public void update(User t) {
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
