<template>
  <div class="about">
    <h1></h1>
  </div>
</template>

<script>
export default {
  created(){
    var ss=this.$route.query.status
    this.$router.push({path:'./List',query: {status:ss}})
  }

}
</script>

