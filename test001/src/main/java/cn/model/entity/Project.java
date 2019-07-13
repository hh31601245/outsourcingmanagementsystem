package cn.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="项目名不能为空")
    private String project_name;
    @NotEmpty(message="项目说明不能为空")
    private String project_description;
    @NotNull(message = "项目预计结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date project_expected_endtime;
    private String project_requirement_document;
    private String project_status;
    private Long project_employer_id;
    private Long project_contractor_id;

    public Project()
    {

    }
    public Project(Long id, String project_name, String project_description, Date project_expected_endtime, String project_requirement_document, String project_status, Long project_employer_id, Long project_contractor_id) {
        this.id = id;
        this.project_name = project_name;
        this.project_description = project_description;
        this.project_expected_endtime = project_expected_endtime;
        this.project_requirement_document = project_requirement_document;
        this.project_status = project_status;
        this.project_employer_id = project_employer_id;
        this.project_contractor_id = project_contractor_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public Date getProject_expected_endtime() {
        return project_expected_endtime;
    }

    public void setProject_expected_endtime(Date project_expected_endtime) {
        this.project_expected_endtime = project_expected_endtime;
    }

    public String getProject_requirement_document() {
        return project_requirement_document;
    }

    public void setProject_requirement_document(String project_requirement_document) {
        this.project_requirement_document = project_requirement_document;
    }

    public String getProject_status() {
        return project_status;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    public Long getProject_employer_id() {
        return project_employer_id;
    }

    public void setProject_employer_id(Long project_employer_id) {
        this.project_employer_id = project_employer_id;
    }

    public Long getProject_contractor_id() {
        return project_contractor_id;
    }

    public void setProject_contractor_id(Long project_contractor_id) {
        this.project_contractor_id = project_contractor_id;
    }
}
