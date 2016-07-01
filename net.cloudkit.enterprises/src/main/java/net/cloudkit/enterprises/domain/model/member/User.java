/*
 * Copyright (C) 2015 The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.cloudkit.enterprises.domain.model.member;

import net.cloudkit.enterprises.domain.shared.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entity - 用户
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年08月26日 上午11:38:34
 */
@Entity
@Table(name = "USER", schema = "", catalog = "enterprises")
public class User extends AbstractEntity<User> {

    private static final long serialVersionUID = 1L;

    /** 用户名 */
    @Basic
    @Column(name = "USERNAME")
    private String username;

    /** 邮箱 */
    @Basic
    @Column(name = "EMAIL")
    private String email;

    /** 性别 */
    @Basic
    @Column(name = "GENDER")
    private Integer gender;

    /** 出生 年/月/日 */
    @Basic
    @Column(name = "BIRTHDAY")
    private Long birthday;

    /** 移动电话 */
    @Basic
    @Column(name = "MOBILE")
    private String mobile;

    /** 昵称 */
    @Basic
    @Column(name = "NICKNAME")
    private String nickname;

    /* 固定电话 */
    @Basic
    @Column(name = "TELEPHONE")
    private String telephone;

    /* 真实姓名 */
    @Basic
    @Column(name = "TRUENAME")
    private String truename;

    /** 身份证 */
    @Basic
    @Column(name = "IDCARD")
    private String idcard;

    /** 邮编 */
    @Basic
    @Column(name = "ZIP")
    private String zip;

    /** 简介 */
    @Basic
    @Column(name = "INTRODUCTION")
    private String introduction;

    /** 地址 */
    @Basic
    @Column(name = "ADDRESS")
    private String address;

    /** 密码 */
    @Basic
    @Column(name = "PASSWORD")
    private String password;

    /** 盐值 */
    @Basic
    @Column(name = "SALT")
    private String salt;

    // EnumType.ORDINAL 索引 EnumType.STRING 字符串
    @Enumerated(EnumType.ORDINAL)
    @Basic
    @Column(name = "USER_STATUS")
    private UserStatus userStatus;

    /** TODO 用户所属角色 */
    // USER_ROLE_RELATIONSHIPS

    public User() {

    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the birthday
     */
    public Long getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the gender
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return the idcard
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * @param idcard the idcard to set
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * @return the introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * @param introduction the introduction to set
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * @return the userStatus
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus the userStatus to set
     */
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the v to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the truename
     */
    public String getTruename() {
        return truename;
    }

    /**
     * @param truename the truename to set
     */
    public void setTruename(String truename) {
        this.truename = truename;
    }

    /**
     * @return the truename
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("username:'").append(username).append('\'');
        sb.append(", email:'").append(email).append('\'');
        sb.append(", gender:").append(gender);
        sb.append(", birthday:").append(birthday);
        sb.append(", mobile:'").append(mobile).append('\'');
        sb.append(", nickname:'").append(nickname).append('\'');
        sb.append(", telephone:'").append(telephone).append('\'');
        sb.append(", truename:'").append(truename).append('\'');
        sb.append(", idcard:'").append(idcard).append('\'');
        sb.append(", zip:'").append(zip).append('\'');
        sb.append(", introduction:'").append(introduction).append('\'');
        sb.append(", address:'").append(address).append('\'');
        sb.append(", password:'").append(password).append('\'');
        sb.append(", salt:'").append(salt).append('\'');
        sb.append(", userStatus:").append(userStatus);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(birthday, user.birthday) &&
                Objects.equals(mobile, user.mobile) &&
                Objects.equals(nickname, user.nickname) &&
                Objects.equals(telephone, user.telephone) &&
                Objects.equals(truename, user.truename) &&
                Objects.equals(idcard, user.idcard) &&
                Objects.equals(zip, user.zip) &&
                Objects.equals(introduction, user.introduction) &&
                Objects.equals(address, user.address) &&
                Objects.equals(password, user.password) &&
                Objects.equals(salt, user.salt) &&
                Objects.equals(userStatus, user.userStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, email, gender, birthday, mobile, nickname, telephone, truename, idcard, zip, introduction, address, password, salt, userStatus);
    }

    @Override
    public boolean sameIdentityAs(User other) {
        return other != null && this.username.equals(other.username);
    }
}
