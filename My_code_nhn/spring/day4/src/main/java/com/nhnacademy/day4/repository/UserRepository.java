package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.User;
import com.nhnacademy.day4.page.Page;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    // save -  JpaRepository 기본 지원
    // update -  JpaRepository 기본 지원
    // findAll - JpaRepository 기본 지원

    Optional<User> findUserByUserIdAndUserPassword(String userId, String password);
    Optional<User> findUserByUserId(String userId);
    int deleteByUserId(String userId);
    @Query("UPDATE User u SET u.latestLoginAt = :productId WHERE u.latestLoginAt = :latestLoginAt")
    int updateUserByUserId(String id, LocalDateTime latestLoginAt); //  updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt)
    int countByUserId(String useId);
    int countByUserAuth(User.Auth auth);
}
