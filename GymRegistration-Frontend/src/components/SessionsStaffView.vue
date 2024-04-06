<template>
    <div class="session-view-instructor">
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
      <div class="session-actions">
        <button>Edit Session</button>
        <button>Create New Session</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'SessionsStaffView',
    data() {
      return {
        sessions: [
          // Session data placeholder
        ],
      };
    },
    //NEED TO ADD METHOD TO MOVE PAGES
  };
  </script>
  
  <style scoped>
  .session-view-instructor {
    background-color: #fff;
    padding: 20px;
  }
  
  .table-container {
    overflow-x: auto;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  table, th, td {
    border: 1px solid #ddd;
  }
  
  th, td {
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
  