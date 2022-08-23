package com.qhuy.authenms.service;

import com.qhuy.authenms.model.UserModel;
import com.qhuy.authenms.repository.UserRepo;
import com.qhuy.authenms.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserDTO getAll(){
        UserDTO userDTO = new UserDTO();
        userDTO.setMessage("success");
        userDTO.setStatus("success");
        userDTO.setPayload(userRepo.findAll());
        return  userDTO;
    }

    public UserDTO getById(Long id){
        UserDTO userDTO  = new UserDTO();
        Optional<UserModel> userOpt = userRepo.findById(id);
        if(userOpt.isEmpty()){
            log.info("ko co nguoi dung nay");
            userDTO.setMessage("failed");
            userDTO.setStatus("failed");
            userDTO.setPayload(null);
            return userDTO;
        }
        else {
            userDTO.setPayload(userOpt.get());
            userDTO.setMessage("success");
            userDTO.setStatus("success");
            log.info("tim thay nguoi dung:" + userOpt.get());
            return userDTO;
        }
    }

    public UserDTO save(UserModel user){
        UserDTO userDTO = new UserDTO();
        Optional<UserModel> userOpt = userRepo.findByEmail(user.getEmail());
        if(userOpt.isPresent()){
            userDTO.setStatus("failed");
            userDTO.setMessage("failed");
            userDTO.setPayload(null);
            log.info("da co nguoi dung" + userOpt.get());
            return userDTO;
        }else{
            user.setPassword(encoder.encode(user.getPassword()));
            UserModel newUser = userRepo.save(user);
            this.updateWithoutPassword(user);
            userDTO.setStatus("success");
            userDTO.setMessage("success");
            userDTO.setPayload(newUser);
            return userDTO;
        }
    }
    public boolean updateWithoutPassword(UserModel inputUser) {
        Optional<UserModel> optUser = userRepo.findById(inputUser.getId());
        if (optUser.isEmpty()) {
            return false;
        } else {
            UserModel currentUser = optUser.get();
            if (inputUser.getPassword().equals(currentUser.getPassword())) {
                userRepo.save(inputUser);
                return true;
            } else {
                return false;
            }
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email){
        Optional<UserModel> user = userRepo.findByEmail(email);
        User springUser = null;
        if(user.isEmpty()){
            log.error("ko tim thay nguoi dung: "+email);
            throw  new UsernameNotFoundException("ko thay nguoi dung" +email);
        }else{
            UserModel newUser = user.get();
            String role = newUser.getRole();
            Set<GrantedAuthority> ga = new HashSet<>();
            ga.add(new SimpleGrantedAuthority(role));
            springUser = new User(email, newUser.getPassword(),ga);
            return springUser;
        }

    }

}
