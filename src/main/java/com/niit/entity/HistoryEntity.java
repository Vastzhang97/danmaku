package com.niit.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "history", schema = "danmaku", catalog = "")
public class HistoryEntity {
    private int hid;
    private Integer vid;
    private Integer uid;
    private Timestamp date;
    @Transient
    private String webpage;

    @Transient
    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    @Transient
    private String title;

    @Transient
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Id
    @Column(name = "hid")
    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
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
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryEntity that = (HistoryEntity) o;
        return hid == that.hid &&
                Objects.equals(vid, that.vid) &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(hid, vid, uid, date);
    }

    @Override
    public String toString() {
        return "HistoryEntity{" +
                "hid=" + hid +
                ", vid=" + vid +
                ", uid=" + uid +
                ", date=" + date +
                ", webpage='" + webpage + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
