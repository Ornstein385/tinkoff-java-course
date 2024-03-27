package edu.java.jpa.repository;

import edu.java.jpa.model.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {
}
