package br.com.asv.model.parse;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import br.com.asv.base.client.dto.IBaseDto;
import br.com.asv.base.model.entities.IBaseEntity;
import br.com.asv.base.model.parse.IBaseParse;

public abstract class ABaseParse<E extends IBaseEntity<I>, D extends IBaseDto<O>, I extends Serializable, O extends Serializable>
		implements IBaseParse<E, D, I, O> {

	private static final long serialVersionUID = -4377772509481736084L;

	@Override
	public D convert(E entity) {
		return convert(entity, Boolean.FALSE);
	}

	@Override
	public E convert(D dto) throws IllegalArgumentException {
		return convert(dto, Boolean.FALSE);
	}

	@Override
	public D convert(E entity, boolean isFull) {
		D result = null;
		if (entity != null) {
			result = convertImpl(entity, isFull);
		}
		return result;
	}

	@Override
	public E convert(D dto, boolean isFull) throws IllegalArgumentException {
		E result = null;
		if (dto != null) {
			return convertImpl(dto, isFull);
		}
		return result;
	}

	@Override
	public O convertPidDto(I id) {
		O mask = null;
		if (id != null) {
			mask = getMask().convertPidDto(id);
		}
		return mask;
	}

	@Override
	public I convertPidEntity(O id) throws IllegalArgumentException {
		I mask = null;
		if (id != null) {
			mask = getMask().convertPid(id);
		}
		return mask;
	}

	public final List<E> covertEntities(List<D> dtos) {
		List<E> result = null;
		if (dtos != null) {
			result = dtos.stream().map(tempObj -> convert(tempObj)).collect(Collectors.toList());
		}
		return result;
	}

	public List<D> covertDtos(List<E> dtos) {
		List<D> result = null;
		if (dtos != null) {
			result = dtos.stream().map(tempObj -> convert(tempObj)).collect(Collectors.toList());
		}
		return result;
	}
}
