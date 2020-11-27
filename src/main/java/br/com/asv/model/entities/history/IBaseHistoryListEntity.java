package br.com.asv.model.entities.history;

import java.io.Serializable;
import java.util.List;

public interface IBaseHistoryListEntity<H extends Serializable> extends IBaseHistoryEntity,Serializable{

	H createHistories();
	List<H> getHistories();
	void setHistories(List<H> histories);
	void processHistories();
}
