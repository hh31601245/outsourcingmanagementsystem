package cn.service;

import cn.model.entity.Task;

import java.util.Date;
import java.util.List;

public interface TaskService {
    /**
     * 添加任务
     * @Param task
     */
    void addTask(Task task);
    /**
     * 修改任务
     * @Param task
     */
    void modifyTask(Task task);
    /**
     * 删除根据id删除任务
     * @Param id
     */
    void deleteTask(Long id);
    /**
     * 显示当前项目下的所有任务
     * @Param project_id
     */
    List<Task> showTaskByProjectId(Long project_id);
    /**
     * 显示当前开发人员下的所有任务
     * @Param developer_id
     */
    List<Task> showTaskByDeveloperId(Long developer_id);
    /**
     * 显示当前开发人员下当前项目的所有任务
     * @Param project_id
     * @Param developer_id
     */
    List<Task> showTaskByProjectIdAndDeveloperId(Long project_id,Long devveloper_id);
    /**
     * 根据id查找任务
     * @Param id
     */
    Task showOneTask(Long id);
    /**
     * 查找该项目下有几个任务
     * @Param project_id
     */
    Long sumTask(Long project_id);

    /**
     * 查找该项目下已完成的任务有几个
     * @Param project_id
     */
    Long finishedTask(Long project_id);

    /**
     * 计算单个的实际人天
     * @Param id
     */
    Long actualPersonDays(Long id);

    /**
     * 计算汇总的预计人天
     * @Parm id
     */
    Long exceptAllPersonDays(Long id);

    /**
     * 计算汇总的实际人天
     * @param id
     * @return
     */
    Long actalAllPersonDays(Long id);
    /**
     * 计算汇总的开始时间
     * @param id
     * @return
     */
    Date startDate(Long id);

    /**
     * 计算汇总的结束时间
     * @param id
     * @return
     */
    Date endDate(Long id);


}
