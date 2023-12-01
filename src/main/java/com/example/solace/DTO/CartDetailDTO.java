package com.example.solace.DTO;

import com.example.solace.Entity.MemberEntity;
import com.example.solace.Entity.ProductEntity;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@ToString
public class CartDetailDTO {
    private Integer id;   //장바구니 번호

    private MemberEntity memberEntity;

    private ProductEntity productEntity;

    private Integer count;
}
