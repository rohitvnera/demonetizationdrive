package com.ampdev.platform.framework.dataaccess;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ampdev.platform.framework.dataaccess.exception.DataAccessException;

public class HibernateUtil
{

	protected static Session getSession(SessionFactory sessionFactory) throws DataAccessException
	{
		Session s = null;
		try
		{
			if (sessionFactory != null)
				s = sessionFactory.openSession();
			else
				throw new DataAccessException("Invalid Session Factory passed for ceating session");
		}
		catch (HibernateException he)
		{
			throw new DataAccessException(he);
		}

		return s;

	}

	protected static Transaction getTransaction(Session s) throws DataAccessException
	{
		Transaction t = null;
		try
		{
			if (s != null)
				t = s.beginTransaction();
			else
				throw new DataAccessException("Invalid Session for creating transaction");
		}
		catch (HibernateException he)
		{
			throw new DataAccessException(he);
		}

		return t;
	}

	protected static void commit(Transaction t, Session s) throws DataAccessException
	{
		try
		{
			if (t != null)
				t.commit();
			else
				throw new DataAccessException("Invalid transaction to commit");
		}
		catch (HibernateException he)
		{
			rollback(t, s);
			throw new DataAccessException(he);
		}
	}

	protected static void closeSession(Session s)
	{
		try
		{
			if (s != null)
			{
				s.flush();
				s.close();
			}
		}
		catch (HibernateException he)
		{
			System.err.println("Flushing and closing session gave error");
		}

	}

	protected static void rollback(Transaction t, Session s) throws DataAccessException
	{
		try
		{
			if (t != null)
				t.rollback();
			else
				throw new DataAccessException("Invalid transaction to commit");
		}
		catch (HibernateException he)
		{
			throw new DataAccessException(he);
		}
	}

}
