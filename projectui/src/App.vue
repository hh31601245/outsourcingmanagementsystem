<template>
<div id="app">

<div id="nav">

<!-- <router-link to="/login">Login</router-link>| -->


</div>
<router-view/>
</div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'

export default {
  
  name: 'app',
  components: {
    HelloWorld
  }
}
</script>

<style>

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 60px; */

  
}
</style>
