package com.birthCertificate.payloads;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpldCrtResponse {
	
	private Integer certId;
	private String certificateNo;
	private String Name;
	private String motherName;
	private String fatherName;
	private String imageName;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date addedDate;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dateOfBirth;
	private String gender;
	private String address;
}
