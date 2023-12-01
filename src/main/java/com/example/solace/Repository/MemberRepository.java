package com.example.solace.Repository;

import com.example.solace.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    MemberEntity findByEmail(String email);

}
