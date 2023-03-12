package com.company.dto;

import com.company.Entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByVerificationCode(String code);
}
