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
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="任务名不能为空")
    private String task_name;
    private String task_design_document;
    @NotEmpty(message="任务说明不能为空")
    private String task_description;
    @NotNull(message="任务预计人天不能为空")
    private Long task_expected_person_days;
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date task_start_time;
    //@NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date task_end_time;
    private Long task_actual_person_days;
    private String task_status;
    private Long task_developers_id;
    private Long task_project_id;

    public Task()
    {

    }

    public Task(Long id, String task_name, String task_design_document, String task_description, Long task_expected_person_days, Date task_start_time, Date task_end_time, Long task_actual_person_days, String task_status, Long task_developers_id, Long task_project_id) {
        this.id = id;
        this.task_name = task_name;
        this.task_design_document = task_design_document;
        this.task_description = task_description;
        this.task_expected_person_days = task_expected_person_days;
        this.task_start_time = task_start_time;
        this.task_end_time = task_end_time;
        this.task_actual_person_days = task_actual_person_days;
        this.task_status = task_status;
        this.task_developers_id = task_developers_id;
        this.task_project_id = task_project_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_design_document() {
        return task_design_document;
    }

    public void setTask_design_document(String task_design_document) {
        this.task_design_document = task_design_document;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public Long getTask_expected_person_days() {
        return task_expected_person_days;
    }

    public void setTask_expected_person_days(Long task_expected_person_days) {
        this.task_expected_person_days = task_expected_person_days;
    }

    public Date getTask_start_time() {
        return task_start_time;
    }

    public void setTask_start_time(Date task_start_time) {
        this.task_start_time = task_start_time;
    }

    public Date getTask_end_time() {
        return task_end_time;
    }

    public void setTask_end_time(Date task_end_time) {
        this.task_end_time = task_end_time;
    }

    public Long getTask_actual_person_days() {
        return task_actual_person_days;
    }

    public void setTask_actual_person_days(Long task_actual_person_days) {
        this.task_actual_person_days = task_actual_person_days;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public Long getTask_developers_id() {
        return task_developers_id;
    }

    public void setTask_developers_id(Long task_developers_id) {
        this.task_developers_id = task_developers_id;
    }

    public Long getTask_project_id() {
        return task_project_id;
    }

    public void setTask_project_id(Long task_project_id) {
        this.task_project_id = task_project_id;
    }
}
