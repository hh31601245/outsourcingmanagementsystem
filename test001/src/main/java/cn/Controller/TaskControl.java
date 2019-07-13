package cn.Controller;

import cn.common.*;
import cn.model.entity.Project;
import cn.model.entity.Task;
import cn.service.impl.ProjectServiceimpl;
import cn.service.impl.TaskServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskControl {
    @Autowired
    TaskServiceimpl serviceimpl;
    @Autowired
    ProjectServiceimpl projectServiceimpl;
    /**
     * 添加并分配任务
     * @param task
     * @return
     */
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public R<String> addTask(@ModelAttribute Task task)
    {
        task.setTask_status("已分配");
        System.out.println(task.getTask_project_id());
        Project p=projectServiceimpl.showOneProject(task.getTask_project_id());
        p.setProject_status("已分配");
        projectServiceimpl.modifyProject(p);
        serviceimpl.addTask(task);
        return R.success("新建任务成功");
    }

//    /**
//     * 分配任务
//     * @param id
//     * @param task_developers_id
//     * @return
//     */
//    @UserLoginToken
//    @contractor("承包方管理员")
//    @RequestMapping(value="/assgintask/{id}",method = RequestMethod.POST)
//    public R<String>  assginTask(@PathVariable Long id, @RequestParam Long task_developers_id,@RequestParam Date task_start_time)
//    {
//        Task task=serviceimpl.showOneTask(id);
//        task.setId(id);
//        task.setTask_start_time(task_start_time);
//        task.setTask_status("已分配");
//        task.setTask_developers_id(task_developers_id);
//        Project p=projectServiceimpl.showOneProject(task.getTask_project_id());
//        p.setProject_status("已分配");
//        projectServiceimpl.modifyProject(p);
//        serviceimpl.modifyTask(task);
//        return R.success("分配任务成功");
//    }

    /**
     * 显示当前项目的所有任务。显示时已经显示的是承包方管理员承包的项目了
     * @param projectid
     * @return
     */
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/showoneprojecttask/{projectid}",method = RequestMethod.POST)
    public R<List<Task>> showOneProjectTask(@PathVariable Long projectid)
    {
        List<Task> result=serviceimpl.showTaskByProjectId(projectid);
        return R.data(result);
    }

    /**
     * 开发人员结束任务，自动计算实际人天以及判断项目是否完成
     * @param id
     * @param task_end_time
     * @return
     */
    @UserLoginToken
    @Developer("开发人员")
    @RequestMapping(value="/finishtask/{id}",method = RequestMethod.POST)
    public R<String> finishTask(@PathVariable Long id,@RequestParam Date task_end_time)
    {
        Task task=serviceimpl.showOneTask(id);
        task.setId(id);
        Date starttime=task.getTask_start_time();
        Long day1=(Long)(task_end_time.getTime()-starttime.getTime())/(1000*3600*24);
        System.out.println(day1);
        if(day1<=0L)
        {
            System.out.println(day1);
            return R.fail("截止日期不能小于开始日期");
        }
        task.setTask_end_time(task_end_time);
        task.setTask_status("已完成");
        serviceimpl.modifyTask(task);
        Long task_actual_person_days=serviceimpl.actualPersonDays(id);
        task.setTask_actual_person_days(task_actual_person_days);
        serviceimpl.modifyTask(task);
        //如果项目已完成任务个数等于所有的任务个数
        if(serviceimpl.sumTask(task.getTask_project_id())==serviceimpl.finishedTask(task.getTask_project_id()))
        {
            Long project_id=task.getTask_project_id();
            System.out.println(project_id);
            Project project=projectServiceimpl.showOneProject(project_id);
            project.setProject_status("待验收");
            projectServiceimpl.modifyProject(project);
        }
        return R.success("成功完成任务");
    }

    /**
     * 查看当前项目下当前开发人员的列表
     * @param projectid
     * @param request
     * @return
     */
    @UserLoginToken
    @Developer("开发人员")
    @RequestMapping(value = "/showdevelopertask/{projectid}",method = RequestMethod.POST)
    public R<List<Task>> showDeveloperTask(@PathVariable Long projectid,HttpServletRequest request)
    {
        Long developer_id= getHeaders.getHeadersId(request);
        List<Task> result=serviceimpl.showTaskByProjectIdAndDeveloperId(projectid,developer_id);
        return R.data(result);
    }


    @UserLoginToken
    @RequestMapping(value="/showonetask/{id}",method = RequestMethod.POST)
    public R<List<Task>> showOneTask(@PathVariable Long id)
    {
        Task a=serviceimpl.showOneTask(id);
        List<Task> result=new ArrayList<>();
        result.add(a);
        return R.data(result);
    }

    /**
     * 获取开发人员数
     * @param projectid
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/sumperson/{projectid}",method=RequestMethod.POST)
    public R<Long> showsumperson(@PathVariable Long projectid)
    {
        Long result=serviceimpl.sumTask(projectid);
        return R.data(result);
    }

    /**
     * 获取总的预计人天
     * @param projectid
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/sumexpectdayandperson/{projectid}",method = RequestMethod.POST)
    public R<Long> showsumExpectDayAndPerson(@PathVariable Long projectid)
    {
        Long result=serviceimpl.exceptAllPersonDays(projectid);
        return R.data(result);
    }

    /**
     * 获取总的实际人天
     * @param projectid
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/sumactualdayandperson/{projectid}",method = RequestMethod.POST)
    public R<Long> showsumActualDayAndPerson(@PathVariable Long projectid)
    {
        Long result=0L;
        if(serviceimpl.finishedTask(projectid)==serviceimpl.sumTask(projectid))
        {
            result=serviceimpl.actalAllPersonDays(projectid);
        }

        return R.data(result);
    }

    /**
     * 获取总的完成任务数
     * @param projectid
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/sumfinishedtask/{projectid}",method = RequestMethod.POST)
    public R<Long> showSumFinishedTask(@PathVariable Long projectid)
    {
        Long result=serviceimpl.finishedTask(projectid);
        return R.data(result);
    }

    /**
     * 获取总的未完成任务数
     * @param projectid
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/sumnofinishedtask/{projectid}",method = RequestMethod.POST)
    public R<Long> showSumNoFinishedTask(@PathVariable Long projectid)
    {
        Long a=serviceimpl.sumTask(projectid);
        Long b=serviceimpl.finishedTask(projectid);
        Long result=a-b;
        return R.data(result);
    }

    /**
     * 获取总的开始时间
     * @param projectid
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/getstartdate/{projectid}",method = RequestMethod.POST)
    public R<Task> showStartDate(@PathVariable Long projectid)
    {
        Date result=serviceimpl.startDate(projectid);
        Task t=new Task();
        t.setTask_start_time(result);
        return R.data(t);
    }

    /**
     * 获取总的结束时间
     * @param projectid
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/getenddate/{projectid}",method = RequestMethod.POST)
    public R<Task> showEndDate(@PathVariable Long projectid)
    {
        Date result=serviceimpl.endDate(projectid);
        Task t=new Task();
        t.setTask_start_time(result);
        return R.data(t);
    }

    @UserLoginToken
    @RequestMapping(value="/showtaskrequirement/{id}",method = RequestMethod.POST)
    public R<String>  gettaskrequirement(@PathVariable Long id)
    {
        Task a=serviceimpl.showOneTask(id);
        String result=a.getTask_design_document();
        return R.data(result);
    }
}
