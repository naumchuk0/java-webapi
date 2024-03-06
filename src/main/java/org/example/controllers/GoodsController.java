package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.goods.GoodsCreateDTO;
import org.example.dto.goods.GoodsItemDTO;
import org.example.entities.CategoryEntity;
import org.example.entities.GoodsEntity;
import org.example.mapper.CategoryMapper;
import org.example.mapper.GoodsMapper;
import org.example.repositories.CategoryRepository;
import org.example.repositories.GoodsRepository;
import org.example.storage.FileSaveFormat;
import org.example.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/goods")
public class GoodsController {
    private final GoodsRepository goodsRepository;
    private final GoodsMapper goodsMapper;
    private final StorageService storageService;

    @GetMapping(path = "/list")
    public ResponseEntity<List<GoodsEntity>> index() {
        List<GoodsEntity> list = goodsRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GoodsItemDTO> create(@ModelAttribute GoodsCreateDTO dto) {
        try {
            GoodsEntity entity = goodsMapper.goodsCreateDTO(dto);
            String image = storageService.saveImage(dto.getImage(), FileSaveFormat.WEBP);
            entity.setImage(image);
            goodsRepository.save(entity);
            return new ResponseEntity<>(goodsMapper.goodsItemDTO(entity), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
