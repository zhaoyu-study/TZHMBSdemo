package com.zy.tzhmbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.tzhmbs.entity.Owner;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface OwnerMapper extends BaseMapper<Owner> {

}
