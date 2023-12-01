package com.example.clean.Service;

import com.example.clean.DTO.CartDetailDTO;
import com.example.clean.DTO.CartItemDTO;
import com.example.clean.Entity.CartEntity;
import com.example.clean.Entity.ProductEntity;
import com.example.clean.Entity.UserEntity;
import com.example.clean.Repository.CartRepository;
import com.example.clean.Repository.MemberRepository;
import com.example.clean.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final MemberRepository memberRepository;
  private final ModelMapper modelMapper = new ModelMapper();


  //장바구니 추가
  public void addCart(CartItemDTO cartItemDTO, String email) throws Exception {
    
    //상품조회 - ProductEntity를 찾을 때 cartItemDTO.getItemId() 사용
    ProductEntity productEntity = productRepository.findById(cartItemDTO.getItemId())
        .orElseThrow(EntityNotFoundException::new);
    //회원조회
    UserEntity userEntity = memberRepository.findByEmail(email);

    //등록된 장바구니 조회 - 회원과 상품번호
    CartEntity cartEntity = cartRepository.findByUserEntityAndProductEntity(userEntity, productEntity);

    //해당회원의  등록된 상품 장바구니가 없다면 장바구니에 회원번호와 상품번호, 수량을 등록
    if (cartEntity == null) {
      cartEntity = cartEntity.builder()
          .userEntity(userEntity)
          .productEntity(productEntity)
          .count(cartItemDTO.getCount())
          .build();
    } else {    //동일한 상품이 존재한다면 수량을 추가
      cartEntity.setCount(cartEntity.getCount()+cartItemDTO.getCount());  //기존수량+신규수량
    }
    cartRepository.save(cartEntity);
  }


  //회원의 장바구니 상품목록
  public List<CartDetailDTO> getCartList(String email) throws Exception {
    
    //장바구니 목록
    List<CartEntity> cartEntities = new ArrayList<>();

    //회원조회
    UserEntity userEntity = memberRepository.findByEmail(email);

    //해당회원의 장바구니가 존재하면 장바구니에 상품을 조회
    cartEntities = cartRepository.findAllByUserEntity(userEntity);

    List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();
    for(CartEntity entity:cartEntities) {
      CartDetailDTO temp = CartDetailDTO.builder()
          .id(entity.getId())
          .userEntity(entity.getUserEntity())
          .productEntity(entity.getProductEntity())
          .count(entity.getCount())
          .build();
      cartDetailDTOList.add(temp);
    }
    return cartDetailDTOList;
  }


  //장바구니에 상품 수량변경 (해당 장바구니 번호를 조회하여 수량을 변경)
  public void updateCartCount(Integer cartId, int count) throws Exception
  {
    CartEntity cartEntity = cartRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);
    
    // 최소 제품 수량은 1개로 설정 & 증가, 감소 시킬 수 있음
    int updatedCount = Math.max(cartEntity.getCount() + count, 1);
    cartEntity.setCount(updatedCount);

    cartRepository.save(cartEntity);
  }


  //장바구니의 상품 개별 삭제
  //개별 삭제는 장바구니에 담긴 특정 상품의 ID (Integer cartId)를 기반으로 삭제
  public void deleteCartItem(Integer cartId) throws Exception {

    cartRepository.findById(cartId).ifPresent(cartEntity -> cartRepository.delete(cartEntity));

  }


  // 장바구니의 모든 상품 삭제
  //사용자의 장바구니에 담긴 모든 상품을 삭제하는 것이기 때문에 해당 사용자의 이메일 주소 (String email)를 기반으로 삭제
  public void deleteAllCartItems(String email) throws Exception {

    UserEntity userEntity = memberRepository.findByEmail(email);

    if (userEntity != null) {
      // 해당 사용자의 장바구니에 있는 모든 상품을 삭제
      List<CartEntity> cartEntities = cartRepository.findAllByUserEntity(userEntity);
      cartRepository.deleteAll(cartEntities);
    }
  }

}
