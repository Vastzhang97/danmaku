package com.niit.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "danmaku", catalog = "")
public class MessageEntity {
    private int mid;
    private int sid;
    private int rid;
    private String context;
    private Timestamp date;
    private Integer status;
    @Transient
    private String pUname;
    @Transient
    private String pHeadshot;

    @Transient
    public String getpUname() {
        return pUname;
    }

    public void setpUname(String pUname) {
        this.pUname = pUname;
    }

    @Transient
    public String getpHeadshot() {
        return pHeadshot;
    }

    public void setpHeadshot(String pHeadshot) {
        this.pHeadshot = pHeadshot;
    }


    @Id
    @Column(name = "mid")
    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Basic
    @Column(name = "sid")
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Basic
    @Column(name = "rid")
    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    @Basic
    @Column(name = "context")
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
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
        MessageEntity that = (MessageEntity) o;
        return mid == that.mid &&
                sid == that.sid &&
                rid == that.rid &&
                Objects.equals(context, that.context) &&
                Objects.equals(date, that.date) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mid, sid, rid, context, date, status);
    }
}
