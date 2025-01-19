package com.pracaInzysnierka.pracainzynierska.unsecuredJwtTokens;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtUserRepository extends JpaRepository<JwtUser, Long> {
   JwtUser findByUsername(String username);
}