<template>
  <div class="container login-page">
    <Navbar />
    <div class="text-zone">
      <h1>
        <AnimatedLetters :letterClass="letterClass" :strArray="welcomeArray" :idx="14" />
      </h1>
      <h2>Enter your login credentials</h2>
      <form action="">
        <label for="emailaddress">
          Email address:
        </label>
        <input type="email" id="emailaddress" name="emailaddress" placeholder="Enter your Email" required/>
        <label for="password">
          Password:
        </label>
        <input type="password" id="password" name="password" placeholder="Enter your Password" required/>
        <label for="accounttype">
          Select your account type:
          <select>
            <option value="Customer">Customer</option>
            <option value="Instructor">Instructor</option>
            <option value="Owner">Owner</option>
          </select>
        </label>
        <div class="wrap">
          <button type="submit" @click="login()" v-bind:disabled="isLoginButtonDisabled"> <!-- Add the login method and isLoginButtonDisabled data property -->
            login
          </button>
        </div>
      </form>
      <p>Not registered?
        <a href="#/register" style="text-decoration: none;">Create an account <!-- Add a link to the registration page -->
        </a>
      </p>
    </div>
  </div>
  
</template>

<script>
import AnimatedLetters from "./AnimatedLetters";
import Navbar from "./Navbar";

export default {
  data() {
    return {
      letterClass: "text-animate",
      welcomeArray: "LogIn"
    };
  },
  mounted() {
    setTimeout(() => {
      this.letterClass = "text-animate-hover";
    }, 4000);
  },
  components: {
    AnimatedLetters,
    Navbar
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
  margin-bottom: 15px;
  padding: 10px;
  box-sizing: border-box;
  border: 1px solid #fff;
  border-radius: 5px;
}

.login-page button {
  padding: 15px;
  border-radius: 10px;
  margin-top: 15px;
  margin-bottom: 15px;
  border: none;
  color: #fff;
  background-color: #4CAF50;
  width: 100%;
  font-size: 16px;
}

.login-page select {
  padding: 15px;
  border-radius: 10px;
  margin-top: 15px;
  margin-bottom: 15px;
  border: none;
  color: #fff;
  width: 100%;
  font-size: 16px;
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