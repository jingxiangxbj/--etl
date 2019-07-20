package com.jingxiang.datachange.entity;

import java.util.Date;
import javax.persistence.*;

public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String user;

    @Column(name = "toEmail")
    private String toemail;

    @Column(name = "fromEmail")
    private String fromemail;

    private String title;

    private String content;

    private Date createtime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     */
    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    /**
     * @return toEmail
     */
    public String getToemail() {
        return toemail;
    }

    /**
     * @param toemail
     */
    public void setToemail(String toemail) {
        this.toemail = toemail == null ? null : toemail.trim();
    }

    /**
     * @return fromEmail
     */
    public String getFromemail() {
        return fromemail;
    }

    /**
     * @param fromemail
     */
    public void setFromemail(String fromemail) {
        this.fromemail = fromemail == null ? null : fromemail.trim();
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}