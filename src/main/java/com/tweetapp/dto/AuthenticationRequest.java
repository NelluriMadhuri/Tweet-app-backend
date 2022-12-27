package com.tweetapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Madhuri
 * @project TweetApp-JAVA-API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    private String username;

    private String password;


}
