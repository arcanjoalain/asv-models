package br.com.asv.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import br.com.asv.model.enums.StatusEntityEnum;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class ABaseEntity implements IBaseEntity, Serializable{

	private static final long serialVersionUID = 1L;

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

	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedAt;

	@CreatedBy
	@Column(name = "create_user_id")
	protected Long createUserID;
	
	@Override
	public void prePersist() {
		setDateAt(new Date());
	}
	
	@Override
	public void preUpdate(){
		setLastModifiedAt(new Date());
	}
	
	@Override
	public void preRemove() {
	}

	@Override
	public void postPersist() {		
	}

	@Override
	public void postUpdate() {
	}

	@Override
	public void postRemove() {
	}

	@Override
	public void postLoad() {
	}
}
