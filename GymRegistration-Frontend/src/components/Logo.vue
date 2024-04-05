<template>
    <div class="logo-container">
      <img class="solid-logo animate" src="@/assets/GymLogo.png" alt="Gym Logo" />
    </div>
  </template>
  
  <script>
  export default {
    name: 'Logo'
  }
  </script>
  
  <style scoped>
.logo-container {
    z-index: 0;
    width: 500px;
    height: 500px;
    opacity: 1;
    position: absolute;
    top: 0;
    right: 15%;
    bottom: 0;
    left: 50%;
    margin: auto;
    animation: fadeIn 2s linear both; 
    animation-delay: 1.4s; 
    &.animate {
      opacity: 1;
    }
  
    .solid-logo {
      position: absolute;
      top: auto;
      right: auto;
      bottom: auto;
      left: 200px;
      margin: auto;
      width: 80%;
      opacity: 0;
      z-index: 1;
  
      &.animate {
        opacity: 1;
      }
    }
  }
  @media screen and (max-width: 1150px) {
    .logo-container{
      .solid-logo{
        left: 0;
        top: 80px;
      }
    }
  }
  </style>