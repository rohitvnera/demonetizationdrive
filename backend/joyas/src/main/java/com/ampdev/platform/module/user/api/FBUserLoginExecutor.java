package com.ampdev.platform.module.user.api;

import com.ampdev.platform.framework.rest.IAuthorizer;
import com.ampdev.platform.framework.rest.PostExecutor;
import com.ampdev.platform.framework.rest.exception.ExecutorException;
import com.ampdev.platform.module.user.UserBO;
import com.ampdev.platform.module.user.dataobject.FBUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class FBUserLoginExecutor extends PostExecutor<FBUserData, String> {

    @Autowired
    private UserBO userBO;

    @Autowired
    private IAuthorizer userAuthorizer;

    @Override
    public ResponseEntity<String> executeBusinessLogic(RequestEntity<FBUserData> requestEntity) throws ExecutorException {
        ResponseEntity<String> responseEntity = null;
        FBUserData userData = requestEntity.getBody();

        try {


        } catch (Exception e) {
            e.printStackTrace();
            throw new ExecutorException();
        }

        return responseEntity;
    }

    @Override
    public IAuthorizer getAuthorizer() {
        return userAuthorizer;
    }

}
