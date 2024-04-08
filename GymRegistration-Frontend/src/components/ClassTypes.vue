<template>
  <div class="container classtype-page">
    <Navbar /> 
    <div class="text-zone">
      <h1>
        <AnimatedLetters :letterClass="letterClass" :strArray="titleArray" :idx="12" />
      </h1>
      <br />
      <h2>Explore Our Diverse Range of Class Types! <br /><br /> Discover a wide variety of gym classes tailored to suit your fitness needs, from yoga and HIIT to strength training and more. <br/><br />
      </h2>
      <div v-if="classTypes.length > 0">
        <h3>Class Types:</h3>
        <ul>
          <li v-for="classType in classTypes" :key="classType.id">{{ classType.name }}</li>
        </ul>
      </div>
      <div v-else-if="classTypesNotFound">
        <p>There are no class types in the system.</p>
      </div>
      <h2>If you're a gym owner you can create new class types right here:</h2>
      <input type="text" v-model="newClassTypeName" placeholder="Enter new class type name">
      <button @click="addClassType">Add Class Type</button>
      <h2>If you're an instructor, please propose class types here:</h2>
      <input type="text" v-model="proposedClassName" placeholder="Enter proposed class type name">
      <button @click="proposeClassType">Propose Class Type</button>
    </div>
  </div>
  </template>
  
<script>
import AXIOS from './axiosConfig.js';
import AnimatedLetters from  "./AnimatedLetters";
import Navbar from "./Navbar";

export default {
  data() {
    return {
      letterClass: "text-animate",
      titleArray: "ClassTypes".split(""),
      classTypes: [],
      classTypesNotFound: false,
      newClassTypeName: "",
      proposedClassName: "",
    };
  },
  mounted() {
    setTimeout(() => {
      this.letterClass = "text-animate-hover";
    }, 4000);
    this.fetchClassTypes();
  },
  methods: {
    async fetchClassTypes() {
        try {
            const response = await AXIOS.get('/class-types');
            this.classTypes = response.data;
        } catch (error) {
            if (error.response && error.response.status === 404) {
                this.classTypesNotFound = true;
            } else {
                console.error('Error fetching class types:', error);
            }
        }
    },
    async addClassType() {
        const storedEmail = localStorage.getItem('email');
        if (!storedEmail) {
            alert("You must sign in first.");
            return;
        }
        try {
            const response = await AXIOS.post('/class-types/create/' + storedEmail, {
                name: this.newClassTypeName,
                approved: true
            });
            this.classTypes.push(response.data);
            this.newClassTypeName = "";
        } catch (error) {
            alert(error.response.data);
            console.error('Error adding class type:', error);
        }
    },
    async proposeClassType() {
        const storedEmail = localStorage.getItem('email');
        if (!storedEmail) {
            alert("You must sign in first.");
            return;
        }
        try {
            const response = await AXIOS.post('/class-types/propose/' + this.proposedClassName, {
                email: storedEmail,
                password: "password",
                person: null
            });
            this.classTypes.push(response.data);
            this.proposedClassName = "";
        } catch (error) {
            alert(error.response.data);
            console.error('Error proposing class type:', error);
        }
    }
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