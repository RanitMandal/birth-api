package com.birthCertificate.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CERTIFICATE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpldCrt {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CERT_ID")
	private Integer certId;

	@Column(name = "CERT_NO", length = 100, nullable = false, unique = true)
	private String certificateNo;

	// This should map to cert_number in the database

	@Column(name = "Name", length = 100, nullable = false)
	private String Name;

	@Column(name = "MOTHER_NM", length = 50, nullable = false)
	private String motherName;

	@Column(name = "FATHER_NM", length = 50, nullable = false)
	private String fatherName;

	@Column(name = "IMG_NM")
	private String imageName;

	@Column(name = "ADDED_DT")
	private Date addedDate;

	@Column(name = "DATE_BIRTH")
	private Date dateOfBirth;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "ADDRESS", length = 255, nullable = false)
	private String address;

	@ManyToOne
	private User user;

}
