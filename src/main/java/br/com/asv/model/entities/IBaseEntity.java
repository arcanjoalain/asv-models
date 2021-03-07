package br.com.asv.model.entities;

import java.util.Date;

import javax.persistence.EntityListeners;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.asv.model.enums.StatusEntityEnum;



@EntityListeners(AuditingEntityListener.class)
public interface IBaseEntity<I> {
	
	I getPid();
	
	void setPid(I pid);
	
	Date getCreatedAt();
	
	void setCreatedAt(Date dateAt);
		
	Long getCreateUserPid();
	
	void setCreateUserPid(Long createUserPid);
		
	StatusEntityEnum getStatusEntity();
	
	void setStatusEntity(StatusEntityEnum statusEntity);

	void preRemove();

	void postPersist();

	void postUpdate();

	void postRemove();

	void postLoad();

	void prePersist();
	
	void preUpdate();
	
}
