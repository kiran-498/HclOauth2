package com.hcl.intranet.springboot2authserver.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcl.intranet.springboot2authserver.entity.Users;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUsername(String username);
	
	 Users save(Users users);
	 
	 @Procedure(procedureName = "CheckContactCity")
	  int checkUser(@Param("CLIENT_ID") String CLIENT_ID);

}


