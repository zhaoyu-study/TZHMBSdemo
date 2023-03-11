package com.zy.tzhmbs.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 
 * @TableName employee
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("employee")
public class Admin implements Serializable {
    /**
     * 员工编号或管理员账号
     */
    @TableId(type = IdType.INPUT)
    private String employeeId;

    /**
     * 员工姓名或管理员
     */
    private String name;

    /**
     * 
     */
    private String password;

    /**
     * 2代表员工，3代表管理员
     */
    private Integer role;

    @TableField(exist = false)
    private String token;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}