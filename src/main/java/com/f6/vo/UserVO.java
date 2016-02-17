package com.f6.vo;

import java.util.Date;

public class UserVO {
    private String userCode;

    private String userName;

    private String corpCode;

    private String organizeCode;

    private String identificationType;

    private String identificationId;

    private String userTelephone;

    private String userEmail;

    private String isActive;

    private String userPassword;

    private String userSalt;

    private String token;

    private String mem;

    private String urCreate;

    private Date dateCreate;

    private String urAlter;

    private Date dateAlter;

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

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode == null ? null : corpCode.trim();
    }

    public String getOrganizeCode() {
        return organizeCode;
    }

    public void setOrganizeCode(String organizeCode) {
        this.organizeCode = organizeCode == null ? null : organizeCode.trim();
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType == null ? null : identificationType.trim();
    }

    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId == null ? null : identificationId.trim();
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem == null ? null : mem.trim();
    }

    public String getUrCreate() {
        return urCreate;
    }

    public void setUrCreate(String urCreate) {
        this.urCreate = urCreate == null ? null : urCreate.trim();
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getUrAlter() {
        return urAlter;
    }

    public void setUrAlter(String urAlter) {
        this.urAlter = urAlter == null ? null : urAlter.trim();
    }

    public Date getDateAlter() {
        return dateAlter;
    }

    public void setDateAlter(Date dateAlter) {
        this.dateAlter = dateAlter;
    }
}