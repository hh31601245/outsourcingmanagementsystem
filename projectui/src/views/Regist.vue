<template>

    <div class="regist">
        <el-row class="row11">

        </el-row>

        <el-row class="row22" >
            

            <el-form  :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">

            <el-col :span="6" :offset="8">
            <el-form-item label="用户名" prop="name">
                <el-input v-model="ruleForm2.name" auto-complete="off"></el-input>
            </el-form-item>
            </el-col>

            <el-col :span="6" :offset="8">
            <el-form-item label="密码" prop="pass">
                <el-input type="password" v-model="ruleForm2.pass" auto-complete="off"></el-input>
            </el-form-item>
             </el-col>

             <el-col :span="6" :offset="8">
            <el-form-item label="确认密码" prop="checkPass">
                <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off"></el-input>
            </el-form-item>
            </el-col>

            <el-col :span="6" :offset="8">
              <el-form-item label="权限">
                <el-radio-group v-model="ruleForm2.role">
                  <el-radio label="发包方"></el-radio>
                  <el-radio label="承包方管理员"></el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            
            <el-col :span="6" :offset="8">
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm2')">提交</el-button>
                
                <el-button type="primary" @click="resetForm('ruleForm2')">重置</el-button>
                
                 <el-button type="primary" @click="backForm('ruleForm2')">返回</el-button>
            </el-form-item>
            </el-col>

            </el-form>
        </el-row>
        
    </div>
</template>

<style>
  .regist{
      height: 800px;
       background-image: url('../assets/registbackgroud.png');  
       background-Repeat: "no-repeat";
      background-size: 100% auto;
  }
  .row11{
      height: 30%;
  }
</style>

<script>
  export default {
    data() {
      var checkName = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('用户名不能为空'));
        }
        else
        {
          return callback();
        }
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
         return callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm2.checkPass !== '') {
            this.$refs.ruleForm2.validateField('checkPass');
          }
         return callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
         return callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm2.pass) {
         return callback(new Error('两次输入密码不一致!'));
        } else {
         return callback();
        }
      };
      return {
     
        ruleForm2: {
          name: '',
          pass: '',
          checkPass: '',
          role: '发包方'        
        },
        rules2: {
          name: [
            { validator: checkName, trigger: 'blur' }
          ],  
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ]
          
        }
      };
    },
    methods: {
      //用户注册
      submitForm(formName) {
        console.log(this.$refs[formName])
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.submit();
           
          } else {
            console.log('error submit!!');
            this.$router.push({path:'./Regist'})
            
          }
        });
      },

      submit(){
          var data=this.qs.stringify({user_name:this.ruleForm2.name,user_password:this.ruleForm2.pass,user_role:this.ruleForm2.role})
          this.$http.post('/user/add',data)
          .then((res)=>{
          console.log(res)
          const {
          code,
          msg,
          data
          } =res.data
          if(code === 200){ 
         
          this.$message.success(msg)//提示成功
          this.$router.push({path:'./'})
          }else{ 
            
          this.$message.error(msg)//否则提示失败
          }
          }).catch(e=>{
            this.$message.warning("123")
          })
      },

      //清空列表
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },

      //返回
      backForm(formName){
          this.$router.push({path:'./'})
      }
    }
  }
</script>