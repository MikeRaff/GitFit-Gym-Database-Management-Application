<template>
    <div class="session-form">
      <h2>Create New Session</h2>
      <form @submit.prevent="submitSession">
        <div class="form-group">
          <label for="date">Date:</label>
          <input type="date" id="date" v-model="sessionDto.date">
        </div>
        <div class="form-group">
          <label for="time">Time:</label>
          <input type="time" id="time" v-model="sessionDto.time">
        </div>
        <div class="form-group">
          <label for="instructor">Instructor:</label>
          <select id="instructor" v-model="sessionDto.instructorId">
            <option disabled value="">Please select one</option>
           
            <!-- ADD SCRIPT to populate instructor -->
          </select>
        </div>
        <div class="form-group">
          <label for="classType">Class Type:</label>
          <select id="classType" v-model="sessionDto.classTypeId">
            <option disabled value="">Please select one</option>
            <
            <!-- ADD SCRIPT to populate class type  -->
          </select>
        </div>
        <div class="form-group">
          <label for="duration">Duration:</label>
          <input type="text" id="duration" v-model="sessionDto.duration">
        </div>
        <div class="form-group">
          <label for="capacity">Capacity:</label>
          <input type="number" id="capacity" v-model="sessionDto.capacity">
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  </template>
  

 <script src="./CreateSession.js">
</script>

  
  <style scoped>
  .session-form {
    background-color: #e74c3c; 
    padding: 20px;
    max-width: 500px; /* Adjust width as necessary */
    margin: 30px auto;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .session-form h2 {
    color: #fff;
    text-align: center;
    margin-bottom: 30px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  .form-group label {
    color: #fff;
    display: block;
    margin-bottom: 5px;
  }
  
  .form-group input,
  .form-group select {
    width: 100%;
    padding: 10px;
    border-radius: 4px;
    border: none;
    margin-bottom: 5px;
  }
  
  .form-group input[type="date"],
  .form-group input[type="time"],
  .form-group select {
    background-color: #fff;
    color: #333;
  }
  
  .form-group input[type="number"] {
    -moz-appearance: textfield; /* Removes spinner from input type number */
  }
  
  .form-group input[type="number"]::-webkit-outer-spin-button,
  .form-group input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none; /* Removes spinner from input type number for webkit browsers */
  }
  
  button {
    width: 100%;
    padding: 10px;
    border-radius: 4px;
    border: none;
    background-color: #c0392b; 
    color: #fff;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  button:hover {
    background-color: #a93226; /* Adjust for hover state */
  }
  
  button:active {
    background-color: #922b21; /* Adjust for active state */
  }
  
  button:focus {
    outline: none;
  }
  </style>
  