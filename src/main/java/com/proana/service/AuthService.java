package com.proana.service;

import java.util.Set;

import com.proana.dto.AuthResponseDto;
import com.proana.dto.LoginRequestDto;
import com.proana.dto.MenuDto;

public interface AuthService {
	AuthResponseDto login(LoginRequestDto loginRequest);
	Set<MenuDto> getMenusByUsuario(String usuario);
}
