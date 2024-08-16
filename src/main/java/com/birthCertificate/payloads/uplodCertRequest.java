package com.birthCertificate.payloads;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class uplodCertRequest {
	
	@JsonProperty("certificateNo")
	private String certificateNo;

}
