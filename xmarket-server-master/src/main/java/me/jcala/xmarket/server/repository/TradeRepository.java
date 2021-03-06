package me.jcala.xmarket.server.repository;


import me.jcala.xmarket.server.entity.document.Trade;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends MongoRepository<Trade,String>{

    @Query(fields = "{ 'id': 1,'title':1,'price':1,'imgUrls': 1,'author':1}")
    List<Trade> findBySchoolNameAndStatus(@Param("schoolName") String schoolName, @Param("status") int status, Sort sort);


    @Query(fields = "{ 'id': 1,'title':1,'price':1,'imgUrls': 1,'author':1}")
    List<Trade> findByTagNameAndStatus(@Param("tagName") String tagName, @Param("status") int status, Sort sort);

    Trade findById(String id);

    @Query(value = "{ 'id' : ?0 }", fields = "{ 'id': 1,'title':1,'price':1,'imgUrls': 1,'author':1}")
    Trade findPartsById(String id);

    //dbc add search query
    @Query(fields = "{ 'id': 1,'title':1,'price':1,'imgUrls': 1,'author':1}")
    List<Trade> findBySchoolNameAndTitleAndStatus(@Param("schoolName") String schoolName, @Param("title") String title, @Param("status") int status, Pageable pageable);
}
