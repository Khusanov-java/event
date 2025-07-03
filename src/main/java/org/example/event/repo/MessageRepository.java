package org.example.event.repo;

import org.example.event.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.transform.sax.SAXResult;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
            String senderId, String receiverId, String receiverId2, String senderId2
    );
}
