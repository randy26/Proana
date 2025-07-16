package com.proana.mapper;
import com.proana.dto.MenuDto;
import com.proana.model.Menu;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MenuMapperTest {

    private final MenuMapper menuMapper = Mappers.getMapper(MenuMapper.class);

    @Test
    void testToDto() {
        Menu menu = Menu.builder()
                .id(1)
                .nombre("Tablero")
                .url("/dashboard")
                .icono("tablero")
                .build();

        MenuDto dto = menuMapper.toDto(menu);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getNombre()).isEqualTo("Tablero");
        assertThat(dto.getUrl()).isEqualTo("/dashboard");
        assertThat(dto.getIcono()).isEqualTo("tablero");
    }

    @Test
    void testToDtoSet() {
        Set<Menu> menus = Set.of(
                new Menu(1, "Tablero", "/dashboard", "tablero"),
                new Menu(2, "Consultas", "/consultas", "consultas")
        );

        Set<MenuDto> dtos = menuMapper.toDtoSet(menus);

        assertThat(dtos).isNotNull();
        assertThat(dtos).hasSize(2);

        assertThat(dtos.stream().map(MenuDto::getNombre))
                .containsExactlyInAnyOrder("Tablero", "Consultas");
    }
}
