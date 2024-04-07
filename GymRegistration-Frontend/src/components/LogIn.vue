<template>
  <div class="container login-page">
    <Navbar />
    <div class="text-zone">

      <h1>
      <AnimatedLetters :letterClass="letterClass" :strArray="welcomeArray" :idx="14"/>
      </h1>

      <h2>Enter your login credentials</h2>
      <form @submit.prevent="login">

        <div class="form-group">
          <label for="email">Email address:</label>
          <input type="email" id="email" v-model="email" placeholder="Enter your Email" required/>
        </div>

        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="password" placeholder="Enter your Password" required/>
        </div>

        <div class="form-group">
          <label for="accounttype">
            Select your account type:
            <select type="type" id="accountType" v-model="accountType" required>

              <option value="" disabled selected>Select your account type</option>
              <option value="customers">Customer</option>
              <option value="instructors">Instructor</option>
              <option value="owners">Owner</option>

            </select>
          </label>
        </div>

        <div class="wrap">
          <button type="submit" @click="login()">
            login
          </button>
        </div>

      </form>

      <p>Not registered?
        <a href="#/register" style="text-decoration: none;">Create an account
        </a>
      </p>

    </div>
  </div>
</template>

<script>
import AXIOS from './axiosConfig.js';
import AnimatedLetters from "./AnimatedLetters";
import Navbar from "./Navbar";

console.log(AXIOS.defaults.baseURL);

export default {
  name: "LogIn",
  data() {
    return {
      letterClass: "text-animate",
      welcomeArray: "LogIn",
      email: '',
      password: '',
      accountType: ''
    };
  },

  mounted() {
  setTimeout(() => {
    this.letterClass = "text-animate-hover";
  }, 4000);
  
  // makes it so that if you are logged in you cannot login
  let user = localStorage.getItem('email');
    if (user) {
      alert("Can not be signed into more than one account at a time.");
      this.$router.push({name:'Home'});
    }
  },

  components: {
    AnimatedLetters,
    Navbar
  },

  methods: {
    async login() {
      // Create url for the GET request
      const url = `/${this.accountType}/login/${this.email}/${this.password}`;

      // Make the GET request with the url
      const response = await AXIOS.get(url)
        .then(response => {
          console.log('Getting a response');
          console.log('Login successfull', response.data);

          // remembers (stores) that user is logged in & send them home
          if (response.status === 200) {
            const storedEmail = localStorage.getItem('email');
            if (storedEmail !== null && storedEmail !== undefined) {
            } else {
              localStorage.setItem('email', this.email);
              this.$router.push({ name: 'Home' });
            }
          }
        })
        .catch(error => {
          alert(error.response);
          console.log(this.email);
          console.log('Desired account type:', this.accountType);
          console.error('Response dara:', error.response.data);
          console.error('Response status:', error.response.status);
          console.error('Response headers:', error.response.headers);
        })

        // reinitialize fields??
    }
  }
};
</script>

<style scoped>
.login-page .text-zone {
  position: absolute;
  align-content: center;
  left: 50%;
  top: 50%;
  transform: translateY(-50%);
  width: 40%;
  max-height: 90%;
  display: absolute;
}

.login-page h1 {
  color: #fff;
  font-size: 60px;
  margin: 0;
  font-weight: 600;
  cursor: pointer;
  text-align: center;
}

.login-page h1::before {
  color: #00b3ff;
  position: absolute;
  margin-top: -40px;
  left: 15px;
  opacity: 0.6;
}

.login-page h1::after {
  color: #00b3ff;
  position: absolute;
  margin-top: 18px;
  left: 20px;
  animation: fadeIn 1s 1.7s backwards;
  opacity: 0.6;
}

.login-page h2 {
  color: #fff;
  margin-top: 20px;
  font-weight: 400;
  font-size: 14px;
  letter-spacing: 2px;
  animation: fadeIn 1s 1.8s backwards;
  text-align: center;
}

.login-page p {
  color: #fff;
  text-align: center;
}

.login-page .flat-button {
  border: none;
  color: #fff;
  background-image: linear-gradient(30deg, #0040ff, #15fd98);
  border-radius: 10px;
  background-size: 100% auto;
  font-family: inherit;
  font-size: 20px;
  text-align: center;
  padding: 0.6em 1.5em;
  white-space: nowrap;
  text-decoration: none;
  margin-top: 25px;
  float: left;
  white-space: nowrap;
  width: 200px;
}

.login-page .flat-button:hover {
  background-position: right center;
  background-size: 200% auto;
  -webkit-animation: pulse 2s infinite;
  animation: pulse512 1.5s infinite;
}

.login-page .form-group {
  margin-bottom: 15px;
}

.login-page label {
  display: block;
  width: 100%;
  margin-top: 10px;
  margin-bottom: 5px;
  text-align: left;
  color: #fff;
  font-weight: bold;
}

.login-page input {
  display: block;
  width: 100%;
  margin-bottom: 10px;
  padding: 5px;
  box-sizing: border-box;
  border: 1px solid #fff;
  border-radius: 5px;
}

.login-page button {
  padding: 15px;
  border-radius: 10px;
  margin-top: 10px;
  margin-bottom: 15px;
  border: none;
  color: #fff;
  background-color: #4CAF50;
  width: 100%;
  font-size: 16px;
}

.login-page select {
  display: block;
  width: 100%;
  margin-bottom: 15px;
  padding: 15px;
  box-sizing: border-box;
  border: 1px solid #fff;
  border-radius: 5px;  
  font-size: inherit;
  line-height: inherit;
}

@keyframes pulse512 {
  0% {
    box-shadow: 0 0 0 0 rgba(0, 64, 255, 0.6);
  }

  70% {
    box-shadow: 0 0 0 10px rgba(0, 64, 255, 0%);
  }

  100% {
    box-shadow: 0 0 0 0 rgba(0, 64, 255, 0%);
  }
}

@media screen and (max-width: 1580px) {
  .login-page h1 {
    font-size: 50px;
  }
}

@media screen and (max-width: 1320px) {
  .login-page h1 {
    font-size: 40px;
  }
}

@media screen and (max-width: 1050px) {
  .login-page h1 {
    font-size: 29px;
    justify-content: center;
    letter-spacing: 0.5px;
  }

  .login-page .text-zone {
    position: initial;
    width: 100%;
    transform: none;
    padding: 10px;
    box-sizing: border-box;
    display: inline-block;
    text-align: left;
    margin-left: 20px;
  }

  .login-page h2 {
    font-size: 12px;
  }

  .login-page .logo-container {
    position: relative;
    width: 200px;
    height: auto;
    right: 0;
    box-sizing: border-box;
    margin: auto;
    left: 0;
  }

  .login-page .flat-button {
    float: none;
    display: block;
  }
}
</style>