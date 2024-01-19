package com.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialsDTO implements Serializable {

	private static final long serialVersionUID = -5351703617775535342L;
	
	private String login;
	private String password;

}
