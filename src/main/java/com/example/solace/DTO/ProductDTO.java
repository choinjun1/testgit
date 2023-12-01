package com.example.solace.DTO;

import com.example.solace.Constant.CategoryTypeRole;
import com.example.solace.Constant.SellStateRole;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//상품과 이미지파일 적용
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDTO {
    //기본키
    private Integer productId;
    //상품이름
    private String productName;
    //상품설명
    private String productContent;
    //상품상세정보
    private String productDetail;
    //소비자가(정가)
    private int productCost;
    //판매가
    private int productPrice;
    //할인율
    private int productDis;
    //상품옵션
    private int productOpt;
    //상품재고수량
    private int productCnt;
    //상품좋아요(관심,찜)
    private int productLike;
    //상품조회수
    private int productViewcnt;
    private CategoryTypeRole categoryTypeRole;
    //상품판매상태 (SELL, STOP, LACK) - 데이터베이스에 Enum 값을 문자열로 저장하도록 지정
    private SellStateRole sellStateRole;

    //이미지 관리 DTO
    private List<ImageDTO> imageDTOs;
    //이미지파일 처리
    private List<MultipartFile> images;
}
