package com.niit.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "comment", schema = "danmaku", catalog = "")
public class CommentEntity {
    private int cid;
    private Integer vid;
    private Integer uid;
    private Integer rid;
    private Integer rcid;
    private String content;
    private Integer likenum;
    private Timestamp date;
    private Integer mark;
    private Integer status;
    @Transient
    private String videoTitle;
    @Transient
    private String rContent;
    @Transient
    private String sHeadshot;
    @Transient
    private String sUname;

    @Transient
    public String getrContent() {
        return rContent;
    }

    public void setrContent(String rContent) {
        this.rContent = rContent;
    }

    @Transient
    public String getsHeadshot() {
        return sHeadshot;
    }

    public void setsHeadshot(String sHeadshot) {
        this.sHeadshot = sHeadshot;
    }

    @Transient
    public String getsUname() {
        return sUname;
    }

    public void setsUname(String sUname) {
        this.sUname = sUname;
    }

    @Transient
    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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
    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "rid")
    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    @Basic
    @Column(name = "rcid")
    public Integer getRcid() {
        return rcid;
    }

    public void setRcid(Integer rcid) {
        this.rcid = rcid;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "likenum")
    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
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
    @Column(name = "mark")
    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
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
        CommentEntity that = (CommentEntity) o;
        return cid == that.cid &&
                Objects.equals(vid, that.vid) &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(rid, that.rid) &&
                Objects.equals(rcid, that.rcid) &&
                Objects.equals(content, that.content) &&
                Objects.equals(likenum, that.likenum) &&
                Objects.equals(date, that.date) &&
                Objects.equals(mark, that.mark) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cid, vid, uid, rid, rcid, content, likenum, date, mark, status);
    }
}
