package br.com.asv.model.parse;

import java.io.Serializable;

import br.com.asv.base.client.dto.IBaseDto;
import br.com.asv.base.model.entities.IBaseEntity;
import br.com.asv.base.model.parse.IBaseParse;

public abstract class ABaseParse<
	E extends IBaseEntity<I>, 
	D extends IBaseDto<O>, 
	I extends Serializable, 
	O extends Serializable> 
	implements IBaseParse<E,D,I,O>{

	private static final long serialVersionUID = -4377772509481736084L;

	public D convert(E entity) {
		return convert(entity, Boolean.FALSE);
	}
	
    public E convert(D dto) throws IllegalArgumentException{
    	return convert(dto, Boolean.FALSE);
    }
    
    @Override
	public O convertPidDto(I id) {
		O mask = null;
		if(id != null) {
			 mask = getMask().convertPidDto(id);
		}
		return mask;	

	}

	@Override
	public I convertPidEntity(O id) throws IllegalArgumentException{
		I mask = null;
		if(id != null) {
			 mask = getMask().convertPid(id);
		}
		return mask;

	}
}
