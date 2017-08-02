package kr.sofac.goodtns.dto;

import java.util.Date;

public class UsersDTO {

    private Long id;
    private String name;
    private String email; // <= login
    private String password;
    private String phone;
    private String company;
    private String description;
    private Date date;
    private Boolean isVisible;

    public UsersDTO() {}

    public UsersDTO(Long id, String name, String email, String password, String phone, String company, String description, Date date, Boolean isVisible) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.company = company;
        this.description = description;
        this.date = date;
        this.isVisible = isVisible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return "UsersDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", isVisible=" + isVisible +
                '}';
    }
}
