package com.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="vmuser")
public class VmUser {
    @Id
    @Column(name="userid",nullable=false)
    private Long userId;

    @Column(name="username",nullable=false,unique=true)
    private String username;

    @Column(name="password",nullable=false)
    private String password;

    @Column(name="mobile_no",nullable=false,unique=true)
    private Long mobileNo;

    @Column(name="emailid",nullable=false,unique=true)
    private String emailId;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type",nullable=false)
    private AccountType accountType;

    @JsonManagedReference
    @OneToMany(mappedBy = "vmUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VmDetail> vmList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailid() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public List<VmDetail> getVmList() {
        return vmList;
    }

    public void setVmList(List<VmDetail> vmList) {
        this.vmList = vmList;
    }

    @Override
    public String toString() {
        return "VmUser{" +
                "userId=" + userId +
                ", userName='" + username + '\'' +
                ", mobileNo=" + mobileNo +
                ", password='" + password + '\'' +
                ", emailId='" + emailId + '\'' +
                ", vmList=" + vmList +
                '}';
    }
}


