package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message,String> {

    List<Message> findByBelongId(String belongId, Sort sort);
}
