package com.birthCertificate.services;

import java.util.List;

import com.birthCertificate.payloads.UpldCrtDto;
import com.birthCertificate.payloads.UpldCrtResponse;
import com.birthCertificate.payloads.uplodCertRequest;


public interface UpldCrtService {
	
	UpldCrtDto createUpldCrt(UpldCrtDto input, Integer userId);

	UpldCrtDto updateUpldCrt(UpldCrtDto input, Integer certId);

	UpldCrtDto getUpldCrtById(Integer certId);

	List<UpldCrtResponse> getAllUpldCrt();

	void deleteUpldCrt(Integer certId);
	
	UpldCrtDto getUpldCrtNo(String certificateNo);

}
