import React, { useState } from 'react'



const Register = ({registerUser}) => {

    const [userData, setUserData] = useState({
        username: '',
        receivername: '',
        connected: false,
        message: ''
      });

    const handleUsername=(event)=>{
        const {value}=event.target;
        setUserData({...userData,"username": value});
    }

    const handleRegisterUser = (e) => {
        e.preventDefault();
        registerUser(userData);
    }

    return (
        <div className="register">
            <input
                id="user-name"
                placeholder="Enter your name"
                name="userName"
                value={userData.username}
                onChange={handleUsername}
                margin="normal"
              />
              <button type="button" onClick={handleRegisterUser}>
                    connect
              </button> 
        </div>
    )

}
export default Register;