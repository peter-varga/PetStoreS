package com.example.sandbox.util.swagger.definitions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Info {
	
	public Info() {}
	
	@JsonProperty
	private int code;
	
	@JsonProperty
	private String type;
	
	@JsonProperty
	private String message;
}
