package com.oliveoa.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
/* JSON 数据抽象为实体类 */
public class CompanyInfo{

    @Id(autoincrement = true)
    private  Long _id ;
    private String username;
    private String password;
    private String fullname;
    private String telephone;
    private String fax;
    private String zipcode;
    private String address;
    private String website;
    private String email;
    private String introduction;

    public CompanyInfo() {
    }

    public CompanyInfo(Long _id, String username, String password, String fullname, String telephone, String fax, String zipcode, String address, String website, String email, String introduction) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.telephone = telephone;
        this.fax = fax;
        this.zipcode = zipcode;
        this.address = address;
        this.website = website;
        this.email = email;
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "_id=" + _id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", fax='" + fax + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
