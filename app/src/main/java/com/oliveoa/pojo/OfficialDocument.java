package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;


@Keep
@Entity
public class OfficialDocument implements Parcelable{
    private String odid;

    private String draftEid;

    private String content;

    private String fid;

    private String title;

    private String nuclearDraftEid;

    private int nuclearDraftIsapproved;

    private String nuclearDraftOpinion;

    private String issuedEid;

    private int issuedIsapproved;

    private String issuedOpinion;

    private int orderby;

    private long createtime;

    private long updatetime;

    public OfficialDocument(String odid, String draftEid, String content, String fid, String title, String nuclearDraftEid, int nuclearDraftIsapproved, String nuclearDraftOpinion, String issuedEid, int issuedIsapproved, String issuedOpinion, int orderby, long createtime, long updatetime) {
        this.odid = odid;
        this.draftEid = draftEid;
        this.content = content;
        this.fid = fid;
        this.title = title;
        this.nuclearDraftEid = nuclearDraftEid;
        this.nuclearDraftIsapproved = nuclearDraftIsapproved;
        this.nuclearDraftOpinion = nuclearDraftOpinion;
        this.issuedEid = issuedEid;
        this.issuedIsapproved = issuedIsapproved;
        this.issuedOpinion = issuedOpinion;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public OfficialDocument() {
        super();
    }

    public String getOdid() {
        return odid;
    }

    public void setOdid(String odid) {
        this.odid = odid == null ? null : odid.trim();
    }

    public String getDraftEid() {
        return draftEid;
    }

    public void setDraftEid(String draftEid) {
        this.draftEid = draftEid == null ? null : draftEid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getNuclearDraftEid() {
        return nuclearDraftEid;
    }

    public void setNuclearDraftEid(String nuclearDraftEid) {
        this.nuclearDraftEid = nuclearDraftEid == null ? null : nuclearDraftEid.trim();
    }

    public int getNuclearDraftIsapproved() {
        return nuclearDraftIsapproved;
    }

    public void setNuclearDraftIsapproved(int nuclearDraftIsapproved) {
        this.nuclearDraftIsapproved = nuclearDraftIsapproved;
    }

    public String getNuclearDraftOpinion() {
        return nuclearDraftOpinion;
    }

    public void setNuclearDraftOpinion(String nuclearDraftOpinion) {
        this.nuclearDraftOpinion = nuclearDraftOpinion == null ? null : nuclearDraftOpinion.trim();
    }

    public String getIssuedEid() {
        return issuedEid;
    }

    public void setIssuedEid(String issuedEid) {
        this.issuedEid = issuedEid == null ? null : issuedEid.trim();
    }

    public int getIssuedIsapproved() {
        return issuedIsapproved;
    }

    public void setIssuedIsapproved(int issuedIsapproved) {
        this.issuedIsapproved = issuedIsapproved;
    }

    public String getIssuedOpinion() {
        return issuedOpinion;
    }

    public void setIssuedOpinion(String issuedOpinion) {
        this.issuedOpinion = issuedOpinion == null ? null : issuedOpinion.trim();
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
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
        return "OfficialDocument{" +
                "odid='" + odid + '\'' +
                ", draftEid='" + draftEid + '\'' +
                ", content='" + content + '\'' +
                ", fid='" + fid + '\'' +
                ", title='" + title + '\'' +
                ", nuclearDraftEid='" + nuclearDraftEid + '\'' +
                ", nuclearDraftIsapproved=" + nuclearDraftIsapproved +
                ", nuclearDraftOpinion='" + nuclearDraftOpinion + '\'' +
                ", issuedEid='" + issuedEid + '\'' +
                ", issuedIsapproved=" + issuedIsapproved +
                ", issuedOpinion='" + issuedOpinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
     //创建带参Parcel构造器
         protected OfficialDocument(Parcel in) {
             //这里read字段的顺序要与write的顺序一致
             odid = in.readString();
             draftEid = in.readString();
             fid = in.readString();
             title = in.readString();
             content = in.readString();
             nuclearDraftEid = in.readString();
             nuclearDraftIsapproved = in.readInt();
             nuclearDraftOpinion = in.readString();
             issuedEid = in.readString();
             issuedIsapproved = in.readInt();
             issuedOpinion = in.readString();
             orderby = in.readInt();
             createtime = in.readLong();
             updatetime = in.readLong();
         }
     
         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<OfficialDocument> CREATOR = new Creator<OfficialDocument>() {
             @Override
             public OfficialDocument createFromParcel(Parcel in) {
                 return new OfficialDocument(in);
             }
     
             @Override
             public OfficialDocument[] newArray(int size) {
                 return new OfficialDocument[size];
             }
         };
     
         @Override
         public int describeContents() {
             return 0;
         }
     
         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(odid);
             parcel.writeString(draftEid);
             parcel.writeString(fid);
             parcel.writeString(title);
             parcel.writeString(content);
             parcel.writeString(nuclearDraftEid);
             parcel.writeInt(nuclearDraftIsapproved);
             parcel.writeString(nuclearDraftOpinion);
             parcel.writeString(issuedEid);
             parcel.writeInt(issuedIsapproved);
             parcel.writeString(issuedOpinion);
             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }
     
         public static Creator<OfficialDocument> getCREATOR() {
             return CREATOR;
         }
}