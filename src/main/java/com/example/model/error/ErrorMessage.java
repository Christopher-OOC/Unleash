package com.example.model.error;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessage {
	
	@JsonProperty("error_date")
	private Date errorDate = new Date();
	
	private int status;
	
	private String path;
	
	private String message;

}
