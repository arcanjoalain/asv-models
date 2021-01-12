package br.com.asv.model.daos;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.asv.model.entities.IBaseEntity;
import br.com.asv.model.enums.StatusEntityEnum;



public interface IBaseDao<E extends IBaseEntity<I>,I> {
	
	E save(E model);
	Collection<E> save(Collection<E> models);

	E update(E model);

	E findOne(I pid);

	Collection<E> findAll();
	Page<E> findAll(Pageable pageable);

	Collection<E> findAllByStatusEntity(StatusEntityEnum statusEntity);
	Page<E> findAllByStatusEntity(Pageable pageable, StatusEntityEnum statusEntity);

	void delete(I pid);
	void delete(Collection<E> models);

	void recovery(I pid);
	void recovery(Collection<E> models);
	Collection<E> findAll(String collumnName);
	
	List<E> findAll(List<SearchCriteria> params, Class<E> clazz);
	List<E> findAll(String search, Class<E> clazz);
	
}
