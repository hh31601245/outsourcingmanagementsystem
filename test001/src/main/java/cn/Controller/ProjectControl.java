package cn.Controller;

import cn.common.*;
import cn.model.entity.Project;
import cn.service.impl.ProjectServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/project")
public class ProjectControl {
    @Autowired
    ProjectServiceimpl projectServiceimpl;

    /**
     * 新建项目，前台要输的是项目名称，项目说明，期望完成时间和需求文档
     * @param project
     * @param request
     * @return
     */
    @UserLoginToken
    @employer("发包方")
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public R<String> addProject(@ModelAttribute Project project, HttpServletRequest request)
    {
        System.out.println(project.getProject_name());
        System.out.println(project.getProject_expected_endtime());
        project.setProject_status("已发布");
        Long employer_id= getHeaders.getHeadersId(request);
        project.setProject_employer_id(employer_id);
        projectServiceimpl.addProject(project);
        return R.success("发布项目成功");
    }

    /**
     * 验收项目，前台只要选择项目点击审核按钮即可
     * @param id
     * @return
     */
    @UserLoginToken
    @employer("发包方")
    @RequestMapping(value="/approval/{id}",method = RequestMethod.POST)
    public R<String> approvalProject(@PathVariable Long id)
    {
        Project project=projectServiceimpl.showOneProject(id);
        if(project.getProject_status().equals("待验收"))
        {
            project.setProject_status("已验收");
            project.setId(id);
            projectServiceimpl.modifyProject(project);
            return R.success("验收成功");
        }
        else
        {
            return R.fail("这个项目还不能验收");
        }

    }

    /**
     * 删除项目，只能删除未承包的项目
     * @param id
     * @return
     */
    @UserLoginToken
    @employer("发包方")
    @RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
    public R<String> deleteProject(@PathVariable Long id)
    {
        Project project=projectServiceimpl.showOneProject(id);
        String status=project.getProject_status();
        if(status.equals("已发布"))
        {
            projectServiceimpl.deleteProject(id);

        }
        else
        {
            return R.fail("已承包的项目无法删除");
        }
        return R.success("删除成功");
    }

    /**
     * 显示所有该发包方的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @employer("发包方")
    @RequestMapping(value = "/showallemployerproject",method = RequestMethod.GET)
    public R<List<Project>> ShowAllEmployerProject(HttpServletRequest request)
    {
        System.out.println("123");
        Long employer_id=getHeaders.getHeadersId(request);
        List<Project> result=projectServiceimpl.showProjectByIdAndRole(employer_id,"发包方");
        return R.data(result);
    }

    /**
     * 显示所有该发包方未被承包的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @employer("发包方")
    @RequestMapping(value="/shownocontractemployerproject",method = RequestMethod.POST)
    public R<List<Project>> ShowNoContractEmployerProject(HttpServletRequest request)
    {
        Long employer_id=getHeaders.getHeadersId(request);
        List<Project> result=projectServiceimpl.showProjectByIdAndRoleAndStatus(employer_id,"发包方","已发布");
        return R.data(result);
    }

    /**
     * 显示该发包方正在进行中的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @employer("发包方")
    @RequestMapping(value="/showcontractemployerproject",method = RequestMethod.POST)
    public R<List<Project>> ShowContractEmployerProject(HttpServletRequest request)
    {
        Long employer_id=getHeaders.getHeadersId(request);
        List<Project> result=projectServiceimpl.showProjectByIdAndRoleAndStatus(employer_id,"发包方","已承包");
        List<Project> a=projectServiceimpl.showProjectByIdAndRoleAndStatus(employer_id,"发包方","已分配");
        result.addAll(a);
        return R.data(result);
    }

    /**
     * 显示该发包方待审核的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @employer("发包方")
    @RequestMapping(value="/shownoapprovalemployerproject",method = RequestMethod.POST)
    public R<List<Project>> ShowNoApprovalEmployerProject(HttpServletRequest request)
    {
        Long employer_id=getHeaders.getHeadersId(request);
        List<Project> result=projectServiceimpl.showProjectByIdAndRoleAndStatus(employer_id,"发包方","待验收");
        return R.data(result);
    }

    /**
     * 显示该发包方已审核的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @employer("发包方")
    @RequestMapping(value="/showapprovalemployerproject",method = RequestMethod.POST)
    public R<List<Project>> ShowApprovalEmployerProject(HttpServletRequest request)
    {
        Long employer_id=getHeaders.getHeadersId(request);
        List<Project> result=projectServiceimpl.showProjectByIdAndRoleAndStatus(employer_id,"发包方","已验收");
        return R.data(result);
    }

    /**
     * 承包项目，前台只要选择项目点击承包按钮即可
     * @param id
     * @param request
     * @return
     */
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/contract/{id}",method=RequestMethod.POST)
    public R<String> contractProject(@PathVariable Long id,HttpServletRequest request)
    {
        Project project=projectServiceimpl.showOneProject(id);
        if(project.getProject_status().equals("已发布"))
        {
            project.setProject_status("已承包");
            Long contractor_id=getHeaders.getHeadersId(request);
            project.setProject_contractor_id(contractor_id);
            project.setId(id);
            projectServiceimpl.modifyProject(project);
            return R.success("承包成功");
        }
        else
        {
            return R.fail("该项目无法承包");
        }
    }

    /**
     * 显示该承包方可接的项目
     * @return
     */
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/showcancontractorproject",method = RequestMethod.POST)
    public R<List<Project>> showcancontractorproject()
    {
        List<Project> result=projectServiceimpl.showAllProjectByStatus("已发布");
        return R.data(result);
    }

    /**
     * 显示该承包方的所有项目
     * @param request
     * @return
     */
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/showallcontractorproject",method = RequestMethod.POST)
    public R<List<Project>> showAllContractorProject(HttpServletRequest request)
    {
        Long contractor_id=getHeaders.getHeadersId(request);
        List<Project> result=projectServiceimpl.showProjectByIdAndRole(contractor_id,"承包方管理员");
        return R.data(result);
    }

    /**
     * 显示该承包方已承包的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/showstartedcontractorproject",method = RequestMethod.POST)
    public R<List<Project>> showStartedContractorProject(HttpServletRequest request)
    {
        Long contractor_id=getHeaders.getHeadersId(request);
        List<Project> result=projectServiceimpl.showProjectByIdAndRoleAndStatus(contractor_id,"承包方管理员","已承包");
        return R.data(result);
    }

    /**
     * 显示该承包方已分配的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/showendcontractorproject",method = RequestMethod.POST)
    public R<List<Project>> showEndContractorProject(HttpServletRequest request)
    {
        Long contractor_id=getHeaders.getHeadersId(request);
        List<Project> result=projectServiceimpl.showProjectByIdAndRoleAndStatus(contractor_id,"承包方管理员","已分配");
        return R.data(result);
    }



    /**
     * 显示该承包方待验收的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/showsnoapprovalcontractorproject",method = RequestMethod.POST)
    public R<List<Project>> showNoApprovalContractorProject(HttpServletRequest request)
    {
        Long contractor_id=getHeaders.getHeadersId(request);
        List<Project> result=projectServiceimpl.showProjectByIdAndRoleAndStatus(contractor_id,"承包方管理员","待验收");
        return R.data(result);
    }

    /**
     * 显示该承包方已验收的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/showsapprovalcontractorproject",method = RequestMethod.POST)
    public R<List<Project>> showApprovalContractorProject(HttpServletRequest request)
    {
        Long contractor_id=getHeaders.getHeadersId(request);
        List<Project> result=projectServiceimpl.showProjectByIdAndRoleAndStatus(contractor_id,"承包方管理员","已验收");
        return R.data(result);
    }

    /**
     * 显示该开发人员涉及到的已经完成的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @Developer("开发人员")
    @RequestMapping(value="/showfinishdeveloperproject",method = RequestMethod.POST)
    public R<List<Project>> showFinishDeveloperProject(HttpServletRequest request)
    {
        Long developer_id=getHeaders.getHeadersId(request);
        List<Long> resultid=projectServiceimpl.showProjectIdByDeveloper(developer_id,"已验收");
        List<Project> result=new ArrayList<>();
        for(Long id : resultid) {
            Project p=projectServiceimpl.showOneProject(id);
            result.add(p);
        }
        return R.data(result);
    }

    /**
     * 显示该开发人员涉及到的未完成项目
     * @param request
     * @return
     */
    @UserLoginToken
    @Developer("开发人员")
    @RequestMapping(value="/shownofinishproject",method = RequestMethod.POST)
    public R<List<Project>> showNoFinishProject(HttpServletRequest request)
    {
        Long developer_id=getHeaders.getHeadersId(request);
        List<Long> resultid=projectServiceimpl.showProjectIdByDeveloper(developer_id,"已分配");
        List<Project> result=new ArrayList<>();
        for(Long id : resultid) {
            Project p=projectServiceimpl.showOneProject(id);
            result.add(p);
        }
        return R.data(result);
    }

    /**
     * 显示该开发人员待验收的项目
     * @param request
     * @return
     */
    @UserLoginToken
    @Developer("开发人员")
    @RequestMapping(value="/showwaitapprovalproject",method = RequestMethod.POST)
    public R<List<Project>> showWaitApprovalProject(HttpServletRequest request)
    {
        Long developer_id=getHeaders.getHeadersId(request);
        List<Long> resultid=projectServiceimpl.showProjectIdByDeveloper(developer_id,"待验收");
        List<Project> result=new ArrayList<>();
        for(Long id : resultid) {
            Project p=projectServiceimpl.showOneProject(id);
            result.add(p);
        }
        return R.data(result);
    }

    /**
     * 显示一个项目的详情
     * @param id
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/showoneproject/{id}",method = RequestMethod.POST)
    public R<List<Project>> showOneProject(@PathVariable Long id)
    {
        Project a=projectServiceimpl.showOneProject(id);
        List<Project> result=new ArrayList<>();
        result.add(a);
        return R.data(result);
    }

    @UserLoginToken
    @RequestMapping(value="/showprojectrequirement/{id}",method = RequestMethod.POST)
    public R<String>  getprojectrequirement(@PathVariable Long id)
    {
        Project a=projectServiceimpl.showOneProject(id);
        String result=a.getProject_requirement_document();
        return R.data(result);
    }
}
