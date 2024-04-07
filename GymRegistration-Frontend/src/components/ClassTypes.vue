<template>
  <div class="container classtype-page">
    <Navbar /> 
    <div class="text-zone">
      <h1>
        <AnimatedLetters :letterClass="letterClass" :strArray="titleArray" :idx="12" />
      </h1>
      <br />
      <h2>Explore Our Diverse Range of Class Types! <br /><br /> Discover a wide variety of gym classes tailored to suit your fitness needs, from yoga and HIIT to strength training and more. <br/><br /> If you're a gym owner or an instructor, you can create new class types right here!
      </h2>
    </div>
  </div>
      <!-- <div id="eventregistration">
      <h2>Persons</h2>
      <table>
        <tr v-for="person in persons" :key="person.name">
          <td>{{ person.name }}</td>
          <td>
            <ul>
              <li v-for="event in person.events" :key="event.name">
                {{ event.name }}
              </li>
            </ul>
          </td>
        </tr>
        <tr>
          <td>
            <input type="text" v-model="newPerson" placeholder="Person Name">
          </td>
          <td>
            <button v-bind:disabled="!newPerson" @click="createPerson(newPerson)">Create Person</button>
          </td>
        </tr>
      </table>
      <p>
        <span v-if="errorPerson" style="color:red">Error: {{ errorPerson }}</span>
      </p>
    </div> -->
  </template>
  
<script>
import AnimatedLetters from  "./AnimatedLetters";
import Navbar from "./Navbar";

export default {
  data() {
    return {
      letterClass: "text-animate",
      titleArray: "ClassTypes".split(""),
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
.classtype-page .text-zone {
    position: absolute;
    align-content: center;
    left: 10%;
    top: 50%;
    transform: translateY(-50%);
    width: 40%;
    max-height: 90%;
    display: absolute;
}

.classtype-page h1 {
    color: #fff;
    font-size: 60px;
    margin: 0;
    font-weight: 600;
    cursor: pointer;
}

.classtype-page h1::before {
    color: #00b3ff;
    position: absolute;
    margin-top: -40px;
    left: 15px;
    opacity: 0.6;
}

.classtype-page h1::after {
    color: #00b3ff;
    position: absolute;
    margin-top: 18px;
    left: 20px;
    animation: fadeIn 1s 1.7s backwards;
    opacity: 0.6;
}

.classtype-page h2 {
    color: #fff;
    margin-top: 20px;
    font-weight: 400;
    font-size: 14px;
    letter-spacing: 2px;
    animation: fadeIn 1s 1.8s backwards;
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
    .classtype-page h1 {
        font-size: 50px;
    }
}

@media screen and (max-width: 1320px) {
    .classtype-page h1 {
        font-size: 40px;
    }
}

@media screen and (max-width: 1050px) {
    .classtype-page h1 {
        font-size: 29px;
        justify-content: center;
        letter-spacing: 0.5px;
    }

    .classtype-page .text-zone {
        position: initial;
        width: 100%;
        transform: none;
        padding: 10px;
        box-sizing: border-box;
        display: inline-block;
        text-align: left;
        margin-left: 20px;
    }

    .classtype-page h2 {
        font-size: 12px;
    }

    .classtype-page .logo-container {
        position: relative;
        width: 200px;
        height: auto;
        right: 0;
        box-sizing: border-box;
        margin: auto;
        left: 0;
    }
}
  </style>  