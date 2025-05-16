package com.chat.chat.messaging.repository;

import com.chat.chat.messaging.model.Conversation;
import com.chat.chat.messaging.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
