<template>
    <div class="tasklists">
      
      <el-row class="row11">
            <el-col :span="15" :offset="4" class="middle">
           <h2 style="margin-right: 15px">任务列表</h2>
            </el-col>
        </el-row>

    <el-row :gutter="10">
  <el-col :span="8" v-for="(o, index) in tableData" :key="index" :offset=2>
    <el-card class="taskcard" :body-style="{ padding: '0px' }">
      <!--transition控制显示或消失 -->
      <transition name="el-fade-in-linear">
      <!-- <img src="~examples/assets/images/hamburger.png" class="image"> -->
      <!-- 任务列表 -->
      <div style="padding: 14px;">
        <span>任务列表</span>
        <div class="bottom clearfix">

          <span>编号：{{o.id}}&nbsp;&nbsp;任务名：{{o.task_name}}&nbsp;&nbsp;任务状态:{{o.task_status}}</span>
          <div  style="padding: 14px;">
          <el-button type="text" class="button" @click="showMore(o.id)">查看详情</el-button>
          <el-button v-show="isshowfinishtask" type="text" class="button" @click="showfinishtask(o.id)">完成任务</el-button>
          </div>
        </div>
      </div>
      
     
      </transition>

      

    </el-card>
  </el-col>
 <!-- 显示任务详情 -->
  <el-dialog
    title="任务详情"
    :visible.sync="dialogVisible"
  
    :before-close="handleClose">

    <el-table class="table"  :data="moretableData" style="width: 100%" >

    <el-table-column type="selection" width="60">
    </el-table-column>

    <!-- <el-table-column label="任务id" width="60" prop="id">
    </el-table-column> -->

    <el-table-column label="任务名" width="60" prop="task_name">
    </el-table-column>

    <el-table-column label="开发内容" width="60" prop="task_description">
    </el-table-column>
    
    <el-table-column label="开始时间" :formatter="dateFormat" width="60" prop="task_start_time">
    </el-table-column>

    <el-table-column label="结束时间" :formatter="dateFormat" width="60" prop="task_end_time">
    </el-table-column>

    <el-table-column label="预计人天" width="60" prop="task_expected_person_days">
    </el-table-column>

    <el-table-column label="实际人天" width="60" prop="task_actual_person_days">
    </el-table-column>

    <el-table-column label="任务状态" width="60" prop="task_status">
    </el-table-column>

    <el-table-column label="开发人员id" width="60" prop="task_developers_id">
    </el-table-column>

    <el-table-column label="所属项目id" width="60" prop="task_project_id">
    </el-table-column>


    <el-table-column label="下载">
    <template slot-scope="scope">
    <el-button size="mini" type="primary" round @click="download(scope.$index, scope.row)">下载需求文档</el-button>
    </template>
    </el-table-column>

    </el-table>
    </el-dialog>

 <!-- 显示选择任务结束时间的弹框 -->
  <el-dialog class="finish"
    title="请选择结束时间"
    :visible.sync="dialogFinish"
  
    :before-close="handleClose">
    <el-form :model="taskFinishForm" ref="taskFinishForm" label-width="100px">

      <!-- <el-col :span="10" :offset="4"> -->
          <el-form-item label="结束时间" prop="endtime">
              <el-date-picker type="datetime"   placeholder="选择结束时间" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" v-model="taskFinishForm.endtime" style="width: 100%;"></el-date-picker>
          </el-form-item>
      <!-- </el-col> -->

      <!-- <el-col :span="6" :offset="15"> -->
          <el-form-item>
              <el-button type="primary" @click="finishtask()">任务完成</el-button>
          </el-form-item>
     
      <!-- </el-col> -->

    </el-form>
    
    </el-dialog>


</el-row>
    </div>
</template>

<style>

.tasklists{
  height: 800px;
       background-image: url('../assets/backgroud1.png');  
       background-Repeat: "no-repeat";
      background-size: 100% auto;
}
.taskcard{
  height: 100px;
       background-image: url('../assets/backgroud4.jpg');  
       background-Repeat: "no-repeat";
      background-size: 100% auto;
}
  .time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
</style>

<script>
import { allResolved } from 'q';
export default {
    data(){
      
        return{
           role:'',
           dialogVisible:false,
           dialogFinish:false,
           isshowfinishtask:false,
           taskid:"",
           taskFinishForm:{
              endtime:'',
           },
            tableData:[],
            moretableData:[],
            loading: true,
        }
        
    },
    created()
    {
      // var projectid=this.$route.query.projectid
      console.log("1"+this.$route.query.projectid);
      console.log("2"+this.$route.query.yourrole);
      console.log("3"+this.$route.query.status);
      //this.getRole();
      this.getList(this.$route.query.projectid,this.$route.query.yourrole,this.$route.query.status);
    },
    methods:{
      //下载文档
      download(index,row)
      {
        var id=row.id;
         
          var url='/file/downloadtaskfile/'+id
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
    
            
            //console.log(this.tableData.getList.get(0))
            this.$message.success(msg+"到桌面")//提示成功
            
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
      },
      //任务完成相关的弹框的方法
      showfinishtask(id){
        this.taskid=id;
        this.dialogFinish=true;
      },
      //完成任务
      finishtask(){
        var date = new Date(this.taskFinishForm.endtime);  
        var date_End=date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + (date.getDate()+1) + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
        
        var data1=this.qs.stringify({task_end_time: date_End})
        var url='/task/finishtask/'+this.taskid
            this.$http.post(url,data1)
            .then((res)=>{
            console.log(res)
            const {
            code,
            msg,
            data
            } =res.data
            if(code === 200){ 
            console.log(data)
            
            //console.log(this.moreProjectData.project_name)
            //console.log(this.tableData.getList.get(0))
            this.$message.success(msg)//提示成功
            this.$router.push({path:'./About1',query: {projectid: this.$route.query.projectid,yourrole:this.$route.query.yourrole}})
            this.dialogFinish=false;
            // this.$router.push({path:'./About',query: {status:"开发人员正开发"}})
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
      },
        //关闭
        handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
        //显示任务列表
        showMore(id){
          console.log(id)
            //this.dialogVisible=true
           var url='/task/showonetask/'+id
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
            this.moretableData=data
            this.dialogVisible=true
            console.log(this.moretableData.task_start_time)
            //console.log(this.tableData.getList.get(0))
            this.$message.success(msg)//提示成功
            
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
        },
        //获取权限
        getRole(){
            this.$http.post('/user/getrole')
            .then((res)=>{
            console.log(res)
            const {
            code,
            msg,
            data
            } =res.data
            if(code === 200){ 
            //console.log(data)
            this.role=data
            //console.log("1:"+this.role)
            //console.log(this.tableData.getList.get(0))
            //this.$message.success(msg)//提示成功
            
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
        },

        //显示任务列表
        getList(projectid,roles,status){
           // console.log("456")
            //var data1=this.qs.stringify({projectid: projectid})
            this.getRole();
            var url='/task/showoneprojecttask/'+projectid
            console.log("1"+this.role)
            if(roles=="承包方管理员")
            {
              console.log("123")
                url='/task/showoneprojecttask/'+projectid
            }
            if(roles=="开发人员")
            {
              this.isshowfinishtask=true;
              console.log("321")
                url='/task/showdevelopertask/'+projectid
            }
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
            if(status=="开发人员待审核")
            {

              this.isshowfinishtask=false;
            }
            if(status=="开发人员已开发")
            {
              this.isshowfinishtask=false;
            }
            //console.log(this.tableData.getList.get(0))
            this.$message.success(msg)//提示成功
            
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
        },
        
    }
    
}
</script>



