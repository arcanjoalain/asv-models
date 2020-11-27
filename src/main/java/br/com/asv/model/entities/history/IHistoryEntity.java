package br.com.asv.model.entities.history;

import java.io.Serializable;

import br.com.asv.model.entities.IBaseEntity;

public interface IHistoryEntity<E> extends IBaseEntity, Serializable{
	
	void setEntity(E entity);
	E getEntity();
	void prepareEntity();


}
