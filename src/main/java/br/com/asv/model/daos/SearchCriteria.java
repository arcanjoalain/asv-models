package br.com.asv.model.daos;

import br.com.asv.base.model.daos.ISearchCriteria;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SearchCriteria implements ISearchCriteria {

	private String key;
    private String operation;
    private Object value;

    public SearchCriteria() {

    }

    public SearchCriteria(final String key, final String operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }


}

