package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;


public class OfficialDocumentCirculread implements Parcelable{
    private String odcid;

    private String oiid;

    private String eid;

    private Integer isread;

    private String report;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public OfficialDocumentCirculread(String odcid, String oiid, String eid, Integer isread, String report, Integer orderby, long createtime, long updatetime) {
        this.odcid = odcid;
        this.oiid = oiid;
        this.eid = eid;
        this.isread = isread;
        this.report = report;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public OfficialDocumentCirculread() {
        super();
    }

    public String getOdcid() {
        return odcid;
    }

    public void setOdcid(String odcid) {
        this.odcid = odcid == null ? null : odcid.trim();
    }

    public String getOiid() {
        return oiid;
    }

    public void setOiid(String oiid) {
        this.oiid = oiid == null ? null : oiid.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
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
        return "OfficialDocumentCirculread{" +
                "odcid='" + odcid + '\'' +
                ", oiid='" + oiid + '\'' +
                ", eid='" + eid + '\'' +
                ", isread=" + isread +
                ", report='" + report + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

    //创建带参Parcel构造器
        protected OfficialDocumentCirculread(Parcel in) {
            //这里read字段的顺序要与write的顺序一致
    
            odcid = in.readString();
            oiid = in.readString();
            eid = in.readString();
            isread = in.readInt();
            report = in.readString();

            
            orderby = in.readInt();
            createtime = in.readLong();
             updatetime = in.readLong();
        }
    
        //创建常量Creator，并实现该接口的两个方法
        public static final Creator<OfficialDocumentCirculread> CREATOR = new Creator<OfficialDocumentCirculread>() {
            @Override
            public OfficialDocumentCirculread createFromParcel(Parcel in) {
                return new OfficialDocumentCirculread(in);
            }
    
            @Override
            public OfficialDocumentCirculread[] newArray(int size) {
                return new OfficialDocumentCirculread[size];
            }
        };
    
        @Override
        public int describeContents() {
            return 0;
        }
    
        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(odcid);
            parcel.writeString(oiid);
            parcel.writeString(eid);
            parcel.writeInt(isread);
            parcel.writeString(report);

            parcel.writeInt(orderby);
            parcel.writeLong(createtime);
            parcel.writeLong(updatetime);
        }
    
        public static Creator<OfficialDocumentCirculread> getCREATOR() {
            return CREATOR;
        }
}