package com.tweetapp.service;

import com.tweetapp.domain.UserModel;
import com.tweetapp.exception.PasswordMisMatchException;
import com.tweetapp.exception.UsernameAlreadyExists;
import com.tweetapp.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Madhuri Nelluri
 * @project TweetApp-JAVA-API
 */
@Log4j2
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserOperationsServiceTest {

    @InjectMocks
    private UserOperationsService userOperationsService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindUserByName() {
        final String username = "ramya@gmail.com";
        final UserModel user = UserModel.builder()
                .username("ramya@gmail.com")
           
                .name("Nelluri")
                .email("ramya@gmail.com")
                .build();

        when(userRepository.findByUsername(username)).thenReturn(user);
        UserModel userModel = userOperationsService.findByUsername(username);
        Assert.assertEquals(user.getUsername(), userModel.getUsername());
        log.debug("Tested - #MethodName: findUserByName() successfully.");

    }

    @Test
    public void testChangePassword_IfCondition() throws PasswordMisMatchException {

        final UserModel model = UserModel.builder()
                .username("henry@yahoo.com")
                .password("newPassword")
               
                .name("ic")
                .contactNum("1234567890")
                .email("henry@yahoo.com").build();

        when(userRepository.findByUsername("henry@yahoo.com")).thenReturn(model);
        when(userRepository.save(model)).thenReturn(model);
        UserModel userModel = userOperationsService.changePassword("henry@yahoo.com", "newPassword", "1234567890");
        Assert.assertEquals(userModel.getContactNum(), model.getContactNum());
        log.debug("Tested - #MethodName: changePassword() successfully.");
    }

    @Test
    public void testChangePassword_ElseCondition() {

        final UserModel model = UserModel.builder()
                .username("henry@yahoo.com")
                .password("newPassword")
               
                .name("ic")
                .contactNum("123")
                .email("henry@yahoo.com").build();
        when(userRepository.findByUsername("henry@yahoo.com")).thenReturn(model);
        assertThrows(PasswordMisMatchException.class,
                () -> userOperationsService.changePassword("henry@yahoo.com",
                        "newPassword", "1234567890"));
        log.debug("Tested - #MethodName: changePassword() successfully.");
    }

    @Test
    public void testSearchByUserName() {
        final String userName = "ramya";

        final List<UserModel> userModelList = Arrays.asList(
                UserModel.builder()
                        .username("ramya@gmail.com")
                        
                        .name("Nelluri")
                        .email("ramya@gmail.com")
                        .build(),
                UserModel.builder()
                        .username("ramya@gmail.com")
                        
                        .name("here")
                        .email("ramya@gmail.com")
                        .build()
        );
        when(userRepository.searchByUsername(userName)).thenReturn(userModelList);
        final List<UserModel> listOfUsers = userOperationsService.getUsersByUsername(userName);
        Assert.assertEquals(userModelList.size(), listOfUsers.size());
        log.debug("Tested - #MethodName: searchByUserName() successfully.");
    }

    @Test
    public void testGetAllUsers() {

        final List<UserModel> userModelList = Arrays.asList(
                UserModel.builder().username("ramya@gmail.com").name("Nelluri").email("ramya@gmail.com").build(),
                UserModel.builder().username("henry@yahoo.com").name("ic").email("henry@yahoo.com").build(),
                UserModel.builder().username("mathew@outloook.com").name("ew").email("mathew@outloook.com").build(),
                UserModel.builder().username("johndoe@smpt.com").name("doe").email("johndoe@smpt.com").build(),
                UserModel.builder().username("heysup@posal.com").name("sup").email("heysup@posal.com").build()
        );
        when(userRepository.findAll()).thenReturn(userModelList);
        final List<UserModel> listOfUsers = userOperationsService.getAllUsers();
        Assert.assertEquals(userModelList.size(), listOfUsers.size());
        log.debug("Tested - #MethodName: getAllUsers() successfully.");
    }

    @Test
    public void testCreateUserThrowingUsernameAlreadyExists() {
        final UserModel model = UserModel.builder()
                .username("henry@yahoo.com")
                .password("newPassword")
                .name("ic")
                .contactNum("1234567890")
                .email("henry@yahoo.com").build();

        when(userRepository.findByUsername("henry@yahoo.com")).thenReturn(model);
        assertThrows(UsernameAlreadyExists.class, () -> userOperationsService.createUser(model));
    }

    @Test
    public void testCreateUser() throws UsernameAlreadyExists {
        final UserModel model = UserModel.builder()
                .username("henry@yahoo.com")
                .password("newPassword")
                .name("ic")
                .contactNum("1234567890")
                .email("henry@yahoo.com").build();

        when(userRepository.save(any())).thenReturn(model);
        UserModel user = userOperationsService.createUser(model);
        assertNotNull(user);
    }
}
