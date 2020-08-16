package br.com.asv.model.dtos;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import br.com.asv.model.entities.IBaseEntity;
import br.com.asv.model.enums.StatusEntityEnum;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class ABaseDto implements IBaseDto{

	protected Long pid;
	
    protected StatusEntityEnum statusEntity;
    
    protected Date dateAt;
    
    protected Long createUserID;
    
    public IBaseEntity toModel() {
    	return toModel(true);
    }
    
}
