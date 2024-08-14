package com.birthCertificate.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.birthCertificate.entities.UpldCrt;
import com.birthCertificate.payloads.UpldCrtDto;
import com.birthCertificate.repositories.UpldCrtRepo;
import com.birthCertificate.services.FileService;
import com.birthCertificate.services.UpldCrtService;

@RestController
@RequestMapping("api/v1")
public class FileMangerController {
	
	@Value("${project.image}")
	private String path;
	
	@Autowired
	private UpldCrtRepo upldCrtRepo;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private UpldCrtService upldCrtService;
	
	@PostMapping("/upldCert/image/upload")
	@Transactional
	public ResponseEntity<UpldCrtDto> uploadUpldCrtImage(@RequestParam("image") MultipartFile image,
			@RequestParam("certId") Integer certId) throws IOException, MissingServletRequestPartException {
	    System.out.println("Received certId: " + certId); // Check value of certId
	    System.out.println("Received file: " + image.getOriginalFilename()); // Check file info

	    if (image == null || image.isEmpty()) {
	        throw new MissingServletRequestPartException("image");
	    }

	    UpldCrtDto upldCrtDto = this.upldCrtService.getUpldCrtById(certId);
	    if (upldCrtDto == null) {
	    	 System.out.println("No record found for certId: " + certId);
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    
	    System.out.println("Fetched DTO: " + upldCrtDto);

	    String fileName = this.fileService.uploadImage(path, image);
	    System.out.println("Uploaded file name: " + fileName);
	    
	    Optional<UpldCrt> findById= upldCrtRepo.findByCertId(certId);
	    if(findById.isPresent()) {
	    	UpldCrt upldCrt = findById.get();
	    	upldCrt.setImageName(fileName);
//	    upldCrtDto.setImageName(weburi);
//	    System.out.println("Set image name in DTO: " + upldCrtDto.getImageName());
	    }
	    UpldCrtDto updatePost = this.upldCrtService.updateUpldCrt(upldCrtDto, certId);
	    System.out.println("Updated DTO image name: " + updatePost.getImageName());
	    
	    return new ResponseEntity<>(updatePost, HttpStatus.OK);
	}


	
	

    //method to serve files
	@GetMapping(value = "/upldCrt/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
	        @PathVariable("imageName") String imageName,
	        HttpServletResponse response
	) throws IOException {

	    InputStream resource = this.fileService.getResource(path, imageName);
	    
	    if (resource == null) {
	        System.err.println("Resource not found for imageName: " + imageName);
	        response.sendError(HttpServletResponse.SC_NOT_FOUND);
	        return;
	    }
	    
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + imageName + "\"");
	    
	    try {
	        StreamUtils.copy(resource, response.getOutputStream());
	    } catch (IOException e) {
	        System.err.println("Error while sending file: " + e.getMessage());
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}


}
