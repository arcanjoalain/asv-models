package br.com.asv.model.entities;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import br.com.asv.model.dtos.IBaseDto;
import br.com.asv.model.enums.StatusEntityEnum;

public interface IBaseEntity {
	
	Long getId();
	
	StatusEntityEnum getStatusEntity();
	
	void setStatusEntity(StatusEntityEnum statusEntity);

	@PreRemove
	void preRemove();

	@PostPersist
	void postPersist();

	@PostUpdate
	void postUpdate();

	@PostRemove
	void postRemove();

	@PostLoad
	void postLoad();

	@PrePersist
	void prePersist();
	
	@PreUpdate
	void preUpdate();
	
	IBaseDto toDTO();
	
	IBaseDto toDTO(Boolean isFull);

}
