package com.goormthon.backend.firstsori.domain.message.domain.service;

import com.goormthon.backend.firstsori.domain.message.domain.entity.Message;
import com.goormthon.backend.firstsori.domain.message.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SaveMessageService {

    private final MessageRepository messageRepository;

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
