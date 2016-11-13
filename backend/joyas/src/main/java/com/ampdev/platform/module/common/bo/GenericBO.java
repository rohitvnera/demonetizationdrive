package com.ampdev.platform.module.common.bo;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ampdev.platform.framework.dataaccess.exception.DAOException;
import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.common.dao.IGenericDAO;
import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.common.exception.BOException;

public class GenericBO implements IGenericBO
{

	@Autowired
	private IGenericDAO genericDAO;

	@Override
	public <T extends PersistedDataObject> List<T> get(Module moduleEnum, Collection<Long> ids) throws BOException
	{
		try
		{
			return genericDAO.get(moduleEnum, ids);
		}
		catch (DAOException e)
		{
			throw new BOException(e);
		}
	}

	@Override
	public <T extends PersistedDataObject> List<T> getAll(Module moduleEnum) throws BOException
	{
		try
		{
			return genericDAO.getAll(moduleEnum);
		}
		catch (DAOException e)
		{
			throw new BOException(e);
		}
	}

	@Override
	public <T extends PersistedDataObject> T add(Module moduleEnum, T t) throws BOException
	{
		try
		{
			return genericDAO.add(moduleEnum, t);
		}
		catch (DAOException e)
		{
			throw new BOException(e);
		}
	}

	@Override
	public <T extends PersistedDataObject> T update(Module moduleEnum, T t) throws BOException
	{
		try
		{
			return genericDAO.update(moduleEnum, t);
		}
		catch (DAOException e)
		{
			throw new BOException(e);
		}
	}

	@Override
	public <T extends PersistedDataObject> void delete(Module moduleEnum, long id) throws BOException
	{
		try
		{
			genericDAO.delete(moduleEnum, id);
		}
		catch (DAOException e)
		{
			throw new BOException(e);
		}

	}

}
