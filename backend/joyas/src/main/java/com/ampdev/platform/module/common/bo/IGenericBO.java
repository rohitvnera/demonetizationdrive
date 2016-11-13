package com.ampdev.platform.module.common.bo;

import java.util.Collection;
import java.util.List;

import com.ampdev.platform.module.common.constants.Module;
import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.common.exception.BOException;

public interface IGenericBO
{

	public <T extends PersistedDataObject> List<T> get(Module moduleEnum, Collection<Long> ids) throws BOException;

	public <T extends PersistedDataObject> List<T> getAll(Module moduleEnum) throws BOException;

	public <T extends PersistedDataObject> T add(Module moduleEnum, T t) throws BOException;

	public <T extends PersistedDataObject> T update(Module moduleEnum, T t) throws BOException;

	public <T extends PersistedDataObject> void delete(Module moduleEnum, long id) throws BOException;
}
