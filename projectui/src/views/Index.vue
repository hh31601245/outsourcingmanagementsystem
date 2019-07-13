<template>

  <el-container style="height: 500px; border: 1px solid #eee">
    <!--顶部  -->
  <el-header class="header">
      <el-row>
        <el-col :span="18" :offset="4" class="middle">
          <h2 style="margin-right: 15px">服务外包管理系统</h2>
        </el-col>
        <el-col :span="2">
          <div >

            <el-dropdown>
	        <i class="el-icon-setting" style="margin-right: 15px"></i>
	      </el-dropdown>
	      <!-- <span  v-text="role"></span> -->
        <span>{{this.name}}{{this.role}}</span>
	      <router-link to="/">&nbsp;注销</router-link>

          </div>
        </el-col>
      </el-row>
    </el-header>

    <el-container >
  <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
    <!-- 发包方的功能显示界面 -->
    <el-menu router :default-active="$route.path" class="el-menu-vertical-demo" theme="dark" v-show="isshowemployer">
      
      <el-submenu  index="1">
        <template slot="title"><i class="el-icon-message"></i>发包方功能</template>
        <el-menu-item-group>
          <template slot="title"></template>
          <el-menu-item  @click="addproject()">发布项目</el-menu-item>
          <el-menu-item  @click="intonocontractemployerproject()">查看未承包项目</el-menu-item>
          <el-menu-item @click="intocontractemployerproject()">查看正在进行中的项目</el-menu-item>
          <el-menu-item @click="intonoapprovalemployerproject()">验收项目</el-menu-item>
          <el-menu-item @click="intoapprovalemployerproject()">查看已完成项目</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

    </el-menu>

    <!-- 承包方管理员的功能显示界面 -->
      <el-menu router :default-active="$route.path" class="el-menu-vertical-demo" theme="dark" v-show="isshowcontractor">
      
      <el-submenu  index="1">
        <template slot="title"><i class="el-icon-message"></i>承包方管理员项目管理功能</template>
        <el-menu-item-group>
          <template slot="title"></template>
          <el-menu-item @click="intocancontractorproject()">可承包项目</el-menu-item>
          <el-menu-item @click="intostartedcontractorproject()">需分配任务</el-menu-item>
          <el-menu-item @click="intoendcontractorproject()">查看已承包项目进度</el-menu-item>
          <el-menu-item @click="intoneedapprovalproject()">查看待验收项目进度</el-menu-item>
          <el-menu-item @click="intoapprovalcontractorproject()">查看已完成项目</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu  index="2">
        <template slot="title"><i class="el-icon-message"></i>承包方管理员人员管理功能</template>
        <el-menu-item-group>
          <template slot="title"></template>
          <el-menu-item @click="intoadddeveloper()">添加开发人员</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group>
          <template slot="title"></template>
          <el-menu-item @click="intodeveloperlist()">查看开发人员列表</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

    </el-menu>

    <!-- 开发人员的功能显示界面 -->
    <el-menu router :default-active="$route.path" class="el-menu-vertical-demo" theme="dark" v-show="isshowdeveloper">
      
      <el-submenu  index="1">
        <template slot="title"><i class="el-icon-message"></i>开发人员功能</template>
        <el-menu-item-group>
          <template slot="title"></template>
          <el-menu-item @click="intodevelopernofinishproject()">正在进行中的任务</el-menu-item>
          <el-menu-item @click="intodeveloperwaitapprovalproject()">待验收的项目</el-menu-item>
          <el-menu-item @click="intodeveloperfinishproject()">已完成的任务</el-menu-item>
        </el-menu-item-group>
      </el-submenu>


    </el-menu>


  </el-aside>
  
  <el-container>

    
    <el-main>
      <router-view ></router-view>
    </el-main>
  </el-container>
</el-container>
</el-container>
</template>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 30px;
  }
  
  .el-aside {
    color: #333;
  }
</style>

<script>

export default {
  data()
  {
      return{  //显示在界面上的数字
          isshowemployer:false,
          isshowcontractor:false,
          isshowdeveloper:false,
          role:'',
          name:'',
      }
  },
  created()
    {
      console.log(localStorage.getItem('token'));
      //this.$router.push({path:'./LeaveLists'});
      this.getRole();


    },
  methods:{

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
            console.log(data)
            this.role=data
            //根据权限显示对应能执行的操作
            if(data=="发包方")
            {
              console.log(1)
              this.isshowemployer=true
            }
            if(data=="承包方管理员")
            {
                this.isshowcontractor=true
            }
            if(data=="开发人员")
            {
                this.isshowdeveloper=true
            }
            //console.log(this.tableData.getList.get(0))
            //this.$message.success(msg)//提示成功
            
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
        },

      //获取姓名
      getName(){
            this.$http.post('/user/getname')
            .then((res)=>{
            console.log(res)
            const {
            code,
            msg,
            data
            } =res.data
            if(code === 200){ 
            console.log(data)
            this.name=data
            console.log(this.name)
            //console.log(this.tableData.getList.get(0))
            //this.$message.success(msg)//提示成功
            
            }else{ 
            this.$message.error(msg)//否则提示失败
            }
            }).catch(e=>{
                this.$message.warning("123")
            })
        },
      //进入新建项目界面
      addproject(){
        this.$router.push({path:'./Add',query: {status:"新建项目"}})
      },

      //显示发包方未被承包的项目
        intonocontractemployerproject(){
           this.$router.push({path:'./About',query: {status:"发包方已发布"}})
           // this.$router.push({path:'./List',query: {status:"发包方已发布"}})
        },
      //显示发包方已被承包还未完成的项目
        intocontractemployerproject(){
         this.$router.push({path:'./About',query: {status:"发包方已承包"}})
          // this.$router.push({path:'./List',query: {status:"发包方已承包"}})
        },
      //显示发包方待验收的项目
        intonoapprovalemployerproject(){
          this.$router.push({path:'./About',query: {status:"发包方待验收"}})
          // this.$router.push({path:'./List',query: {status:"发包方待验收"}})
        },
      //显示发包方已完成的项目
        intoapprovalemployerproject(){
          this.$router.push({path:'./About',query: {status:"发包方已验收"}})
          // this.$router.push({path:'./List',query: {status:"发包方已验收"}})
        },
        //显示承包方能承包的项目
        intocancontractorproject(){
          this.$router.push({path:'./About',query: {status:"承包方可承包"}})
          // this.$router.push({path:'./List',query: {status:"承包方可承包"}})
        },
        //显示承包方已承包但未分配的项目
        intostartedcontractorproject(){
          this.$router.push({path:'./About',query: {status:"承包方已承包"}})
          // this.$router.push({path:'./List',query: {status:"承包方已承包"}})
        },
        //显示承包方已分配的项目
        intoendcontractorproject(){
          this.$router.push({path:'./About',query: {status:"承包方已分配"}})
          // this.$router.push({path:'./List',query: {status:"承包方已分配"}})
        },
        //显示承包方待验收的项目
        intoneedapprovalproject(){
          this.$router.push({path:'./About',query: {status:"承包方待验收"}})
        },
        //承包方已完成的项目
        intoapprovalcontractorproject(){
          this.$router.push({path:'./About',query: {status:"承包方已审批"}})
          // this.$router.push({path:'./List',query: {status:"承包方已审批"}})
          
        },
        //建立开发人员
        intoadddeveloper(){
          // this.$router.push({path:'./Add',query: {status:"创建开发人员"}})
          this.$router.push({path:'./AddDeveloper'})
        },
        //开发人员列表
        intodeveloperlist(){
          this.$router.push({path:'./DeveloperList',query: {status:"开发人员列表"}})
          // this.$router.push({path:'./List',query: {status:"开发人员列表"}})
        },
        //开发人员开发中的任务列表
        intodevelopernofinishproject(){
          this.$router.push({path:'./About',query: {status:"开发人员正开发"}})
          // this.$router.push({path:'./List',query: {status:"开发人员正开发"}})
        },
        //开发人员待验收的项目列表
        intodeveloperwaitapprovalproject()
        {
          this.$router.push({path:'./About',query: {status:"开发人员待审核"}})
        },
        //开发人员已开发完成
        intodeveloperfinishproject(){
          this.$router.push({path:'./About',query: {status:"开发人员已开发"}})
          // this.$router.push({path:'./List',query: {status:"开发人员已开发"}})
        }

   }
}
</script>
