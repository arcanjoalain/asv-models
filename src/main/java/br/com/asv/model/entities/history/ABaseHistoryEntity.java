package br.com.asv.model.entities.history;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.asv.model.entities.ABaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ABaseHistoryEntity extends ABaseEntity implements IBaseHistoryEntity{

	private static final long serialVersionUID = -311214553060483138L;
	
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	protected Date updatedAt;
	
	@LastModifiedBy
	@Column(name = "updated_user_id")
	protected Long updatedUserID;


}
