package com.nhnacademy.day4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {
    public enum Auth{
        ROLE_ADMIN,ROLE_USER
    }

    @Id
    @Column(name = "user_id")
    private String userId;

    @Length(min = 1, max = 20)
    @NotNull
    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Setter
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_birth")
    private String userBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_auth")
    private Auth userAuth;

    @Column(name = "user_point")
    private int userPoint;

    @NotNull
    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "last_login_at")
    private LocalDateTime latestLoginAt;

    @OneToMany(mappedBy = "user")
    private List<Address> addressList;


    public User(String userId, String userName, String userPassword, String userBirth, Auth userAuth, int userPoint, LocalDateTime createdAt, LocalDateTime latestLoginAt) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userBirth = userBirth;
        this.userAuth = userAuth;
        this.userPoint = userPoint;
        this.createdAt = createdAt;
        this.latestLoginAt = latestLoginAt;
    }
}
