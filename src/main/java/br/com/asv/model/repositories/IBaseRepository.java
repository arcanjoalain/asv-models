package br.com.asv.model.repositories;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import br.com.asv.base.model.entities.IBaseEntity;
import br.com.asv.base.model.enums.StatusEntityEnum;

@Transactional
@NoRepositoryBean
public interface IBaseRepository<E extends IBaseEntity<I>,I extends Serializable> extends JpaRepository<E, I>, JpaSpecificationExecutor<E>{

	Collection<E> findAllByStatusEntityOrderByPid(StatusEntityEnum statusEntity);

	Collection<E> findAllByStatusEntity(StatusEntityEnum statusEntity, Sort sort);
    
    List<E> findAll();
}
