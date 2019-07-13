package cn.model.dao;

import cn.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRespository extends JpaRepository<Project,Long> {

    //根据发包员id和状态来查找项目
    @Query("from Project p where p.project_status=:status and p.project_employer_id=:employer_id")
    List<Project> findByStatusAndEmployer(@Param("status") String status,@Param("employer_id") Long employer_id);

    //根据承包员id和状态来查找项目
    @Query("from Project p where p.project_status=:status and p.project_contractor_id=:contractor_id")
    List<Project> findByStatusAndContractor(@Param("status") String status,@Param("contractor_id") Long contractor_id);

    //根据状态来查找项目
    @Query("from Project p where p.project_status=:status")
    List<Project> findByStatus(@Param("status") String status);

    //根据发包员id来查找项目
    @Query("from Project p where p.project_employer_id=:employer_id")
    List<Project> findByEmployer(@Param("employer_id") Long employer_id);

    //根据承包方id来查找项目
    @Query("from Project p where p.project_contractor_id=:contractor_id")
    List<Project> findByContractor(@Param("contractor_id") Long contractor_id);

//    @Query("from Project p where p.id In (select task_project_id from Task t where task_developers_id=:developer_id)")
//    List<Project> findByDevelopers(@Param("developer_id") Long developer_id);

    /**
     * 查找开发人员对应的项目
     * @param developer_id
     * @return
     */
    @Query("select distinct p.id from Project p left join Task t on p.id=task_project_id where task_developers_id=:developer_id and p.project_status=:status")
    List<Long> findIdByDevelopers(@Param("developer_id") Long developer_id,@Param("status") String status);

    @Query("from Project p where p.id=:id")
    Project findOneById(@Param("id") Long id);

}
