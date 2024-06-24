package my.space.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import my.space.model.Message;

import java.util.List;

@Repository
public interface EmailRepository extends ElasticsearchRepository<Message, String> {
    List<Message> findByStatus(String status);
}
