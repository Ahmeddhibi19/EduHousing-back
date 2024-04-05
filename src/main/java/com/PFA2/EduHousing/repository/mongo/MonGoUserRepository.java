package com.PFA2.EduHousing.repository.mongo;

import com.PFA2.EduHousing.model.ConnexionStatus;
import com.PFA2.EduHousing.model.User;
import com.PFA2.EduHousing.model.chat.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mongoUserRepository")
public interface MonGoUserRepository extends MongoRepository<MongoUser, Integer> {
    List<MongoUser> findAllByStatus(ConnexionStatus status);
}
