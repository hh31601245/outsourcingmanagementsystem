package cn.service;

import cn.model.entity.User;

import java.util.List;

public interface UserService {
    /**
     * 新增用户
     *@param user
     */
    void addUser(User user);
    /**
     * 修改一个用户
     *@param user
     */
    void updateUser(User user);
    /**
     * 根据id删除用户
     * @param id
     */
    void deleteById(Long id);
    /**
     * 获取所有用户信息
     */
    List<User> getAllUser();
    /**
     * 获取该管理人员手下等等开发人员信息
     * @param id
     */
    List<User> getdevelopers(Long id);
    /**
     * 根据用户名和密码查找用户
     * @param user_name;
     * @param user_password;
     */
    User getUserByNameAndPassword(String user_name,String user_password);
    /**
     * 根据Id查找用户
     * @param userId
     */
    User getUserById(Long userId);
    /**
     * 根据用户名查找用户
     * @param name
     */
    User getUserByName(String name);

}
