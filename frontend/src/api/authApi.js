import api from "./axios"
//group all auth related calls together
const authApi={
    //take only what the backend expects
    login:async(email,password)=>{
        //The followiing uses post to login, there is no token that exists yet as this is login. It then uses baseURL from axios.js
        //no headers added
        const response=await api.post("/auth/login",{
            email,
            password
        });
        //we extract the token from the response of backend
        const token=response.data.token;
        localStorage.setItem("token",token);
        return token;
    },
    logout:()=>{
        localStorage.removeItem("token");
     
    }
};
export default authApi;

/*Auth api.js only talks to api/auth/** and handles the authentication specific logic.
It needs to send login credentials , a post request 
Receive a JWT and extract the token from the response
return control*/