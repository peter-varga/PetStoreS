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
public class Order {

	public Order() {}
	
    @JsonProperty
    private long id;
    
    @JsonProperty
    private long petId;
    
    @JsonProperty
    private long quantity;
    
    @JsonProperty
    private String shipDate;
    
    @JsonProperty
    private String status;
    
    @JsonProperty
    private boolean complete;
}
