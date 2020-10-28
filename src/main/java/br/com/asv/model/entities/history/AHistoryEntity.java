package br.com.asv.model.entities.history;


import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.google.gson.Gson;

import br.com.asv.model.entities.ABaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AHistoryEntity<E extends IBaseHistoryListEntity<?>> 
	extends ABaseEntity implements IHistoryEntity<E>{

	private static final long serialVersionUID = 8050429770128500489L;
		
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private E entity;
	
	@Column
	private String historyEntity;
	
	public void prepareEntity() {
		Gson gson = new Gson();
		historyEntity = gson.toJson(entity.toDTO());
	}
}
