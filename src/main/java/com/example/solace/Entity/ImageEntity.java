package com.example.solace.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "image")
@SequenceGenerator(
        name = "image_SEQ",
        sequenceName = "image_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class ImageEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="iamge_SEQ")
    private Integer imageId;

    @Column(nullable = false)
    private String imageFile;

    @Column(nullable = false)
    private Integer imageType; //대표이미지=0, 서브이미지=1, 상세이미지=2

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "productId")
    private ProductEntity productEntity;

}
