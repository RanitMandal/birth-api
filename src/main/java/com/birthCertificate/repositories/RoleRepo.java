package com.birthCertificate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birthCertificate.entities.Role;

public interface RoleRepo  extends JpaRepository<Role, Integer>{

}
