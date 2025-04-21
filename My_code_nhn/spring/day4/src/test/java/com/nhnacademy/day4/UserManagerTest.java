package com.nhnacademy.day4;

import com.nhnacademy.day4.entity.User;
import com.nhnacademy.day4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@DataJpaTest
public class UserManagerTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void createUserTest(){
        // given
        String id = "1";
        String password = "1";
        User user1 = new User(id, "nhn1", password, "20250421", User.Auth.ROLE_USER, 10, null, null);
        userRepository.save(user1);

        // when
        User user2 = userRepository.findById(id).orElse(null);

        // then
        assertThat(user2).isNotNull();
        assertThat(user2.getUserId()).isEqualTo(id);
        assertThat(user2.getUserPassword()).isEqualTo(password);
    }

    @Test
    void userNotFoundTest() {
        // given
        String id = "admin";

        // when
        User user = userRepository.findById(id).orElse(null);

        // then
        assertThat(user).isNull();
    }


    @Sql("user-test.sql")
    @Test
    void findUserTest() {
        // given
        String id = "1";
        String password = "1";

        // when
        User user = userRepository.findById(id).orElse(null);

        // then
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(id);
        assertThat(user.getUserPassword()).isEqualTo(password);
    }


}
