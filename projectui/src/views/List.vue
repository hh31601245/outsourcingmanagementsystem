<template>
<div class="Lists">

      <el-row class="row1122">
            <el-col :span="18" :offset="4" class="middle">
           <h2 style="margin-right: 15px">{{this.title}}</h2>
            </el-col>
        </el-row>

    <!-- 项目列表 -->
    <el-row :gutter="10">
  <el-col :span="8" v-for="(o, index) in tableData" :key="index" :offset=2>

    <el-card class="projectcard" :body-style="{ padding: '0px' }">
      <!--transition控制显示或消失 -->
      <transition name="el-fade-in-linear">
      <!-- <img src="~examples/assets/images/hamburger.png" class="image"> -->
      
      <div style="padding: 14px;">
        <span>项目</span>
        <div class="bottom clearfix">

          <span>编号：{{o.id}}&nbsp;&nbsp;项目名：{{o.project_name}}&nbsp;&nbsp;项目状态：{{o.project_status}}</span>
          <div  style="padding: 14px;">
          <el-button  type="text" class="button" v-show="isShowTask" @click="showtasks(o.id)">查看任务  </el-button>          
          <el-button type="text" class="button" v-show="isShowMore" @click="showMore(o.id)">项目详情</el-button>
          <el-button type="text" class="button" v-show="isShowSum" @click="showSum(o.id)">汇总详情</el-button>           
          </div>
          <div style="padding: 14px;">
          <el-button  type="text" class="button"  v-show="isAddTask" @click="AddTask(o.id)">分配任务</el-button>
          <el-button  type="text" class="button" v-show="isContractTask"  @click="contractTask(o.id)">承包项目</el-button>
          <el-button type="text" class="button" v-show="isApprovalProject" @click="approvalProject(o.id)">验收项目</el-button>
          </div>
        </div>
      </div>
          
      </transition>

    </el-card>
  </el-col>

 <!-- 显示汇总详情 -->
 <el-dialog
    title="项目详情"
    :visible.sync="SumVisible"
  
    :before-close="handleClose">
    <el-card :body-style="{ padding: '0px' }">
      <!--transition控制显示或消失 -->
      <transition name="el-fade-in-linear">
      <!-- <img src="~examples/assets/images/hamburger.png" class="image"> -->
      
      <div style="padding: 14px;">
        <span>汇总详情</span>
        <div class="bottom clearfix">
          <span>开发人数：{{this.SumData.PersonNumber}}</span>
          <div class="bottom clearfix">
          <span>未完成任务个数：{{this.SumData.NoFinishTaskNumber}}&nbsp;&nbsp;已完成任务个数：{{this.SumData.FinishTaskNumber}}</span>
           </div>
          <div class="bottom clearfix">
          <span>预计人天：{{this.SumData.ExceptPersonDays}}&nbsp;&nbsp;实际人天：{{this.SumData.ActualPersonDays}}</span>
           </div>
          <div class="bottom clearfix">
            <span>开始时间：{{this.SumData.AllStartDate}}&nbsp;&nbsp;结束时间：{{this.SumData.AllEndDate}}</span>
          </div>

        </div>
      </div>
          
      </transition>

    </el-card>
   
    </el-dialog>


  <!-- 显示项目详情 -->
  <el-dialog
    title="项目详情"
    :visible.sync="dialogVisible"
  
    :before-close="handleClose">

    <el-table class="table"  :data="moreProjectData" style="width: 100%" >

    <el-table-column type="selection" width="60">
    </el-table-column>

    <el-table-column label="项目id" width="60" prop="id">
    </el-table-column>

    <el-table-column label="项目名" width="60" prop="project_name">
    </el-table-column>
    
    <el-table-column label="项目说明" width="60" prop="project_description">
    </el-table-column>
 
    <el-table-column label="期望完成时间" :formatter="dateFormat" width="60" prop="project_expected_endtime">
    </el-table-column>

    <el-table-column label="项目状态" width="60" prop="project_status">
    </el-table-column>

    <el-table-column label="发包方id" width="60" prop="project_employer_id">
    </el-table-column>

    <el-table-column label="承包方id" width="60" prop="project_contractor_id">
    </el-table-column>


    <el-table-column label="下载">
    <template slot-scope="scope">
    <el-button size="mini" type="primary" round @click="download(scope.$index, scope.row)">下载需求文档</el-button>
    </template>
    </el-table-column>

    </el-table>
    </el-dialog>

</el-row>

</div>

</template>

<style>
  .Lists{
     height: 800px;
       background-image: url('../assets/backgroud1.png');  
       background-Repeat: "no-repeat";
      background-size: 100% auto;
  }
  .projectcard{
    height: 150px;
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
          title:"",
            sum:"123",
            requirement:"",
            currentDate: new Date(),
            isShowTask:false,
            isAddTask:false,
            isShowSum:false,
            isContractTask:false,
            isShowMore:false,
            isApprovalProject:false,
            dialogVisible:false,
            SumVisible:false,
            SumData:{
              PersonNumber:"",
              AllTaskNumber:"",
              FinishTaskNumber:"",
              NoFinishTaskNumber:"",
              ActualPersonDays:"",
              ExceptPersonDays:"",
              AllStartDate:"",
              AllEndDate:""
            },
            tableData:[],
            moreProjectData:[],
            loading: true,
        }
        
    },
    created()
    {
      //var projectstatus=this.$route.query.status
      //console.log(this.$route.query.status)
      //if(this.$route.query.status=="发包方已发布") 
      this.getList(this.$route.query.status)
    },
    methods:{
      //下载文档
      download(index,row)
      {
        var id=row.id;
         
          var url='/file/downloadprojectfile/'+id
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
      //获取汇总信息
      showSum(id){
            this.sumActualDayAndPerson(id);
        this.sumPerson(id);
        this.sumExpectDayAndPerson(id);
        this.getStartTime(id);
        this.getEndTime(id);
        this.getFinishTaskNumber(id);
        this.getNoFinishTaskNumber(id);
        this.SumVisible=true;
      },
      //获取总人数信息
      sumPerson(id){
          var url='/task/sumperson/'+id
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
            this.SumData.PersonNumber=data
            //console.log(this.moreProjectData.project_name)
            //console.log(this.tableData.getList.get(0))
            //this.$message.success(msg)//提示成功
            
            }else{ 
            //this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
      },
      //获取预计人天
      sumExpectDayAndPerson(id){
        var url='/task/sumexpectdayandperson/'+id
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
            this.SumData.ExceptPersonDays=data
            //console.log(this.moreProjectData.project_name)
            //console.log(this.tableData.getList.get(0))
            //this.$message.success(msg)//提示成功
            
            }else{ 
            //this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
      },
      //获取实际人天
      sumActualDayAndPerson(id){
        var url='/task/sumactualdayandperson/'+id
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
            this.SumData.ActualPersonDays=data
            //console.log(this.moreProjectData.project_name)
            //console.log(this.tableData.getList.get(0))
            //this.$message.success(msg)//提示成功
            
            }else{ 
            //this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
      },
      //获取项目开始时间
      getStartTime(id){
            var url='/task/getstartdate/'+id
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
             var date = new Date(data.task_start_time);  
           var date_Start=date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + (date.getDate()+1) + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
            this.SumData.AllStartDate=date_Start
            //console.log(this.moreProjectData.project_name)
            //console.log(this.tableData.getList.get(0))
           // this.$message.success(msg)//提示成功
            
            }else{ 
           // this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
      },
      //获取项目结束时间
      getEndTime(id){
            var url='/task/getenddate/'+id
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
             var date = new Date(data.task_start_time);  
           var date_Start=date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + (date.getDate()+1) + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
            
            this.SumData.AllEndDate=date_Start
            //console.log(this.moreProjectData.project_name)
            //console.log(this.tableData.getList.get(0))
           // this.$message.success(msg)//提示成功
            
            }else{ 
            //this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
      },
      //已完成任务个数
      getFinishTaskNumber(id){
          var url='/task/sumfinishedtask/'+id
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
            this.SumData.FinishTaskNumber=data
            //console.log(this.moreProjectData.project_name)
            //console.log(this.tableData.getList.get(0))
            //this.$message.success(msg)//提示成功
            
            }else{ 
            //this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
      },
      //未完成任务个数
      getNoFinishTaskNumber(id){
           var url='/task/sumnofinishedtask/'+id
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
            this.SumData.NoFinishTaskNumber=data
            //console.log(this.moreProjectData.project_name)
            //console.log(this.tableData.getList.get(0))
            //this.$message.success(msg)//提示成功
            
            }else{ 
           // this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
      },
        //审批项目
        approvalProject(id){
          var url='/project/approval/'+id
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
            
            //console.log(this.moreProjectData.project_name)
            //console.log(this.tableData.getList.get(0))
            this.$message.success(msg)//提示成功
            this.$router.push({path:'./About',query: {status:"发包方已验收"}})
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
           var url='/project/showoneproject/'+id
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
            this.moreProjectData=data
            this.dialogVisible=true
            //console.log(this.moreProjectData.project_name)
            //console.log(this.tableData.getList.get(0))
            this.$message.success(msg)//提示成功
            
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
        },
      //承包项目
      contractTask(id){
        var url='/project/contract/'+id
        this.$http.post(url)
            .then((res)=>{
            console.log(res)
            const {
            code,
            msg,
            data
            } =res.data
            if(code === 200){ 
            // console.log(data)
            // this.tableData=data
            //console.log(this.tableData.getList.get(0))
            this.$message.success(msg)//提示成功
            this.$router.push({path:'./About',query: {status:"承包方已承包"}})
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
      },
      //分配任务
      AddTask(id){
        this.$router.push({path:'./AddTask',query:{projectid: id}})
      },
      //显示该项目下的所有任务
      showtasks(id){
        // this.show=!this.show
        // this.show2=!this.show2
        console.log(id);
        var role="承包方管理员"
        if(this.$route.query.status=="开发人员正开发"||this.$route.query.status=="开发人员待审核"||this.$route.query.status=="开发人员已开发")
        {
          role="开发人员"
        }
        var statuss=this.$route.query.status
        this.$router.push({path:'./Tasklist',query: {projectid: id,yourrole:role,status:statuss}})
      },
      //显示项目列表
      getList(projectstatus){
        console.log(this.thisisstatus)
        var url='/project/shownocontractemployerproject'
        //显示发包方未被承包的项目列表
        if(projectstatus=="发包方已发布")
        {
          this.title="发包方未被承包的项目列表";
          this.isShowMore=true;
          this.isShowSum=true;
          url='/project/shownocontractemployerproject'
        }
        //显示发包方的以承包的项目列表
        if(projectstatus=="发包方已承包")
        {
          this.title="发包方已被承包的项目列表";
          this.isShowMore=true;
          this.isShowSum=true;
          url='/project/showcontractemployerproject'
        }
        //显示发包方的待验收的项目列表
        if(projectstatus=="发包方待验收")
        {
          this.title="发包方待验收项目列表";
          this.isShowMore=true;
          this.isShowSum=true;
          this.isApprovalProject=true;
          url='/project/shownoapprovalemployerproject'
        }
        //显示发包方的已验收的项目列表
        if(projectstatus=="发包方已验收")
        {
          this.title="发包方已验收项目列表";
          this.isShowSum=true;
          this.isShowMore=true;
          url='/project/showapprovalemployerproject'
        }
        //显示承包方可承包的项目列表
         if(projectstatus=="承包方可承包")
         {
           this.title="承包方可承包的项目列表";
           this.isShowSum=true;
           this.isContractTask=true;
           this.isShowMore=true;
           url="/project/showcancontractorproject"
         }
         //显示承包方未分配的项目列表
         if(projectstatus=="承包方已承包")
         {
           this.title="承包方未分配项目列表";
           this.isShowSum=true;
           this.isAddTask=true;
           this.isShowMore=true;
           url="/project/showstartedcontractorproject"
         }
         //显示承包方已分配正在进行中的项目列表
         if(projectstatus=="承包方已分配")
         {
           this.title="承包方进行中项目列表";
           this.isShowSum=true;
           this.isShowMore=true;
           this.isShowTask=true;
           url="/project/showendcontractorproject"
         }
         //显示承包方待发包方验收的项目列表
         if(projectstatus=="承包方待验收")
         {
           this.title="承包方待验收项目列表";
           this.isShowSum=true;
           this.isShowMore=true;
           this.isShowTask=true;
           url="/project/showsnoapprovalcontractorproject"
         }
        //显示承包方已验收的项目
         if(projectstatus=="承包方已审批")
         {
           this.title="承包方已验收项目列表";
           this.isShowSum=true;
            this.isShowMore=true;
            this.isShowTask=true;
           url="/project/showsapprovalcontractorproject"
         }
        //显示开发人员列表
        if(projectstatus=="开发人员列表")
        {
          url="/user/developerlist"
        }
        if(projectstatus=="开发人员正开发")
        {
          this.title="开发人员正开发的项目列表";
          this.isShowSum=true;
          this.isShowTask=true;
          this.isShowMore=true;
          url="/project/shownofinishproject"
        }
        if(projectstatus=="开发人员待审核")
        {
          this.title="开发人员待审核的项目列表";
          this.isShowSum=true;
          this.isShowTask=true;
          this.isShowMore=true;
          url="/project/showwaitapprovalproject"
        }
        if(projectstatus=="开发人员已开发")
        {
          this.title="开发人员已完成的项目列表";
          this.isShowSum=true;
          this.isShowTask=true;
          this.isShowMore=true;
          url="/project/showfinishdeveloperproject"
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



