package br.com.asv.model.entities;

import java.io.Serializable;
import java.util.Date;



import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.asv.base.model.entities.IBaseEntity;
import br.com.asv.base.model.enums.StatusEntityEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ABaseEntity<I extends Serializable> implements IBaseEntity<I>, Serializable {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status_entity")
	private StatusEntityEnum statusEntity = StatusEntityEnum.ENABLED;

	@CreatedDate
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@CreatedBy
	@Column(name = "create_user_pid")
	private I createUserPid;

	
	@PrePersist
	public void prePersist() {
		createdAt = new Date();
	}

	@PreUpdate
	public void preUpdate() {
	}

	@PreRemove
	public void preRemove() {
	}

	@PostPersist
	public void postPersist() {

	}

	@PostUpdate
	public void postUpdate() {
	}

	@PostRemove
	public void postRemove() {
	}

	@PostLoad
	public void postLoad() {
	}


}
