package com.oliveoa.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
/* JSON 数据抽象为实体类 */
public class CompanyInfo implements Parcelable {
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

    public CompanyInfo(String username, String password, String fullname, String telephone, String fax, String zipcode, String address, String website, String email, String introduction) {
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
         parcel.writeString(username);
         parcel.writeString(password);
         parcel.writeString(fullname);
         parcel.writeString(telephone);
         parcel.writeString(fax);
         parcel.writeString(zipcode);
         parcel.writeString(website);
         parcel.writeString(introduction);
         parcel.writeString(email);
         parcel.writeString(address);
    }

    //创建带参Parcel构造器
    public CompanyInfo(Parcel source){
        //这里read字段的顺序要与write的顺序一致
        username=source.readString();
        password=source.readString();
        fullname=source.readString();
        telephone=source.readString();
        fax=source.readString();
        zipcode=source.readString();
        website=source.readString();
        introduction=source.readString();
        email=source.readString();
        address=source.readString();

    }

    //创建常量Creator，并实现该接口的两个方法
    public static final Parcelable.Creator<CompanyInfo> CREATOR = new Creator<CompanyInfo>(){
        @Override
        public CompanyInfo createFromParcel(Parcel source) {
            return new CompanyInfo(source);
        }

        @Override
        public CompanyInfo[] newArray(int size) {
            return new CompanyInfo[size];
        }
    };

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

    public static Creator<CompanyInfo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "username='" + username + '\'' +
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
}
