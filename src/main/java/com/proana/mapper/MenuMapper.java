package com.proana.mapper;

import com.proana.dto.MenuDto;
import com.proana.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    MenuDto toDto(Menu menu);

    Set<MenuDto> toDtoSet(Set<Menu> menus);
}
