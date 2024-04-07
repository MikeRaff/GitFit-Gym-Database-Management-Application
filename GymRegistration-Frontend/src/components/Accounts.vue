<template>
  <div class="accounts-page">
    <Navbar /> <!-- Added navbar component here -->
    <div class="accounts-table-container">
      <h1>
        <AnimatedLetters :letterClass="letterClass" :strArray="welcomeArray" :idx="14" />
      </h1>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="account in accounts" :key="account.id">
            <td>{{ account.id }}</td>
            <td>{{ account.name }}</td>
            <td>{{ account.email }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="pagination">
      Page <span>1</span> <span>2</span> <span>3</span> <span>4</span>
    </div>
    <div class="delete-button">
      <button>Delete</button>
    </div>
  </div>
</template>

<script>
import AnimatedLetters from "./AnimatedLetters";
import Navbar from "./Navbar"; 

export default {
  name: 'Accounts',
  data() {
    return {
      letterClass: "text-animate",
      welcomeArray: "Accounts",
      accounts: [], // Initialize accounts data
    };
  },
  mounted() {
    setTimeout(() => {
      this.letterClass = "text-animate-hover";
    }, 4000);
    this.fetchAccounts(); // Fetch accounts data when component is mounted
  },
  components: {
    AnimatedLetters,
    Navbar
  },
  methods: {
    fetchAccounts() {
      // Here you can fetch the accounts data from the database using API calls or other methods
      // For now, let's simulate some sample accounts data
      this.accounts = [
        { id: 1, name: "John Doe", email: "john@example.com" },
        { id: 2, name: "Jane Smith", email: "jane@example.com" },
        { id: 3, name: "John ", email: "john1@example.com" },
        { id: 4, name: "Jane ", email: "jane2@example.com" },
      ];
    },
    deleteAccount() {
      // Implement logic to delete an account
    }
  }
};
</script>

<style scoped>
.accounts-page .text-zone {
  position: absolute;
  align-content: center;
  left: 10%;
  top: 50%;
  transform: translateY(-50%);
  width: 40%;
  max-height: 90%;
  display: absolute;
}
.accounts-page {
  padding: 20px;
  margin: auto;
}
.accounts-page h1 {
  top: 40%; /* Adjust distance below navbar */
  color: #fff;
  font-size: 60px;
  margin: 0;
  font-weight: 600;
  cursor: pointer;
  text-align: center;
}
.content {
  position: relative;
}

.accounts-table-container {
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
  padding: 10px;
} 

tr:nth-child(even) {
  background-color: #f2f2f27f;
}
tr:nth-child(odd) {
  background-color: #f2f2f2c8;
}

.pagination {
  text-align: center;
  margin-bottom: 20px;
}

.delete-button {
  display: flex;
  justify-content: flex-end;
  position: absolute;
  bottom: 300px; 
  right: 130px; 
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
