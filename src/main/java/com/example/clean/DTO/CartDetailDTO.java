package com.example.clean.DTO;

import com.example.clean.Entity.ProductEntity;
import com.example.clean.Entity.UserEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartDetailDTO {

  private Integer id;                   //장바구니 번호

  private UserEntity userEntity;        //회원정보

  private ProductEntity productEntity;  //상품정보

  private Integer count;                //장바구니 상품 수량

}
