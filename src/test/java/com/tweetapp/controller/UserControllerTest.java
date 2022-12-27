package com.tweetapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.config.constants.Constants;
import com.tweetapp.domain.UserModel;
import com.tweetapp.dto.NewPassword;
import com.tweetapp.exception.PasswordMisMatchException;
import com.tweetapp.service.UserOperationsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author Madhuri Nelluri
 * @project TweetApp-JAVA-API
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

	@Mock
	private UserOperationsService userOperationsService;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	private static String convertToJson(Object ob) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(ob);
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testChangePassword() throws Exception {
		NewPassword newPassword = new NewPassword();
		newPassword.setNewPassword("password");
		newPassword.setContact("1234567890");

		final UserModel model = UserModel.builder().username("Madhuri").password("password").name("Madhuri")
				.contactNum("7075442745").email("madhuri119.nelluri@gmail.com").build();

		Mockito.doReturn(model).when(userOperationsService).changePassword("Madhuri", newPassword.getNewPassword(),
				newPassword.getContact());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/tweets/{username}/forgot", "Madhuri")
				.contentType(MediaType.APPLICATION_JSON).content(convertToJson(model))
				.accept(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void testGetAllUsers() throws Exception {
		final List<UserModel> userModelList = Arrays.asList(
				UserModel.builder().username("Madhuri").name("Madhuri")
						.email("Madhuri@gmail.com").build(),
				UserModel.builder().username("henry").name("ic")
						.email("henry@yahoo.com").build(),
				UserModel.builder().username("mathewjobin").name("mathew")
						.email("mathew@outloook.com").build(),
				UserModel.builder().username("johndoe").name("doe")
						.email("johndoe@smpt.com").build(),
				UserModel.builder().username("bhanu").name("ghantasala")
						.email("bhanu@ipready.com").build());

		Mockito.doReturn(userModelList).when(userOperationsService).getAllUsers();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tweets/users/all");

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void testChangePasswordForPassWordMismatchException() throws Exception {

		NewPassword newPassword = new NewPassword();
		newPassword.setNewPassword("newPassword");
		newPassword.setContact("1234567890");

		Mockito.doThrow(
				new PasswordMisMatchException(Constants.Password_MisMatch_Exception))
				.when(userOperationsService)
				.changePassword("user2", newPassword.getNewPassword(), newPassword.getContact());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/tweets/{username}/forgot", "user2")
				.content(convertToJson(newPassword)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

	@Test
	public void searchForUsers() throws Exception {
		final List<UserModel> userModelList = Arrays.asList(
				UserModel.builder().username("Madhuri").name("Madhuri")
						.email("Madhuri@gmail.com").build(),
				UserModel.builder().username("Madhuri").name("Madhuri")
						.email("Madhuri1@gmail.com").build());

		Mockito.doReturn(userModelList).when(userOperationsService).getUsersByUsername("Madhuri");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tweets/user/search/{username}","Madhuri");

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
}
