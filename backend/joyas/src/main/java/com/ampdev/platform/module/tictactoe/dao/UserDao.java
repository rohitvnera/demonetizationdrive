package com.ampdev.platform.module.tictactoe.dao;

import com.ampdev.platform.module.tictactoe.dataobject.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Avi on 5/22/16.
 */
@Repository("userCrudDao")
@Transactional
public interface UserDao extends CrudRepository<User, String> {
}
