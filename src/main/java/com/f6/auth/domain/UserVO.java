package com.f6.auth.domain;

/**
 * @author Administrator
 *
 */
public class UserVO {
	private String userCode;
 
	private String userName;
 
	private String organizeCode;

	private String identificationCode;

	private String userTelephone;

	private String userEmail;

	private String jobTitle;

	private String isActive;

	private String userPassword;

	private String userSalt;
	private String token;

	private String userPassword1;
	private String userPassword2;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserPassword1() {
		return userPassword1;
	}

	public void setUserPassword1(String userPassword1) {
		this.userPassword1 = userPassword1;
	}

	public String getUserPassword2() {
		return userPassword2;
	}

	public void setUserPassword2(String userPassword2) {
		this.userPassword2 = userPassword2;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode == null ? null : userCode.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getOrganizeCode() {
		return organizeCode;
	}

	public void setOrganizeCode(String organizeCode) {
		this.organizeCode = organizeCode == null ? null : organizeCode.trim();
	}

	public String getRealUser() {
		return identificationCode;
	}

	public void setRealUser(String realUser) {
		this.identificationCode = realUser == null ? null : realUser.trim();
	}

	public String getUserTelephone() {
		return userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone == null ? null : userTelephone.trim();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail == null ? null : userEmail.trim();
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle == null ? null : jobTitle.trim();
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive == null ? null : isActive.trim();
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword == null ? null : userPassword.trim();
	}

	public String getUserSalt() {
		return userSalt;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt == null ? null : userSalt.trim();
	}

	@Override
	public String toString() {
		return "UserVO [userCode=" + userCode + ", userName=" + userName + ", organizeCode=" + organizeCode + ", realUser=" + identificationCode
				+ ", userTelephone=" + userTelephone + ", userEmail=" + userEmail + ", jobTitle=" + jobTitle + ", isActive=" + isActive + "]";
	}

}