package com.zy.tzhmbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.tzhmbs.entity.Owner;

public interface OwnerService extends IService<Owner> {
    Owner login(Owner user);

    void register(Owner user);
}
