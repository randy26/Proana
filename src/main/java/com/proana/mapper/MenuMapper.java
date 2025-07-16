package com.proana.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.proana.dto.MenuDto;
import com.proana.model.Menu;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    // Mapea un solo objeto
    MenuDto toDto(Menu menu);
    Menu toEntity(MenuDto menuDto);

    // Mapea listas y sets
    List<MenuDto> toDtoList(List<Menu> menus);
    List<Menu> toEntityList(List<MenuDto> menusDto);

    Set<MenuDto> toDtoSet(Set<Menu> menus);
    Set<Menu> toEntitySet(Set<MenuDto> menusDto);
}




