package br.com.asv.model.entities;

import java.util.Date;

import javax.persistence.EntityListeners;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.asv.model.dtos.IBaseDto;
import br.com.asv.model.enums.StatusEntityEnum;

@EntityListeners(AuditingEntityListener.class)
public interface IBaseEntity {
	
	Long getId();
	
	void setId(Long id);
	
	Date getDateAt();
	
	void setDateAt(Date dateAt);
		
	Long getCreateUserID();
	
	void setCreateUserID(Long createUserID);
		
	StatusEntityEnum getStatusEntity();
	
	void setStatusEntity(StatusEntityEnum statusEntity);

	void preRemove();

	void postPersist();

	void postUpdate();

	void postRemove();

	void postLoad();

	void prePersist();
	
	void preUpdate();
	
	IBaseDto toDTO();
	
	IBaseDto toDTO(Boolean isFull);

}
