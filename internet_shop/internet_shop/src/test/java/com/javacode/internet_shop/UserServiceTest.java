//package com.javacode.internet_shop;
//
//import com.javacode.internet_shop.model.User;
//import com.javacode.internet_shop.repository.UserRepository;
//import com.javacode.internet_shop.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    public void getUserById_Id1_returnOlya() {
//        long userId = 1;
//        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(new User("Kolya", "kolya@gmail.com")));
//
//        User foundUser = userService.findUserById(1);
//
//        Mockito.verify(userRepository.findById(1), (1)).findById(1);
//
//    }


//}
