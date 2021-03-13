<template>
  <div>
    <input type="text" v-model="username" placeholder="Username" size="42">
    <br><br>
    <input type="password" v-model="password" placeholder="Password" size="42">
    <br>
    <router-link to="/profile" tag="button">Log in</router-link>
  </div>
</template>
