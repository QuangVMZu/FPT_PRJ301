/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.model;

import java.sql.Date;

/**
 *
 * @author Computing Fundamental - HCM Campus
 */
public class RoomForRentDto {
    //-----            your code here   --------------------------------
//    id INT PRIMARY KEY IDENTITY(1,1),          -- Mã phòng, tự tăng
//    title NVARCHAR(100) NOT NULL,              -- Tiêu đề (ví dụ: "Phòng trọ 25m2, Q1")
//    price DECIMAL(10,2) NOT NULL,              -- Giá thuê (đồng/tháng)
//    location NVARCHAR(150) NOT NULL,           -- Địa chỉ phòng cho thuê
//    description NVARCHAR(MAX),                 -- Mô tả chi tiết về phòng muốn cho thuê
//    postedDate DATETIME DEFAULT GETDATE(),     -- Ngày đăng tin
//	status int default 0,					   -- Trạng thái của tin: -2: Đã xóa; -1: Bị báo cáo; 0: Chờ thuê; 1: Đã thuê; 2:Tin hết hạn  
//    username NVARCHAR(50) NOT NULL,            -- Chủ phòng → tham chiếu tới Account
//    FOREIGN KEY (username) REFERENCES Account(username)
    
    private int id;
    private String title;
    private double price;
    private String location;
    private String description;
    private Date postedDate;
    private int status;
    private String username;

    public RoomForRentDto() {
    }

    public RoomForRentDto(int id, String title, double price, String location, String description, Date postedDate, int status, String username) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.location = location;
        this.description = description;
        this.postedDate = postedDate;
        this.status = status;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
