package com.tweetapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Madhuri Nelluri
 * @project TweetApp-JAVA-API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPassword {
	private String newPassword;
	private String contact;

}
