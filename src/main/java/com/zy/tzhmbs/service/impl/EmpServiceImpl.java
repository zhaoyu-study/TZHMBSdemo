package com.zy.tzhmbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.tzhmbs.common.Constants;
import com.zy.tzhmbs.entity.Emp;
import com.zy.tzhmbs.exception.ServiceException;
import com.zy.tzhmbs.service.EmpService;
import com.zy.tzhmbs.mapper.EmpMapper;
import com.zy.tzhmbs.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 呀哈哈
* @description 针对表【employee】的数据库操作Service实现
* @createDate 2023-03-11 13:29:24
*/
@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements EmpService {

    //@Resource
    //private EmpMapper empMapper;
    /**
     * 将此类的重复代码封装
     * @param emp
     * @return
     */
    private Emp getEmpInfo(Emp emp){
        QueryWrapper<Emp> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("employee_id",emp.getEmployeeId());
        queryWrapper.eq("password", emp.getPassword());
        queryWrapper.eq("role", emp.getRole());

        Emp one;
        try {
            one = getOne(queryWrapper);//如果结果不唯一，则抛出异常
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500, "系统错误，结果不唯一");
        }
        return one;
    }
    @Override
    public Emp login(Emp emp) {
        Emp one = getEmpInfo(emp);

        if(one != null){//能查出结果
            //BeanUtils.copyProperties(one,user);//用one里的赋值userDTO相同属性的值
            //设置token
            String token = TokenUtils.generateToken(one.getEmployeeId(), one.getPassword(),one.getRole().toString());
            one.setToken(token);
            return one;
        }else {
            throw new ServiceException(Constants.CODE_600,"账号或密码或身份选择错误");
        }
    }
}




