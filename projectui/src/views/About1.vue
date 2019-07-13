<template>
  <div class="about1">
    <h1></h1>
  </div>
</template>

<script>
export default {
  created(){
    //var ss=this.$route.query.status
    this.$router.push({path:'./Tasklist',query: {projectid: this.$route.query.projectid,yourrole:this.$route.query.yourrole}})
  }

}
</script>
