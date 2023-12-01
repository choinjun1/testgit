package com.example.solace.Repository;

import com.example.solace.DTO.CartDetailDTO;
import com.example.solace.Entity.CartEntity;
import com.example.solace.Entity.MemberEntity;
import com.example.solace.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    CartEntity findByMemberEntityAndProductEntity(MemberEntity memberEntity, ProductEntity productEntity);

    List<CartEntity> findAllByMemberEntity(MemberEntity memberEntity);

}
