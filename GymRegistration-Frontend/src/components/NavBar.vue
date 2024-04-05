<template>
    <div class="nav-bar">
      <nav :class="{ 'mobile-show': showNav }">
        <router-link exact to="/" @click="closeNav">
          <img class = "icon" src="@/assets/Home.png" alt="Home Logo" />
        </router-link>
        <router-link exact to="/class-types" class="classtypes-link" @click="closeNav">
          <img src="@/assets/ClassTypes.png" class = "icon" alt="ClassTypes Logo" />
        </router-link>
        <router-link exact to="/session" class="session-link" @click="closeNav">
          <img src="@/assets/Session.png" class = "icon" alt="Session Logo" />
        </router-link>
        <router-link exact to="/accounts" class="account-link" @click="closeNav">
          <img src="@/assets/Account.png" class = "icon" alt="Account Logo" />
        </router-link>
        <a class="button-link" target="_blank" rel="noopener noreferrer" @click="closeNav">
          SIGN IN / LOG OUT
        </a>
        <img src="@/assets/GymLogo.png" width="10px" alt="Gym Logo" class="close-icon" @click="closeNav" />
      </nav>
      <img @click="toggleNav" src="@/assets/GymLogo.png"  width="10px" alt="Gym Logo" class="hamburger-icon" />
    </div>
  </template>
  
  <script>
  
  export default {
    data() {
      return {
        showNav: false,
      }
    },
    methods: {
      closeNav() {
        this.showNav = false;
      },
      toggleNav() {
        this.showNav = !this.showNav;
      }
    }
  }
  </script>
  
  <style scoped>
  .nav-bar { 
      background: transparent; 
      width: 100%; 
      height: 130px; 
      position: fixed; 
      top: 0; 
      z-index: 3;  
      display: flex; 
      align-items: center;
      
      .logo{
          padding: 40px 40px; 
          
          img { 
              display: block; 
              margin: 3px auto; 
              margin-left: 15px;
              width: 80px; 
              height: auto;
              
              &.sub-logo { 
                  width: 150px; 
                  margin-left: -14px;
              }
          }
      }
      nav { 
          display: flex; 
          align-items: center;
          justify-content: space-evenly; 
          padding-left: 0;
          flex-grow: 1;
          a { 
              font-size: 25px; 
              color: #f0f0f083;
              display: block; 
              margin: 0 80px; 
              position: relative; 
              text-decoration: none; 
  
              i { 
                  transition: all 0.3s ease-out; 
              }
  
              &:hover { 
                  color: #fff;
                  
                  svg { 
                      opacity: 0; 
                  }
  
                  &:after { 
                      opacity: 1; 
                  }
              }
  
              &:after { 
                  content: ""; 
                  font-size: 20px; 
                  letter-spacing: 2px; 
                  position: absolute; 
                  bottom: 0; 
                  display: block; 
                  text-align: center; 
                  opacity: 0; 
                  transition: all 0.3s ease-out;
              }
  
              &:first-child { 
                  &::after { 
                      content: 'Home'; 
                      left: 23px;
                      top: -2px;
                  }
              }
          }
  
          a.classtypes-link { 
              &:after { 
                  content: "ClassTypes";
                  left: 5px;
                  top: -2px;
              }
          }
  
          a.session-link { 
              &:after { 
                  content: "Sessions"; 
                  left: 15px;
                  top: -2px;
              }
          }
          a.account-link {
              &:after {
                  content: "Accounts";
                  left: 12px;
                  top: -2px;
              }
          }
  
          a.active{
              svg{
                  color: #fff; 
              }
          }
          .button-link {
              border: none;
              color: #fff;
              background-image: linear-gradient(30deg, #0040ff, #15fd98);
              border-radius: 10px;
              background-size: 100% auto;
              font-family: inherit;
              font-size: 15px;
              padding: 0.6em 1.5em;
              white-space: nowrap;
          }
          .button-link:hover {
              background-position: right center;
              background-size: 200% auto;
              -webkit-animation: pulse 2s infinite;
              animation: pulse512 1.5s infinite;
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
      }
      .icon{
          width: 50px;
          margin: 30px;
          opacity: 0.8;
      }
  }
  
  .hamburger-icon, 
  .close-icon { 
      display: none;
  }
  @media screen and (max-width: 1150px){
      .nav-bar{
          position: initial; 
          height: 100px; 
          .logo{
              img { 
                  width: 50px; 
                  &.sub-logo { 
                      width: 100px; 
                      margin-left: -7px;
                  }
              }
          }
  
          nav{
              display: none;
              width: 100%; 
              height: 100%;
              background: #1a0408;
              top: 0; 
              left: 0; 
              position: fixed; 
              z-index: 2; 
              margin: 0;
  
              a { 
                  display: flex;
                  padding-left: 33%; 
                  margin: 30px;
              }
  
              a:after{
                  opacity: 1;
                  position: initial;
                  width: auto;
                  padding-left: 50px;;
              }
  
              a svg {
                  opacity: 1 !important;
              }
  
              &.mobile-show { 
                  display: block;
              }
              .button-link{
                  margin-top: 150px;
                  margin-left: 40%;
                  width: 100px;
              }
          }
          .hamburger-icon, 
          .close-icon {
              display: block;
              position: absolute; 
              top: 15px; 
              right: 15px;
              color: #f76d3f; 
          }
  
      }
    }
    
    @media screen and (max-width: 500px){
      .nav-bar{
          nav{
              a { 
                  padding-left: 22%; 
                  margin: 30px;
              }
              .button-link{
                  margin-left: 32%;
              }
          }
      }
  }
  </style>
  