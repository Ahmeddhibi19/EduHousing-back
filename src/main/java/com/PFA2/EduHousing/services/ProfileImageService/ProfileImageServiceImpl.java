package com.PFA2.EduHousing.services.ProfileImageService;

import com.PFA2.EduHousing.dto.ProfileImagedto;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.model.*;
import com.PFA2.EduHousing.repository.ProfileImageRepository;
import com.PFA2.EduHousing.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProfileImageServiceImpl implements ProfileImageService{
    private final ProfileImageRepository profileImageRepository;
    private final UserRepository userRepository;
    @Autowired
    public ProfileImageServiceImpl(
            ProfileImageRepository profileImageRepository,
            UserRepository userRepository
    ){
        this.profileImageRepository=profileImageRepository;
        this.userRepository=userRepository;
    }

    @Override
    public ProfileImagedto save(ProfileImagedto profileImagedto, Integer userId) {
        if(userId==null){
            log.error("user id is null");
            throw new IllegalArgumentException("user id is null");
        }
        User user = userRepository.findById(userId).orElseThrow(
                ()->new EntityNotFoundException("NO user with this id :"+userId,
                        ErrorCodes.USER_NOT_FOUND)
        );
        ProfileImage profileImage=ProfileImagedto.toEntity(profileImagedto);
        if(user instanceof Student){
            profileImage.setUser((Student) user);
            ((Student) user).setProfileImage(profileImage);
            userRepository.save((Student) user);
        } else if (user instanceof Homeowner) {
            profileImage.setUser((Homeowner)user);
            ((Homeowner)user).setProfileImage(profileImage);
            userRepository.save((Homeowner)user);
        } else if (user instanceof Admin) {
            profileImage.setUser((Admin)user);
            ((Admin)user).setProfileImage(profileImage);
            userRepository.save((Admin)user);

        }

        return ProfileImagedto.fromEntity(profileImageRepository.save(profileImage));
    }

    @Override
    public ProfileImagedto findById(Integer id) {
        if(id==null){
            log.error("image id is null");
            return null;
        }
        Optional<ProfileImage> profileImage=profileImageRepository.findById(id);
        return Optional.of(ProfileImagedto.fromEntity(profileImage.get())).orElseThrow(
                ()->new EntityNotFoundException("no profile image with this id :"+id,
                        ErrorCodes.PROFILE_IMAGE_NOT_FOUND)
        );
    }

    @Override
    public ProfileImagedto findByUserId(Integer userId) {
        if(userId==null){
            log.error("user id is null");
            return null;
        }
        Optional<ProfileImage> profileImage= profileImageRepository.findProfileImageByUserId(userId);
        return Optional.of(ProfileImagedto.fromEntity(profileImage.get())).orElseThrow(
                ()->new EntityNotFoundException("no profile image with this id :"+userId,
                        ErrorCodes.APPLICATION_FEEDBACK_NOT_FOUND)
        );
    }

    @Override
    public void deleteById(Integer id) {
        if(id==null){
            log.error("user id is null");
            return ;
        }
        profileImageRepository.deleteById(id);

    }
}
