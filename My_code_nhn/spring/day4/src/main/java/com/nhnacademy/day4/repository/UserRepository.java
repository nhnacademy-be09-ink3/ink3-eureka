package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
