package cn.service.impl;

import cn.model.dao.TaskRespository;
import cn.model.entity.Task;
import cn.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceimpl implements TaskService {

    @Autowired
    TaskRespository respository;

    /**
     * 保存任务
     * @param task
     */
    @Override
    public void addTask(Task task) {
        respository.save(task);
    }

    /**
     * 修改任务
     * @param task
     */
    @Override
    public void modifyTask(Task task) {
        respository.save(task);
    }

    /**
     * 根据id删除任务
     * @param id
     */
    @Override
    public void deleteTask(Long id) {
        respository.deleteById(id);
    }

    /**
     * 根据项目id显示任务
     * @param project_id
     * @return
     */
    @Override
    public List<Task> showTaskByProjectId(Long project_id) {

        return respository.findByProjectId(project_id);
    }

    /**
     * 根据开发人员id显示任务
     * @param developer_id
     * @return
     */
    @Override
    public List<Task> showTaskByDeveloperId(Long developer_id) {
        return respository.findByDeveloperId(developer_id);
    }

    /**
     * 根据项目id和开发人员id显示任务
     * @param project_id
     * @param devveloper_id
     * @return
     */
    @Override
    public List<Task> showTaskByProjectIdAndDeveloperId(Long project_id, Long devveloper_id) {
        return respository.findByProjectAndDeveloper(project_id,devveloper_id);
    }

    /**
     * 根据任务id找任务
     * @param id
     * @return
     */
    @Override
    public Task showOneTask(Long id) {
        return respository.findOneById(id);
    }

    /**
     * 查找该项目下的所有任务个数
     * @param project_id
     * @return
     */
    @Override
    public Long sumTask(Long project_id) {
        return respository.sumTaskNumber(project_id);
    }

    /**
     * 查找该项目下的完成的任务个数
     * @param project_id
     * @return
     */
    @Override
    public Long finishedTask(Long project_id) {
        return respository.sumfinishedTask(project_id);
    }

    /**
     * 计算实际人天
     * @param id
     * @return
     */
    @Override
    public Long actualPersonDays(Long id) {
        Long result=0L;
        Task task=respository.findById(id).get();
        Date startTime=task.getTask_start_time();
        Date endTime=task.getTask_end_time();
        Calendar cal=Calendar.getInstance();
        while(startTime.compareTo(endTime)!=1)
        {
            cal.setTime(startTime);
            int week=cal.get(Calendar.DAY_OF_WEEK)-1; //Calendar.DAY_OF_MONTH表示这天是几号,Calendar.DAY_OF_WEEK表示是星期几
            if(week==0||week==6)
            {
                cal.add(Calendar.DAY_OF_MONTH,+1);
                startTime=cal.getTime();
                continue;
            }
            result+=1;
            cal.add(Calendar.DAY_OF_MONTH,+1);
            startTime=cal.getTime();
        }
        return result;
    }

    @Override
    public Long exceptAllPersonDays(Long id) {
        return respository.sumAllExceptPersonDays(id);
    }

    @Override
    public Long actalAllPersonDays(Long id) {
        return respository.sumAllActualPersonDays(id);
    }


    @Override
    public Date startDate(Long id) {
        return respository.sumAllStartData(id);
    }

    @Override
    public Date endDate(Long id) {
        return respository.sumAllEndData(id);
    }

}
