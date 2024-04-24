package com.PFA2.EduHousing.repository.mongo;

import com.PFA2.EduHousing.model.ConnexionStatus;
import com.PFA2.EduHousing.model.User;
import com.PFA2.EduHousing.model.chat.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("mongoUserRepository")
public interface MonGoUserRepository extends MongoRepository<MongoUser, Integer> {
    List<MongoUser> findAllByStatus(ConnexionStatus status);
    public Optional<MongoUser> findMongoUserByEmail(String email);
    public void deleteById(String id);
}
