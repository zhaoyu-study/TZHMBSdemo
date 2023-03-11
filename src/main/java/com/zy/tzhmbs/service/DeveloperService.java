package com.zy.tzhmbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.tzhmbs.entity.Developer;

public interface DeveloperService extends IService<Developer> {
    Developer login(Developer developer);

    void register(Developer developer);
}
