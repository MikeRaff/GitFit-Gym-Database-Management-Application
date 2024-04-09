<template>
    <div class="container classtype-page">
      <Navbar /> 
      <h1>
          <AnimatedLetters :letterClass="letterClass" :strArray="titleArray" :idx="12" />
    </h1>
      <div class="text-zone">
        <br />
        <div class="left-section">
          <h2>Explore Our Diverse Range of Class Types! <br /><br /> Discover a wide variety of gym classes tailored to suit your fitness needs, from yoga and HIIT to strength training and more. <br/><br />
          </h2>
          <div v-if="approvedClassTypes.length > 0">
            <h3>Approved Class Types:</h3>
            <ul>
              <li v-for="classType in approvedClassTypes" :key="classType.id">
                {{ classType.name }}
                <button @click="deleteClassType(classType)">Remove</button>
              </li>
            </ul>
          </div>
          <div v-if="unapprovedClassTypes.length > 0">
            <h3>Class Types Needing Approval:</h3>
            <ul>
              <li v-for="classType in unapprovedClassTypes" :key="classType.id">
                {{ classType.name }}
                <button @click="approveClassType(classType)">Approve</button>
                <button @click="deleteClassType(classType)">Remove</button>
              </li>
            </ul>
          </div>
          <div v-else-if="classTypesNotFound">
            <p>There are no class types in the system.</p>
          </div>
        </div>
        <div class="right-section">
          <h2>If you're a gym owner you can create new class types here:</h2>
          <input type="text" v-model="newClassTypeName" placeholder="Enter new class type name">
          <button @click="addClassType">Add Class Type</button>
          <h2>If you're an instructor, please propose class types here:</h2>
          <input type="text" v-model="proposedClassName" placeholder="Enter proposed class type name">
          <button @click="proposeClassType">Propose Class Type</button>
        </div>
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
  computed: {
    approvedClassTypes() {
      return this.classTypes.filter(classType => classType.approved);
    },
    unapprovedClassTypes() {
      return this.classTypes.filter(classType => !classType.approved);
    }
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

            if (this.classTypesNotFound && this.classTypes.length > 0) {
                this.classTypesNotFound = false;
            }
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
    },
    async approveClassType(classType) {
        const storedEmail = localStorage.getItem('email');
        if (!storedEmail) {
            alert("You must sign in first.");
            return;
        }
        try {
            const response = await AXIOS.put('/class-types/approve/' + classType.name, {
                email: storedEmail
            });
            classType.approved = true;
        } catch (error) {
            console.error('Error approving class type:', error);
        }
    }, 
    async deleteClassType(classType) {
        const storedEmail = localStorage.getItem('email');
        if (!storedEmail) {
            alert("You must sign in first.");
            return;
        }
        try {
            const response = await AXIOS.delete('/class-types/delete/' + classType.name, {
                data: {
                    email: storedEmail,
                    password: "password",
                    person: null
                }
            });
            const index = this.classTypes.findIndex(ct => ct.id === classType.id);
            if (index !== -1) {
            this.classTypes.splice(index, 1);
            }
        } catch (error) {
            console.error('Error deleting class type:', error);
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
.classtype-page .container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.classtype-page .text-zone {
  flex: 1;
  display: flex;
  justify-content: center;
  overflow-y: auto; /* Added overflow handling */
}

.left-section {
  width: 50%;
  padding-right: 10px;
  margin-left: 20px;
  animation: fadeIn 1s 1.8s backwards;
}

.right-section {
  width: 50%;
  padding-left: 10px;
  animation: fadeIn 1s 1.8s backwards;
}

.classtype-page h1 {
  color: #fff;
  font-size: 60px;
  margin-top: 120px;
  font-weight: 600;
  cursor: pointer;
  text-align: center;
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

.classtype-page h3 {
  color: #fff; /* White text */
  font-size: 24px; /* Adjust font size as needed */
  margin-top: 30px; /* Adjust margin top as needed */
  font-weight: 600; /* Bold font weight */
}

.classtype-page h3::before {
  content: ""; /* Add a pseudo-element for styling */
  display: inline-block;
  width: 10px; /* Adjust width as needed */
  height: 10px; /* Adjust height as needed */
  background-color: #00b3ff; /* Blue color for the bullet */
  margin-right: 10px; /* Adjust margin-right as needed */
  border-radius: 50%; /* Round shape for the bullet */
}

.classtype-page h3 + ul {
  margin-top: 10px; /* Adjust margin top between heading and list */
}

.classtype-page h3 + ul li {
  color: #fff; /* White text for list items */
  margin-top: 5px; /* Adjust margin top between list items */
}

.classtype-page h3 + ul li button {
  background-color: #00b3ff; /* Blue color for buttons */
  color: #fff; /* White text for buttons */
  border: none;
  padding: 5px 10px; /* Adjust padding as needed */
  cursor: pointer;
  margin-left: 10px; /* Adjust margin left between buttons */
  border-radius: 5px; /* Rounded corners for buttons */
}

.classtype-page h3 + ul li button:hover {
  background-color: #0080ff; /* Darker blue color on hover */
}

.classtype-page input[type="text"] {
  padding: 10px; /* Adjust padding as needed */
  margin-top: 10px; /* Adjust margin top as needed */
  width: 100%; /* Take up full width */
  border: 2px solid #00b3ff; /* Blue border */
  border-radius: 5px; /* Rounded corners */
  background-color: #1a1a1a; /* Dark background */
  color: #fff; /* White text */
  box-sizing: border-box; /* Include padding and border in width */
}

.classtype-page input[type="text"]::placeholder {
  color: #aaa; /* Placeholder text color */
}

/* Stylings for buttons */
.classtype-page button {
  background-color: #00b3ff; /* Blue color for buttons */
  color: #fff; /* White text for buttons */
  border: none;
  padding: 10px 20px; /* Adjust padding as needed */
  margin-top: 10px; /* Adjust margin top as needed */
  cursor: pointer;
  border-radius: 5px; /* Rounded corners for buttons */
  transition: background-color 0.3s; /* Smooth transition */
}

.classtype-page button:hover {
  background-color: #0080ff; /* Darker blue color on hover */
}

.classtype-page button + button {
  margin-left: 10px; /* Adjust margin left between buttons */
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

@media screen and (max-width: 1150px) {
  .classtype-page h1 {
    margin-top: 0;
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