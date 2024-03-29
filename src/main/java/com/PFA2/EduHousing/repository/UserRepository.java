package com.PFA2.EduHousing.repository;

import com.PFA2.EduHousing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    public Optional<User> findUserByFirstName(String firstName);

    public Optional<User> findUserByEmailIgnoreCase(String email);

    public Optional<User> findUserByPhoneNumber(String phoneNumber);

    public List<User> findAllByFirstName(String firstName);

    public void deleteUserByEmail(String email);

    public void deleteUserByPhoneNumber(String phoneNumber);

}
