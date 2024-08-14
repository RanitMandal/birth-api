package com.birthCertificate.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birthCertificate.entities.CertificateNumber;

public interface CertificateNumberRepo extends JpaRepository<CertificateNumber, Long> {
    // Custom query to find the max sequence number for a given year
    Optional<CertificateNumber> findTopByYearOrderBySequenceNumberDesc(int year);
}
