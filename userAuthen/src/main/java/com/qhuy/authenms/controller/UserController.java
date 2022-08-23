package com.qhuy.authenms.controller;

import com.qhuy.authenms.dto.AuthorizedDTO;
import com.qhuy.authenms.dto.UserDTO;
import com.qhuy.authenms.dto.UserLoginDTO;
import com.qhuy.authenms.jwtUtil.JwlUtil;
import com.qhuy.authenms.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.qhuy.authenms.repository.UserRepo;
import com.qhuy.authenms.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwlUtil util;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/getall")
    public ResponseEntity<UserDTO> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<UserDTO> save(@RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.save(userModel), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
            String token = util.generateToken(userLoginDTO.getEmail());
            Optional<UserModel> userOpt = userRepo.findByEmail(userLoginDTO.getEmail());
            UserModel newUser = userOpt.get();
            newUser.setPassword("");
            return new ResponseEntity<UserDTO>(new UserDTO("success", "success", new AuthorizedDTO(newUser, token)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<UserDTO>(new UserDTO("fail", "fail", null), HttpStatus.OK);


        }
    }
}