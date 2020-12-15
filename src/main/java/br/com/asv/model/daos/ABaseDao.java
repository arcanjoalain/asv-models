package br.com.asv.model.daos;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.asv.model.entities.IBaseEntity;
import br.com.asv.model.entities.history.IBaseHistoryListEntity;
import br.com.asv.model.enums.StatusEntityEnum;
import br.com.asv.model.exceptions.ObjectNotFoundException;
import br.com.asv.model.exceptions.ServiceException;
import br.com.asv.model.repositories.IBaseRepository;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class ABaseDao<E extends IBaseEntity, R extends IBaseRepository<E>> implements IBaseDao<E>{
	
	protected String strIdMissing= ".id.missing";
	protected String strNotFound= ".not.found";

	@Getter(AccessLevel.PROTECTED)
    private		final	R			repository;

    @Getter(AccessLevel.PROTECTED)
    private 	final	String		className;

    @Autowired
    @SuppressWarnings("unchecked")
	public ABaseDao(R repository) {
        this.repository = repository;
        this.className	= ((Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName().toLowerCase();
    }   
    
    @Override
    public E findOne(Long id) {
        if (id == null)
            throw new ServiceException(getClassName() + strIdMissing);

        return getRepository().findById(id)
        		.orElseThrow(() -> new ObjectNotFoundException(getClassName() + strNotFound));
    }

    @Override
    public Collection<E> findAll() {
        return StreamSupport.stream(getRepository().findAll().spliterator(), false).collect(Collectors.toList());
    }
    
    @Override
    public Collection<E> findAll(String collumnName) {
        return StreamSupport.stream(getRepository().findAll(Sort.by(collumnName)).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }
    
    @Override
	public Collection<E> findAllByStatusEntity(StatusEntityEnum statusEntity) {
		return getRepository().findAllByStatusEntityOrderById(statusEntity);
	}

    @Override
    public Page<E> findAllByStatusEntity(Pageable pageable, StatusEntityEnum statusEntity) {
        return getRepository().findAllByStatusEntityOrderById(statusEntity, pageable);
    }

    @Override
    @Transactional
    public E save(E entity) {
        entity = beforeSave(entity);
        entity = getRepository().save(entity);
        return afterSave(entity);

    }

    private E afterSave(E entity) {
    	return entity;
    }

	protected E beforeSave(E entity){
    	return entity;
    }

	@Override
	@Transactional
    public E update(E entity) {
        if (entity.getId() == null)
            throw new ServiceException(getClassName() + strIdMissing);

        entity = beforeUpdate(entity);
        entity = getRepository().save(entity);
        entity = afterUpdate(entity);
        return entity;
    }

    @SuppressWarnings("rawtypes")
	protected E beforeUpdate(E entity) {
    	if(entity instanceof IBaseHistoryListEntity) {
			((IBaseHistoryListEntity)entity).processHistories();
		}
    	return entity;
    }
    
    protected E afterUpdate(E entity) {
    	return entity;
    }

	@Override
    public Collection<E> save(Collection<E> entitys) {
        return StreamSupport.stream(getRepository().saveAll(entitys).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new ServiceException(getClassName() + strIdMissing);

        E entity = getRepository().findById(id).orElseThrow(() -> new ObjectNotFoundException(getClassName() + strNotFound));
        entity.setStatusEntity(StatusEntityEnum.DISABLED);
        getRepository().save(entity);
    }

    @Override
    public void recovery(Long id) {
        if (id == null)
            throw new ServiceException(getClassName() + strIdMissing);

        E entity = getRepository().findById(id).orElseThrow(() -> new ObjectNotFoundException(getClassName() + strNotFound));
        entity.setStatusEntity(StatusEntityEnum.ENABLED);
        getRepository().save(entity);
    }

    @Override
    public void delete(Collection<E> models) {
        models.forEach(item -> delete(item.getId()));
    }

    @Override
    public void recovery(Collection<E> models) {
        models.forEach(item -> recovery(item.getId()));
    }
}
