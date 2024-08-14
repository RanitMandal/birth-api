package com.birthCertificate.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.birthCertificate.entities.CertificateNumber;
import com.birthCertificate.repositories.CertificateNumberRepo;

import java.time.Year;
import java.util.Optional;

@Service
public class CertificateNumberServiceImpl {

    @SuppressWarnings("unused")
	private final CertificateNumberRepo certificateNumberRepo;

    @Autowired
    public CertificateNumberServiceImpl(CertificateNumberRepo certificateNumberRepo) {
        this.certificateNumberRepo = certificateNumberRepo;
    }

    public String generateCertificateNumber() {
        String prefix = "CRT";
        int currentYear = Year.now().getValue();
        int nextSequenceNumber = getNextSequenceNumber(currentYear);

        CertificateNumber certificateNumber = new CertificateNumber();
        certificateNumber.setPrefix(prefix);
        certificateNumber.setYear(currentYear);
        certificateNumber.setSequenceNumber(nextSequenceNumber);
        certificateNumberRepo.save(certificateNumber);

        return String.format("%s/%d/%02d", prefix, currentYear, nextSequenceNumber);
    }

    private int getNextSequenceNumber(int year) {
        Optional<CertificateNumber> lastCertificate = certificateNumberRepo.findTopByYearOrderBySequenceNumberDesc(year);
        return lastCertificate.map(c -> c.getSequenceNumber() + 1).orElse(1);
    }
}
