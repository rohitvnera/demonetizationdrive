package com.ampdev.platform.module.common.dao;

import com.ampdev.platform.framework.dataaccess.IDataAccess;
import com.ampdev.platform.framework.dataaccess.exception.DataAccessException;
import com.ampdev.platform.framework.uuid.Guid;
import com.ampdev.platform.module.common.dataobject.Uuid;
import com.google.common.base.Preconditions;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * Created by Avi on 5/22/16.
 */
@Service("uuidGenerator")
public class UuidGenerator {

    @Autowired
    private UuidDao uuidDao;

    @Autowired
    private IDataAccess dataAccess;

    public Uuid getOrCreateUUID(@Nonnull final Guid guid) throws DataAccessException {
        Preconditions.checkNotNull(guid, "Value can't be null");
        final int hashCode = guid.getGuid().hashCode();
        String uuid = getRandom(hashCode);
        Uuid uuidObj = uuidDao.findOne(uuid);
        if(uuidObj != null){
            return uuidObj;
        }
        while (uuidObj != null) {
            uuid = new String(Base64.encodeBase64(String.valueOf(hashCode).getBytes()));
            uuid = uuid.replaceAll("[\\W_]", "");
            uuidObj = uuidDao.findOne(uuid);
        }

        uuidObj = new Uuid();
        uuidObj.setGuid(guid.getGuid());
        uuidObj.setUuid(uuid);
        dataAccess.create(uuidObj);
        return uuidObj;
    }

    private String getRandom(String value) {
        final int hashCode = value.hashCode();
        return getRandom(hashCode);
    }

    private String getRandom(int hashCode) {
        String uuid = new String(Base64.encodeBase64(String.valueOf(hashCode).getBytes()));
        uuid = uuid.replaceAll("[\\W_]", "");
        return uuid;
    }
}
