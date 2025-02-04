package com.example.sandbox.util.swagger.definitions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pet {
	
	public Pet() {}
	
    @JsonProperty
    private long id;

    @JsonProperty
    private Item category;

    @JsonProperty
    private String name;

    @Singular()
    @JsonProperty
    private List<String> photoUrls;

    @Singular()
    @JsonProperty
    private List<Item> tags;

    @JsonProperty
    private String status;
    
}
