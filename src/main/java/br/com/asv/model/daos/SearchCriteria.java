package br.com.asv.model.daos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {

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

