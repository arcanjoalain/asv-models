package br.com.asv.model.parse;

import com.google.gson.Gson;

import br.com.asv.client.dto.IBaseDto;
import br.com.asv.model.entities.IBaseEntity;

public abstract class ABaseParseHistory<E extends IBaseEntity<I>, D extends IBaseDto<I>, I> implements IBaseParseHistory<E>{
	
	private IBaseParse<E,D,I> parseEntity;
	
	public ABaseParseHistory(IBaseParse<E,D,I> parseEntity) {
		this.parseEntity = parseEntity;
	}

	public String prepareHistory(E entity) {
		Gson gson = new Gson();
		return gson.toJson(parseEntity.toDTO(entity));
	}
}
