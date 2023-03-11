package com.zy.tzhmbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.tzhmbs.common.Constants;
import com.zy.tzhmbs.entity.Admin;
import com.zy.tzhmbs.exception.ServiceException;
import com.zy.tzhmbs.mapper.AdminMapper;
import com.zy.tzhmbs.service.AdminService;
import com.zy.tzhmbs.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 呀哈哈
* @description 针对表【employee】的数据库操作Service实现
* @createDate 2023-03-11 13:29:24
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
/*
    @Resource
    private AdminMapper adminMapper;*/

    private Admin getAdminInfo(Admin admin){
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("employee_id",admin.getEmployeeId());
        wrapper.eq("password", admin.getPassword());
        wrapper.eq("role", admin.getRole());
        Admin one;
        try {
            one = getOne(wrapper);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500, "系统错误，结果不唯一");
        }
        return one;
    }

    @Override
    public Admin login(Admin admin) {
        Admin one = getAdminInfo(admin);

        if (one !=null){
            String token = TokenUtils.generateToken(one.getEmployeeId(), one.getPassword(),one.getRole().toString());
            one.setToken(token);
            return one;
        }else {
            throw new ServiceException(Constants.CODE_600,"账号或密码或身份选择错误");
        }
    }
}




