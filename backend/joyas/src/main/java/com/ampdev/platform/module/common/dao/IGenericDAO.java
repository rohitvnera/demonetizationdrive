package com.ampdev.platform.module.common.dao;

import java.util.Collection;
import java.util.List;

import com.ampdev.platform.framework.dataaccess.exception.DAOException;
import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.common.dataobject.PersistedDataObject;

public interface IGenericDAO
{
	public <T extends PersistedDataObject> List<T> get(Module moduleEnum,Collection<Long> ids) throws DAOException;

	public <T extends PersistedDataObject> List<T> getAll(Module moduleEnum) throws DAOException;

	public <T extends PersistedDataObject> T add(Module moduleEnum,T t) throws DAOException;

	public <T extends PersistedDataObject> T update(Module moduleEnum,T t) throws DAOException;

	public <T extends PersistedDataObject> void delete(Module moduleEnum,long id) throws DAOException;
}
