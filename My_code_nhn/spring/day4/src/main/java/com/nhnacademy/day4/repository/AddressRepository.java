package com.nhnacademy.day4.repository;

import com.nhnacademy.day4.entity.Address;
import com.nhnacademy.day4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.chrono.IsoChronology;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    // save -  JpaRepository 기본 지원
    // update -  JpaRepository 기본 지원
    // findById -  JpaRepository 기본 지원
    // delete -  JpaRepository 기본 지원
    // count - JpaRepository 기본 지원

    List<Address> findAddressesByUser(User user);


}
