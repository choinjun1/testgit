package com.example.solace.DTO;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ImageDTO {
    private Integer imageId;
    private String imageFile;
    private Integer imageType; //대표이미지=0, 서브이미지=1, 상세이미지=2
    private Integer productId;

}
