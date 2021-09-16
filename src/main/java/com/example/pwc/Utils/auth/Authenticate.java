package com.example.pwc.Utils.auth;

import com.example.pwc.Models.Users;
import com.example.pwc.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Authenticate {
    @Autowired
    UserRepository repo;

    public Users authenticateUser(String header){
        String authorizationCredentials = header.substring("Basic".length()).trim();
        String[] decodedCredentials = new String(Base64.getDecoder().decode(authorizationCredentials)).split(":");
        return repo.findUsersByUsernameAndPassword(decodedCredentials[0], decodedCredentials[1]);
    }
}
