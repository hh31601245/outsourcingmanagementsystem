package cn.model.dao;

import cn.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface UserRespository extends JpaRepository<User,Long> {

    @Query("from User u where u.user_name=:user_name and u.user_password=:user_password")
    User findByNameAndPassword(@Param("user_name") String user_name,@Param("user_password") String user_password);

    @Query("from User u where u.user_role=:role")
    List<User> findByRole(@Param("role") String role);

    @Query("from User u where u.user_name=:name")
    User findByName(@Param("name") String name);

    @Query("from User u where u.user_role=:role and u.user_leader_id=:user_leader_id")
    List<User> findDeveloper(@Param("role") String role,@Param("user_leader_id") Long user_leader_id);
}
