package br.com.asv.model.entities.history;

import br.com.asv.model.entities.IBaseEntity;

public interface IHistoryEntity<E> extends IBaseEntity{
	
	void setEntity(E entity);
	E getEntity();
	void prepareEntity();


}
