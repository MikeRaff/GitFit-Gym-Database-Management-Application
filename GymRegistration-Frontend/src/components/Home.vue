<template>
  <div class="container home-page">
    <div class="text-zone">
      <h1>
        <AnimatedLetters :letterClass="letterClass" :strArray="welcomeArray1" :idx="12" />
        <br />
        <AnimatedLetters :letterClass="letterClass" :strArray="welcomeArray2" :idx="14" />
      </h1>
      <br />
      <h2>Add your exams to your calendar with ease! <br /> Just search, click add, and export...
        <br /><br /><br /><router-link to="/search" class="flat-button">GET STARTED</router-link>
      </h2>
    </div>
    <Logo class="Home"/>
  </div>
</template>

<script>
import AnimatedLetters from "./AnimatedLetters";
import Logo from "./Logo";

export default {
  data() {
    return {
      letterClass: "text-animate",
      welcomeArray1: "Welcome To".split(""),
      welcomeArray2: "McGill Exam Scheduler!".split("")
    };
  },
  mounted() {
    setTimeout(() => {
      this.letterClass = "text-animate-hover";
    }, 4000);
  },
  components: {
    AnimatedLetters,
    Logo
  }
};
</script>

<style scoped>
.home-page .text-zone {
    position: absolute;
    align-content: center;
    left: 10%;
    top: 50%;
    transform: translateY(-50%);
    width: 40%;
    max-height: 90%;
    display: absolute;
}

.home-page h1 {
    color: #fff;
    font-size: 60px;
    margin: 0;
    font-weight: 600;
    cursor: pointer;
}

.home-page h1::before {
    color: #ffd700;
    position: absolute;
    margin-top: -40px;
    left: 15px;
    opacity: 0.6;
}

.home-page h1::after {
    color: #ffd700;
    position: absolute;
    margin-top: 18px;
    left: 20px;
    animation: fadeIn 1s 1.7s backwards;
    opacity: 0.6;
}

.home-page h2 {
    color: #fff;
    margin-top: 20px;
    font-weight: 400;
    font-size: 14px;
    letter-spacing: 2px;
    animation: fadeIn 1s 1.8s backwards;
}

.home-page .flat-button {
    border: none;
    color: #fff;
    background-image: linear-gradient(30deg, #ff4000, #fd9815);
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

.home-page .flat-button:hover {
    background-position: right center;
    background-size: 200% auto;
    -webkit-animation: pulse 2s infinite;
    animation: pulse512 1.5s infinite;
}

@keyframes pulse512 {
    0% {
        box-shadow: 0 0 0 0 rgba(255, 165, 0, 0.6);
    }

    70% {
        box-shadow: 0 0 0 10px rgba(255, 165, 0, 0%);
    }

    100% {
        box-shadow: 0 0 0 0 rgba(255, 165, 0, 0%);
    }
}

@media screen and (max-width: 1580px) {
    .home-page h1 {
        font-size: 50px;
    }
}

@media screen and (max-width: 1320px) {
    .home-page h1 {
        font-size: 40px;
    }
}

@media screen and (max-width: 1150px) {
    .home-page h1 {
        font-size: 29px;
        justify-content: center;
        letter-spacing: 0.5px;
    }

    .home-page .text-zone {
        position: initial;
        width: 100%;
        transform: none;
        padding: 10px;
        box-sizing: border-box;
        display: inline-block;
        text-align: left;
        margin-left: 20px;
    }

    .home-page h2 {
        font-size: 12px;
    }

    .home-page .logo-container {
        position: relative;
        width: 200px;
        height: auto;
        right: 0;
        box-sizing: border-box;
        margin: auto;
        left: 0;
    }

    .home-page .flat-button {
        float: none;
        display: block;
    }
}
</style>
