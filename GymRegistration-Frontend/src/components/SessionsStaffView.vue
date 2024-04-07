<template>
  <div class="session-view-instructor">
    <div class="table-container">
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
            <td>{{ session.classType.name }}</td>
            <td>{{ session.capacity }}</td>
            <td>{{ session.startTime }}</td>
            <td>{{ session.endTime }}</td>
            <td>{{ session.date }}</td>
            <td>{{ session.instructor }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="pagination">
      Page <span>1</span> <span>2</span> <span>3</span> <span>4</span>
    </div>
    <div class="session-actions">
      <button>Edit Session</button>
      <button>Create New Session</button>
    </div>
  </div>
</template>

<script>
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
  name: 'SessionsStaffView',
  created() {
    this.getSessions();
  },
  data() {
    return {
      sessions: [],
      leadInstructors: []
    };
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
    }
  }
};
</script>

<style scoped>
.session-view-instructor {
  background-color: #fff;
  padding: 20px;
  overflow-y: auto;
}

.table-container {
  overflow-x: auto;
  overflow-y: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

table,
th,
td {
  border: 1px solid #ddd;
}

th,
td {
  text-align: left;
  padding: 8px;
}

th {
  background-color: #e74c3c;
  color: white;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}

.session-actions {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}

button {
  background-color: #e74c3c;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  opacity: 0.9;
}
</style>
