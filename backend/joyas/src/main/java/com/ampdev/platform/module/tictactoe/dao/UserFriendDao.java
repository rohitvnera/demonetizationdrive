package com.ampdev.platform.module.tictactoe.dao;

import com.ampdev.platform.module.tictactoe.dataobject.UserFriend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import javax.transaction.Transactional;

/**
 * Created by Avi on 5/23/16.
 */
@Repository("userFriendDao")
@Transactional
public interface UserFriendDao extends PagingAndSortingRepository<UserFriend, Long> {

    public Page<UserFriend> findByUserId(@Param("userId") String userId, Pageable pageable);

    public List<UserFriend> findByUserIdAndFriendId(@Param("userId") String userId, @Param("friendId") String friendId);

}

