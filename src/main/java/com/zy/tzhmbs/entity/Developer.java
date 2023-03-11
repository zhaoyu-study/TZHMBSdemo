package com.zy.tzhmbs.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("user")
public class Developer {
    @TableId(type = IdType.INPUT)
    private String userId;

    private String name;

    //@JsonIgnore //如果不想展示密码，如果需要增加或修改，需要表单提交，json提交会忽略
    private String password;

    private String phone;

    private Integer role;

    @TableField(exist = false)
    private String token;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Boolean deleted;
}
