package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.*;
import com.github.wxiaoqi.security.admin.entity.Group;
import com.github.wxiaoqi.security.admin.entity.Menu;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.rpc.service.PermissionService;
import com.github.wxiaoqi.security.admin.util.MatrixToImageWriter;
import com.github.wxiaoqi.security.admin.vo.ChildVO;
import com.github.wxiaoqi.security.admin.vo.FrontUser;
import com.github.wxiaoqi.security.admin.vo.MenuTree;
import com.github.wxiaoqi.security.admin.vo.UserVo;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.api.entity.Teacher;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.constant.CommonConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.taobao.api.ApiException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-08 11:51
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController<UserBiz,User> {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private TeacherBiz teacherBiz;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private IdentifyCodeBiz iu;

    @Autowired
    private SmsAlidayuBiz msgService;

    @Autowired
    private GroupBiz groupBiz;
    
    @Autowired
    private OrgBiz orgBiz;

    @Autowired
    StringRedisTemplate redisTemplate;
    
    @Autowired
    private MediaAdminBiz mediaAdminBiz;

    Logger logger = Logger.getLogger(UserController.class);
    /**
     * 添加账号
     * @param user
     * @param roleId
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/{roleId}",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<User> add(@RequestBody User user,@PathVariable Integer roleId) throws Exception{
        Group g = groupBiz.selectById(roleId);
        if("orgRole".equals(g.getCode())){//添加机构账号
            user.setType("0");
            baseBiz.insertSelective(user);
            User user2 = userBiz.getUserByUsername(user.getUsername());
            groupBiz.addRole(roleId,user2.getId());
            Org org = new Org();
            org.setOrgName(user2.getUsername());
            org.setBaseUserId(user2.getId());
            orgBiz.insertSelective(org);
            org = orgBiz.selectOne(org);
            File file = MatrixToImageWriter.createQrcodeFile(String.valueOf(org.getId()));
            String imgUrl = mediaAdminBiz.uploadToOss(file, MediaType.IMAGE_JPEG_VALUE);
            org.setOrgQrcodeUrl(imgUrl);
            orgBiz.updateById(org);
            try{
            	Thread.currentThread().sleep(5000);
            	file.delete();
            }catch(Exception e){
            	file.delete();
            }
        }else{//添加管理员账号
            baseBiz.insertSelective(user);
            User user2 = userBiz.getUserByUsername(user.getUsername());
            groupBiz.addRole(roleId,user2.getId());
        }
        return new ObjectRestResponse<User>().rel(true);
    }

    /**
     * 获取角色列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    public List<Group> getAllRoles() throws Exception {
        List<Group> groups = new ArrayList<>();
        List<Group> groupList =  groupBiz.selectListAll();
        for(Group g : groupList){
            if(!"teacherRole".equals(g.getCode())){
                groups.add(g);
            }
        }
        return groups;
    }

    @RequestMapping(value = "/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserInfo(String token) throws Exception {
        FrontUser userInfo = permissionService.getUserInfo(token);
        if(userInfo==null) {
            return ResponseEntity.status(401).body(false);
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }

    @RequestMapping(value = "/front/menus", method = RequestMethod.GET)
    public @ResponseBody
    List<MenuTree> getMenusByUsername(String token) throws Exception {
        return permissionService.getMenusByUsername(token);
    }

    @RequestMapping(value = "/front/menu/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Menu> getMenuByUserId(@PathVariable Integer userId) throws Exception {
        return permissionService.getMenuByUserId(userId);
    }

    @RequestMapping(value = "/byPhone", method = RequestMethod.GET)
    public @ResponseBody
    List<ChildVO> getChildByPhone(@RequestParam("cellphone") String cellphone) throws Exception {
        return userBiz.getChildByPhone(cellphone);
    }

    /**
     * 注册老师
     * @param
     * @return
     */
   
    @PostMapping("/register")
    public ObjectRestResponse<User> register(@RequestBody UserVo uvo) {
        if(!iu.identifyCode(uvo.getPhone(), uvo.getIdentifyCode())){
            return new ObjectRestResponse<User>().rel(false).data("验证码错误");
        }else{
            User user = userBiz.getUserByUsername(uvo.getPhone());
            if(user==null){
                user = new User();
                user.setUsername(uvo.getPhone());
                user.setName(uvo.getName());
                user.setMobilePhone(uvo.getPhone());
                user.setEmail(uvo.getEmail());
                user.setType("1");
                user.setPassword(encoder.encode(uvo.getPassword()));
                userBiz.insert(user);
                user = userBiz.getUserByUsername(uvo.getPhone());
                
                Teacher teacher = new Teacher();
                teacher.setTeacherName(uvo.getName());
                teacher.setPhone(uvo.getPhone());
                teacher.setCreateTime(new Date());
                teacher.setUserId(Long.valueOf(user.getId()));
                teacherBiz.insertSelective(teacher);

                //为老师账号分配教师角色
                groupBiz.addRole(11,user.getId());
             }else{
            	 return new ObjectRestResponse<User>().rel(false).data("该手机号已经注册.");
             }
             return new ObjectRestResponse<User>().rel(true);
           
        }


    }
    @IgnoreClientToken
    @PostMapping("/phone/{cellPhone}")
    @ResponseBody
    public ObjectRestResponse<User> phoneSms(@PathVariable(value = "cellPhone") String cellPhone){

        if(userBiz.phoneExist(cellPhone)){
            return new ObjectRestResponse<User>().rel(false).data("手机号已注册！");
        }else{
          String randomkey = String.format(CommonConstants.RANDOMKEY, cellPhone);
          int random = (int) ((Math.random() * 9 + 1) * 100000);
          try {
              msgService.sendLoginVerifySMS(cellPhone, String.valueOf(random));
              ValueOperations<String, String> ops = redisTemplate.opsForValue();
              ops.set(randomkey, String.valueOf(random));
              return new ObjectRestResponse<User>().rel(true).data("短信发送成功！");
          } catch (ApiException e) {
              return new ObjectRestResponse<User>().rel(false).data("短信发送失败！");
          }

        }

    }
    @IgnoreClientToken
    @PostMapping("/phone/valid/{cellPhone}")
    @ResponseBody
    public ObjectRestResponse<User> phoneValid(@PathVariable(value = "cellPhone") String cellPhone){

        if(userBiz.phoneExist(cellPhone)){
          String randomkey = String.format(CommonConstants.RANDOMKEY, cellPhone);
          int random = (int) ((Math.random() * 9 + 1) * 100000);
          try {
              msgService.sendLoginVerifySMS(cellPhone, String.valueOf(random));
              ValueOperations<String, String> ops = redisTemplate.opsForValue();
              ops.set(randomkey, String.valueOf(random));
              return new ObjectRestResponse<User>().rel(true).data("短信发送成功！");
          } catch (ApiException e) {
              return new ObjectRestResponse<User>().rel(false).data("短信发送失败！");
          }

        }else{
            return new ObjectRestResponse<User>().rel(false).data("手机号不存在！");

        }

    }

    @IgnoreClientToken
    @PostMapping("/forget")
    @ResponseBody
    public ObjectRestResponse<User> forgetPwd(@RequestBody UserVo uvo){
        if(!iu.identifyCode(uvo.getPhone(), uvo.getIdentifyCode())){
            return new ObjectRestResponse<User>().rel(false).data("验证码错误");
        }else{
            User user = new User();
            user.setMobilePhone(uvo.getPhone());
            try{
                User ul = userBiz.selectOne(user);
                if(ul==null){
                    return new ObjectRestResponse<User>().rel(false).data("无此用户！");
                }else{
                    ul.setPassword(encoder.encode(uvo.getPassword()));
                    userBiz.updateSelectiveById(ul);
                    return new ObjectRestResponse<User>().rel(true);
                }
            }catch (Exception e){
                return new ObjectRestResponse<User>().rel(false).data("系统错误，请联系管理员!");
            }
        }
    }
    

	@PostMapping("/changemobile")
    @ResponseBody
    public ObjectRestResponse<User> changeMobile(@RequestBody UserVo uvo) {
        if (!iu.identifyCode(uvo.getNewCellPhone(), uvo.getIdentifyCode())) {
            return new ObjectRestResponse<User>().rel(false).data("验证码错误");
        } else {
            String userId = this.getCurrentUserId();
            User user = userBiz.selectById(Integer.parseInt(userId));
            if (encoder.matches(uvo.getPassword(), user.getPassword())) {
                user.setMobilePhone(uvo.getNewCellPhone());
                userBiz.updateById(user);
                return new ObjectRestResponse<User>().rel(true).data("修改成功");
            } else {
                return new ObjectRestResponse<User>().rel(false).data("密码错误");
            }
        }
    }

    @PostMapping("/changepassword")
    @ResponseBody
    public ObjectRestResponse<User> changePassword(@RequestBody UserVo uvo) {
            String userId = this.getCurrentUserId();
            User user = userBiz.selectById(Integer.parseInt(userId));
            if (encoder.matches(uvo.getPassword(), user.getPassword())) {
                user.setPassword(encoder.encode(uvo.getNewPassword()));
                userBiz.updateById(user);
                return new ObjectRestResponse<User>().rel(true).data("修改成功");
            } else {
                return new ObjectRestResponse<User>().rel(false).data("密码错误");
            }

    }

    /**
     * 清空session
     */
    @DeleteMapping("/delSession")
    @ResponseBody
    public void clearSession(){
        logger.info("clear session.....");
    }

}
