package com.PFA2.EduHousing.services.mongoUserService;

import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.model.ConnexionStatus;
import com.PFA2.EduHousing.model.User;
import com.PFA2.EduHousing.model.chat.MongoUser;
import com.PFA2.EduHousing.repository.mongo.MonGoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoUserServiceImpl implements MongoUserService{

    private final MonGoUserRepository repository;

    /*public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        repository.save(user);
    }*/
    @Autowired
    public MongoUserServiceImpl(MonGoUserRepository repository){
        this.repository=repository;
    }

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

    @Override
    public String findUserIdByEmail(String email) {
        MongoUser user =repository.findMongoUserByEmail(email).orElseThrow(
                ()->new EntityNotFoundException(
                        "no user with the provided email",
                        ErrorCodes.USER_NOT_FOUND
                )
        );
        return user.getId();
    }
}
