package kr.sofac.goodtns.dto;

import java.util.Date;


public class CaseDTO {

    private Long id;
    private Long userId;
    private Date date;
    private Boolean status;

    public CaseDTO() {
    }

    public CaseDTO(Long id, Long userId, Date date, Boolean status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CaseDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
