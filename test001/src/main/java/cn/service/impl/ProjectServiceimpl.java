package cn.service.impl;

import cn.model.dao.ProjectRespository;
import cn.model.entity.Project;
import cn.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceimpl implements ProjectService {

    @Autowired
    ProjectRespository respository;

    //添加项目
    @Override
    public void addProject(Project project) {
        respository.save(project);
    }

    //修改项目
    @Override
    public void modifyProject(Project project) {
        respository.save(project);
    }

    //删除项目
    @Override
    public void deleteProject(Long id) {
        respository.deleteById(id);
    }

    //查看对应id和权限下的项目
    @Override
    public List<Project> showProjectByIdAndRole(Long id, String role) {

        if(role.equals("发包方"))
        {
             return respository.findByEmployer(id);
        }
        if(role.equals("承包方管理员"))
        {
             return respository.findByContractor(id);
        }
        return null;
    }

    //查看对应id和权限和状态的项目
    @Override
    public List<Project> showProjectByIdAndRoleAndStatus(Long id, String role, String status) {
        if(role.equals("发包方"))
        {
            return respository.findByStatusAndEmployer(status,id);
        }
        if(role.equals("承包方管理员"))
        {
            return respository.findByStatusAndContractor(status,id);
        }
        return null;
    }

    //查看所有对应状态的项目
    @Override
    public List<Project> showAllProjectByStatus(String status) {
        return respository.findByStatus(status);
    }

    //查看对应项目id对应的项目
    @Override
    public Project showOneProject(Long id) {
        return respository.findOneById(id);
    }


    /**
     * 显示开发人员对应项目状态所对应的项目
     * @param developer_id
     * @Param status
     * @return
     */
    @Override
    public List<Long> showProjectIdByDeveloper(Long developer_id,String status) {
        return respository.findIdByDevelopers(developer_id,status);
    }


}
