package com.zy.tzhmbs.service;

import com.zy.tzhmbs.entity.Emp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 呀哈哈
* @description 针对表【employee】的数据库操作Service
* @createDate 2023-03-11 13:29:24
*/
public interface EmpService extends IService<Emp> {
    Emp login(Emp emp);
}
