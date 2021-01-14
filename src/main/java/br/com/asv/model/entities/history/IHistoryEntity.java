package br.com.asv.model.entities.history;

import java.io.Serializable;

import br.com.asv.model.entities.IBaseEntity;
import br.com.asv.model.parse.IBaseParse;

public interface IHistoryEntity<E,I> extends IBaseEntity<I>, Serializable{
	
	void setEntity(E entity);
	E getEntity();
	void prepareEntity();
	IBaseParse<?,?,I> getParse(); 

}
