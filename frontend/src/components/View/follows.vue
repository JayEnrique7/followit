<template>
<div>
    <div class="listLeft">
        <ul aria-label="Followers:">
            <div v-for="(follow, index) in follows.userFollowing" :key="index">
                <li>{{ follow.firstName }} {{ follow.lastName }}</li>
            </div>
        </ul>
    </div>
    <div class="listRigh">
        <ul aria-label="Following:">
            <div v-for="(follow, index) in follows.userFollower" :key="index">
                <li>{{ follow.firstName }} {{ follow.lastName }}</li>
            </div>
        </ul>
    </div>
</div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'login',
  data(){
        return {
                follows: []
        }
    },
    mounted() {
    axios.get('http://localhost:8080/api/follow/list/' + window.localStorage.getItem('id'))
      .then(response => this.follows = response.data)
      .catch(console.log)
    }
}
</script>

<style scoped>
.listRight {
    float: right;
    width: 50%;
}

.listLeft {
    float: left;
    width: 50%;
}

ul:before{
    content:attr(aria-label);
    font-size:120%;
    font-weight:bold;
    margin-left:-15px;
}
</style>