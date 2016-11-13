package com.ampdev.platform.module.findbank.dao;

import com.ampdev.platform.module.findbank.dataobject.BankStatus;
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
@Repository("bankDao")
@Transactional
public interface BankDao extends PagingAndSortingRepository<BankStatus, Long> {

    @Query("SELECT c FROM BankStatus c WHERE ((c.latX = :latX AND c.latY = :latY))")
    public BankStatus findByLatXAndLatY(@Param("latX") String latX,
                                        @Param("latY") String latY);
}
