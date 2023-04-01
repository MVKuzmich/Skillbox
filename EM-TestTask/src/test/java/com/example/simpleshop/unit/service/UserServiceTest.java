package com.example.simpleshop.unit.service;

import com.example.simpleshop.dto.ReplenishBalanceDto;
import com.example.simpleshop.dto.UserCreateEditDto;
import com.example.simpleshop.dto.UserReadDto;
import com.example.simpleshop.entity.User;
import com.example.simpleshop.enums.Role;
import com.example.simpleshop.mapper.UserCreateEditMapper;
import com.example.simpleshop.mapper.UserReadMapper;
import com.example.simpleshop.repository.MessageRepository;
import com.example.simpleshop.repository.MessageSendingRepository;
import com.example.simpleshop.repository.UserRepository;
import com.example.simpleshop.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserReadMapper userReadMapper;
    @Mock
    private UserCreateEditMapper userCreateEditMapper;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessageSendingRepository messageSendingRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void checkCreateMethod() {

        UserCreateEditDto userCreateEditDto = getUserCreateEditDto();

        User user = getUser(userCreateEditDto.getUsername(),
                userCreateEditDto.getEmail(),
                userCreateEditDto.getPassword());
        User userEntity = getUserEntity(99L, user.getUsername(), user.getEmail(), user.getPassword());

        UserReadDto userReadDto = getUserReadDto();

        doReturn(user).when(userCreateEditMapper).map(any(UserCreateEditDto.class));
        doReturn(userEntity).when(userRepository).save(any(User.class));
        doReturn(userReadDto).when(userReadMapper).toUserReadDto(any(User.class));

        assertThat(userService.create(userCreateEditDto)).isEqualTo(userReadDto);

        verify(userCreateEditMapper).map(any(UserCreateEditDto.class));
        verify(userRepository).save(any(User.class));
        verify(userReadMapper).toUserReadDto(any(User.class));
    }


    @Test
    void checkReplenishBalanceMethod() {
        ReplenishBalanceDto replenishBalanceDto = new ReplenishBalanceDto(99L, BigDecimal.TEN);
        User user = getUserEntity(replenishBalanceDto.getUserId(), "mk", "mk@gmail.com", "123");
        UserReadDto expected = UserReadDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .balance(user.getBalance().add(replenishBalanceDto.getReplenishAmount()))
                .role(user.getRole().name())
                .build();
        doReturn(Optional.of(user)).when(userRepository).findById(99L);
        doReturn(expected).when(userReadMapper).toUserReadDto(user);

        UserReadDto actual = userService.replenishBalance(replenishBalanceDto);
        assertThat(actual).isEqualTo(expected);

        verify(userRepository).findById(replenishBalanceDto.getUserId());
        verify(userReadMapper).toUserReadDto(user);

    }

    private UserReadDto getUserReadDto() {
        return UserReadDto.builder()
                .id(99L)
                .username("test")
                .email("test@gmail.com")
                .balance(BigDecimal.TEN)
                .role(Role.USER.name())
                .build();
    }

    private User getUserEntity(Long id, String username, String email, String password) {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .balance(BigDecimal.TEN)
                .role(Role.USER)
                .productCart(null)
                .build();
    }

    private User getUser(String username, String email, String password) {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .balance(BigDecimal.TEN)
                .role(Role.USER)
                .productCart(null)
                .build();
    }

    private UserCreateEditDto getUserCreateEditDto() {
        return UserCreateEditDto.builder()
                .username("test")
                .email("test@gmail.com")
                .password("123")
                .build();
    }


}