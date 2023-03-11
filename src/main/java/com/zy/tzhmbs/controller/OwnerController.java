package com.zy.tzhmbs.controller;

import cn.hutool.core.util.StrUtil;
import com.zy.tzhmbs.common.Constants;
import com.zy.tzhmbs.common.Result;
import com.zy.tzhmbs.entity.Owner;
import com.zy.tzhmbs.service.OwnerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController // 使用 @GetMapping("/")
@RequestMapping("/owner") //统一加前缀
public class OwnerController {

/*    @Autowired
    private UserService userService;*/
    @Resource
    private OwnerService ownerService;

    /**
     * 登录
     * @param owner
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Owner owner){ //requestBody 将前端传来的数据转换成java对象
        String userId = owner.getUserId();
        String password = owner.getPassword();
        Integer role = owner.getRole();
        //后端再次判断参数是否为空
        if(StrUtil.isBlank(userId)||StrUtil.isBlank(password)||role==null){
            return Result.error(Constants.CODE_400,"参数有空");
        }

        //User user1 = userService.login(user);

        return Result.success(ownerService.login(owner));
    }


    /**
     * 注册
     * @param owner
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody Owner owner){
        String userId = owner.getUserId();
        String password = owner.getPassword();
        Integer role = owner.getRole();
        if(StrUtil.isBlank(userId)||StrUtil.isBlank(password)||role==null){
            return Result.error(Constants.CODE_400,"参数有空");
        }
        ownerService.register(owner);
        return Result.success();
    }

    /**
     * 添加和修改数据
     * @param owner
     * @return
     */
/*    @PostMapping()
    public boolean save(@RequestBody User user){
        return userService.saveOrUpdate(user); //有id且id存在，则修改，无id则添加，有id但id不存在，则添加
    }*/

    //
/*    @GetMapping() //拼接上面的url
    public List<User> index(){
        //List<User> list = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        //不用 mapper了。用service的
        return userService.listByIds(Arrays.asList(1, 2, 3));
    }*/

    /**
     * 通过id查询数据
     * PathVariable 映射 URL 绑定的占位符
     * @param ownerId
     * @return
     */
    @GetMapping("/selectById/{ownerId}")
    public Result selectById(@PathVariable Integer ownerId){
        Owner owner = ownerService.getById(ownerId);
        return Result.success(owner);
    }

    /**
     * 导出数据
     * @param response
     * @throws Exception
     */
/*    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception{
        //从数据库查询出所有数据
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select("u_name","u_nickname","u_email","u_phone","u_address");
        List<User> list = userService.list(userQueryWrapper);

        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);//本来写磁盘路径，但是要写道浏览器，就不用了

        //自定义标题别名
*//*        writer.addHeaderAlias("uName","用户名");
        writer.addHeaderAlias("uNickname","昵称");
        writer.addHeaderAlias("uEmail","邮箱");
        writer.addHeaderAlias("uPhone","电话");
        writer.addHeaderAlias("uAddress","地址");*//*
        //writer.addHeaderAlias("avatarUrl","头像");
        //设置只导出有别名的数据
        writer.setOnlyAlias(true);
        //自定义标题别名

        //一次性写出list内的对象到excel,使用默认样式，强制输出标题
        writer.write(list,true);
        AdaptiveWidthUtils.setSizeColumn(writer.getSheet(),10);

        //设置浏览器相应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charsetutf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();

        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();
    }*/

    /**
     * 导入数据
     * @param file
     * @return
     * @throws Exception
     */
/*    @PostMapping("/import")
    public boolean imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        reader.close();
        inputStream.close();
        userService.saveBatch(list);
        return true;
    }*/

    /**
     * 删除数据
     * @param uId
     * @return
     */
/*    @DeleteMapping("/{uId}")
    public boolean delete(@PathVariable Integer uId){

        return userService.removeById(uId);
    }*/

    /**
     * 批量删除
     * @param ids
     * @return
     */
/*    @DeleteMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeByIds(ids);
    }*/

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param uName
     * @return
     */
/*    @GetMapping("/page")
    //@RequestParam 相当于接收 ?pageNo=1&pageSize=5 这样的参数
    public Map<String,Object> findPage(@RequestParam Integer pageNo,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(defaultValue = "") String uName){
        Page<User> page = new Page<>(pageNo,pageSize);
        //设置条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!uName.equals(""))
        wrapper.like("u_name",uName);//这里使用数据库字段名，不要用User属性名
        //在模糊查询出来的表格里分页
        //userMapper.selectPage(page,wrapper);
        userService.page(page,wrapper);
        HashMap<String, Object> res = new HashMap<>();
        res.put("Total",page.getTotal());
        List<User> list=page.getRecords();
        int sid=1;
        for (User user:list){
            list.get(sid-1).setId(sid++);
        }
       // page.setRecords(list);
        res.put("Data",list);
        return res;
    }*/

}
