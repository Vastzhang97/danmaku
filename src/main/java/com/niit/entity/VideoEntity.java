package com.niit.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "video", schema = "danmaku", catalog = "")
public class VideoEntity {
    private int vid;
    private Integer uid;
    private String title;
    private String intro;
    private int category;
    private String coversrc;
    private String videosrc;
    private Integer view;
    private Integer likenum;
    private Timestamp date;
    private Integer danmakunum;
    private Integer popularitynum;
    private String webpage;
    private Integer grade;
    private Integer gradenum;
    private String duration;
    private int status;
    @Transient
    private String author;

    @Transient
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vid")
    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "intro")
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Basic
    @Column(name = "category")
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Basic
    @Column(name = "coversrc")
    public String getCoversrc() {
        return coversrc;
    }

    public void setCoversrc(String coversrc) {
        this.coversrc = coversrc;
    }

    @Basic
    @Column(name = "videosrc")
    public String getVideosrc() {
        return videosrc;
    }

    public void setVideosrc(String videosrc) {
        this.videosrc = videosrc;
    }

    @Basic
    @Column(name = "view")
    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        if (view == null) {
            this.view = 0;
        }
        this.view = view;
    }

    @Basic
    @Column(name = "likenum")
    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        if (likenum == null) {
            this.likenum = 0;
        }
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
    @Column(name = "danmakunum")
    public Integer getDanmakunum() {
        return danmakunum;
    }

    public void setDanmakunum(Integer danmakunum) {
        if (danmakunum == null) {
            this.danmakunum = 0;
        }
        this.danmakunum = danmakunum;
    }

    @Basic
    @Column(name = "popularitynum")
    public Integer getPopularitynum() {
        return popularitynum;
    }

    public void setPopularitynum(Integer popularitynum) {
        if (popularitynum == null) {
            this.popularitynum = 0;
        }
        this.popularitynum = popularitynum;
    }

    @Basic
    @Column(name = "webpage")
    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    @Basic
    @Column(name = "grade")
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        if (grade == null) {
            this.grade = 0;
        }
        this.grade = grade;
    }

    @Basic
    @Column(name = "gradenum")
    public Integer getGradenum() {
        return gradenum;
    }

    public void setGradenum(Integer gradenum) {
        if (gradenum == null) {
            this.gradenum = 0;
        }
        this.gradenum = gradenum;
    }

    @Basic
    @Column(name = "duration")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoEntity that = (VideoEntity) o;
        return vid == that.vid &&
                category == that.category &&
                status == that.status &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(title, that.title) &&
                Objects.equals(intro, that.intro) &&
                Objects.equals(coversrc, that.coversrc) &&
                Objects.equals(videosrc, that.videosrc) &&
                Objects.equals(view, that.view) &&
                Objects.equals(likenum, that.likenum) &&
                Objects.equals(date, that.date) &&
                Objects.equals(danmakunum, that.danmakunum) &&
                Objects.equals(popularitynum, that.popularitynum) &&
                Objects.equals(webpage, that.webpage) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(gradenum, that.gradenum) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {

        return Objects.hash(vid, uid, title, intro, category, coversrc, videosrc, view, likenum, date, danmakunum, popularitynum, webpage, grade, gradenum, duration, status);
    }

    @Override
    public String toString() {
        return "VideoEntity{" +
                "vid=" + vid +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", category=" + category +
                ", coversrc='" + coversrc + '\'' +
                ", videosrc='" + videosrc + '\'' +
                ", view=" + view +
                ", likenum=" + likenum +
                ", date=" + date +
                ", danmakunum=" + danmakunum +
                ", popularitynum=" + popularitynum +
                ", webpage='" + webpage + '\'' +
                ", grade=" + grade +
                ", gradenum=" + gradenum +
                ", duration='" + duration + '\'' +
                ", status=" + status +
                ", author='" + author + '\'' +
                '}';
    }
}
