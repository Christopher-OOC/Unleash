package com.example.model.error;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessages {
	
	@JsonProperty("error_date")
	@JsonFormat(shape=Shape.STRING, pattern="HH:mm:ss")
	private Date errorDate = new Date();
	
	private int status;
	
	private String path;
	
	private List<String> messages = new ArrayList<>();

}
