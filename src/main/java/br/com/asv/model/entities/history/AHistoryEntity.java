package br.com.asv.model.entities.history;


import java.io.Serializable;



import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.asv.base.model.entities.history.IBaseHistoryListEntity;
import br.com.asv.base.model.entities.history.IHistoryEntity;
import br.com.asv.base.model.parse.IBaseParseHistory;
import br.com.asv.model.entities.ABaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AHistoryEntity<E extends IBaseHistoryListEntity<?,I>,I extends Serializable> 
	extends ABaseEntity<I> implements IHistoryEntity<E,I>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IBaseParseHistory<E> parseHistoryEntity;
	
	public AHistoryEntity(IBaseParseHistory<E> parseHistoryEntity){
		this.parseHistoryEntity = parseHistoryEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private E entity;
	
	@Column
	private String historyEntity;
	
	@SuppressWarnings("unchecked")
	public void prepareEntity() {
		historyEntity = parseHistoryEntity.prepareHistory((E) this);
	}
}
