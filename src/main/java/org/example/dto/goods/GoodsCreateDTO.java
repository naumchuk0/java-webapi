package org.example.dto.goods;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GoodsCreateDTO {
    private String name;
    private MultipartFile image;
    private String description;
}
