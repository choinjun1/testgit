package com.example.solace.Entity;

import com.example.solace.Constant.CategoryTypeRole;
import com.example.solace.Constant.SellStateRole;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "product")
@SequenceGenerator(
        name = "product_SEQ",
        sequenceName = "product_SEQ",
        initialValue = 1,
        allocationSize = 1
)

public class ProductEntity extends BaseEntity {

    //기본키
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_SEQ")
    private Integer productId;

    //상품이름
    @Column(name ="productName", length = 50, nullable = false)
    private String productName;

    //상품설명
    @Column(name ="productContent", length = 200, nullable = false)
    private String productContent;

    //상품상세정보
    @Lob
    @Column(name ="productDetail", length = 500)
    private String productDetail;

    //소비자가(정가)
    @Column(name ="productCost", nullable = false)
    private Integer productCost;

    //판매가
    @Column(name ="productPrice", nullable = false)
    private Integer productPrice;

    //할인율
    @Column(name ="productDis")
    private Integer productDis;

    //상품옵션
    @Column(name ="productOpt")
    private Integer productOpt;

    //상품재고수량
    @Column(name ="productCnt", nullable = false)
    private Integer productCnt;

    //상품좋아요(관심,찜)
    @Column(name ="productLike")
    private Integer productLike;

    //상품조회수
    @ColumnDefault("0")
    @Column(name ="productViewcnt")
    private Integer productViewcnt;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type_role")
    private CategoryTypeRole categoryTypeRole;

    //상품판매상태 (SELL, STOP, LACK) - 데이터베이스에 Enum 값을 문자열로 저장하도록 지정
    @Enumerated(EnumType.STRING)
    private SellStateRole sellStateRole;

    //이미지작업을 위한 외래키적용
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<ImageEntity> productImages = new ArrayList<>();
}
