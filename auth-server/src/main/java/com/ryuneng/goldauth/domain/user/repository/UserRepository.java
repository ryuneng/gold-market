package com.ryuneng.goldauth.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ryuneng.goldauth.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); // 주어진 유저 아이디에 해당하는 User 존재 여부 확인

}
