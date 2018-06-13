package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.MediaAdminBiz;
import com.github.wxiaoqi.security.admin.entity.Media;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传文件
 * Created by Jason on 16/5/23.
 */
@IgnoreClientToken
@IgnoreUserToken
@Controller
@RequestMapping(value="/filemg")
public class FileManageController extends BaseController<MediaAdminBiz, Media> {

    @Autowired
    private MediaAdminBiz mediaAdminService;

    @RequestMapping(value="upload")
    @ResponseBody
    public Map<String,Object> fileUpload(@RequestParam("file") MultipartFile file, String type){
        Map<String,Object> ret = new HashMap<>();
        ret.put("SUCCESS", Boolean.FALSE);

        String url = null;
        try {
            url = this.mediaAdminService.uploadToOss(file,type);
            ret.put("SUCCESS", Boolean.TRUE);
        } catch (IOException e) {
            ret.put("SUCCESS", Boolean.FALSE);
            e.printStackTrace();
        }
//        String url = this.mediaService.uploadToLocal(file,type);
        ret.put("url",url);

        return ret;
    }
    
    @RequestMapping(value="/uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("upfile") MultipartFile file, HttpServletRequest request, HttpServletResponse response){
    	String type = request.getParameter("type");
    	Map<String,Object> ret = new HashMap<>();
        String url = null;
        try {
            url = this.mediaAdminService.uploadToOss(file,type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String url = this.mediaService.uploadToLocal(file,type);
        ret.put("url",url);
        ret.put("state", "SUCCESS");
        return ret;  
    }  
}
