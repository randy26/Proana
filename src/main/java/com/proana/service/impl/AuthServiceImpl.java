package com.proana.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proana.configuration.security.JwtTokenProvider;
import com.proana.dto.AuthResponseDto;
import com.proana.dto.LoginRequestDto;
import com.proana.dto.MenuDto;
import com.proana.mapper.MenuMapper;
import com.proana.model.Menu;
import com.proana.model.Usuario;
import com.proana.repository.UsuarioRepository;
import com.proana.service.AuthService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Servicio de autenticación que maneja el login de usuarios.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MenuMapper menuMapper;
    
    /**
     * Valida las credenciales del usuario.
     *
     * @param loginRequest objeto con usuario y password.
     * @return Usuario autenticado si las credenciales son válidas.
     * @throws SecurityException si las credenciales son inválidas.
     */
    @Transactional
	@Override
	public AuthResponseDto login(LoginRequestDto request) {
    	Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario())
    	        .orElseThrow(() -> new SecurityException("Usuario no encontrado"));

    	    //if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {// por el momento se comenta hasta dar de alta un susario se guarde hasheada
    		if (!request.getPassword().equals(usuario.getPassword())) {
    	        LOGGER.warn("Contraseña inválida para '{}'", request.getUsuario());
    	        throw new SecurityException("Credenciales inválidas");
    	    }

    	    var auth = new UsernamePasswordAuthenticationToken(usuario.getUsuario(), null);
    	    String token = jwtTokenProvider.generateToken(auth);

    	    LOGGER.info("Usuario '{}' autenticado correctamente", usuario.getUsuario());
    	    return new AuthResponseDto(token, usuario.getUsuario());
    	}
    
	    @Override
	    public Set<MenuDto> getMenusByUsuario(String nombreUsuario) {
	        Usuario usuarioEntity = usuarioRepository.findByUsuario(nombreUsuario)
	            .orElseThrow(() -> new SecurityException("Usuario no encontrado"));

	        Set<MenuDto> menusDto = new HashSet<>();
	        
	        for (Menu menu : usuarioEntity.getMenus()) {
	            MenuDto dto = new MenuDto();
	            // Aquí seteas los campos manualmente según tu MenuDto
	            dto.setId(menu.getId());
	            dto.setNombre(menu.getNombre());
	            dto.setUrl(menu.getUrl());
	            dto.setIcono(menu.getIcono());
	            // ... otros campos que tenga MenuDto

	            menusDto.add(dto);
	        }
	        
	        return menusDto;
	    }
	    /*public Set<MenuDto> getMenusByUsuario(String nombreUsuario) {
	        Usuario usuarioEntity = usuarioRepository.findByUsuario(nombreUsuario)
	            .orElseThrow(() -> new SecurityException("Usuario no encontrado"));
	
	        return menuMapper.toDtoSet(usuarioEntity.getMenus());
	    }*/
}
