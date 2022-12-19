package br.com.asv.model.entities.history;

import java.io.Serializable;
import java.util.Date;



import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.asv.base.model.entities.history.IBaseHistoryEntity;
import br.com.asv.model.entities.ABaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ABaseHistoryEntity<I extends Serializable> extends ABaseEntity<I> implements IBaseHistoryEntity<I>{


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
