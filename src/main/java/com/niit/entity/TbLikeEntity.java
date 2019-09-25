package com.niit.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tb_like", schema = "danmaku", catalog = "")
public class TbLikeEntity {
    private int lid;
    private Integer uid;
    private Integer vid;
    private Integer cid;
    private Integer did;
    private Timestamp date;
    private Integer status;
    @Transient
    private String type;
    @Transient
    private String context;
    @Transient
    private String likeuname;

    @Transient
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Transient
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Transient
    public String getLikeuname() {
        return likeuname;
    }

    public void setLikeuname(String likeuname) {
        this.likeuname = likeuname;
    }

    @Id
    @Column(name = "lid")
    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    @Basic
    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "vid")
    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    @Basic
    @Column(name = "cid")
    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "did")
    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbLikeEntity that = (TbLikeEntity) o;
        return lid == that.lid &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(vid, that.vid) &&
                Objects.equals(cid, that.cid) &&
                Objects.equals(did, that.did) &&
                Objects.equals(date, that.date) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(lid, uid, vid, cid, did, date, status);
    }
}
