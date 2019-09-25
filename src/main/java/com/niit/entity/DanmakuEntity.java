package com.niit.entity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "danmaku", schema = "danmaku", catalog = "")
public class DanmakuEntity {
    private int did;
    private int uid;
    private int vid;
    private String content;
    private Integer color;
    private Integer position;
    private Integer likenum;
    private Time dbCurrenttime;
    private Timestamp dbDate;
    private Integer status;

    @Transient
    private String author;

    @Transient
    private int currenttime;
    @Transient
    private String date;
    @Transient
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Transient
    public int getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(int currenttime) {
        this.currenttime = currenttime;
    }

    @Transient
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "did")
    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    @Basic
    @Column(name = "uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "vid")
    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
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
    @Column(name = "color")
    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    @Basic
    @Column(name = "position")
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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
    @Column(name = "db_currenttime")
    public Time getDbCurrenttime() {
        return dbCurrenttime;
    }

    public void setDbCurrenttime(Time dbCurrenttime) {
        this.dbCurrenttime = dbCurrenttime;
    }

    @Basic
    @Column(name = "db_date")
    public Timestamp getDbDate() {
        return dbDate;
    }

    public void setDbDate(Timestamp dbDate) {
        this.dbDate = dbDate;
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
        DanmakuEntity that = (DanmakuEntity) o;
        return did == that.did &&
                uid == that.uid &&
                vid == that.vid &&
                Objects.equals(content, that.content) &&
                Objects.equals(color, that.color) &&
                Objects.equals(position, that.position) &&
                Objects.equals(likenum, that.likenum) &&
                Objects.equals(dbCurrenttime, that.dbCurrenttime) &&
                Objects.equals(dbDate, that.dbDate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(did, uid, vid, content, color, position, likenum, dbCurrenttime, dbDate, status);
    }
}
