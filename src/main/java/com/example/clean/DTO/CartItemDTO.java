package com.example.clean.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartItemDTO {

  private Integer itemId;     //장바구니아이템 번호

  private Integer count;      //장바구니 상품 수량

}
