package com.ryuneng.goldauth.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import com.ryuneng.goldauth.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "roleList")
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("사용자 ID")
    private Long id;

    @Column(nullable = false, unique = true)
    @Comment("사용자 이메일")
    private String email;

    @Column(nullable = false)
    @Comment("사용자 비밀번호")
    private String password;

    @Column(nullable = false)
    @Comment("사용자 닉네임")
    private String nickname;

    @Column(nullable = false)
    @Comment("사용자 전화번호")
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private List<Role> roleList = new ArrayList<>();
}
