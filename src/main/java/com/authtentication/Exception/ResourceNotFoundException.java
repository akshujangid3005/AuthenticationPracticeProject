package com.authtentication.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	String resourceName;
	String fileldName;
	long fieldValue;
	public ResourceNotFoundException(String resourceName, String fileldName, long fieldValue) {
		super(String.format("%s not found with %s : %s" , resourceName,fileldName,fieldValue));
		this.resourceName = resourceName;
		this.fileldName = fileldName;
		this.fieldValue = fieldValue;
	}

}
