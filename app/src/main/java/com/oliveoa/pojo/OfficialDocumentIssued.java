package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Keep
@Entity
public class OfficialDocumentIssued implements Parcelable{
    private String odiid;

    private String oiid;

    private String dcid;

    private Integer isreceive;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public OfficialDocumentIssued(String odiid, String oiid, String dcid, Integer isreceive, Integer orderby, long createtime, long updatetime) {
        this.odiid = odiid;
        this.oiid = oiid;
        this.dcid = dcid;
        this.isreceive = isreceive;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public OfficialDocumentIssued() {
        super();
    }

    public String getOdiid() {
        return odiid;
    }

    public void setOdiid(String odiid) {
        this.odiid = odiid == null ? null : odiid.trim();
    }

    public String getOiid() {
        return oiid;
    }

    public void setOiid(String oiid) {
        this.oiid = oiid == null ? null : oiid.trim();
    }

    public String getDcid() {
        return dcid;
    }

    public void setDcid(String dcid) {
        this.dcid = dcid == null ? null : dcid.trim();
    }

    public Integer getIsreceive() {
        return isreceive;
    }

    public void setIsreceive(Integer isreceive) {
        this.isreceive = isreceive;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "OfficialDocumentIssued{" +
                "odiid='" + odiid + '\'' +
                ", oiid='" + oiid + '\'' +
                ", dcid='" + dcid + '\'' +
                ", isreceive=" + isreceive +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

 //创建带参Parcel构造器
     protected OfficialDocumentIssued(Parcel in) {
         //这里read字段的顺序要与write的顺序一致
 
         odiid = in.readString();
         oiid = in.readString();
         dcid = in.readString();
         isreceive = in.readInt();
         
         orderby = in.readInt();
         createtime = in.readLong();
          updatetime = in.readLong();
     }
 
     //创建常量Creator，并实现该接口的两个方法
     public static final Creator<OfficialDocumentIssued> CREATOR = new Creator<OfficialDocumentIssued>() {
         @Override
         public OfficialDocumentIssued createFromParcel(Parcel in) {
             return new OfficialDocumentIssued(in);
         }
 
         @Override
         public OfficialDocumentIssued[] newArray(int size) {
             return new OfficialDocumentIssued[size];
         }
     };
 
     @Override
     public int describeContents() {
         return 0;
     }
 
     @Override
     public void writeToParcel(Parcel parcel, int i) {
         parcel.writeString(odiid);
         parcel.writeString(oiid);
         parcel.writeString(dcid);
          parcel.writeInt(isreceive);
        
         parcel.writeInt(orderby);
         parcel.writeLong(createtime);
         parcel.writeLong(updatetime);
     }
 
     public static Creator<OfficialDocumentIssued> getCREATOR() {
         return CREATOR;
     }
}