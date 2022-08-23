package com.qhuy.authenms.repository;

import com.qhuy.authenms.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo  extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
}
