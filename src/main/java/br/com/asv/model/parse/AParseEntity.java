package br.com.asv.model.parse;

import br.com.asv.client.dto.IBaseDto;
import br.com.asv.model.entities.IBaseEntity;

public abstract class AParseEntity<E extends IBaseEntity<I>, D extends IBaseDto<I>, I> 
	implements IParseEntity<E,D,I>{

	public D toDTO(E entity) {
		return toDTO(entity, Boolean.TRUE);
	}
	
    public E toModel(D dto) {
    	return toModel(dto, Boolean.TRUE);
    }
}
