package cn.Controller;

import cn.common.R;
import cn.common.UserLoginToken;
import cn.model.entity.Project;
import cn.model.entity.Task;
import cn.service.impl.ProjectServiceimpl;
import cn.service.impl.TaskServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/file")
public class FileControl {
    private static final Logger log= LoggerFactory.getLogger(FileControl.class);
    @Autowired
    ProjectServiceimpl projectServiceimpl;
    @Autowired
    TaskServiceimpl serviceimpl;
    //@UserLoginToken
    @RequestMapping(value="/upload",method= RequestMethod.POST)
    @ResponseBody  //上传
    public String upload1(@RequestParam("file") MultipartFile file, Object object, ModelAndView modelAndView) throws IOException
    {
        log.info("[文件类型]-[{}]",file.getContentType());
        log.info("[文件名称]-[{}]",file.getName());
        log.info("[文件大小]-[{}]",file.getSize());
        //TODO将文件写入到指定目录
        file.transferTo(new File("D:\\"+file.getOriginalFilename()));
//        Map<String,String> result=new HashMap<>(16);
//        result.put("contentType",file.getContentType());
//        result.put("fileName",file.getOriginalFilename());
//        result.put("fileSize",file.getSize()+"");
//        String filename=file.getOriginalFilename();
//        String fileurl="D:\\"+file.getOriginalFilename();
        return file.getOriginalFilename();
    }

    @UserLoginToken
    @RequestMapping(value = "/downloadprojectfile/{id}",method = RequestMethod.POST)
    @ResponseBody
    public R<String> downloadprojectfile(@PathVariable Long id) throws IOException {

        Project p=projectServiceimpl.showOneProject(id);
        String prodocument=p.getProject_requirement_document();
        String url1 = "D:\\"+prodocument;// 源文件路径
        String url2 = "C:\\Users\\Administrator\\Desktop\\"+prodocument;          // 复制到目标路
        FileInputStream in = new FileInputStream(new File(url1));
        FileOutputStream out = new FileOutputStream(new File(url2));
        byte[] buff = new byte[512];
        int n = 0;
//        System.out.println("复制文件：" + "\n" + "源路径：" + url1 + "\n" + "目标路径：" + url2);
        while ((n = in.read(buff)) != -1) {
            out.write(buff, 0, n);
        }
        out.flush();
        in.close();
        out.close();
        return R.success("已成功下载"+prodocument);
    }

    @UserLoginToken
    @RequestMapping(value = "/downloadtaskfile/{id}",method = RequestMethod.POST)
    @ResponseBody
    public R<String> downloadtaskfile(@PathVariable Long id) throws IOException {

        Task t=serviceimpl.showOneTask(id);
        String prodocument=t.getTask_design_document();
        String url1 = "D:\\"+prodocument;// 源文件路径
        String url2 = "C:\\Users\\Administrator\\Desktop\\"+prodocument;          // 复制到目标路
        FileInputStream in = new FileInputStream(new File(url1));
        FileOutputStream out = new FileOutputStream(new File(url2));
        byte[] buff = new byte[512];
        int n = 0;
//        System.out.println("复制文件：" + "\n" + "源路径：" + url1 + "\n" + "目标路径：" + url2);
        while ((n = in.read(buff)) != -1) {
            out.write(buff, 0, n);
        }
        out.flush();
        in.close();
        out.close();
        return R.success("已成功下载"+prodocument);
    }
}
