<template>
    <div>
      <button @click="logout()" class="logout">Logga ut</button>
      <br>
      <h1>{{ name }}</h1>
        <br>
        <p>
          <textarea type="text" v-model="bio" rows="10" cols="40" />
          <span class="buttonfortxtarea">
                    <button @click="editBio()" class="button button1">Edit</button>
                </span>
        <br>
        <br>
        <br>
        <button @click="follow()" class="button button1">{{ followName }}</button>
        <br>
        <br>
        <p>
            <router-link to="follows">
                Followers <span class="tab1">{{ followers }}</span>
            </router-link>
            <span class="tab2">
                <router-link to="follows">
                    Following<span class="tab1">{{ followings }}</span>
                </router-link>
            </span>
        </p>
        <br>
            <router-link to="messages"><h3>Messages:</h3></router-link>
        <div id="app">
          <router-view></router-view>
      </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'profile',
  data() {
        return {
          name: '',
          followName: '',
          messages: [],
          newMessage: '',
          followers: 0,
          followings: 0,
          bio: ''
        }
    },
    mounted() {
      axios.get('http://localhost:8080/api/profile/' + window.localStorage.getItem('id'))
      .then(response => {
        this.bio = response.data.user.info.bio;
        this.name = response.data.user.firstName + ' ' + response.data.user.lastName;
        this.followName = 'Follow ' + response.data.user.firstName;
      })
      .catch(console.log)
      axios.get('http://localhost:8080/api/follow/list/' + window.localStorage.getItem('id'))
      .then(response => {
        this.followers = response.data.followerSize;
        this.followings = response.data.followingSize;
      })
      .catch(console.log)
    },
  methods: {
    logout: function() {
      axios.post('http://localhost:8080/api/logout')
      .then(() => {
        window.localStorage.removeItem('token');
        window.localStorage.removeItem('user');
        window.localStorage.removeItem('id');
        this.$router.push('/');
    }).catch(() => {
        window.localStorage.removeItem('token');
        window.localStorage.removeItem('user');
        window.localStorage.removeItem('id');
        this.$router.push('/');
    });
    },
    follow: function() {
      axios.put('http://localhost:8080/api/follow/user/' + window.localStorage.getItem('id'))
      .then(response => console.log(response))
      .catch(error => console.log(error.response.data.message))
    },
    editBio: function() {
      axios.put('http://localhost:8080/api/user/profile/edit', {
      bio: this.bio
      })
      .then(response => {
        console.log(response.data)
      }).catch(console.log)
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
.logout{
			float: right;
		}
    textarea {
    resize: none;
}
</style>