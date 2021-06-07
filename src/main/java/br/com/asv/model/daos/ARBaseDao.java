package br.com.asv.model.daos;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.asv.base.model.entities.IBaseEntity;
import br.com.asv.model.repositories.IBaseRepository;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class ARBaseDao<
R extends IBaseRepository<E, I>,
E extends IBaseEntity<I>, 
I extends Serializable> extends ABaseDao<E, I> {

	@Autowired
	@Getter(AccessLevel.PROTECTED)
	private R repository;
}
