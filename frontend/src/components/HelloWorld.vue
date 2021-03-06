<template>
  <div>
    <div v-for="(message, index) in messages" :key="index">
      {{message.message}}<br>
    </div>
    <button @click="loadMessagesFromBackend()">Reload message</button>

    <br><br>
    <h2>Post new message:</h2>
    <input type="text" v-model="newMessage">
    <button @click="postToBackend()">Post</button>
  </div>
</template>

<script>
export default {
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
}
</script>

<style scoped>
</style>
