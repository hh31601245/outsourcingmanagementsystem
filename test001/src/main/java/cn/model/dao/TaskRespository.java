package cn.model.dao;

import cn.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TaskRespository extends JpaRepository<Task,Long> {
    /**
     * 根据项目id查找任务
     * @param project_id
     * @return
     */
    @Query("from Task t where t.task_project_id=:project_id")
    List<Task> findByProjectId(@Param("project_id") Long project_id);

    /**
     * 根据开发人员id查找任务
     * @param developers_id
     * @return
     */
    @Query("from Task t where t.task_developers_id=:developers_id")
    List<Task> findByDeveloperId(@Param("developers_id") Long developers_id);

    /**
     * 根据项目id和开发人员id查找任务
     * @param project_id
     * @param developers_id
     * @return
     */
    @Query("from Task t where t.task_project_id=:project_id and t.task_developers_id=:developers_id")
    List<Task> findByProjectAndDeveloper(@Param("project_id") Long project_id,@Param("developers_id") Long developers_id);

    /**
     * 查找该项目下的任务个数
     * @param project_id
     * @return
     */
    @Query(value = "select count(*) from Task t where t.task_project_id=:project_id",nativeQuery = true)
    Long sumTaskNumber(@Param("project_id") Long project_id);

    /**
     * 查找该项目下的完成的任务个数
     * @param project_id
     * @return
     */
    @Query(value="select count(*) from Task t where t.task_project_id=:project_id and t.task_end_time is not null",nativeQuery = true)
    Long sumfinishedTask(@Param("project_id") Long project_id);

    @Query("from Task t where t.id=:id")
    Task findOneById(@Param("id") Long id);

    /**
     * 查找一个项目总的实际人天
     * @param project_id
     * @return
     */
    @Query(value="select sum(t.task_actual_person_days) from Task t where t.task_project_id=:project_id and t.task_end_time is not null",nativeQuery = true)
    Long sumAllActualPersonDays(@Param("project_id") Long project_id);

    /**
     * 查找一个项目总的预计人天
     * @param project_id
     * @return
     */
    @Query(value="select sum(t.task_expected_person_days) from Task t where t.task_project_id=:project_id",nativeQuery = true)
    Long sumAllExceptPersonDays(@Param("project_id") Long project_id);

    /**
     * 查找一个项目总的开始时间
     * @param project_id
     * @return
     */
    @Query(value="select min(t.task_start_time) from Task t where t.task_project_id=:project_id",nativeQuery = true)
    Date sumAllStartData(@Param("project_id") Long project_id);
    /**
     * 查找一个项目总的结束时间
     * @param project_id
     * @return
     */
    @Query(value="select max(t.task_end_time) from Task t where t.task_project_id=:project_id",nativeQuery = true)
    Date sumAllEndData(@Param("project_id") Long project_id);


}
