package com.birthCertificate.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birthCertificate.entities.UpldCrt;

public interface UpldCrtRepo extends JpaRepository<UpldCrt, Integer>{
	

	Optional<UpldCrt> findByCertificateNo(String certificateNo);

	Optional<UpldCrt> findByCertId(Integer certId);
}
