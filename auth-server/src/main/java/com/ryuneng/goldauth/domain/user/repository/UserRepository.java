package com.ryuneng.goldauth.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ryuneng.goldauth.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // 주어진 이메일에 해당하는 User 확인

}
