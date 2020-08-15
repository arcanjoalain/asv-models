package br.com.asv.model.daos;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.asv.model.entities.IBaseEntity;
import br.com.asv.model.enums.StatusEntityEnum;



public interface IBaseDao<E extends IBaseEntity> {
	
	E save(E model);
	Collection<E> save(Collection<E> models);

	E update(E model);

	E findOne(Long id);

	Collection<E> findAll();
	Page<E> findAll(Pageable pageable);

	Collection<E> findAllByStatusEntity(StatusEntityEnum statusEntity);
	Page<E> findAllByStatusEntity(Pageable pageable, StatusEntityEnum statusEntity);

	void delete(Long id);
	void delete(Collection<E> models);

	void recovery(Long id);
	void recovery(Collection<E> models);
	Collection<E> findAll(String collumnName);
}
