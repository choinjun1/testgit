package com.example.clean.Repository;

import com.example.clean.Entity.CartEntity;
import com.example.clean.Entity.ProductEntity;
import com.example.clean.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

  //회원과 상품조회
  CartEntity findByUserEntityAndProductEntity(UserEntity userEntity, ProductEntity productEntity);

  //회원조회
  List<CartEntity> findAllByUserEntity(UserEntity userEntity);

}
