package com.ampdev.platform.module.findbank.dao;

import com.ampdev.platform.module.findbank.dataobject.Feedback;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


/**
 * Created by Avi on 5/21/16.
 */
@Repository("feedbackDao")
@Transactional
public interface FeedbackDao extends PagingAndSortingRepository<Feedback, Long> {

}
