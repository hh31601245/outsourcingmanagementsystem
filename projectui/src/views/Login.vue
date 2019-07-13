<template>
  <div class="login">
      <el-row class="row1">

      </el-row>

    <el-row class="row2" :gutter="10">
      
      <el-form  :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">

      <el-col :span="6" :offset="15">
          <el-form-item label="用户名" prop="name">
              <el-input v-model="ruleForm2.name" auto-complete="off"></el-input>
          </el-form-item>
      </el-col>

      <el-col :span="6" :offset="15">
          <el-form-item label="密码" prop="pass">
              <el-input type="password" v-model="ruleForm2.pass" auto-complete="off"></el-input>
          </el-form-item>
      </el-col>

      <el-col :span="6" :offset="15">
          <el-form-item>
              <el-button type="primary" @click="loginForm('ruleForm2')">登录</el-button>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-button type="primary" @click.prevent="regist()">注册</el-button>
          </el-form-item>
     
      </el-col>

    </el-form>

    </el-row>
    <el-row class="row5">
    </el-row>

  </div>

</template>

<style>
  .login{
      height: 800px;
       background-image: url('../assets/loginbackgroud.png');  
       background-Repeat: "no-repeat";
      background-size: 100% auto;
  }
   .row1{
      height: 46%;
  }

</style>

<script>

export default {
 
   data(){
     //用在登录里
     var checkName = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('用户名不能为空'));
        }
        else
        {
          return callback();
        }
      };
      //用在登录里
      var validatePass = (rule, value, callback) => {
        if (!value) {
         return callback(new Error('请输入密码'));
        } else {

         return callback();
        }
      };


      return{

        dialogVisible:false,
        //登录
        ruleForm2:{
          name:'',
          pass:''
        },

        //登录
        rules2: {
          name: [
            { validator: checkName, trigger: 'blur' }
          ],  
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],    
        },
 
      }
    },

    methods:{
      //登录相关
    loginForm(formName) {
        console.log(this.$refs[formName])
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.login();
            
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
    //登录相关
    login()
    {
      console.log(this.ruleForm2.username)
      var data=this.qs.stringify({user_name:this.ruleForm2.name,user_password:this.ruleForm2.pass})
      this.$http.post('/user/login',data)
      .then((res)=>{
      console.log(res)
      const {
      code,
      msg,
      data
      } =res.data
      if(code === 200){ 
      //cosole.log(data)
      localStorage.setItem('token', data);
      this.$router.push({path:'./Index'})
      this.$message.success(msg)//提示成功
     
      }else{ 
      this.$message.error(msg)//否则提示失败
      }
      }).catch(e=>{
        this.$message.warning("123")
      })
    },
    //登录界面相关
    regist()
    {
      this.$router.push({path:'./Regist'})
 
    },

  }
}
</script>


