package com.example.clean.Entity;


import lombok.*;

import javax.persistence.*;

//회원별 장바구니 관리
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
@SequenceGenerator(name = "cart_SEQ",
                    sequenceName = "cart_SEQ",
                    initialValue = 1,
                    allocationSize = 1)

public class CartEntity extends BaseEntity {

  //기본키 - 장바구니 번호
  @Id
  @Column(name = "cart_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_SEQ")
  private Integer id;

  //외래키 - 회원번호
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  private UserEntity userEntity;

  //외래키 - 상품번호
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="productIds")
  private ProductEntity productEntity;

  private Integer count;        //장바구니 상품 수량

}
