package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;
//ПЕРЕВІРИТИ
@Service
 public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDb = userRepo.findByUsername(user.getUsername());
        // перевірка чи є користувач в бд
        if(userFromDb !=null){
            return false;

        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        //Напевно сохраняє в БД
        userRepo.save(user);
        //  check String !=null
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please visit next link : http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
        return true;
    }

    public boolean activateUser(String code){
        User user = userRepo.findByActivationCode(code);

        if(user ==null){
            return false;
        }

        user.setActivationCode(null);
        userRepo.save(user);



        return false;
    }
}
