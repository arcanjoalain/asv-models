package br.com.asv.model.entities.history;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;



import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.asv.base.model.entities.history.IBaseHistoryListEntity;
import br.com.asv.base.model.entities.history.IHistoryEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ABaseHitoryListEntity<H extends IHistoryEntity<H,I>,I extends Serializable> 
	extends ABaseHistoryEntity<I> implements IBaseHistoryListEntity<H,I>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Lob
	@ElementCollection
    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private List<H> histories = new LinkedList<>();	
	
	
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
