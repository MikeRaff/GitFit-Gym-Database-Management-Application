<template>
    <div class="sessions-client-view">
      <div class="table-container">
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
      <div class="register-button">
        <button>Register</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'SessionsClientView',
    data() {
      return {
        // Placeholder array for sessions data
        sessions: [],
      };
    },
    // Additional methods to fetch and handle sessions data would be added here
  };
  </script>
  
  <style scoped>
  .sessions-client-view {
    background-color: #fff; /* Use the color that matches the image */
    padding: 20px;
    margin: auto;
    width: 80%;
    max-width: 1000px;
  }
  
  .table-container {
    overflow-x: auto;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
  }
  
  table, th, td {
    border: 1px solid #ddd;
  }
  
  th {
    background-color: #e74c3c;
    color: white;
    padding: 10px;
  }
  
  td {
    padding: 10px;
    text-align: center;
  }
  
  tr:nth-child(even) {
    background-color: #f2f2f2;
  }
  
  .pagination {
    text-align: center;
    margin-bottom: 20px;
  }
  
  .register-button {
    display: flex;
    justify-content: flex-end;
  }
  
  button {
    padding: 10px 20px;
    background-color: #e74c3c;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button:hover {
    opacity: 0.9;
  }
  </style>
  