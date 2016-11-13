package com.ampdev.platform.module.common.dao;

import com.ampdev.platform.framework.dataaccess.CriteriaObject;
import com.ampdev.platform.framework.dataaccess.CriteriaObject.CRITERIA_TYPES;
import com.ampdev.platform.framework.dataaccess.IDataAccess;
import com.ampdev.platform.framework.dataaccess.exception.DAOException;
import com.ampdev.platform.framework.dataaccess.exception.DataAccessException;
import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.common.factory.ObjectModifer;
import com.ampdev.platform.module.common.factory.QueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericDAO implements IGenericDAO {

    @Autowired
    private IDataAccess dataAccess;
    
    @Override
    public <T extends PersistedDataObject> List<T> get(Module moduleEnum, Collection<Long> ids) throws DAOException {
        List<T> list = null;
        List<CriteriaObject> crieterias = new ArrayList<CriteriaObject>();
        CriteriaObject criteria = new CriteriaObject("ids", CRITERIA_TYPES.COLLECTION, ids);
        crieterias.add(criteria);

        String query = QueryFactory.getQuery(moduleEnum);

        try {
            list = dataAccess.getResults(query, crieterias);
            return list;
        } catch (DataAccessException e) {
            System.err.println("Error getting result for following module and ids: " + moduleEnum + "," + ids);
            throw new DAOException("Error in get", e);
        }

    }

    @Override
    public <T extends PersistedDataObject> List<T> getAll(Module moduleEnum) throws DAOException {
        List<T> list = null;
        String query = QueryFactory.getAllQuery(moduleEnum);
        try {
            list = dataAccess.getResults(query, null);
            return list;
        } catch (DataAccessException e) {
            System.err.println("Error getting all entries for following module: " + moduleEnum);
            throw new DAOException("Error while getAll", e);
        }

    }

    @Override
    public <T extends PersistedDataObject> T add(Module moduleEnum, T t) throws DAOException {
        try {
            if (t != null) {
                t = ObjectModifer.getModifiedObjectCreate(moduleEnum, t);
                dataAccess.create(t);

            }
        } catch (DataAccessException e) {
            throw new DAOException("Error adding country", e);
        }

        return t;

    }

    @Override
    public <T extends PersistedDataObject> T update(Module moduleEnum, T t) throws DAOException {
        try {
            if (t != null) {
                t = ObjectModifer.getModifiedObjectUpdate(moduleEnum, t);
                dataAccess.update(t);

            }
        } catch (DataAccessException de) {
            throw new DAOException("Error in updating country", de);
        }
        return t;
    }

    @Override
    public <T extends PersistedDataObject> void delete(Module moduleEnum, long id) throws DAOException {
        try {
            List<CriteriaObject> list = new ArrayList<CriteriaObject>();
            CriteriaObject criteria = new CriteriaObject("id", CRITERIA_TYPES.LONG, id);
            list.add(criteria);

            String query = QueryFactory.getDeleteQueery(moduleEnum);
            dataAccess.executeUpdate(query, list);
        } catch (DataAccessException de) {
            throw new DAOException("Error is deleting", de);
        }

    }

}
