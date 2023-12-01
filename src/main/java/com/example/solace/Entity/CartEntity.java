package com.example.solace.Entity;

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
@SequenceGenerator(
        name = "cart_SEQ",
        sequenceName = "cart_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class CartEntity extends BaseEntity{
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_SEQ")
    private Integer id;   //장바구니 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Product_id")
    private ProductEntity productEntity;

    private Integer count;
}
