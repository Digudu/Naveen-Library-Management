import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = ({ onLogin }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', {
        email,
        password,
      });

      if (response.status === 200) {
        const librarian = response.data;
        console.log('Logged in librarian:', librarian);

       
        localStorage.setItem('librarian', JSON.stringify(librarian));

        onLogin(); 
        navigate('/'); 
      } else {
        setError('Invalid email or password');
      }
    } catch (error) {
      setError('Invalid email or password');
      console.error('Login error:', error);
    }
  };

  return (
    <div
      className="d-flex justify-content-center align-items-center vh-100"
      style={{
        backgroundImage: 'url(https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEggTu7DsNwN06djRJalUksB-4Ev7Od5gqiLK3tGUianJl7nbaRoUcOpxpOSlfVMmt4l2bBQejcsUfEqP_AJp0j_fZDzXftR7fz_wZ2-SBFA1NaaFVBSVQAyMZR6NJO14iRApHY7mW5NYbg/s1600/Library.png)',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
      }}
    >
      <form
        className="p-5 rounded shadow"
        style={{ backgroundColor: 'rgba(255, 255, 255, 0.8)', width: '300px' }}
        onSubmit={handleSubmit}
      >
        <h3 className="text-center mb-4">Sign In</h3>
        {error && <div className="alert alert-danger">{error}</div>}
        <div className="form-group mb-3">
          <label>Email address</label>
          <input
            type="email"
            className="form-control"
            placeholder="Enter email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group mb-3">
          <label>Password</label>
          <input
            type="password"
            className="form-control"
            placeholder="Enter password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary w-100">Submit</button>
      </form>
    </div>
  );
};

export default Login;
