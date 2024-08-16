package com.birthCertificate.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.birthCertificate.payloads.ApiResponse;
import com.birthCertificate.payloads.UpldCrtDto;
import com.birthCertificate.payloads.UpldCrtResponse;
import com.birthCertificate.payloads.uplodCertRequest;
import com.birthCertificate.services.UpldCrtService;

@RestController
@RequestMapping("/api/v1/")
public class UploadCertificateController {

	@Autowired
	private UpldCrtService upldCrtService;
	
	@PostMapping("/user/{userId}/upldCertificate")
	public ResponseEntity<UpldCrtDto> createUploadCertificate(@RequestBody UpldCrtDto upldCrtDto, @PathVariable Integer userId) {
		UpldCrtDto createUpldCrt = this.upldCrtService.createUpldCrt(upldCrtDto, userId);
		return new ResponseEntity<UpldCrtDto>(createUpldCrt, HttpStatus.CREATED);
	}


	@GetMapping("/certs/{certId}")
	public ResponseEntity<UpldCrtDto> getUploadCertificateByCertId(@PathVariable Integer certId) {
	    // Log the received certId
	    System.out.println("Received certId: " + certId);

	    UpldCrtDto upldCrtDto = this.upldCrtService.getUpldCrtById(certId);
	    return new ResponseEntity<>(upldCrtDto, HttpStatus.OK);
	}
	
	@GetMapping("/getAllUpldCertificate")
	public ResponseEntity<List<UpldCrtResponse>> getAllUploadCertificate() {
		List<UpldCrtResponse> upldCrtResponses = this.upldCrtService.getAllUpldCrt();
		return ResponseEntity.ok(upldCrtResponses);
	}

	@DeleteMapping("/deleteUpldCert/{certId}")
	public ApiResponse deleteUploadCertificate(@PathVariable Integer certId) {
		this.upldCrtService.deleteUpldCrt(certId);
		return new ApiResponse("UpldCert is successfully deleted !!", true);
	}


	@PutMapping("/editUpldCert/{certId}")
	public ResponseEntity<UpldCrtDto> updateUploadCertificate(@RequestBody UpldCrtDto upldCrtDto, @PathVariable Integer certId) {

		UpldCrtDto updatePost = this.upldCrtService.updateUpldCrt(upldCrtDto, certId);
		return new ResponseEntity<UpldCrtDto>(updatePost, HttpStatus.OK);

	}

	
	@PostMapping("/getUpldCertificateNo")
	public ResponseEntity<UpldCrtDto> getUpldCertificateNo(@RequestBody uplodCertRequest input) {

		UpldCrtDto upldCrtDto = this.upldCrtService.getUpldCrtNo(input.getCertificateNo());
		return new ResponseEntity<>(upldCrtDto, HttpStatus.OK);

	}
	
	
}
