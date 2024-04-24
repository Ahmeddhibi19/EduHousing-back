package com.PFA2.EduHousing.services.ProfileImageService;

import com.PFA2.EduHousing.Utils.ImageUtils;
import com.PFA2.EduHousing.exceptions.EntityNotFoundException;
import com.PFA2.EduHousing.exceptions.ErrorCodes;
import com.PFA2.EduHousing.model.*;
import com.PFA2.EduHousing.repository.jpa.ProfileImageRepository;
import com.PFA2.EduHousing.repository.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public String save(MultipartFile file, Integer userId) throws IOException {
        if (file == null) {
            log.error("File is null");
            throw new IllegalArgumentException("File is null");
        }
        if(userId==null){
            log.error("user id is null");
            throw new IllegalArgumentException("user id is null");
        }
        User user = userRepository.findById(userId).orElseThrow(
                ()->new EntityNotFoundException("NO user with this id :"+userId,
                        ErrorCodes.USER_NOT_FOUND)
        );
        /*ProfileImage profileImage=ProfileImagedto.toEntity(profileImagedto);
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

        }*/
        byte[] data = file.getBytes();
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();

        ProfileImage profileImage=ProfileImage.builder()
                .name(fileName)
                .type(fileType)
                .data(ImageUtils.compressImage(file.getBytes()))
                .user(user)
                .build();
        user.setProfileImage(profileImage);
        if (profileImage==null){
            return "file failed to upload"+fileName;
        }
        //userRepository.save(user);
        profileImageRepository.save(profileImage);

        return "file uploaded successfully"+fileName;
    }

    @Override
    public byte[] findById(Integer id) {
        if(id==null){
            log.error("image id is null");
            return null;
        }
        Optional<ProfileImage> profileImage=profileImageRepository.findById(id);
        if(profileImage.isEmpty()){
            throw new EntityNotFoundException("no profile image with this id :"+id,
                    ErrorCodes.PROFILE_IMAGE_NOT_FOUND);
        }
        return ImageUtils.decompressImage(profileImage.get().getData());
    }

    @Override
    public byte[] findByUserId(Integer userId) {
        if(userId==null){
            log.error("user id is null");
            return null;
        }
        Optional<ProfileImage> profileImage= profileImageRepository.findProfileImageByUserId(userId);
        if(profileImage.isEmpty()){
            throw new EntityNotFoundException("no profile image with this user id :"+userId,
                    ErrorCodes.PROFILE_IMAGE_NOT_FOUND);
        }
        return ImageUtils.decompressImage(profileImage.get().getData());

    }
    @Override
    public void deleteById(Integer id) {
        if(id==null){
            log.error("user id is null");
            return ;
        }
        profileImageRepository.deleteById(id);

    }

    @Override
    public void deleteByUserId(Integer userId) {
        if(userId==null){
            log.error("user id is null");
            return ;
        }
        profileImageRepository.deleteProfileImageByUserId(userId);
    }

    @Override
    public byte[] findByUserEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("user email is null");
            return null;
        }
        Optional<ProfileImage> profileImage= profileImageRepository.findProfileImageByUserEmail(email);
        if(profileImage.isEmpty()){
            throw new EntityNotFoundException("no profile image with this user id :"+email,
                    ErrorCodes.PROFILE_IMAGE_NOT_FOUND);
        }
        return ImageUtils.decompressImage(profileImage.get().getData());
    }
}
