package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.example.mapper.CategoryMapper;
import org.example.repositories.CategoryRepository;
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
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final StorageService storageService;

    @GetMapping(path = "/list")
    public ResponseEntity<List<CategoryEntity>> index() {
        List<CategoryEntity> list = categoryRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryItemDTO> create(@ModelAttribute CategoryCreateDTO dto) {
        try {
            CategoryEntity entity = categoryMapper.categoryCreateDTO(dto);
            String image = storageService.saveImage(dto.getImage(), FileSaveFormat.WEBP);
            entity.setImage(image);
            categoryRepository.save(entity);
            return new ResponseEntity<>(categoryMapper.categoryItemDTO(entity), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryItemDTO> edit(@ModelAttribute CategoryCreateDTO dto, @PathVariable("id") Integer id) {
        try {
            Optional<CategoryEntity> tmp = categoryRepository.findById(id);
            CategoryEntity entity = categoryMapper.categoryCreateDTO(dto);
            entity.setId(tmp.get().getId());
            String image = storageService.saveImage(dto.getImage(), FileSaveFormat.WEBP);
            entity.setImage(image);
            categoryRepository.save(entity);
            return new ResponseEntity<>(categoryMapper.categoryItemDTO(entity), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryItemDTO> delete(@PathVariable("id") Integer id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
