import { useEffect, useState } from "react"
import { deleteUserById, getAllUsers } from "../backend/apis";
import { Link, useNavigate } from "react-router-dom";

export default function UserList(){
    const [users,setUsers]=useState([]);
    const navigate=useNavigate();
    useEffect(() => {
        return async () => {
          const result = await getAllUsers();
          console.log({ result });
          setUsers(result);
        };
      }, []);
      const handleDelete=async (id)=>{
        if(window.confirm("Are you sure you want to delete")){
        await deleteUserById(id);
        const response=  await getAllUsers();
        setUsers(response);
        }
        navigate("/users")
    }
    return <div style={styles.tableContainer}>
            <h2 style={styles.tableTitle}>Users List</h2>
      <table className="table table-striped" style={styles.table}>
        <thead>
          <tr>
            <th style={styles.header}>ID</th>
            <th style={styles.header}>Name</th>
            <th style={styles.header}>Gender</th>
            <th style={styles.header}>DOB</th>
            <th style={styles.header}>Email</th>
            <th style={styles.header}>Mobile</th>
            <th style={styles.header}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.id}>
              <td style={styles.cell}>{user.id}</td>
              <td style={styles.cell}>{user.name}</td>
              <td style={styles.cell}>{user.gender}</td>
              <td style={styles.cell}>{user.dob}</td>
              <td style={styles.cell}>{user.email}</td>
              <td style={styles.cell}>{user.mobile}</td>
              <td style={styles.cell}>
                <Link to={`/viewUser/${user.id}`} className="btn btn-primary">View</Link>
                <Link to={`/editUser/${user.id}`} className="btn btn-primary mx-2">Edit</Link>
                <button onClick={() => handleDelete(user.id)}  className="btn btn-danger" style={styles.button}>Remove</button>
              </td>
            </tr>
          ))}
        </tbody>
        </table>
        <Link to={`/addUser`} className="btn btn-primary mx-2">Add User</Link>
    </div>
}
const styles = {
  tableContainer: {
    padding: '20px',
    backgroundColor: 'rgba(255, 255, 255, 0.8)',
    borderRadius: '8px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)'
  },
  tableTitle: {
    textAlign: 'center',
    marginBottom: '20px',
    color: '#333',
    fontSize: '24px',
    fontWeight: 'bold'
  },
  addButton: {
    marginTop: '10px',
    backgroundColor: '#4CAF50',
    borderColor: '#4CAF50',
    color: '#ffffff',
    display: 'block',
    margin: '0 auto' // Center the button
  },
  table: {
    width: '100%',
    textAlign: 'center'
  },
  header: {
    textAlign: 'center',
    fontWeight: 'bold',
    backgroundColor: '#f2f2f2'
  },
  cell: {
    textAlign: 'center',
    verticalAlign: 'middle'
  },
  button: {
    margin: '0 5px'
  }
};