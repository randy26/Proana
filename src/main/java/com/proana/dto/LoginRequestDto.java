package com.proana.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para recibir credenciales de login.
 */
@Data
public class LoginRequestDto {
	@NotBlank
	private String usuario;

	@NotBlank
	private String password;
}
