<template>
        <div class="descriptionarea">
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
                message: ''
        }
    },
methods: {
  postToBackend() {
    axios.post('http://localhost:8080/api/messages/post', {
        username: window.localStorage.getItem('user'),
        message: this.message
        }).then((response) => {
        console.log(window.localStorage.getItem('id'));
            console.log(response)
            }).catch((error) => {
    console.log(error);
    })}
}
}
</script>


<style scoped>
textarea {
    resize: none;
}
</style>