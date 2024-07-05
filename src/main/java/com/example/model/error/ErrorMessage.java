package com.example.model.error;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessage {
	
	@JsonProperty("error_date")
	@JsonFormat(shape=Shape.STRING, pattern="HH:mm:ss")
	private Date errorDate = new Date();
	
	private int status;
	
	private String path;
	
	private String message;

}
