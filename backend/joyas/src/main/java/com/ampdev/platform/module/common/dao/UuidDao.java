package com.ampdev.platform.module.common.dao;

import com.ampdev.platform.module.common.dataobject.Uuid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Avi on 5/22/16.
 */@Repository("uuidDao")
@Transactional
public interface UuidDao extends CrudRepository<Uuid, String> {
}
