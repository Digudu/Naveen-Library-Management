import { useEffect, useState } from "react"
import { deleteBookById, getAllAvailableBooks, getAllBooks } from "../backend/apis";
import { Link, useNavigate } from "react-router-dom";

export default function BookList(){
    const [books,setBooks]=useState([]);
    const navigate=useNavigate();
    useEffect(() => {
        return async () => {
          const result = await getAllBooks();
          console.log({ result });
          setBooks(result);
        };
      }, []);
      
      const handleDelete=async (id)=>{
        if(window.confirm("Are you sure you want to delete")){
        await deleteBookById(id);
        const response=  await getAllBooks();
        setBooks(response);
        }
        navigate("/")
    }
    return <div style={styles.tableContainer}>
            <h2 style={styles.tableTitle}>Books List</h2>
      <table className="table table-striped" style={styles.table}>
        <thead>
          <tr>
            <th style={styles.header}>ID</th>
            <th style={styles.header}>Title</th>
            <th style={styles.header}>Author</th>
            <th style={styles.header}>Type</th>
            <th style={styles.header}>Price</th>
            <th style={styles.header}>ISBN</th>
            <th style={styles.header}>Rating</th>
            <th style={styles.header}>Actions</th>
          </tr>
        </thead>
        <tbody>
          {books.map(book => (
            <tr key={book.id}>
              <td style={styles.cell}>{book.id}</td>
              <td style={styles.cell}>{book.title}</td>
              <td style={styles.cell}>{book.author}</td>
              <td style={styles.cell}>{book.type}</td>
              <td style={styles.cell}>{book.price}</td>
              <td style={styles.cell}>{book.isbn}</td>
              <td style={styles.cell}>{book.rating}</td>
              <td style={styles.cell}>
                <Link to={`/viewBook/${book.id}`} className="btn btn-primary">View</Link>
                <Link to={`/editBook/${book.id}`} className="btn btn-primary mx-2">Edit</Link>
                {/* <button  onClick={handleDelete(book.id)} className="btn btn-danger" style={styles.button}>Remove</button> */}
                <button 
                  onClick={() => handleDelete(book.id)} 
                  className="btn btn-danger" 
                  style={styles.button}
                >
                  Remove
                </button>
              </td>
            </tr>
          ))}
        </tbody>
        </table>
        <Link to={`/addBook`} className="btn btn-primary mx-2">Add Book</Link>
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