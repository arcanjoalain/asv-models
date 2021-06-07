package br.com.asv.model.daos;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import br.com.asv.base.model.daos.IBasePatchDao;
import br.com.asv.base.model.daos.ISearchCriteria;
import br.com.asv.base.model.entities.IBaseEntity;
import br.com.asv.base.model.entities.history.IBaseHistoryListEntity;
import br.com.asv.base.model.enums.StatusEntityEnum;
import br.com.asv.model.exceptions.ObjectNotFoundException;
import br.com.asv.model.exceptions.ServiceException;
import br.com.asv.model.repositories.IBaseRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
@Controller
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class ABaseDao<
	E extends IBaseEntity<I>, 
	I extends Serializable> 
		implements IBasePatchDao<E, I> {

	protected static final String STATUS_ENTITY = "statusEntity";
	protected static final String STR_ID_MISSING = ".id.missing";
	protected static final String STR_NOT_FOUND = ".not.found";
	
	private static final Logger LOGGER = LogManager.getLogger(ABaseDao.class);

	@Autowired
	@Getter(AccessLevel.PROTECTED)
	private IBaseRepository<E, I> repository;

	@Getter(AccessLevel.PROTECTED)
	private final String className;

	@PersistenceContext
	protected EntityManager entityManager;

	private ObjectMapper objectMapper = new ObjectMapper();

	protected Class<E> clazzE;

	@Autowired
	@SuppressWarnings("unchecked")
	public ABaseDao() {
		this.className = ((Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]).getSimpleName().toLowerCase();
		if (clazzE == null) {
			clazzE = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
	}

	@Override
	public E findOne(I id) {
		if (id == null)
			throw new ServiceException(getClassName() + STR_ID_MISSING);

		return getRepository().findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(getClassName() + STR_NOT_FOUND));
	}

	@Override
	public Collection<E> findAll() {
		return StreamSupport.stream(getRepository().findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public Collection<E> findAllSort(String collumnName) {
		return StreamSupport.stream(getRepository().findAll(Sort.by(collumnName)).spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public Page<E> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}

	@Override
	public Collection<E> findAllByStatusEntity(StatusEntityEnum statusEntity) {
		return getRepository().findAllByStatusEntityOrderByPid(statusEntity);
	}
	
	@Override
	public Collection<E> findAllByStatusEntitySortByPid(StatusEntityEnum statusEntity) {
		return getRepository().findAllByStatusEntity(statusEntity, Sort.by("pid"));
	}

	@Override
	public Collection<E> findAllByStatusEntity(StatusEntityEnum statusEntity, Sort sort) {
		return getRepository().findAllByStatusEntity(statusEntity, sort);
	}

	@Override
	@Transactional
	public E save(E entity) {
		System.out.println("Dao SAVE");
		entity = beforeSave(entity);
		entity = getRepository().save(entity);
		return afterSave(entity);

	}

	private E afterSave(E entity) {
		return entity;
	}

	protected E beforeSave(E entity) {
		return entity;
	}

	@Override
	@Transactional
	public E update(E entity) {
		if (entity.getPid() == null)
			throw new ServiceException(getClassName() + STR_ID_MISSING);

		entity = beforeUpdate(entity);
		entity = getRepository().save(entity);
		return afterUpdate(entity);
	}

	protected E beforeUpdate(E entity) {
		E result = entity;
		if (result instanceof IBaseHistoryListEntity) {
			((IBaseHistoryListEntity<?, ?>) result).processHistories();
		}
		return result;
	}

	protected E afterUpdate(E entity) {
		return entity;
	}

	@Override
	public Collection<E> save(Collection<E> entitys) {
		return StreamSupport.stream(getRepository().saveAll(entitys).spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public void delete(I pid) {
		if (pid == null)
			throw new ServiceException(getClassName() + STR_ID_MISSING);

		E entity = getRepository().findById(pid)
				.orElseThrow(() -> new ObjectNotFoundException(getClassName() + STR_NOT_FOUND));
		entity.setStatusEntity(StatusEntityEnum.DISABLED);
		getRepository().save(entity);
	}

	@Override
	public void recovery(I pid) {
		if (pid == null)
			throw new ServiceException(getClassName() + STR_ID_MISSING);

		E entity = getRepository().findById(pid)
				.orElseThrow(() -> new ObjectNotFoundException(getClassName() + STR_NOT_FOUND));
		entity.setStatusEntity(StatusEntityEnum.ENABLED);
		getRepository().save(entity);
	}

	@Override
	public void delete(Collection<E> models) {
		models.forEach(item -> delete(item.getPid()));
	}

	@Override
	public void recovery(Collection<E> models) {
		models.forEach(item -> recovery(item.getPid()));
	}

	@Override
	public List<E> findAll(String search) {
		return findCriteria(search, getClazzE());
	}

	private List<E> findCriteria(String search, Class<E> clazz) {
		List<ISearchCriteria> params = new ArrayList<>();
		try {
			if (clazz.getDeclaredConstructor().newInstance() instanceof IBaseEntity) {
				if (search == null) {
					search = "statusEntity:ENABLED";
				} else {
					if (!search.contains(STATUS_ENTITY)) {
						search = "statusEntity:ENABLED," + search;
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOGGER.error(e.getMessage());
			
		}
		Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
		Matcher matcher = pattern.matcher(search + ",");
		while (matcher.find()) {
			String p1 = matcher.group(1);
			String p3 = matcher.group(3);
			selectStatusEntity(params, matcher, p1, p3);

		}
		return findAll(params, clazz);
	}

	private void selectStatusEntity(Collection<ISearchCriteria> params, MatchResult matcher, String p1, String p3) {
		if (p1.contains(STATUS_ENTITY)) {
			if (!"ALL".equals(p3)) {
				params.add(new SearchCriteria(p1, matcher.group(2), StatusEntityEnum.valueOf(p3)));
			}
		} else {
			if ("true".equals(p3)) {
				params.add(new SearchCriteria(p1, matcher.group(2), Boolean.TRUE));
			} else if ("false".equals(p3)) {
				params.add(new SearchCriteria(p1, matcher.group(2), Boolean.FALSE));
			} else {
				params.add(new SearchCriteria(p1, matcher.group(2), p3));
			}
		}
	}

	@Override
	@Transactional
	public List<E> findAll(List<ISearchCriteria> params, Class<E> clazz) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(clazz);
		Root<E> r = query.from(clazz);

		Predicate predicate = builder.conjunction();

		SearchQueryCriteriaConsumer searchConsumer = new SearchQueryCriteriaConsumer(predicate, builder, r);
		params.stream().forEach(searchConsumer);
		predicate = searchConsumer.getPredicate();
		query.where(predicate);

		return entityManager.createQuery(query).getResultList();
	}

	@Override
	@Transactional
	public List<E> findAll(String search, Class<E> clazz) {
		return findCriteria(search, clazz);
	}

	@Override
	public void remove(I pid) {
		if (pid == null)
			throw new ServiceException(getClassName() + STR_ID_MISSING);

		E entity = getRepository().findById(pid)
				.orElseThrow(() -> new ObjectNotFoundException(getClassName() + STR_NOT_FOUND));
		getRepository().delete(entity);
	}

	@Override
	public void remove(Collection<E> models) {
		models.forEach(item -> remove(item.getPid()));
	}

	@Override
	@Transactional
	public E patch(I id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
		E entity = findOne(id);
		E patched = applyPatch(patch, entity);
		save(patched);
		return patched;

	}

	private E applyPatch(JsonPatch patch, E targetCustomer) throws JsonPatchException, JsonProcessingException {
		JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
		return objectMapper.treeToValue(patched, clazzE);
	}

}
