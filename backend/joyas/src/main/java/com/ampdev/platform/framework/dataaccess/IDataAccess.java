package com.ampdev.platform.framework.dataaccess;

import java.util.List;

import com.ampdev.platform.framework.dataaccess.exception.DataAccessException;
import com.ampdev.platform.module.common.dataobject.IDataObject;

public interface IDataAccess
{

	public <T extends IDataObject> void create(T t) throws DataAccessException;

	public <T extends IDataObject> void create(String name, T t) throws DataAccessException;

	public <T extends IDataObject> void update(T t) throws DataAccessException;

	public <T extends IDataObject> void delete(Class<T> cl, long id) throws DataAccessException;

	public <T extends IDataObject> T getSingleResult(String query, List<CriteriaObject> criteriaList)
		throws DataAccessException;

	public <T extends IDataObject> List<T> getResults(String query, List<CriteriaObject> criteriaList)
		throws DataAccessException;

	public int executeUpdate(String query, List<CriteriaObject> criteriaList) throws DataAccessException;

	public <T extends IDataObject> T load(Class<T> cl, long id) throws DataAccessException;

	public Object getSingleObjectResult(String queryString, List<CriteriaObject> criteriaList) throws DataAccessException;

	public Object getObjectResults(String queryString, List<CriteriaObject> criteriaList) throws DataAccessException;

	public List<Object> executeNativeQuery(String query, List<CriteriaObject> criteriaList) throws DataAccessException;

	public <T extends IDataObject> List<T> executeNativeQuery(Class<T> clazz, String query, List<CriteriaObject> criteriaList) throws DataAccessException;
}
