package kr.sofac.goodtns.dto;


import java.util.Date;

public class MessageDTO {

    private Long id;
    private Long caseId;
    private Long userId;
    private Long managerId;
    private String messageText;
    private Date date;
    private Boolean isRead;

    public MessageDTO(){}

    public MessageDTO(Long id, Long caseId, Long userId, Long managerId, String messageText, Date date, Boolean isRead) {
        this.id = id;
        this.caseId = caseId;
        this.userId = userId;
        this.managerId = managerId;
        this.messageText = messageText;
        this.date = date;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
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

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", caseId=" + caseId +
                ", userId=" + userId +
                ", managerId=" + managerId +
                ", messageText='" + messageText + '\'' +
                ", date=" + date +
                ", isRead=" + isRead +
                '}';
    }
}
