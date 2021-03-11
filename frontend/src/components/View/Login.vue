<template>
  <div>
    <input type="text" v-model="username" placeholder="Username">
    <br><br>
    <input type="password" v-model="password" placeholder="password">
    <br>
    <router-link to="/Profile" tag="button">Log in</router-link>
  </div>
</template>

<script>
/*export default {
  name: 'HelloWorld',
  data: function() {
    return {
      messages: [],
      newMessage: ''
    }
  },
  methods: {
    loadMessagesFromBackend: function() {
      fetch('http://ec2-13-53-45-17.eu-north-1.compute.amazonaws.com:8080/messages')
      .then(response => response.json())
      .then(data => this.messages = data)
    },
    postToBackend: function() {
      fetch('http://ec2-13-53-45-17.eu-north-1.compute.amazonaws.com:8080/message?message='+this.newMessage, {method: 'POST'})
      .then(response => response.json())
      .then(data => console.log(data))
      this.newMessage = '';
    }
  }
}*/
</script>

/*<style scoped>
</style>*/
