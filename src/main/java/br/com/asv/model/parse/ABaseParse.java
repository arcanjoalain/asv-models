package br.com.asv.model.parse;

import br.com.asv.base.client.dto.IBaseDto;
import br.com.asv.base.model.entities.IBaseEntity;
import br.com.asv.base.model.parse.IBaseParse;

public abstract class ABaseParse<E extends IBaseEntity<I>, D extends IBaseDto<O>, I, O> 
	implements IBaseParse<E,D,I,O>{

	public D convert(E entity) {
		return convert(entity, Boolean.FALSE);
	}
	
    public E convert(D dto) {
    	return convert(dto, Boolean.FALSE);
    }
}
