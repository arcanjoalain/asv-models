package br.com.asv.model.parse;

import br.com.asv.client.dto.IBaseDto;
import br.com.asv.model.entities.IBaseEntity;

public interface IParseEntity<E extends IBaseEntity<I>, D extends IBaseDto<I>,I> {

	D toDTO(E entity);

	D toDTO(E entity, boolean isFull);

	E toModel(D dto);

	E toModel(D dto, boolean isFull);

}
