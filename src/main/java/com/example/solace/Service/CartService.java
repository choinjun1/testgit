package com.example.solace.Service;

import com.example.solace.DTO.CartDetailDTO;
import com.example.solace.DTO.CartItemDTO;
import com.example.solace.DTO.ProductDTO;
import com.example.solace.Entity.CartEntity;
import com.example.solace.Entity.MemberEntity;
import com.example.solace.Entity.ProductEntity;
import com.example.solace.Repository.CartRepository;
import com.example.solace.Repository.MemberRepository;
import com.example.solace.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    //장바구니 추가
    public void addCart(CartItemDTO cartItemDTO, String email) {
        //상품조회
        ProductEntity productEntity = productRepository.findById(cartItemDTO.getItemId()).orElseThrow(EntityNotFoundException::new);
        //회원조회
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        //등록된 장바구니조회
        CartEntity cartEntity = cartRepository.findByMemberEntityAndProductEntity(memberEntity, productEntity);

        if (cartEntity == null) { //해당회원의  등록된 상품 장바구니가 없다면 장바구니에 회원번호와 상품번호, 수량을 등록
            cartEntity = cartEntity.builder()
                .memberEntity(memberEntity)
                .productEntity(productEntity)
                .count(cartItemDTO.getCount())
                .build();
        } else { //동일한 상품이 존재한다면 수량을 추가
            cartEntity.setCount(cartEntity.getCount()+cartItemDTO.getCount()); //기존수량+신규수량
        }

        cartRepository.save(cartEntity);
    }

    //장바구니에 상품 수량변경
    //해당 장바구니 번호를 조회하여 수량을 변경한다.
    public void updateCartCount(Integer cartId, int count) {
        CartEntity cartEntity = cartRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);

        cartEntity.setCount(cartEntity.getCount()+count);
        cartRepository.save(cartEntity);
    }

    //장바구니의 상품삭제
    public void deleteCartItem(Integer cartId) {
        Optional<CartEntity> cartEntity = cartRepository.findById(cartId);
        if(cartEntity != null) { //등록된 상품이 있으면
            cartRepository.deleteById(cartId); //삭제처리
        }
    }

    //회원의 장바구니 상품목록
    public List<CartDetailDTO> getCartList(String email) {
        List<CartEntity> cartEntities = new ArrayList<>();
        //회원조회
        MemberEntity memberEntity = memberRepository.findByEmail(email);

        //해당회원의 장바구니가 존재하면 장바구니에 상품을 조회한다.
        cartEntities = cartRepository.findAllByMemberEntity(memberEntity);

        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();
        for(CartEntity entity:cartEntities) {
            CartDetailDTO temp = CartDetailDTO.builder()
                    .id(entity.getId())
                    .memberEntity(entity.getMemberEntity())
                    .productEntity(entity.getProductEntity())
                    .count(entity.getCount())
                    .build();
            cartDetailDTOList.add(temp);
        }

        return cartDetailDTOList;
    }
}