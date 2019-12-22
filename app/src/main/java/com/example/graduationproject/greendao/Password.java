package com.example.graduationproject.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Password {
    @Id(autoincrement = true)
    Long id;
    Long ownerId;
    String password_name;
    String password_userName;
    String password_password;
    String password_createTime;
    String password_type;
    String password_url;
    String password_note;
    @Generated(hash = 56144817)
    public Password(Long id, Long ownerId, String password_name,
            String password_userName, String password_password,
            String password_createTime, String password_type, String password_url,
            String password_note) {
        this.id = id;
        this.ownerId = ownerId;
        this.password_name = password_name;
        this.password_userName = password_userName;
        this.password_password = password_password;
        this.password_createTime = password_createTime;
        this.password_type = password_type;
        this.password_url = password_url;
        this.password_note = password_note;
    }
    @Generated(hash = 565943725)
    public Password() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOwnerId() {
        return this.ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    public String getPassword_name() {
        return this.password_name;
    }
    public void setPassword_name(String password_name) {
        this.password_name = password_name;
    }
    public String getPassword_userName() {
        return this.password_userName;
    }
    public void setPassword_userName(String password_userName) {
        this.password_userName = password_userName;
    }
    public String getPassword_password() {
        return this.password_password;
    }
    public void setPassword_password(String password_password) {
        this.password_password = password_password;
    }
    public String getPassword_createTime() {
        return this.password_createTime;
    }
    public void setPassword_createTime(String password_createTime) {
        this.password_createTime = password_createTime;
    }
    public String getPassword_type() {
        return this.password_type;
    }
    public void setPassword_type(String password_type) {
        this.password_type = password_type;
    }
    public String getPassword_url() {
        return this.password_url;
    }
    public void setPassword_url(String password_url) {
        this.password_url = password_url;
    }
    public String getPassword_note() {
        return this.password_note;
    }
    public void setPassword_note(String password_note) {
        this.password_note = password_note;
    }


}
