package net.cloudkit.ecosystem.domain.model.user;

import net.cloudkit.ecosystem.domain.model.AbstractEntity;
import net.cloudkit.ecosystem.domain.model.Address;

import javax.persistence.*;
import java.util.Date;

/**
 * implements Comparable<People>
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/3/17 11:15
 */
@Entity
@Table(name = "USER", schema = "", catalog = "")
public class User extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    /** 用户名 */
    @Basic
    @Column(name = "USERNAME")
    private String username;

    /** 移动电话 */
    @Basic
    @Column(name = "MOBILE")
    private String mobile;

    /** 邮箱 */
    @Basic
    @Column(name = "EMAIL")
    private String email;

    /** 密码 */
    @Basic
    @Column(name = "PASSWORD")
    private String password;

    /** 盐值 */
    @Basic
    @Column(name = "SALT")
    private String salt;

    /** 昵称 */
    @Basic
    @Column(name = "NICKNAME")
    private String nickname;

    /* 真实姓名 realName */
    @Basic
    @Column(name = "TRUE_NAME")
    private String trueName;

    /** 性别 */
    @Basic
    @Column(name = "GENDER")
    private Integer gender;

    /** 出生（年/月/日） */
    @Basic
    @Column(name = "BIRTHDAY")
    private Date birthday;

    /* 固定电话 */
    @Basic
    @Column(name = "TELEPHONE")
    private String telephone;

    /** 简介 */
    @Basic
    // @Column(name = "views", columnDefinition = "bigint default 0")
    @Column(name = "INTRODUCTION")
    private String introduction;

    /** 地址 */
    @Embedded
    private Address homeAddress;

    /** 用户状态 EnumType.ORDINAL 索引 | EnumType.STRING 字符串 */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "USER_STATUS")
    private UserStatus userStatus;

    /**
     registered_time
     last_login_time
     last_login_ip
     visited_count
     activation_key
     */

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public User setUsername(String username) {
        this.username = username;
        return this;
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
    public User setMobile(String mobile) {
        this.mobile = mobile;
        return this;
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
    public User setEmail(String email) {
        this.email = email;
        return this;
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
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public User setSalt(String salt) {
        this.salt = salt;
        return this;
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
    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    /**
     * @return the trueName
     */
    public String getTrueName() {
        return trueName;
    }

    /**
     * @param trueName the trueName to set
     */
    public User setTrueName(String trueName) {
        this.trueName = trueName;
        return this;
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
    public User setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
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
    public User setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
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
    public User setIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    /**
     * @return the homeAddress
     */
    public Address getHomeAddress() {
        return homeAddress;
    }

    /**
     * @param homeAddress the homeAddress to set
     */
    public User setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
        return this;
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
    public User setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    @Override
    public boolean sameIdentityAs(Object other) {
        return false;
    }
}
