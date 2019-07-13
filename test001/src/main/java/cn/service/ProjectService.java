package cn.service;

import cn.model.entity.Project;

import java.util.List;

public interface ProjectService {
    /**
     *添加项目
     * @Param project
     */
    void addProject(Project project);
    /**
     * 修改项目
     * @Param project
     */
    void modifyProject(Project project);
    /**
     * 根据项目id删除项目
     * @Param id
     */
    void deleteProject(Long id);
    /**
     * 查看对应人员id和权限下的项目
     * @Param id
     * @Param role
     */
    List<Project> showProjectByIdAndRole(Long id,String role);
    /**
     * 查看对应人员id和权限和状态的项目
     * @Param id
     * @Param role
     * @Param status
     */
    List<Project> showProjectByIdAndRoleAndStatus(Long id,String role,String status);
    /**
     * 查看所有对应状态的项目
     * @Param status
     */
    List<Project> showAllProjectByStatus(String status);
    /**
     * 查找项目id对应的项目
     * @Param id
     */
    Project showOneProject(Long id);

    /**
     * 查找开发人员对应的项目id
     * @Param developer_id
     * @Param status
     * @return
     */
    List<Long> showProjectIdByDeveloper(Long developer_id,String status);
}
