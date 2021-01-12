package br.com.asv.model.repositories;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import br.com.asv.model.entities.IBaseEntity;
import br.com.asv.model.enums.StatusEntityEnum;

@Transactional
@NoRepositoryBean
public interface IBaseRepository<E extends IBaseEntity<I>,I> extends JpaRepository<E, I>, JpaSpecificationExecutor<E>{

	Collection<E> findAllByStatusEntityOrderById(StatusEntityEnum statusEntity);

    Page<E> findAllByStatusEntityOrderById(StatusEntityEnum statusEntity, Pageable pageable);
    
    List<E> findAll();
}
