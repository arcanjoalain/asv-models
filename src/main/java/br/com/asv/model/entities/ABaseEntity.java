package br.com.asv.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

import br.com.asv.model.dtos.IBaseDto;
import br.com.asv.model.enums.StatusEntityEnum;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ABaseEntity implements IBaseEntity, Serializable {

	private static final long serialVersionUID = 7029980376136936378L;

	public ABaseEntity() {
		super();
	}

	public ABaseEntity(IBaseDto dto) {
		super();
		id = dto.getPid();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_GENERATOR")
	@Column(name = "id", updatable = false)
	protected Long id;

	@Column
	@Enumerated(EnumType.ORDINAL)
	protected StatusEntityEnum statusEntity = StatusEntityEnum.ENABLED;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateAt;

	@CreatedBy
	@Column(name = "create_user_id")
	protected Long createUserID;

	
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

	public IBaseDto toDTO() {
		return toDTO(true);
	}
}
