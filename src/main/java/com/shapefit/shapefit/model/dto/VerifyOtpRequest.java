package com.shapefit.shapefit.model.dto;

@lombok.Getter
@lombok.Setter
public class VerifyOtpRequest {
    private String emailAddress;
    private String otp;
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
    

    // Getters and setters
}