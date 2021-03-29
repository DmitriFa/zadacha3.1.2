package com.example.demoboot.service;


import com.example.demoboot.entitiy.User;
import com.example.demoboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @PersistenceContext
    private EntityManager em;

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {
    }

    public void addUser(User user)  {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public void removeUser(User user)  {
        userRepository.delete(user);
    }

    public void updateUser(User user)  {
        if (user.getPassword().length() != 60) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
        }
        else {
            userRepository.save(user);
        }
    }

    public List<User> getAllUsers()  {
        return userRepository.findAll();
    }


    public User getUserById(long id)  {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            user = optional.get();
        }
        return user;
    }


    public boolean checkEmail(String email)  {
        return !userRepository.existsByEmail(email);
    }
}

