package com.tweetapp.repository;

import com.tweetapp.domain.UserModel;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Madhuri Nelluri
 * @project TweetApp-JAVA-API
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class TweetRepositoryTests {

    @Autowired
    private TweetRepository userRepository;

    @Test
    public void testFindUserByName() {
        final String username = "Madhuri";
        final UserModel user = UserModel.builder()
                .username("Madhuri")
              
                .name("Madhuri")
                .email("madhuri119.nelluri@gmail.com")
                .build();
        userRepository.findByUsername(username);
        Assertions.assertThat(username).isEqualTo(user.getUsername());
    }

}
