package com.birthCertificate.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.birthCertificate.common.DateUtil;
import com.birthCertificate.entities.UpldCrt;
import com.birthCertificate.entities.User;
import com.birthCertificate.payloads.UpldCrtDto;
import com.birthCertificate.payloads.UpldCrtResponse;
import com.birthCertificate.payloads.uplodCertRequest;
import com.birthCertificate.repositories.UpldCrtRepo;
import com.birthCertificate.repositories.UserRepo;
import com.birthCertificate.services.UpldCrtService;

import io.swagger.v3.oas.annotations.servers.Server;

import com.birthCertificate.exceptions.ResourceNotFoundException;

@Service
public class UpldCrtServiceImpl implements UpldCrtService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UpldCrtRepo upldCrtRepo;
	
	 @Autowired
	 private ModelMapper modelMapper;
	 
//	 @Autowired
//	 private CertificateNumberServiceImpl certificateNumberServiceImpl;
	
	@Override
	public UpldCrtDto createUpldCrt(UpldCrtDto input, Integer userId) {
		 User user = this.userRepo.findById(userId)
	                .orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));
		 
		 UpldCrt upldCrt = this.modelMapper.map(user, UpldCrt.class);
		 //service.generateCertificateNumber();
		  
		// upldCrt.setCertId(Integer.valueOf(certificateNumberServiceImpl.generateCertificateNumber()));
		 
		 
		 upldCrt.setAddedDate(new Date());
		 upldCrt.setAddress(input.getAddress());
		 upldCrt.setCertificateNo(input.getCertificateNo());
//		 try {
//				upldCrt.setDateOfBirth(DateUtil.str2date(input.getDateOfBirth(), "dd-MM-yyyy"));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		 upldCrt.setDateOfBirth(input.getDateOfBirth());
		 upldCrt.setFatherName(input.getFatherName());
		 upldCrt.setGender(input.getGender());
		 upldCrt.setImageName("default.png");
		 upldCrt.setMotherName(input.getMotherName());
		 upldCrt.setName(input.getName());
		 upldCrt.setUser(user);
		 
		 UpldCrt newsave= upldCrtRepo.save(upldCrt);
		 
		return this.modelMapper.map(newsave, UpldCrtDto.class);
	}

	@Override
	public UpldCrtDto updateUpldCrt(UpldCrtDto input, Integer certId) {
		UpldCrt upldCrt = this.upldCrtRepo.findByCertId(certId)
                .orElseThrow(() -> new ResourceNotFoundException("UpldCrt ", "cert id", certId));
	 
	 //UpldCrt upldCrt = this.modelMapper.map(crt, UpldCrt.class);
	 
//
//	 try {
//		upldCrt.setDateOfBirth(DateUtil.str2date(input.getDateOfBirth(), "dd-MM-yyyy"));
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	 }
	 upldCrt.setAddedDate(new Date());
	 upldCrt.setAddress(input.getAddress());
	 upldCrt.setDateOfBirth(input.getDateOfBirth());
	 upldCrt.setFatherName(input.getFatherName());
	 upldCrt.setGender(input.getGender());
	 upldCrt.setMotherName(input.getMotherName());
	 upldCrt.setName(input.getName());
	 
	 UpldCrt updateUpldCrt= upldCrtRepo.saveAndFlush(upldCrt);
	 
	return this.modelMapper.map(updateUpldCrt, UpldCrtDto.class);
	}

	@Override
	public UpldCrtDto getUpldCrtById(Integer certId) {
	    // Extract certId from the input and process
	    UpldCrt upldCrt = this.upldCrtRepo.findByCertId(certId)
	            .orElseThrow(() -> new ResourceNotFoundException("upldCrt", "certId", 0));
	    return this.modelMapper.map(upldCrt, UpldCrtDto.class);
	}

	@Override
	public List<UpldCrtResponse> getAllUpldCrt() {
		
		 List<UpldCrt> allfind = this.upldCrtRepo.findAll();
		 
		 List<UpldCrtResponse> crtResponses = allfind.stream().map((crt) -> this.modelMapper.map(crt, UpldCrtResponse.class))
					.collect(Collectors.toList());        

			return crtResponses;
	}

	@Override
	public void deleteUpldCrt(Integer certId) {
		UpldCrt upldCrt = this.upldCrtRepo.findByCertId(certId)
                .orElseThrow(() -> new ResourceNotFoundException("upldCrt", "cert id", certId));
		this.upldCrtRepo.delete(upldCrt);
	}

	@Override
	public UpldCrtDto getUpldCrtNo(String certificateNo) {
		UpldCrt upldCrt = this.upldCrtRepo.findByCertificateNo(certificateNo)
				.orElseThrow(() -> new ResourceNotFoundException("upldCrt", "certNo",0));
	        return this.modelMapper.map(upldCrt, UpldCrtDto.class);
	}

}
