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
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ABaseHistoryEntity<I> extends ABaseEntity<I> implements IBaseHistoryEntity<I>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@LastModifiedBy
	@Column(name = "updated_user_id")
	private Long updatedUserID;


}
