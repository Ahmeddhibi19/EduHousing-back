package com.PFA2.EduHousing.services.mongoUserService;

import com.PFA2.EduHousing.model.ConnexionStatus;
import com.PFA2.EduHousing.model.User;
import com.PFA2.EduHousing.model.chat.MongoUser;
import com.PFA2.EduHousing.repository.mongo.MonGoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoUserServiceImpl implements MongoUserService{

    private final MonGoUserRepository repository;

    /*public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        repository.save(user);
    }*/

    public void disconnect(User user) {
        var storedUser = repository.findById(user.getId()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(ConnexionStatus.OFFLINE);
            repository.save(storedUser);
        }
    }

    public List<MongoUser> findConnectedUsers() {
        return repository.findAllByStatus(ConnexionStatus.ONLINE);
    }
}
