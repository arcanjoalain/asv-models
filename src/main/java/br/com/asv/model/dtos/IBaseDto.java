package br.com.asv.model.dtos;

import br.com.asv.model.entities.IBaseEntity;

public interface IBaseDto {
	
	Long getPid();
	
	IBaseEntity toModel();
}
