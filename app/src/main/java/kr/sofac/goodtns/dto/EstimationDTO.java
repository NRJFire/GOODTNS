package kr.sofac.goodtns.dto;


import java.util.Date;

public class EstimationDTO {

    private Long id;
    private Long userId;
    private String messageText;
    private Date date;
    private Boolean status; //isDeleted
    private Boolean isVisible;

    public EstimationDTO() {
    }

    public EstimationDTO(Long id, Long userId, String messageText, Date date, Boolean status, Boolean isVisible) {
        this.id = id;
        this.userId = userId;
        this.messageText = messageText;
        this.date = date;
        this.status = status;
        this.isVisible = isVisible;
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

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
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

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return "EstimationDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", messageText='" + messageText + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", isVisible=" + isVisible +
                '}';
    }
}
