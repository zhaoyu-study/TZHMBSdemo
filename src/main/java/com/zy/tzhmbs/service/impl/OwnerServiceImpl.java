package com.zy.tzhmbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.tzhmbs.common.Constants;
import com.zy.tzhmbs.entity.Owner;
import com.zy.tzhmbs.exception.ServiceException;
import com.zy.tzhmbs.mapper.OwnerMapper;
import com.zy.tzhmbs.service.OwnerService;
import com.zy.tzhmbs.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OwnerServiceImpl extends ServiceImpl<OwnerMapper, Owner> implements OwnerService {
    @Resource
    private OwnerMapper ownerMapper;

    /**
     * 将此类的重复代码封装
     * @param owner
     * @return
     */
    private Owner getUserInfo(Owner owner){
        QueryWrapper<Owner> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",owner.getUserId());
        queryWrapper.eq("password", owner.getPassword());
        queryWrapper.eq("role", owner.getRole());

        Owner one;
        try {
            one = getOne(queryWrapper);//如果结果不唯一，则抛出异常
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500, "系统错误，结果不唯一");
        }
        return one;
    }

    @Override
    public Owner login(Owner owner) {

        Owner one = getUserInfo(owner);

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
    public void register(Owner owner) {

        Owner one = getUserInfo(owner);

        if(one == null){//能查出结果，说明用户名与密码重复了
            one = new Owner();           //这里不new的话下面报空指针异常
            BeanUtils.copyProperties(owner,one);
            save(one);
        }else {
            throw new ServiceException(Constants.CODE_700, "用户名与密码重复，请修改用户名或密码");
        }
    }

}
