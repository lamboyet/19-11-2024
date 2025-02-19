package com.appsdeveloperblog.app.ws.mobile_app_ws.service.impl;

import com.appsdeveloperblog.app.ws.mobile_app_ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.mobile_app_ws.repository.UserRepository;
import com.appsdeveloperblog.app.ws.mobile_app_ws.service.UserService;
import com.appsdeveloperblog.app.ws.mobile_app_ws.shared.Utils;
import com.appsdeveloperblog.app.ws.mobile_app_ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;


    @Override
    public UserDto createUser(UserDto user) {

        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user,userEntity);


        userEntity.setEncryptedPassword("test");

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);

        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails,returnValue);
        return returnValue;
    }
}
