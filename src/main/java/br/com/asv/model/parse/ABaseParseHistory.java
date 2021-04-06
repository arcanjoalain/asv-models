package br.com.asv.model.parse;

import com.google.gson.Gson;

import br.com.asv.base.client.dto.IBaseDto;
import br.com.asv.base.model.entities.IBaseEntity;
import br.com.asv.base.model.parse.IBaseParse;
import br.com.asv.base.model.parse.IBaseParseHistory;


public abstract class ABaseParseHistory<E extends IBaseEntity<I>, D extends IBaseDto<O>, I, O> implements IBaseParseHistory<E>{
	
	private IBaseParse<E,D,I,O> parseEntity;
	
	public ABaseParseHistory(IBaseParse<E,D,I,O> parseEntity) {
		this.parseEntity = parseEntity;
	}

	public String prepareHistory(E entity) {
		Gson gson = new Gson();
		return gson.toJson(parseEntity.convert(entity));
	}
}
