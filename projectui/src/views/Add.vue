<template>
  <div class="add">
      <el-row class="addprojectrow1">

      </el-row>

    <!-- 发包方新建项目 -->
    <el-row class="addprojectrow2" :gutter="10" v-show="isprojectadd">
      <el-col :span="18" :offset="4" class="middle">
          <h2 style="margin-right: 15px">新建项目</h2>
        </el-col>

      <el-form  :model="projectForm" ref="projectForm" label-width="100px">

      <el-col :span="6" :offset="9">
          <el-form-item label="项目名称" prop="name">
              <el-input v-model="projectForm.name"></el-input>
          </el-form-item>
      </el-col>

      <el-col :span="6" :offset="9">
          <el-form-item label="项目说明" prop="description">
              <el-input v-model="projectForm.description"></el-input>
          </el-form-item>
      </el-col>

      <el-col :span="6" :offset="9">
          <el-form-item label="预计结束时间" prop="expectedendtime">
              <el-date-picker type="datetime"   placeholder="选择预计结束时间" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" v-model="projectForm.expectedendtime" style="width: 100%;"></el-date-picker>
          </el-form-item>
      </el-col>

      <el-col :span="6" :offset="9">
          <el-form-item label="需求文档" prop="requirementdocument">
               <el-upload
                  :on-success="handleFileUploaded"
                  class="upload-demo"
                  action='http://localhost:82/file/upload'
                  :on-preview="handlePreview"
                  :on-remove="handleRemove"
                  :before-remove="beforeRemove"
                  multiple
                  :limit="3"
                  :on-exceed="handleExceed"
                  :file-list="fileList">
                  <el-button size="small" type="primary">上传项目需求文档</el-button>
                  <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
              </el-upload>
          </el-form-item>
      </el-col>


      <el-col :span="6" :offset="9">
          <el-form-item>
              <el-button type="primary" @click="addproject()">确定</el-button>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-button type="primary" @click="cancel()">取消</el-button>
          </el-form-item>
     
      </el-col>

    </el-form>

    </el-row>

    <!-- 管理人员新建开发人员 -->
    <el-row class="adddeveloperrow1" gutter="10" v-show="isdeveloperadd">
            

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
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm2')">提交</el-button>
                
                <el-button type="primary" @click="resetForm('ruleForm2')">重置</el-button>
                
                 <el-button type="primary" @click="backForm('ruleForm2')">返回</el-button>
            </el-form-item>
            </el-col>

            </el-form>
        </el-row>


    <el-row class="addprojectrow3">
    </el-row>

  </div>

</template>

<style>
  .add{
    height: 800px;
       background-image: url('../assets/backgroud1.png');  
       background-Repeat: "no-repeat";
      background-size: 100% auto;
  }

</style>

<script>
export default {
    data(){
      //检查添加的开发人员用户名
      var checkName = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('用户名不能为空'));
        }
        else
        {
          return callback();
        }
      };
      //检查添加的开发人员密码
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
      //检查添加人员的第二次密码
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
         return callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm2.pass) {
         return callback(new Error('两次输入密码不一致!'));
        } else {
         return callback();
        }
      };
        return{
          fileList:[],
            isprojectadd:true,
            isdeveloperadd:true,
            role:'',
            txturl:'',
            userid:'',
            // 新建项目相关 
            projectForm:{
                name:'',
                description:'',
                expectedendtime:'',
                requirementdocument:''
            },
            //新建开发人员相关
            ruleForm2: {
            name: '',
            pass: '',
            checkPass: '',        
            },
            //开发人员验证相关
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

        }
        
    },
    created(){
        //var projectstatus=this.$route.query.status;
        //console.log(this.$route.query.status)
        //console.log(this.projectstatus)
        if(this.$route.query.status=="新建项目")
        {
            this.isdeveloperadd=false;
        }
        if(this.$route.query.status=="创建开发人员")
        {
            this.isprojectadd=false;
        }

    },
    methods:{
        
        //发包方新建项目
        addproject(){
            var date = new Date(this.projectForm.expectedendtime);  
           var date_Expect=date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + (date.getDate()+1) + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
            var data=this.qs.stringify({project_name:this.projectForm.name,
            project_description:this.projectForm.description,
             project_expected_endtime:this.projectForm.expectedendtime,
            project_requirement_document:this.projectForm.requirementdocument,
            })
            this.$http.post('/project/add',data)
            .then((res)=>{
            console.log(res)
            const {
            code,
            msg,
            data
            } =res.data
            if(code === 200){ 
            console.log(data)
            this.role=data
            //console.log(this.tableData.getList.get(0))
            this.$message.success(msg)//提示成功
            
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("填写的信息不全")
            })
        },

        //承包方添加开发人员相关
      submitForm(formName) {
        console.log(this.$refs[formName])
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.submit();
           
          } else {
            console.log('error submit!!');
            this.$router.push({path:'./List',query: {status:"发包方已发布"}})
            
          }
        });
      },

      //承包方添加开发人员相关
      submit(){
          var data=this.qs.stringify({user_name:this.ruleForm2.name,user_password:this.ruleForm2.pass})
          this.$http.post('/user/adddeveloper',data)
          .then((res)=>{
          console.log(res)
          const {
          code,
          msg,
          data
          } =res.data
          if(code === 200){ 
         
          this.$message.success(msg)//提示成功
          this.$router.push({path:'./List',query: {status:"发包方已发布"}})
          }else{ 
            
          this.$message.error(msg)//否则提示失败
          }
          }).catch(e=>{
            this.$message.warning("123")
          })
      },

      ////承包方添加开发人员相关，清空列表
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },

      ////承包方添加开发人员相关，返回
      backForm(formName){
          this.$router.push({path:'./List',query: {status:"发包方已发布"}})
      },
      //文件上传相关
      handleFileUploaded(response,file, fileList){
        this.projectForm.requirementdocument = response
        console.log(response);
      },
      handleRemove(response,file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file.name);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },

    }
}
</script>

