<template>
    <div>
        <h1>Bo Dahl</h1>
        <button @click="postToBackend()" class="button button1">Follow Bosse</button>
        <br>
        <br>
        <p>
            <router-link to="/">
                Followers <span class="tab1">77</span>
            </router-link>
            <span class="tab2">
                <router-link to="/">
                    Following<span class="tab1">22</span>
                </router-link>
            </span>
        </p>
        <br>
        <div class="descriptionarea">
            <span><h4>Messages:</h4></span>
                <textarea type="text" v-model="newMessage" placeholder="Write your message" rows="10" cols="40"/>
                <span class="buttonfortxtarea">
                    <button @click="postToBackend()" class="button button1">Send</button>
                </span>
        </div>
    </div>
</template>

<script>
export default {
  name: 'profile',
  data: function() {
    return {
      messages: [],
      newMessage: ''
    }
  },
  methods: {
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
.tab2 { 
    display:inline-block; 
    margin-left: 40px; 
}

.tab1 { 
    display:inline-block; 
    margin-left: 10px; 
}
textarea {
resize: none;
}
</style>