package com.zy.tzhmbs.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zy.tzhmbs.common.Constants;
import com.zy.tzhmbs.entity.Admin;
import com.zy.tzhmbs.entity.Developer;
import com.zy.tzhmbs.entity.Emp;
import com.zy.tzhmbs.entity.Owner;
import com.zy.tzhmbs.exception.ServiceException;
import com.zy.tzhmbs.service.AdminService;
import com.zy.tzhmbs.service.DeveloperService;
import com.zy.tzhmbs.service.EmpService;
import com.zy.tzhmbs.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private DeveloperService developerService;
    @Autowired
    private EmpService empService;
    @Autowired
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");

        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //执行认证
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        //获取token里的userId和角色
        String ID;
        int role;
        try {
            ID = JWT.decode(token).getAudience().get(0);
            role = Integer.parseInt(JWT.decode(token).getAudience().get(1));
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401, "token验证失败");
        }
        //根据token中的userID查询数据库
        if (role == 0) {
            Owner U = ownerService.getById(ID);
            if (U == null){
                throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
            }
            //用户密码加上签名 验证token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(U.getPassword())).build();
            try{
                jwtVerifier.verify(token);
            }catch (JWTVerificationException e){
                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
            }
            return true;

        }else if (role == 1) {
            Developer U = developerService.getById(ID);
            if (U == null){
                throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
            }
            //用户密码加上签名 验证token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(U.getPassword())).build();
            try{
                jwtVerifier.verify(token);
            }catch (JWTVerificationException e){
                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
            }
            return true;

        }else if (role == 2){
            Emp U = empService.getById(ID);
            if (U == null){
                throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
            }
            //用户密码加上签名 验证token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(U.getPassword())).build();
            try{
                jwtVerifier.verify(token);
            }catch (JWTVerificationException e){
                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
            }
            return true;

        }else {
            Admin U = adminService.getById(ID);
            if (U == null){
                throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
            }
            //用户密码加上签名 验证token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(U.getPassword())).build();
            try{
                jwtVerifier.verify(token);
            }catch (JWTVerificationException e){
                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
            }
            return true;
        }
    }
}
