package com.ampdev.platform.framework.dataaccess;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.ampdev.platform.framework.dataaccess.CriteriaObject.CRITERIA_TYPES;
import com.ampdev.platform.framework.dataaccess.exception.DataAccessException;
import com.ampdev.platform.module.common.dataobject.IDataObject;
import com.ampdev.platform.module.common.util.Util;

public class DataAccessHibImpl implements IDataAccess
{

	@Autowired
	SessionFactory sessionFactory;

	public DataAccessHibImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public <T extends IDataObject> void create(T t) throws DataAccessException
	{
		create(null, t);
	}

	public <T extends IDataObject> void create(String entityName, T t) throws DataAccessException
	{
		saveInternal(entityName, t);
	}

	public <T extends IDataObject> void update(T t) throws DataAccessException
	{
		update(null, t);
	}

	public <T extends IDataObject> void update(String entityName, T t) throws DataAccessException
	{
		updateInternal(entityName, t);
	}

	public <T extends IDataObject> void delete(Class<T> cl, long id) throws DataAccessException
	{
		deleteInternal(null, cl, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IDataObject> T getSingleResult(String queryString, List<CriteriaObject> criteriaList)
		throws DataAccessException
	{
		Session session = null;
		T t = null;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			Query query = session.getNamedQuery(queryString);
			if (!Util.isEmpty(criteriaList))
			{
				setCriteriaInQuery(query, criteriaList);
			}
			t = (T) query.uniqueResult();
		}
		catch (HibernateException | DataAccessException he)
		{
			he.printStackTrace();
			throw new DataAccessException("Error occurred while saving data!!", he);
		}
		finally
		{
			HibernateUtil.closeSession(session);
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IDataObject> List<T> getResults(String queryString, List<CriteriaObject> criteriaList)
		throws DataAccessException
	{
		Session session = null;
		List<T> list = null;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			Query query = session.getNamedQuery(queryString);
			if (!Util.isEmpty(criteriaList))
			{
				setCriteriaInQuery(query, criteriaList);
			}
			list = query.list();
		}
		catch (HibernateException | DataAccessException he)
		{
			throw new DataAccessException("Error occurred while saving data!!", he);
		}
		finally
		{
			HibernateUtil.closeSession(session);
		}
		return list;

	}

	private Query setCriteriaInQuery(Query q, List<CriteriaObject> criteriaList)
	{
		if (q != null)
		{
			if (!Util.isEmpty(criteriaList))
			{
				for (CriteriaObject c : criteriaList)
				{
					CRITERIA_TYPES type = c.getObjectType();
					switch (type)
					{
						case LONG:
							q.setLong(c.getObjectName(), Long.valueOf(c.getObjectValue().toString()));
							break;
						case INT:
							q.setInteger(c.getObjectName(), Integer.valueOf(c.getObjectValue().toString()));
							break;
						case STRING:
							q.setString(c.getObjectName(), c.getObjectValue().toString());
							break;
						case COLLECTION:
							q.setParameterList(c.getObjectName(), (Collection<?>) c.getObjectValue());
							break;
						case DATE:
							q.setDate(c.getObjectName(),(Date)c.getObjectValue());

						case LIMIT:
							if (c.getObjectValue() != null) {
								q.setMaxResults(Integer.parseInt(String.valueOf(c.getObjectValue())));
							}
							break;
						case OFFSET:
							if (c.getObjectValue() != null) {
								q.setFirstResult((Integer) c.getObjectValue());
							}
							break;
						default:
							break;
					}
				}
			}
		}
		return q;
	}

	@Override
	public int executeUpdate(String queryString, List<CriteriaObject> criteriaList) throws DataAccessException
	{
		Session session = null;
		Transaction tran = null;
		int result = 0;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			tran = HibernateUtil.getTransaction(session);
			if (queryString != null)
			{
				Query query = session.getNamedQuery(queryString);
				if (!Util.isEmpty(criteriaList))
				{
					setCriteriaInQuery(query, criteriaList);
				}
				result = query.executeUpdate();
			}
			HibernateUtil.commit(tran, session);
			HibernateUtil.closeSession(session);
		}
		catch (HibernateException | DataAccessException he)
		{
			HibernateUtil.rollback(tran, session);
			throw new DataAccessException("Error occurred while saving data!!", he);
		}

		return result;
	}

	@Override
	public <T extends IDataObject> T load(Class<T> cl, long id) throws DataAccessException
	{
		Session session = null;
		T persistedUserData = null;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			persistedUserData = (T) session.load(cl, id);
			HibernateUtil.closeSession(session);
		}
		catch (HibernateException | DataAccessException he)
		{
			throw new DataAccessException("Error occurred while saving data!!", he);
		}

		return persistedUserData;
	}

	private <T> void saveInternal(String entityName, T t) throws DataAccessException
	{
		Session session = null;
		Transaction tran = null;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			tran = HibernateUtil.getTransaction(session);
			session.save(entityName, t);
			HibernateUtil.commit(tran, session);
			HibernateUtil.closeSession(session);
		}
		catch (HibernateException | DataAccessException he)
		{
			HibernateUtil.rollback(tran, session);
			he.printStackTrace();
			throw new DataAccessException("Error occurred while saving data!!", he);
		}

	}

	private <T> void updateInternal(String entityName, T t) throws DataAccessException
	{
		Session session = null;
		Transaction tran = null;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			tran = HibernateUtil.getTransaction(session);
			session.update(entityName, t);
			HibernateUtil.commit(tran, session);
			HibernateUtil.closeSession(session);
		}
		catch (HibernateException | DataAccessException he)
		{
			HibernateUtil.rollback(tran, session);
			throw new DataAccessException("Error occurred while saving data!!", he);
		}

	}

	private <T> void deleteInternal(String entityName, Class<T> cl, long id) throws DataAccessException
	{
		Session session = null;
		Transaction tran = null;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			T t = (T) sessionFactory.getCurrentSession().load(cl, id);
			sessionFactory.getCurrentSession().delete(t);
			tran = HibernateUtil.getTransaction(session);
			session.delete(entityName, t);
			HibernateUtil.commit(tran, session);
			HibernateUtil.closeSession(session);
		}
		catch (HibernateException | DataAccessException he)
		{
			HibernateUtil.rollback(tran, session);
			throw new DataAccessException("Error occurred while saving data!!", he);
		}

	}

	@Override
	public Object getObjectResults(String queryString, List<CriteriaObject> criteriaList) throws DataAccessException
	{
		Session session = null;
		Object obj = null;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			Query query = session.getNamedQuery(queryString);
			if (!Util.isEmpty(criteriaList))
			{
				setCriteriaInQuery(query, criteriaList);
			}
			obj = query.list();
		}
		catch (HibernateException | DataAccessException he)
		{
			throw new DataAccessException("Error occurred while saving data!!", he);
		}
		finally
		{
			HibernateUtil.closeSession(session);
		}
		return obj;

	}

	@Override
	public Object getSingleObjectResult(String queryString, List<CriteriaObject> criteriaList) throws DataAccessException
	{
		Session session = null;
		Object t = null;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			Query query = session.getNamedQuery(queryString);
			if (!Util.isEmpty(criteriaList))
			{
				setCriteriaInQuery(query, criteriaList);
			}
			t = query.uniqueResult();
		}
		catch (HibernateException | DataAccessException he)
		{
			he.printStackTrace();
			throw new DataAccessException("Error occurred while saving data!!", he);
		}
		finally
		{
			HibernateUtil.closeSession(session);
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> executeNativeQuery(String queryString, List<CriteriaObject> criteriaList) throws DataAccessException
	{

		Session session = null;
		List<Object> obj = null;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			Query query =session.createSQLQuery(queryString);
			if (!Util.isEmpty(criteriaList))
			{
				setCriteriaInQuery(query, criteriaList);
			}
			obj = query.list();
		}
		catch (HibernateException | DataAccessException he)
		{
			throw new DataAccessException("Error occurred while saving data!!", he);
		}
		finally
		{
			HibernateUtil.closeSession(session);
		}
		return obj;

	}

	@Override
	public <T extends IDataObject> List<T> executeNativeQuery(Class<T> clazz, String queryString, List<CriteriaObject> criteriaList) throws DataAccessException {
		Session session = null;
		List<T> obj = null;
		try
		{
			session = HibernateUtil.getSession(sessionFactory);
			Query query =session.createSQLQuery(queryString).addEntity(clazz);
			if (!Util.isEmpty(criteriaList))
			{
				setCriteriaInQuery(query, criteriaList);
			}
			obj = query.list();
		}
		catch (HibernateException | DataAccessException he)
		{
			throw new DataAccessException("Error occurred while saving data!!", he);
		}
		finally
		{
			HibernateUtil.closeSession(session);
		}
		return obj;
	}
}
