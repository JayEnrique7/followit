<template>
  <div>
    <input type="text" v-model="username" placeholder="Username" style="width: 300px;">
    <br>
    <div class="descriptionarea">
      <input type="password" v-model="password" placeholder="Password" style="width: 300px;">
      <br>
      <span class="buttonfortxtarea">
        <router-link to="/profile" tag="button" class="button button1">Log in</router-link>
      </span>
    </div>
  </div>
</template>

<style scoped>
input[type=text], input[type=password] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
</style>
