package com.zy.tzhmbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.tzhmbs.entity.Admin;

/**
* @author 呀哈哈
* @description 针对表【employee】的数据库操作Service
* @createDate 2023-03-11 13:29:24
*/
public interface AdminService extends IService<Admin> {
    Admin login(Admin admin);
}
