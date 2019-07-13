package cn.Controller;

import cn.common.*;
import cn.model.entity.User;
import cn.service.impl.UserServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/user")
public class UserControl {

    @Autowired
    UserServiceimpl userServiceimpl;

//    //登录
//    @PassToken
//    @RequestMapping(value="/login",method = RequestMethod.POST)
//   public R<String> Login(@RequestBody User user/*@RequestParam String user_name,@RequestParam String user_password*/)
//    {
//        User user1=userServiceimpl.getUserByName(user.getUser_name());
//        if(user1==null)
//        {
//            return R.fail("该用户不存在");
//        }
//        return user1.getUser_password().equals(HashKit.sha512(user.getUser_password()+user1.getSalt()))?R.loginsuccess(user):R.fail("密码错误");
//    }

    //登录
    @PassToken
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public R<String> Login(@RequestParam String user_name,@RequestParam String user_password)
    {
        User user=userServiceimpl.getUserByName(user_name);
        if(user==null)
        {
            return R.fail("该用户不存在");
        }
        return user.getUser_password().equals(HashKit.sha512(user_password+user.getSalt()))?R.loginsuccess(user):R.fail("密码错误");
    }

    //注册发包方和承包方用户
    @PassToken
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public R<String> AddUser(@Valid @ModelAttribute User user)
    {

        if(userServiceimpl.getUserByName(user.getUser_name())!=null)
        {
            return R.fail("该用户名已被注册，请重新注册");
        }
        user.setSalt(HashKit.generateSalt(8));
        user.setUser_password(HashKit.sha512(user.getUser_password()+user.getSalt()));
        if(userServiceimpl.getUserByNameAndPassword(user.getUser_name(),user.getUser_password())==null)
        {
            userServiceimpl.addUser(user);
            return R.success("注册成功");
        }
        else
        {
            return R.fail("用户已存在");
        }
    }

    //注册开发人员
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/adddeveloper",method=RequestMethod.POST)
    @ResponseBody
    public R<String> AddDeveloper(@Valid @ModelAttribute User user, HttpServletRequest request)
    {
        if(userServiceimpl.getUserByName(user.getUser_name())!=null)
        {
            return R.fail("该用户名已被注册，请重新注册");
        }
        user.setUser_role("开发人员");
        user.setSalt(HashKit.generateSalt(8));
        user.setUser_password(HashKit.sha512(user.getUser_password()+user.getSalt()));
        Long LeaderId=getHeaders.getHeadersId(request); //获取承包管理员的id
        user.setUser_leader_id(LeaderId);
        userServiceimpl.addUser(user);
        return R.success("注册成功");
    }

    //修改用户
    @UserLoginToken
    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public R<String> ModifyUser(@PathVariable Long id,@Valid @ModelAttribute User user)
    {
        user.setSalt(HashKit.generateSalt(8));
        user.setUser_password(HashKit.sha512(user.getUser_password()+user.getSalt()));
        //user.setId(id);
        userServiceimpl.updateUser(user);
        return R.success("修改成功");
    }

    //删除开发用户信息
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public R<String> DeleteUser(@PathVariable Long id)
    {
        userServiceimpl.deleteById(id);
        return R.success("删除成功");
    }

    //显示开发人员列表
    @UserLoginToken
    @contractor("承包方管理员")
    @RequestMapping(value="/developerlist")
    public R<List<User>> showDeveloperList(HttpServletRequest request)
    {
        Long id=getHeaders.getHeadersId(request);
        List<User> result=userServiceimpl.getdevelopers(id);
        return R.data(result);
    }

    //显示所有用户信息
    @UserLoginToken
    @RequestMapping(value="/alluserlist",method=RequestMethod.POST)
    public R<List<User>> ShowAllUserList()
    {
        List<User> result=userServiceimpl.getAllUser();
        return R.data(result);
    }

    /**
     * 获取token的权限
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/getrole",method = RequestMethod.POST)
    public R<String> Showrole(HttpServletRequest request)
    {
        return R.data(getHeaders.getHeaderRole(request));
    }

    /**
     * 获取token的id
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/getid",method = RequestMethod.POST)
    public R<Long> Showuserid(HttpServletRequest request)
    {
        return R.data(getHeaders.getHeadersId(request));
    }

    /**
     * 获取token的name
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value="/getname",method = RequestMethod.POST)
    public R<String> Showusername(HttpServletRequest request)
    {
        return R.data(getHeaders.getHeaderName(request));
    }
}
