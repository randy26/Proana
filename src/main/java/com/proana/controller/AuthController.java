package com.proana.controller;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proana.dto.AuthResponseDto;
import com.proana.dto.LoginRequestDto;
import com.proana.dto.MenuDto;
import com.proana.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	 private final AuthService authService;

	 @PostMapping("/login")
	 public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginDto) {
	     return ResponseEntity.ok(authService.login(loginDto));
	 }

	    @GetMapping("/menus")
	    public ResponseEntity<Set<MenuDto>> getMenus(Authentication authentication) {
	        String username = authentication.getName();
	        return ResponseEntity.ok(authService.getMenusByUsuario(username));
	    }
}
