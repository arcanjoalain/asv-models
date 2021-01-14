package br.com.asv.model.parse;

import br.com.asv.client.dto.IBaseDto;
import br.com.asv.model.entities.IBaseEntity;

public abstract class ABaseParse<E extends IBaseEntity<I>, D extends IBaseDto<I>, I> 
	implements IBaseParse<E,D,I>{

	public D toDTO(E entity) {
		return toDTO(entity, Boolean.FALSE);
	}
	
    public E toModel(D dto) {
    	return toModel(dto, Boolean.FALSE);
    }
}