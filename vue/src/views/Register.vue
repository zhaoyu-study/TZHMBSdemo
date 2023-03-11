<template>
  <div class="wrapper">

    <div style="margin: 200px auto;width: 450px;height: 450px;padding: 20px"><!--padding 边框和主体空一点距离，border-radius边框圆角-->
      <el-tabs type="border-card" :stretch="stretch" >
        <el-tab-pane >
          <span slot="label"><i class="el-icon-s-custom"></i> 我是业主</span>
          <el-form :model="owner" :rules="rulesOwner" ref="ownerForm">
            <el-form-item prop="ownerId">
              <el-input placeholder="输入身份证号"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="owner.userId"></el-input>
            </el-form-item>
            <el-form-item prop="phone">
              <el-input placeholder="输入电话"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-phone-outline" v-model="owner.phone"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input placeholder="设置密码"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="owner.password"></el-input>
            </el-form-item>
            <el-form-item prop="confirm_password">
              <el-input placeholder="确认密码"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="owner.confirm_password"></el-input>
            </el-form-item>

            <div style="margin: 30px 0; text-align: center">
              <el-button type="primary" size="small" autocomplete="off" @click="registerOwner">确定</el-button>
              <el-button type="warning" size="small" autocomplete="off " @click="$router.push('/login')">返回</el-button>
            </div>
          </el-form>
        </el-tab-pane>
        <el-tab-pane >
          <span slot="label"><i class="el-icon-office-building"></i> 我是开发商</span>
          <el-form :model="developer" :rules="rulesDeveloper" ref="developerForm">
            <el-form-item prop="developerId">
              <el-input placeholder="输入营业编号"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="developer.userId"></el-input>
            </el-form-item>
            <el-form-item prop="phone">
              <el-input placeholder="输入电话"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-phone-outline" v-model="developer.phone"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input placeholder="设置密码"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="developer.password"></el-input>
            </el-form-item>
            <el-form-item prop="confirm_password">
              <el-input placeholder="确认密码"  size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="developer.confirm_password"></el-input>
            </el-form-item>

            <div style="margin: 30px 0; text-align: center">
              <el-button type="primary" size="small" autocomplete="off" @click="registerDeveloper">确定</el-button>
              <el-button type="warning" size="small" autocomplete="off " @click="$router.push('/login')">返回</el-button>
            </div>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      stretch:true,
      owner:{},
      developer:{},
      rulesOwner: {
/*        ownerId: [
          { required: true, message: '请输入身份证号', trigger: 'blur' },/!*blur表示鼠标失焦时*!/
          { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
        ],*/
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },/*blur表示鼠标失焦时*/
          { min: 11, max: 11, message: '长度11个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },/*blur表示鼠标失焦时*/
          { min: 1, max: 30, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ],
        confirm_password: [
          { required: true, message: '请确认密码', trigger: 'blur' },/*blur表示鼠标失焦时*/
          { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
        ]
      },
      rulesDeveloper: {
/*        developerId: [
          { required: true, message: '请输入营业编号', trigger: 'blur' },/!*blur表示鼠标失焦时*!/
          { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
        ],*/
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },/*blur表示鼠标失焦时*/
          { min: 11, max: 11, message: '长度11个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },/*blur表示鼠标失焦时*/
          { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
        ],
        confirm_password: [
          { required: true, message: '请确认密码', trigger: 'blur' },/*blur表示鼠标失焦时*/
          { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
        ]
      },

    }
  },
  methods:{
    //业主注册
    registerOwner(){
      this.$refs["ownerForm"].validate((valid) => {
        if (valid) {
          this.$set(this.owner,'role',0)
          if(this.owner.confirm_password!==this.owner.password){
            this.$message.error("两次输入的密码不一致")
            return false
          }

          this.request.post("/owner/register",this.owner).then(res=>{
            if(res.code === "200" ){/*/!*请求成功，跳到主页面*!/*/
              this.$router.push("/login")
              this.$message.success("注册成功")
            }else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });
    },

    //开发商注册
    registerDeveloper(){
      this.$refs["developerForm"].validate((valid) => {
        if (valid) {
          this.$set(this.developer,'role',1)
          if(this.developer.confirm_password!==this.developer.password){
            this.$message.error("两次输入的密码不一致")
            return false
          }

          this.request.post("/developer/register",this.developer).then(res=>{
            if(res.code === "200" ){/*/!*请求成功，跳到主页面*!/*/
              this.$router.push("/login")
              this.$message.success("注册成功")
            }else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });
    },
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