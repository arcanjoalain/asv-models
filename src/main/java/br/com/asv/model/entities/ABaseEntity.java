package br.com.asv.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.asv.model.enums.StatusEntityEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ABaseEntity<I> implements IBaseEntity<I>, Serializable {

	private static final long serialVersionUID = -7692821558073898783L;

//	public ABaseEntity() {
//		super();
//	}
//
//	public ABaseEntity(IBaseDto dto) {
//		super();
//		id = dto.getPid();
//	}
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_GENERATOR")
//	@Column(name = "id", updatable = false)
//	protected Long id;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status_entity")
	private StatusEntityEnum statusEntity = StatusEntityEnum.ENABLED;

	@CreatedDate
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@CreatedBy
	@Column(name = "create_user_pid")
	private Long createUserPid;

	
	@PrePersist
	public void prePersist() {
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
