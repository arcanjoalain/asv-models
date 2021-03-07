package br.com.asv.model.entities.history;


import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.asv.base.model.entities.history.IBaseHistoryListEntity;
import br.com.asv.base.model.entities.history.IHistoryEntity;
import br.com.asv.base.model.parse.IBaseParseHistory;
import br.com.asv.model.entities.ABaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AHistoryEntity<E extends IBaseHistoryListEntity<?,I>,I> 
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
