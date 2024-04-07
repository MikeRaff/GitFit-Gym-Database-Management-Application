<template>
    <div class="session-view-instructor">
      <Navbar /> <!-- Added navbar component here -->
      <div class="table-container">
        <h1>
          <AnimatedLetters :letterClass="letterClass" :strArray="welcomeArray" :idx="14" />
        </h1>
        <table>
          <thead>
            <tr>
              <th>Class Type</th>
              <th>Capacity</th>
              <th>Duration</th>
              <th>Instructor</th>
              <th>Time</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="session in sessions" :key="session.id">
              <td>{{ session.classType }}</td>
              <td>{{ session.capacity }}</td>
              <td>{{ session.duration }}</td>
              <td>{{ session.instructor }}</td>
              <td>{{ session.time }}</td>
              <td>{{ session.date }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="pagination">
        Page <span>1</span> <span>2</span> <span>3</span> <span>4</span>
      </div>
      <div class="session-actions">
        <button class="edit-session" @click="editSession">Edit Session</button>
      <button class="create-session" @click="createSession">Create New Session</button>
      </div>
    </div>
  </template>
  
  <script>
  import AnimatedLetters from "./AnimatedLetters";
  import Navbar from "./Navbar"; 

  export default {
    name: 'SessionsStaffView',
    
    data() {
      return {
        letterClass: "text-animate",
        welcomeArray: "Sessions",
        sessions: [
          // Session data placeholder
        ],
      };
    },
    //NEED TO ADD METHOD TO MOVE PAGES
    mounted() {
      setTimeout(() => {
        this.letterClass = "text-animate-hover";
      }, 4000);
      this.fetchSessions(); // Fetch sessions data when component is mounted
    },
    components: {
      AnimatedLetters,
      Navbar
    },
    methods: {
      fetchSessions() {
        // Here you can fetch the sessions data from the database using API calls or other methods
        // For now, let's simulate some sample sessions data
        this.sessions = [
          { id: 1, classType: "Yoga", startTime: "10:00 AM", endTime: "11:00 AM" },
          { id: 2, classType: "Pilates", startTime: "11:30 AM", endTime: "12:30 PM" },
          // Add more sessions as needed
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
    .session-view-instructor .text-zone {
    position: absolute;
    align-content: center;
    left: 10%;
    top: 50%;
    transform: translateY(-50%);
    width: 40%;
    max-height: 90%;
    display: absolute;
  }

  .session-view-instructor {
    padding: 20px;
    margin: auto;
  }

  .content {
  position: relative;
  }

  .session-view-instructor h1 {
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
    top: calc(30%); 
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 80%;
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
  
  th, td {
    text-align: left;
    padding: 8px;
  }
  
  th {
    background-color: #f0f0f0;
    color: #444;
    /* padding: 10px; */
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
    background-color: #f2f2f27f;
  }
  
  .pagination {
    margin-top: 20px;
    text-align: center;
  }

  .session-actions {
    display: flex;
    justify-content: flex-end;
    position: absolute;
    bottom: 300px; 
    right: 130px; 
}

.edit-session {
  margin-right: 10px; /* Add some space between buttons */
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
  