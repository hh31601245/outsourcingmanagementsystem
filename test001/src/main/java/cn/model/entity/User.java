package cn.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="用户名不能为空")
    private String user_name;
    @NotEmpty(message="密码不能为空")
    private String user_password;
    @NotEmpty(message="权限不能为空")
    private String user_role;
    private String salt;
    private Long user_leader_id;
    public User() {

    }

    public User(String user_name, String user_password, String user_role, String salt) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_role = user_role;
        this.salt = salt;
    }

    public Long getUser_leader_id() {
        return user_leader_id;
    }

    public void setUser_leader_id(Long user_leader_id) {
        this.user_leader_id = user_leader_id;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
