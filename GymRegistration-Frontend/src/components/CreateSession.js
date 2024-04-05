import axios from "axios"
import config from '../../config'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl}
})

function InstructorDto (email)
{
  this.email=email
  this.name=name
}

export default {
  name: 'CreateSession',
  data() {
    return {
      sessionDto: {
        date: '',
        time: '',
        instructorId: null,
        classTypeId: null,
        duration: '',
        capacity: null
      },

      iinstructorsDto: [], // the list of instructors

      classTypes:[],
    
    };
  },
  methods: {
    submitSession() {
      // ADD SCRIPT to handle the form submission
    },

    getInstructors() {
      // The URL should be the endpoint from your InstructorRestController that returns all instructors
      const url = '/instructors';
      
      axios.get(url)
        .then(response => {
         
          this.instructorsDto = response.data;
        })
        .catch(error => {
          console.error('There was an error getting the instructors:', error);
        });
    },

    getClassTypes() {
      // The URL should be the endpoint from your InstructorRestController that returns all instructors
      const url = '/class-types';
      
      axios.get(url)
        .then(response => {
         
          this.classTypes = response.data;
        })
        .catch(error => {
          console.error('There was an error getting the ClassTypes:', error);
        });
    },
    //other methods
  }
  // ADD SCRIPT 
}

  