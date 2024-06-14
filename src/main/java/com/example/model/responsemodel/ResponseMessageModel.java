package com.example.model.responsemodel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseMessageModel {

	private ResponseStatusType responseStatusType;
	
	private RequestStatusType RequestStatusType;
}
