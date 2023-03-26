package com.example.simpleshop.service;

import com.example.simpleshop.dto.*;
import com.example.simpleshop.entity.Message;
import com.example.simpleshop.entity.MessageSending;
import com.example.simpleshop.entity.User;
import com.example.simpleshop.mapper.UserCreateEditMapper;
import com.example.simpleshop.mapper.UserReadMapper;
import com.example.simpleshop.repository.MessageRepository;
import com.example.simpleshop.repository.MessageSendingRepository;
import com.example.simpleshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final MessageRepository messageRepository;
    private final MessageSendingRepository messageSendingRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s not found", username)));
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::toUserReadDto)
                .orElseThrow();
    }

    @Transactional
    public UserReadDto replenishBalance(ReplenishBalanceDto replenishBalanceDto) {
        User user = userRepository.findById(replenishBalanceDto.getUserId()).orElseThrow();
        user.setBalance(user.getBalance().add(replenishBalanceDto.getReplenishAmount()));

        return userReadMapper.toUserReadDto(user);
    }

    public Page<UserReadDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userReadMapper::toUserReadDto);
    }

    public UserReadDto findById(Long userId) {
        return userRepository.findById(userId)
                .map(userReadMapper::toUserReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public boolean deleteUser(Long userId) {
        return userRepository.findById(userId)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public MessageSendReport sendMessageAllUsers(MessageDto messageDto) {
        Message message = messageRepository.save(new Message(messageDto.getTitle(),
                        LocalDateTime.now(),
                        messageDto.getMessageText()
                )
        );
        List<MessageSending> sendingList= userRepository.findAll().stream()
                .map(user -> new MessageSending(user, message, LocalDateTime.now())).collect(Collectors.toList());

        long count = messageSendingRepository.saveAll(sendingList).size();

        return new MessageSendReport(message.getId(),
                message.getTitle(),
                message.getCreateDate(),
                message.getMessageText(),
                count);

    }
}

