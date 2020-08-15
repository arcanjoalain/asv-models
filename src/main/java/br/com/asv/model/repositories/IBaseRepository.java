package br.com.asv.model.repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.asv.model.entities.IBaseEntity;
import br.com.asv.model.enums.StatusEntityEnum;

@NoRepositoryBean
public interface IBaseRepository<E extends IBaseEntity> extends PagingAndSortingRepository<E, Long>, JpaSpecificationExecutor<E>{

	Collection<E> findAllByStatusEntityOrderById(StatusEntityEnum statusEntity);

    Page<E> findAllByStatusEntityOrderById(StatusEntityEnum statusEntity, Pageable pageable);
    
    Collection<E> findAll();
}
