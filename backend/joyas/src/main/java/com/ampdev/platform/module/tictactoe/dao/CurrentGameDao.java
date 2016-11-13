package com.ampdev.platform.module.tictactoe.dao;

import com.ampdev.platform.module.tictactoe.dataobject.CurrentGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


/**
 * Created by Avi on 5/21/16.
 */
@Repository("currentGameDao")
@Transactional
public interface CurrentGameDao extends PagingAndSortingRepository<CurrentGame, Long> {

    @Query("SELECT c FROM CurrentGame c WHERE ((c.firstUser = :firstUser AND c.secondUser = :secondUser) OR (c.firstUser = :secondUser AND c.secondUser = :firstUser)) AND c.gameState = 0")
    public Page<CurrentGame> findCurrentGamesBetweenUsers(@Param("firstUser") String firstUser, @Param("secondUser") String secondUser, Pageable pageable);

    @Query("SELECT c FROM CurrentGame c WHERE ((c.firstUser = :userId) OR (c.secondUser = :userId)) AND c.gameState = 1")
    public Page<CurrentGame> findStatsForUser(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT c FROM CurrentGame c WHERE ((c.firstUser = :firstUser AND c.secondUser = :secondUser) OR (c.firstUser = :secondUser AND c.secondUser = :firstUser)) AND c.gameState = 1")
    public Page<CurrentGame> getStatsBetweenUsers(@Param("firstUser") String firstUser, @Param("secondUser") String secondUser, Pageable pageable);

}
