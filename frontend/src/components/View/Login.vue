<template>
<form @submit.prevent="submit">
  <div>
    <input type="text" v-model="form.email" placeholder="Username" style="width: 300px;">
    <br>
    <div class="descriptionarea">
      <input type="password" v-model="form.secret" placeholder="Password" style="width: 300px;">
      <br>
      <span class="buttonfortxtarea">
        <button type="submit" class="button button1">Log in</button>
      </span>
    </div>
  </div>
</form>
</template>

<script>
import axios from 'axios'
export default {
  name: 'login',
  data(){
        return {
            form: {
                email: '',
                secret: ''
            }
        }
    },
methods: {
  submit() {
    axios.post('http://localhost:8080/login', this.form).then((response) => {
      if (response.status === 200) {
        window.localStorage.setItem('token', response.data.token);
        window.localStorage.setItem('user', this.form.email);
        window.localStorage.setItem('id', response.data.user.id);
        this.$router.push('profile');
        this.$router.go();
      }
    }).catch(() => {
      window.localStorage.removeItem('token')
      window.localStorage.removeItem('user');
      this.$router.go();
    });
    }
}
}
</script>

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
