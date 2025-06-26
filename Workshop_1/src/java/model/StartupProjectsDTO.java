package model;

import java.util.Date;

public class StartupProjectsDTO {

    private int projectID;
    private String projectName;
    private String description;
    private String status;
    private Date estimated;

    public StartupProjectsDTO() {
    }

    public StartupProjectsDTO(int projectID, String projectName, String description, String status, Date estimated) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.description = description;
        this.status = status;
        this.estimated = estimated;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEstimated() {
        return estimated;
    }

    public void setEstimated(Date estimated) {
        this.estimated = estimated;
    }

}
