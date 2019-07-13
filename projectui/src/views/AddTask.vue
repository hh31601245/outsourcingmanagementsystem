<template>
  <div class="add">
      <el-row class="addtaskrow1">
          <el-col :span="18" :offset="4" class="middle">
           <h2 style="margin-right: 15px">分配任务</h2>
            </el-col>
      </el-row>

    <!-- 发包方新建任务 -->
    <el-row class="addtaskrow2" :gutter="10" v-show="isprojectadd">
      
      <el-form  :model="taskForm" ref="taskForm" label-width="100px">

      <el-col :span="6" :offset="9">
          <el-form-item label="任务名称" prop="name">
              <el-input v-model="taskForm.name"></el-input>
          </el-form-item>
      </el-col>

      <el-col :span="6" :offset="9">
          <el-form-item label="开发内容" prop="description">
              <el-input v-model="taskForm.description"></el-input>
          </el-form-item>
      </el-col>

      <el-col :span="6" :offset="9">
          <el-form-item label="开始时间" prop="starttime">
              <el-date-picker type="datetime"   placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" v-model="taskForm.starttime" style="width: 100%;"></el-date-picker>
          </el-form-item>
      </el-col>

      <el-col :span="6" :offset="9">
          <el-form-item label="预计人天" prop="expectpersonday">
              <el-input v-model="taskForm.expectpersonday"></el-input>
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
          <el-form-item label="选择开发人员" prop="selectdeveloper">
              <el-button type="primary" @click="selectDeveloper()">人员分配</el-button>
          
          </el-form-item>
      </el-col>
      
       <el-col :span="6" :offset="9">
          <el-form-item label="开发人员id" prop="developerid">
              <span>{{this.developerid}}</span>
          </el-form-item>
      </el-col>
 
      <!-- 弹框选择开发人员 -->
    <el-dialog
    title="提示"
    :visible.sync="dialogVisible"
  
    :before-close="handleClose">
    <el-table class="table"  :data="tableData" style="width: 100%" >

    <el-table-column type="selection" width="100">
    </el-table-column>

    <el-table-column label="开发人员编号" width="160" prop="id">
    </el-table-column>

    <el-table-column label="开发人员姓名" width="200" prop="user_name">
    </el-table-column>

    <el-table-column label="操作">
    <template slot-scope="scope">
    <el-button size="mini" type="primary" round @click="Select(scope.$index, scope.row)">选择</el-button>
    </template>
    </el-table-column>

    </el-table>
    </el-dialog>

      <el-col :span="6" :offset="9">
          <el-form-item>
              <el-button type="primary" @click="addtask()">继续分配</el-button>
              <el-button type="primary" @click="finishadd()">完成</el-button>
          </el-form-item>
     
      </el-col>

    </el-form>

    

    </el-row>


    <el-row class="addtaskrow3">
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
      
        return{
            dialogVisible : false,
            isprojectadd:true,
            isdeveloperadd:true,
            role:'',
            //文件存储地址
            txturl:'',
            fileList:'',
            userid:'',
            //项目id
            projectid:'',
            //开发人员id
            developerid:'',
            // 新建项目相关 
            taskForm:{
                name:'',
                description:'',
                starttime:'',
                expectpersonday:'',
                requirementdocument:''
            },
            tableData:[],

        }
        
    },
    created(){
        //var projectstatus=this.$route.query.status;
        //console.log(this.$route.query.status)
        //console.log(this.projectstatus)
        //console.log(this.$route.query.projectid)
        this.projectid=this.$route.query.projectid

    },
    methods:{
        //关闭
        handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
        //显示可选择的开发人员列表
        selectDeveloper(){
            //this.dialogVisible=true
           var url='/user/developerlist'
            this.$http.post(url)
            .then((res)=>{
            console.log(res)
            const {
            code,
            msg,
            data
            } =res.data
            if(code === 200){ 
            console.log(data)
            this.tableData=data
            this.dialogVisible=true
            //console.log(this.tableData.getList.get(0))
            this.$message.success(msg)//提示成功
            
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
        },
        //将选择到的开发人员的id记录
        Select(index,row){
            this.developerid=row.id
            this.dialogVisible=false
        },
        //承包方新建任务
        addtask(){
            console.log(this.taskForm.expectpersonday);
            var date = new Date(this.taskForm.starttime);  
           var date_Start=date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + (date.getDate()+1) + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
            var data=this.qs.stringify({task_name:this.taskForm.name,
            task_description:this.taskForm.description,
            task_start_time:this.taskForm.starttime,
            task_expected_person_days:this.taskForm.expectpersonday,
            task_design_document:this.taskForm.requirementdocument,
            task_developers_id:this.developerid,
            task_project_id:this.projectid
            })
            this.$http.post('/task/add',data)
            .then((res)=>{
            console.log(res)
            const {
            code,
            msg,
            data
            } =res.data
            if(code === 200){ 
            //console.log(data)
            //this.role=data
            //console.log(this.tableData.getList.get(0))

            this.$message.success(msg)//提示成功
            this.developerid="";
            this.$refs["taskForm"].resetFields();
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("信息填写不全")
            })
        },

        //承包方结束分配任务
        finishadd(){
            var data=this.qs.stringify({task_name:this.taskForm.name,
            task_description:this.taskForm.description,
            task_start_time:this.taskForm.starttime,
            task_expected_person_days:this.taskForm.expectpersonday,
            task_design_document:this.txturl,
            task_developers_id:this.developerid,
            task_project_id:this.projectid
            })
            this.$http.post('/task/add',data)
            .then((res)=>{
            console.log(res)
            const {
            code,
            msg,
            data
            } =res.data
            if(code === 200){ 
            //console.log(data)
            //this.role=data
            //console.log(this.tableData.getList.get(0))

            this.$message.success(msg)//提示成功
            this.developerid="";
            this.$router.push({path:'./List',query: {status:"承包方已分配"}})
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("信息填写不全")
            })
            
        },

        //文件上传相关
      handleFileUploaded(response,file, fileList){
        this.taskForm.requirementdocument = response
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

