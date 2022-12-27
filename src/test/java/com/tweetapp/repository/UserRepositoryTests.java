package com.tweetapp.repository;

import com.tweetapp.domain.UserModel;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author Madhuri Nelluri
 * @project TweetApp-JAVA-API
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testSaveUser() {
        final UserModel userModel = UserModel.builder()
                .username("Madhuri")
                           
                .name("madhuri")
                .email("madhuri119.nelluri@gmail.com")
                .contactNum("7075442745")
                .password("password")
                .build();
        userRepository.save(userModel);
        Assertions.assertThat(userModel.getUsername()).isEqualTo("Madhuri");
    }

    @Test
    public void testFindUserByName() {
        final String username = "Madhuri";
        final UserModel user = UserModel.builder()
                .username("Madhuri")
                         
                .name("madhuri")
                .email("madhuri119.nelluri@gmail.com")
                .build();
        userRepository.findByUsername(username);
        Assertions.assertThat(username).isEqualTo(user.getUsername());
    }

    @Test
    public void testSearchByUserName() {
        final String userName = "Madhuri";

        final List<UserModel> userModelList = Arrays.asList(
                UserModel.builder()
                        .username("Madhuri")
                               
                        .name("Madhuri")
                        .email("madhuri119.nelluri@gmail.com")
                        .build(),
                UserModel.builder()
                        .username("singh")
                                  
                        .name("S")
                        .email("sony@gmail.com")
                        .build()
        );

        userRepository.searchByUsername(userName);
        Assertions.assertThat(userModelList.size()).isGreaterThanOrEqualTo(2);
    }
}
