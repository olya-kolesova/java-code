package com.javacode.internet_shop;

import com.javacode.internet_shop.model.User;
import com.javacode.internet_shop.repository.UserRepository;
import com.javacode.internet_shop.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("Kolya");
        user.setEmail("kolya@gmail.com");
    }

    @Test
    @Order(1)
    public void saveUser_givenUser_isNotNull() {

        given(userRepository.save(user)).willReturn(user);

        User savedUser = userService.save(user);

        assertThat(savedUser).isNotNull();
    }

    @Test
    @Order(2)
    public void getUser_id1_isNotNull() {
        given(userRepository.findById(1)).willReturn(Optional.of(user));

        User existingUser = userService.findUserById(1);

        assertThat(existingUser).isNotNull();
    }

    @Test
    @Order(3)
    public void getAllUsers_isEqual2() {
        User anotherUser = new User();
        anotherUser.setName("Petya");
        anotherUser.setEmail("petya@gmail.com");

        given(userRepository.findAll()).willReturn(List.of(user, anotherUser));

        List<User> list = userService.getUsers();

        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @Order(4)
    public void updateUser_givenUser_isNotNull() {
        given(userRepository.findById(1)).willReturn(Optional.of(user));
        user.setName("Nikolay");
        user.setEmail("nikolay@gmail.com");
        given(userRepository.save(user)).willReturn(user);

        User updatedUser = userService.save(user);

         assertThat(updatedUser.getName()).isEqualTo("Nikolay");
         assertThat(updatedUser.getEmail()).isEqualTo("nikolay@gmail.com");
    }

    @Test
    @Order(5)
    public void deleteUser_id_verified() {
        willDoNothing().given(userRepository).deleteById(1L);

        userService.removeUser(1);

        verify(userRepository, times(1)).deleteById(1L);
    }


}
