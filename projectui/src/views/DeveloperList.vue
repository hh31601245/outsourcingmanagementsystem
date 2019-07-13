<template>
<div class="developerlists">
    <!-- 开发人员列表 -->
    <el-row :gutter="10">
  <el-col :span="8" v-for="(o, index) in tableData" :key="index" :offset=2>
    <el-card class="developercar" :body-style="{ padding: '0px' }">
      <!--transition控制显示或消失 -->
      <transition name="el-fade-in-linear">
      <!-- <img src="~examples/assets/images/hamburger.png" class="image"> -->
      
      <div style="padding: 14px;">
        <span>开发人员</span>
        <div class="bottom clearfix">

          <span>编号：{{o.id}}&nbsp;&nbsp;姓名：{{o.user_name}}</span>

        
        </div>
      </div>
          
      </transition>

    </el-card>
  </el-col>
</el-row>
</div>


</template>

<style>
.developerlists{
  height: 800px;
       background-image: url('../assets/backgroud1.png');  
       background-Repeat: "no-repeat";
      background-size: 100% auto;
}
.developercar{
  height: 150px;
       background-image: url('../assets/backgroud2.jpg');  
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
         
            currentDate: new Date(),
  
            tableData:[],
            loading: true,
        }
        
    },
    created()
    {

      this.getList()
    },
    methods:{
      //显示所有开发人员
      getList(){
        console.log(this.thisisstatus)
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



