import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const EditUser = () => {
  const { id } = useParams();
  console.log({ id });
  const [user, setUser] = useState({ address: {} });
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const result = await axios.get(`http://localhost:8080/api/users/${id}`);
        setUser(result.data);
      } catch (error) {
        console.error("Error fetching user data", error);
      }
    };

    if (id) {
      fetchUser();
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser((prevUser) => ({
      ...prevUser,
      [name]: value,
    }));
  };

  const handleAddressChange = (e) => {
    const { name, value } = e.target;
    setUser((prevUser) => ({
      ...prevUser,
      address: {
        ...prevUser.address,
        [name]: value,
      },
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/api/users/${id}`, user);
      alert("User updated successfully");
      navigate("/users"); // Redirect to the users list or any other page
    } catch (error) {
      console.error("Error updating user", error);
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.heading}>Edit User</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <div style={styles.formGroup}>
          <label style={styles.label}>ID:</label>
          <input
            type="number"
            name="id"
            value={user.id || ""}
            required
            style={styles.input}
            disabled
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Name:</label>
          <input
            type="text"
            name="name"
            value={user.name || ""}
            onChange={handleChange}
            required
            style={styles.input}
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Gender:</label>
          <select
            name="gender"
            value={user.gender || ""}
            onChange={handleChange}
            required
            style={styles.input}
          >
            <option value="">Select</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </select>
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Date of Birth:</label>
          <input
            type="date"
            name="dob"
            value={user.dob || ""}
            onChange={handleChange}
            required
            style={styles.input}
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Email:</label>
          <input
            type="email"
            name="email"
            value={user.email || ""}
            onChange={handleChange}
            required
            style={styles.input}
            pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$"
            title="Please enter a valid email address"
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Mobile:</label>
          <input
            type="text"
            name="mobile"
            value={user.mobile || ""}
            onChange={handleChange}
            required
            style={styles.input}
            pattern="[6-9]\d{9}"
            title="Mobile number must be exactly 10 digits and start with 6, 7, 8, or 9"
          />
        </div>
        <h3>Address</h3>
        <div style={styles.formGroup}>
          <label style={styles.label}>Street:</label>
          <input
            type="text"
            name="street"
            value={user.address.street || ""}
            onChange={handleAddressChange}
            required
            style={styles.input}
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>City:</label>
          <input
            type="text"
            name="city"
            value={user.address.city || ""}
            onChange={handleAddressChange}
            required
            style={styles.input}
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>State:</label>
          <input
            type="text"
            name="state"
            value={user.address.state || ""}
            onChange={handleAddressChange}
            required
            style={styles.input}
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Country:</label>
          <input
            type="text"
            name="country"
            value={user.address.country || ""}
            onChange={handleAddressChange}
            required
            style={styles.input}
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Pincode:</label>
          <input
            type="text"
            name="pincode"
            value={user.address.pincode || ""}
            pattern="\d{6}"
            title="Pincode must be exactly 6 digits"
            onChange={handleAddressChange}
            required
            style={styles.input}
          />
        </div>
        <div style={styles.buttonContainer}>
          <button type="submit" style={styles.submitButton}>
            Save Changes
          </button>
          <button
            type="button"
            style={styles.cancelButton}
            onClick={() => navigate("/users")}
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

const styles = {
  container: {
    maxWidth: "600px",
    margin: "20px auto",
    padding: "20px",
    borderRadius: "8px",
    backgroundColor: "#ffffff",
    boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
  },
  heading: {
    textAlign: "center",
    marginBottom: "20px",
    color: "#4CAF50",
  },
  form: {
    display: "flex",
    flexDirection: "column",
  },
  formGroup: {
    marginBottom: "15px",
  },
  label: {
    display: "block",
    marginBottom: "5px",
    color: "#333",
  },
  input: {
    width: "100%",
    padding: "10px",
    fontSize: "16px",
    borderRadius: "4px",
    border: "1px solid #ccc",
  },
  buttonContainer: {
    display: "flex",
    justifyContent: "space-between",
  },
  submitButton: {
    backgroundColor: "#4CAF50",
    color: "#fff",
    border: "none",
    padding: "10px 20px",
    borderRadius: "5px",
    cursor: "pointer",
    fontSize: "16px",
  },
  cancelButton: {
    backgroundColor: "#f44336",
    color: "#fff",
    border: "none",
    padding: "10px 20px",
    borderRadius: "5px",
    cursor: "pointer",
    fontSize: "16px",
  },
};

export default EditUser;
