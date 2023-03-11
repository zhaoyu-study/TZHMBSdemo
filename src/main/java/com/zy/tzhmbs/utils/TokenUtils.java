package com.zy.tzhmbs.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zy.tzhmbs.entity.Owner;
import com.zy.tzhmbs.service.AdminService;
import com.zy.tzhmbs.service.DeveloperService;
import com.zy.tzhmbs.service.EmpService;
import com.zy.tzhmbs.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static OwnerService staticOwnerService;
    private static DeveloperService staticDeveloperService;
    private static EmpService staticEmpService;
    private static AdminService staticAdminService;

    @Resource
    private OwnerService ownerService;

    @Resource
    private DeveloperService developerService;

    @Resource
    private EmpService empService;

    @Resource
    private AdminService adminService;

    @PostConstruct
    public void setOwnerService(){
        staticOwnerService = ownerService;
    }

    @PostConstruct
    public void setDeveloperService(){
        staticDeveloperService = developerService;
    }

    @PostConstruct
    public void setEmpService(){
        staticEmpService = empService;
    }

    @PostConstruct
    public void setAdminService(){
        staticAdminService = adminService;
    }

    /**
     *生成token,
     * ID是实体类id,uId
     * signKey就是当前用户的密码,uPassword
     * @param ID
     * @param signKey
     * @return
     */
    public static  String generateToken(String ID,String signKey,String role){
         return JWT.create().withAudience(ID,role) //将id保存到token
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))//设置token过期时间,2h 后
                .sign(Algorithm.HMAC256(signKey));//以password作为token的密钥
    }

    /**
     * 获取当前登录的用户信息
     * 静态方法不需要new，直接引用就行
     * 引用：TokenUtils.getCurrentUser();
     * 有可能是null，需要作判定
     * @return
     */
    //静态方法不能引用非静态属性
    public static Object getCurrentU(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isNotBlank(token)){
                String Id = JWT.decode(token).getAudience().get(0);
                int role = Integer.parseInt(JWT.decode(token).getAudience().get(1));
                if (role == 0) {
                    return staticOwnerService.getById(Id);
                }else if (role == 1) {
                    return staticDeveloperService.getById(Id);
                }else if (role == 2) {
                    return staticEmpService.getById(Id);
                }else{
                    return staticAdminService.getById(Id);
                }
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
