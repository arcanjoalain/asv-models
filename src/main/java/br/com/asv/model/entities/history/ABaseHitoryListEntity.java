package br.com.asv.model.entities.history;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ABaseHitoryListEntity<H extends IHistoryEntity<?,I>,I> 
	extends ABaseHistoryEntity<I> implements IBaseHistoryListEntity<H,I>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Lob
	@ElementCollection
    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	protected List<H> histories = new LinkedList<>();	
	
	
	@Override
	public void prePersist() {
		super.prePersist();
		processHistories();
	}

	@Override
	public void preUpdate() {
		super.preUpdate();
		processHistories();
	}
	
	public void processHistories() {
		H entity = createHistories();
		entity.prepareEntity();
		getHistories().add(entity);
	}
}
