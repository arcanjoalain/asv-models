package br.com.asv.model.daos;

import java.util.function.Consumer;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.asv.base.model.daos.ISearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class SearchQueryCriteriaConsumer implements Consumer<ISearchCriteria>{
	
	private static final Logger LOGGER = LogManager.getLogger(SearchQueryCriteriaConsumer.class);
	 
    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root<?> r;
    
    public SearchQueryCriteriaConsumer(Predicate predicate, CriteriaBuilder builder, Root<?> r) {
        super();
        this.predicate = predicate;
        this.builder = builder;
        this.r= r;
    }

 
    public void accept(SearchCriteria param) {
        if (">".equalsIgnoreCase(param.getOperation())) {
            predicate = builder.and(predicate, builder
              .greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
        } else if ("<".equalsIgnoreCase(param.getOperation())) {
            predicate = builder.and(predicate, builder.lessThanOrEqualTo(
              r.get(param.getKey()), param.getValue().toString()));
        } else if (":".equalsIgnoreCase(param.getOperation())) {
            if (r.get(param.getKey()).getJavaType() == String.class) {
                predicate = builder.and(predicate, builder.like(
                  r.get(param.getKey()), "%" + param.getValue() + "%"));
            } else {
                predicate = builder.and(predicate, builder.equal(
                  r.get(param.getKey()), param.getValue()));
            }
        }
    }
    
    public Predicate getPredicate() {
        return predicate;
    }


	@Override
	public void accept(ISearchCriteria t) {
		LOGGER.info(t);
	}


	

}

