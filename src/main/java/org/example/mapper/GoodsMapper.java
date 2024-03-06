package org.example.mapper;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.goods.GoodsCreateDTO;
import org.example.dto.goods.GoodsItemDTO;
import org.example.entities.CategoryEntity;
import org.example.entities.GoodsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GoodsMapper {
    @Mapping(target = "image", ignore = true)
    GoodsEntity goodsCreateDTO(GoodsCreateDTO dto);

    @Mapping(target = "name", source = "name", dateFormat = "dd.MM.yyyy HH:mm:ss")
    GoodsItemDTO goodsItemDTO(GoodsEntity goods);
}
