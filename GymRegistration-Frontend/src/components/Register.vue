<template>
  <div class="container register-page">
    <Navbar />
    <div class="text-zone">

      <h1>
      <AnimatedLetters :letterClass="letterClass" :strArray="welcomeArray" :idx="14"/>
      </h1>
      
      <h2>Enter your credentials</h2>
      <form @submit.prevent="register">

        <label for="personName">Name:</label>
        <input type="name" id="personName" v-model="personName" placeholder="Enter your Name" required/>

        <label for="email">Email address:</label>
        <input type="email" id="email" v-model="email" placeholder="Enter your Email" @change="checkEmails" required/>
        <input type="email" id="reemail" placeholder="Re-enter your Email" @keyup="checkEmails" required/>
        
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" placeholder="Enter your Password" @change="checkPasswords" required/>
        <input type="password" id="repassword" placeholder="Re-enter your Password" @keyup="checkPasswords" required/>
        
        <label for="accounttype">
          Select your account type:
          <select type="type" id="accountType" v-model="accountType" required>
        
            <option value="" disabled selected>Select your account type</option>
            <option value="customers">Customer</option>
            <option value="instructors">Instructor</option>
            <option value="owners">Owner</option>
        
          </select>
        </label>
        
        <div class="wrap">
          <button type="submit" @click="register()">
            register
          </button>
        </div>
      
      </form>
      
      <p>Already have an account?
        <a href="#/login" style="text-decoration: none;">Login
        </a>
      </p>
    
    </div>
  </div>
</template>
  
<script>
  import AXIOS from './axiosConfig.js';
  import AnimatedLetters from "./AnimatedLetters";
  import Navbar from "./Navbar";

  export default {
    data() {
      return {
        letterClass: "text-animate",
        welcomeArray: "Register",
        personName: '',
        email: '',
        password: '',
        accountType: '',
        persons: [],
        person: '',
        errorPerson: '',
        errorAccount: '',
        response: []
      };
    },

    mounted() {
      setTimeout(() => {
        this.letterClass = "text-animate-hover";
      }, 4000);

      // no idea if its good or necessary
      // makes it so that if you are logged in you cannot register
      let user = localStorage.getItem('user-info');
      if (user) {
        this.$router.push({name:'Home'});
      }      
    },

    components: {
      AnimatedLetters,
      Navbar
    },

    methods: {
      checkEmails() {
        var input = document.getElementById('email');
        if (input.value != document.getElementById('reemail').value) {
            input.setCustomValidity('Emails Must be Matching.');
        } else {
            // input is valid -- reset the error message
            input.setCustomValidity('');
        }
      },

      checkPasswords() {
        var input = document.getElementById('password');
        if (input.value != document.getElementById('repassword').value) {
            input.setCustomValidity('Passwords Must be Matching.');
        } else {
            // input is valid -- reset the error message
            input.setCustomValidity('');
        }
      },

      getPersonByName() {
        // The URL should be the endpoint from your PersonRestController that returns the person found by name
        const url = `/persons/byname/${this.personName}`;

        AXIOS.get(url)
        .then(response => {
          this.persons = response.data;
        })
        .catch(e => {
          this.errorPerson = e;
        });
      },

      createPerson() {
        AXIOS.post('/persons/create', { name: this.personName})
        .then(response => {
          this.persons.push(response.data);
          this.errorPerson = '';
          this.personName = '';
        })
        .catch(e => {
          const errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorPerson = errorMsg;
        });
      },

      // This probably needs to be reworked the most
      // based on billy's work
      async register() {
        try {
          // Try finding the person
          this.getPersonByName();

          if(this.persons.length < 1) {
            this.createPerson();
          }

          // set the person in the data
          this.person = this.persons[0];

          // THIS IS SO FUCKED I HAVE NO CLUE WHAT IM DOING

          // Construct the URL with the account type parameter
          const url = `/${this.accountType}/create`;
        
          // Make the POST request with the registerData as the request body
          const response = await AXIOS.post(url, register.Data);

          // Handle the response here (e.g., show a success message, redirect, etc.)
          console.log('Account created successfully', response.data);

          // no clue if its good or even necessary
          // remembers that account is created and sends them home
          if(response.status == 201) {
            localStorage.setItem('user-info', JSON.stringify(response.data));
            this.$router.push({name:'Home'});
          }

          // Reinitialize the fields??
                    
          // Optionally, return the response for further processing
          return response.data;
        }
        catch(e) {
          const errorMsg = e.response.data.message;
          console.log(errorMsg);
          this.errorAccount = errorMsg;
        }
      }
    }
  };
</script>
  
<style scoped>
  .register-page .text-zone {
    position: absolute;
    align-content: center;
    left: 50%;
    top: 50%;
    transform: translateY(-50%);
    width: 40%;
    max-height: 90%;
    display: absolute;
  }

  .register-page h1 {
    top: 40px; /* Adjust distance below navbar */
    color: #fff;
    font-size: 60px;
    margin: 0;
    font-weight: 600;
    cursor: pointer;
    text-align: center;
  }
  
  .register-page h1::before {
    color: #00b3ff;
    position: absolute;
    margin-top: -40px;
    left: 15px;
    opacity: 0.6;
  }
  
  .register-page h1::after {
    color: #00b3ff;
    position: absolute;
    margin-top: 18px;
    left: 20px;
    animation: fadeIn 1s 1.7s backwards;
    opacity: 0.6;
  }
  
  .register-page h2 {
    color: #fff;
    margin-top: 20px;
    font-weight: 400;
    font-size: 14px;
    letter-spacing: 2px;
    animation: fadeIn 1s 1.8s backwards;
    text-align: center;
  }

  .register-page .flat-button {
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

  .register-page p {
    color: #fff;
    text-align: center;
  }

  
  .register-page .flat-button:hover {
    background-position: right center;
    background-size: 200% auto;
    -webkit-animation: pulse 2s infinite;
    animation: pulse512 1.5s infinite;
  }
  
  .register-page label {
    display: block;
    width: 100%;
    margin-top: 10px;
    margin-bottom: 5px;
    text-align: left;
    color: #fff;
    font-weight: bold;
  }
  
  .register-page input {
    display: block;
    width: 100%;
    margin-bottom: 15px;
    padding: 10px;
    box-sizing: border-box;
    border: 1px solid #fff;
    border-radius: 5px;
  }
  
  .register-page button {
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
  
  .register-page select {
    padding: 15px;
    border-radius: 10px;
    margin-top: 15px;
    margin-bottom: 15px;
    border: none;
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
    .register-page h1 {
      font-size: 50px;
    }
  }
  
  @media screen and (max-width: 1320px) {
    .register-page h1 {
      font-size: 40px;
    }
  }
  
  @media screen and (max-width: 1050px) {
    .register-page h1 {
      font-size: 29px;
      justify-content: center;
      letter-spacing: 0.5px;
    }

    .register-page .text-zone {
      position: initial;
      width: 100%;
      transform: none;
      padding: 10px;
      box-sizing: border-box;
      display: inline-block;
      text-align: left;
      margin-left: 20px;
    }

    .register-page h2 {
      font-size: 12px;
    }

    .register-page .logo-container {
      position: relative;
      width: 200px;
      height: auto;
      right: 0;
      box-sizing: border-box;
      margin: auto;
      left: 0;
    }

    .register-page .flat-button {
      float: none;
      display: block;
    }
  }
</style>