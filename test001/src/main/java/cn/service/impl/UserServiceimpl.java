package cn.service.impl;

import cn.model.dao.UserRespository;
import cn.model.entity.User;
import cn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Service
public class UserServiceimpl implements UserService {
    @Autowired
    UserRespository userRespository;
    @Override
    public void addUser(User user) {
        userRespository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRespository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRespository.deleteById(id);
    }

    @Override
    public List<User> getAllUser() {
        return userRespository.findAll();
    }

    @Override
    public List<User> getdevelopers(Long id) {
        return userRespository.findDeveloper("开发人员",id);
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) {
        return userRespository.findByNameAndPassword(name,password);
    }

    @Override
    public User getUserById(Long userId) {
        return userRespository.findById(userId).get();
    }

    @Override
    public User getUserByName(String name) {
        return userRespository.findByName(name);
    }
}
