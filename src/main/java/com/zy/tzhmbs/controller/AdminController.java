package com.zy.tzhmbs.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.tzhmbs.common.Constants;
import com.zy.tzhmbs.common.Result;
import com.zy.tzhmbs.entity.Admin;
import com.zy.tzhmbs.service.AdminService;
import com.zy.tzhmbs.service.EmpService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Employee)表控制层
 *
 * @author makejava
 * @since 2023-03-11 13:31:03
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    /**
     * 服务对象
     */
    @Resource
    private AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        //只作判空用，将参数传递到真正的login接口
        String employeeId = admin.getEmployeeId();
        String password = admin.getPassword();
        Integer role = admin.getRole();

        if(StrUtil.isBlank(employeeId)||StrUtil.isBlank(password)||role==null){
            return Result.error(Constants.CODE_400, "参数有空");
        }

        return Result.success(adminService.login(admin));
    }

    /**
     * 分页查询所有数据
     *
     * @param pageNo 第几页
     * @param pageSize 每页的大小
     * @return 所有数据
     */
/*    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        //queryWrapper.orderByDesc("");
        return Result.success(this.AdminService.page(new Page<>(pageNo,pageSize),wrapper));
    }*/

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
/*    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.employeeService.getById(id));
    }*/
/*
    /**
     * 新增数据
     *
     * @param employee 实体对象
     * @return 新增结果
     */
/*
    @PostMapping
    public Result insert(@RequestBody Employee employee) {
        return Result.success(this.employeeService.save(employee));
    }
*/

    /**
     * 修改数据
     *
     * @param employee 实体对象
     * @return 修改结果
     */
/*    @PutMapping
    public Result update(@RequestBody Employee employee) {
        return Result.success(this.employeeService.updateById(employee));
    }*/

        /**
     * 添加或修改数据
     * @param role
     * @return
     */
/*    @PostMapping("/saveOrUpdate")
    public Result save(@RequestBody Employee employee){
        this.roleService.saveOrUpdate(employee);
        return Result.success();
    }*/
    
    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
/*    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.success(this.employeeService.removeByIds(idList));
    }*/
}

