<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff;width: 450px;height: 350px;padding: 20px;border-radius: 10px"><!--padding 边框和主体空一点距离，border-radius边框圆角-->
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>登录</b></div>
        <el-form :model="U" :rules="rules" ref="UForm" >
          <el-form-item prop="id">
            <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="U.id"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="U.password"></el-input>
          </el-form-item>
          <el-form-item label="" prop="role" class="el-icon-user-solid">
            <i class="el-icon-user-solid" style="padding: 0 10px 0 10px"></i>
            <el-select v-model="U.role"  placeholder="请选择您的身份">
              <el-option label="业主" value="0"></el-option>
              <el-option label="地产开发商" value="1"></el-option>
              <el-option label="工作人员" value="2"></el-option>
              <el-option label="管理员" value="3"></el-option>
            </el-select>
          </el-form-item>
<!--          <el-form-item prop="code">
            <el-input v-model="U.code" auto-complete="off" placeholder="验证码" style="width: 63%;" @keyup.enter.native="login">
              <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
            </el-input>
            <div class="login-code">
              <img src="codeUrl" @click="getCode" class="login-code-img"/>
            </div>
          </el-form-item>-->

          <el-link type="info" style="float: right; margin-top: -20px">忘记密码</el-link>
          <div style="margin: 20px 0;text-align: center">
            <el-button type="primary" size="small" autocomplete="off"  @click="login">登录</el-button>
            <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
          </div>
        </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      U: {},
      rules: {
        id: [
          {required: true, message: '请输入id', trigger: 'blur'},/*blur表示鼠标失焦时*/
          {min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},/*blur表示鼠标失焦时*/
          {min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur'}
        ],
        role: [
          {required: true, message: '请选择身份', trigger: 'blur'},/*blur表示鼠标失焦时*/
        ]
      },
    }
  },
  methods: {
    login() {
      this.$refs["UForm"].validate((valid) => {
        if (valid) {
          if(this.U.role==='0'){
            let user = JSON.parse(JSON.stringify(this.U).replace(/id/g,"userId"))
            this.request.post("/owner/login",user).then(res => {
              localStorage.setItem("U", JSON.stringify(res.data))//存储用户信息到浏览器（永久存储，需要有限制的存储可以考虑session，目前不会）
              if (res.code === "200") {/*/!*请求成功，跳到主页面*!/*/
                this.$router.push("/")
                this.$message.success("登录成功")
              } else {
                this.$message.error(res.msg)
              }
            })
          } else if (this.U.role==='1'){
            let developer = JSON.parse(JSON.stringify(this.U).replace(/id/g,"userId"))
            this.request.post("/developer/login", developer).then(res => {
              localStorage.setItem("U", JSON.stringify(res.data))//存储用户信息到浏览器（永久存储，需要有限制的存储可以考虑session，目前不会）
              if (res.code === "200") {/*/!*请求成功，跳到主页面*!/*/
                this.$router.push("/")
                this.$message.success("登录成功")
              } else {
                this.$message.error(res.msg)
              }
            })
          }else if (this.U.role==='2'){
            let emp = JSON.parse(JSON.stringify(this.U).replace(/id/g,"employeeId"))
            this.request.post("/emp/login", emp).then(res => {
              localStorage.setItem("U", JSON.stringify(res.data))//存储用户信息到浏览器（永久存储，需要有限制的存储可以考虑session，目前不会）
              if (res.code === "200") {/*/!*请求成功，跳到主页面*!/*/
                this.$router.push("/")
                this.$message.success("登录成功")
              } else {
                this.$message.error(res.msg)
              }
            })
          }else if (this.U.role==='3'){
            let admin = JSON.parse(JSON.stringify(this.U).replace(/id/g,"employeeId"))
            this.request.post("/admin/login", admin).then(res => {
              localStorage.setItem("U", JSON.stringify(res.data))//存储用户信息到浏览器（永久存储，需要有限制的存储可以考虑session，目前不会）
              if (res.code === "200") {/*/!*请求成功，跳到主页面*!/*/
                this.$router.push("/")
                this.$message.success("登录成功")
              } else {
                this.$message.error(res.msg)
              }
            })
          }else {
            return false;
          }
        }
      });
    },
/*    getCode(){
      getCodeImg().then((res) =>{
        if(res.state == 200){
          this.codeUrl = res.data.img;
        }
      })
    },*/
  }
}
</script>

<style>
  .wrapper{
    height: 100vh; /*视图窗口高度，即100%窗口高度*/
    background-image: linear-gradient(to bottom right,#FC4668,#3F5EFB);/*渐变色*/
    overflow: hidden; /*隐藏超出部分*/
  }
</style>