package com.birthCertificate.payloads;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpldCrtDto {
	
	private Integer certId;
    
	@NotEmpty
	@Size(min = 14, max = 20, message = "certficateNo must be min of 14 chars and max of 20 chars !!")
	private String certificateNo;
	
	@NotEmpty
	private String Name;
	
	@NotEmpty
	private String motherName;
	
	@NotEmpty
	private String fatherName;
	
	private String imageName;
	@NotEmpty
	private String gender;
	
	@NotEmpty
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dateOfBirth;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date addedDate;
	
	@NotEmpty
	private String address;
	

}
