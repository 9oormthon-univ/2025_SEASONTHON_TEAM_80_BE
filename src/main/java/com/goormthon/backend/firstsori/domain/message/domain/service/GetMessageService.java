package com.goormthon.backend.firstsori.domain.message.domain.service;

import com.goormthon.backend.firstsori.domain.message.domain.entity.Message;
import com.goormthon.backend.firstsori.domain.message.domain.repository.MessageRepository;
import com.goormthon.backend.firstsori.global.common.exception.ErrorCode;
import com.goormthon.backend.firstsori.global.common.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GetMessageService {

    private final MessageRepository messageRepository;


    public Message getMessage(UUID messageId) {
        messageRepository.markReadIfUnread(messageId);
        // 수정됐든 안됐든 메시지 조회 (1번 쿼리)
        return messageRepository.findByMessageId(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.MESSAGE_NOT_FOUND));
    }


    public Page<Message> getMessageList(UUID userId, Pageable pageable) {
        Page<Message> messages = messageRepository.findAllByUserId(userId, pageable);
//        if (messages.isEmpty()) {
//            throw new CustomException(ErrorCode.MESSAGE_NOT_FOUND);
//        }
        return messages;
    }
}
