package com.zy.tzhmbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.tzhmbs.common.Constants;
import com.zy.tzhmbs.entity.Developer;
import com.zy.tzhmbs.exception.ServiceException;
import com.zy.tzhmbs.mapper.DeveloperMapper;
import com.zy.tzhmbs.service.DeveloperService;
import com.zy.tzhmbs.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeveloperServiceImpl extends ServiceImpl<DeveloperMapper, Developer> implements DeveloperService {
    @Resource
    private DeveloperMapper developerMapper;

    /**
     * 将此类的重复代码封装
     * @param user
     * @return
     */
    private Developer getUserInfo(Developer user){
        QueryWrapper<Developer> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getUserId());
        queryWrapper.eq("password", user.getPassword());
        queryWrapper.eq("role", user.getRole());

        Developer one;
        try {
            one = getOne(queryWrapper);//如果结果不唯一，则抛出异常
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500, "系统错误，结果不唯一");
        }
        return one;
    }

    @Override
    public Developer login(Developer developer) {

        Developer one = getUserInfo(developer);

        if(one != null){//能查出结果
            //BeanUtils.copyProperties(one,user);//用one里的赋值userDTO相同属性的值
            //设置token
            String token = TokenUtils.generateToken(one.getUserId(), one.getPassword(),one.getRole().toString());
            one.setToken(token);
            return one;
        }else {
            throw new ServiceException(Constants.CODE_600,"账号或密码或身份选择错误");
        }

    }

    @Override
    public void register(Developer developer) {

        Developer one = getUserInfo(developer);

        if(one == null){//能查出结果，说明用户名与密码重复了
            one = new Developer();           //这里不new的话下面报空指针异常
            BeanUtils.copyProperties(developer,one);
            save(one);
        }else {
            throw new ServiceException(Constants.CODE_700, "用户名与密码重复，请修改用户名或密码");
        }
    }

}
