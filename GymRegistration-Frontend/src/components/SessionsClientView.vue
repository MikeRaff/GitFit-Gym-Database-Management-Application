<template>
  <div class="sessions-client-view">
    <Navbar /> 
    <div class="table-container">
      <h1>
        <AnimatedLetters :letterClass="letterClass" :strArray="welcomeArray" :idx="14" />
      </h1>
      <table>
        <thead>
          <tr>
            <th>Class Type</th>
            <th>Capacity</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Date</th>
            <th>Lead Instructor</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="session in sessions" :key="session.id">
            <td>{{ session.classType.name }}</td> <!-- Changed to session.classType -->
            <td>{{ session.capacity }}</td>
            <td>{{ session.startTime }}</td>
            <td>{{ session.endTime }}</td>
            <td>{{ session.date }}</td>
            <td>{{ session.instructor }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="register-button">
      <button>Register</button>
    </div>
  </div>
</template>
  
  <script>
  import AnimatedLetters from "./AnimatedLetters";
  import Navbar from "./Navbar"; 

  import axios from "axios";
  import config from "../../config";
  const frontEndUrl = 'http://' + config.dev.host + ':' + config.dev.port;
  const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
  console.log("backend url", backendUrl);
  const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontEndUrl }
  });

  export default {
    name: 'SessionsClientView',
  created() {
    this.getSessions();
  },

  data() {
      return {
        letterClass: "text-animate",
        welcomeArray: "Sessions",
        sessions: [], 
        leadInstructors: []
      };
    },

    mounted() {
      setTimeout(() => {
        this.letterClass = "text-animate-hover";
      }, 4000);
      this.getSessions(); // Fetch sessions data when component is mounted
    },
 
    components: {
      AnimatedLetters,
      Navbar
    },

    methods: {
      getSessions() {
        const url = '/sessions';
        AXIOS.get(url)
          .then(response => {
            console.log("Successfully Found Sessions", response.data);
            this.sessions = response.data;
            this.getInstructor(); // Call getInstructor here, after sessions is set
          })
          .catch(error => {
            console.error('There was an error getting the sessions:', error);
          });
    },

    getInstructor() {
      // Now this.sessions should have data if getSessions was successful
      const url = '/instructor-registration-s/';
      for (const session of this.sessions) {
        var sessionId = session.id
        AXIOS.get(url+sessionId)
        .then(response => {
          console.log("Successfully Found Session Registration", response.data);
          let instructor =  response.data;
          session.instructor = instructor[0].instructor.person.name;
        })
        .catch(error => {
          session.instructor = "To Be Assigned";
          console.error('There was an error getting the sessions:', error);
        });
        
      }
    },

    fetchSessions() {
        // Here you can fetch the sessions data from the database using API calls or other methods
        // For now, let's simulate some sample sessions data
        this.sessions = [
        { id: 1, classType: "Yoga", capacity: 20, duration: "1 hour", instructor: "John Doe", time: "10:00 AM", date: "2024-04-06" },
        { id: 2, classType: "Pilates", capacity: 15, duration: "1 hour", instructor: "Jane Smith", time: "11:30 AM", date: "2024-04-07" },

        ];
      },
      addSession() {
        // Implement logic to add a new session
      }
    }
    // Additional methods to fetch and handle sessions data would be added here
  };
  </script>
  
  <style scoped>
.text-animate {
  position: absolute;
  top: calc(50% + 30px); /* Adjust distance below navbar */
  left: 50%;
  transform: translate(-50%, -50%);
  color: #fff;
  font-size: 60px;
  margin: 0;
  font-weight: 600;
  cursor: pointer;
  text-align: center;
}
.text-animate-hover {
  color: #fff;
  font-size: 60px;
  margin: 0;
  font-weight: 600;
  cursor: pointer;
  text-align: center;
  transition: color 0.5s ease-in-out;
}

  .sessions-client-view .text-zone {
    position: absolute;
    align-content: center;
    left: 10%;
    top: 50%;
    transform: translateY(-50%);
    width: 40%;
    max-height: 90%;
    display: absolute;
  }

  .sessions-client-view {
    padding: 20px;
    margin: auto;
  }
 
  .content {
  position: relative;
  }

  .sessions-client-view h1 {
    top: 40%; /* Adjust distance below navbar */
    color: #fff;
    font-size: 60px;
    margin: 0;
    font-weight: 600;
    cursor: pointer;
    text-align: center;
  }
  
  .table-container {
    overflow-x: auto;
    overflow-y: auto;
    top: calc(45% + 20px); 
    position: absolute;
    left: 50%;
    transform: translate(-50%, -40%);
    width: 80%;
    max-height: calc(80%);
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  table, th, td {
    padding: 8px;
    text-align: left;
    border: 1px solid #ccc;
    color: #FFF;
  }
  
  th {
    background-color: #f0f0f0;
    color: #444;
    font-size: 20px; /* Adjust the font size here */
    padding: 5px;
  }

  tbody tr:hover {
    background-color: #0040ff;
  }
  
  td { 
    /* background-color: #0040ff; */
    padding: 10px;
    /* text-align: center; */
   } 
  
  tr:nth-child(even) {
    background-color: #f2f2f261;
  }
  tr:nth-child(odd) {
    background-color: #f2f2f2af;
  }
  
  .register-button {
    display: flex;
    justify-content: flex-end;
    position: absolute;
    bottom: 30px; 
    right: 40px; 
  }

button {
  padding: 10px 20px;
  background-color: #0040ff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  opacity: 0.85;
}
  </style>
  
