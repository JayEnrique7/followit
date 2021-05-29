<template>
        <div class="descriptionarea">
            <div v-for="(msg, index) in getMessages" :key="index">
                <b>{{msg.userName}}</b> : {{msg.message}}<br>
            </div>
            <br>
            <br>
                <textarea type="text" v-model="message" placeholder="Write your message" rows="10" cols="40"/>
                <span class="buttonfortxtarea">
                    <button @click="postToBackend()" class="button button1">Send</button>
                </span>
        </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'login',
  data(){
        return {
                getMessages: [],
                message: ''
        }
    },
    mounted() {
    axios.get('http://localhost:8080/api/messages/' + window.localStorage.getItem('id'))
    .then(response => this.getMessages = response.data)
    },
methods: {
  postToBackend() {
    axios.post('http://localhost:8080/api/messages/post', {
        username: window.localStorage.getItem('user'),
        message: this.message
        }).then(() => {
            this.$router.go();
            }).catch(console.log)}
}
}
</script>


<style scoped>
textarea {
    resize: none;
}
</style>