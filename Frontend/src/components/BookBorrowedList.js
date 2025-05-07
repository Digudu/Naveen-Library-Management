import { useEffect, useState } from "react";
import { getAllLendingBooks } from "../backend/apis";
import { useNavigate } from "react-router-dom";

const containerStyle = {
  maxWidth: "900px",
  margin: "0 auto",
  padding: "30px",
  borderRadius: "10px",
  boxShadow: "0 0 20px rgba(0, 0, 0, 0.2)",
  backgroundColor: "#f0f8ff",
};

const inputStyle = {
  fontSize: "16px",
  marginRight: "10px",
};

const buttonStyle = {
  padding: "10px 20px",
  fontSize: "16px",
  border: "none",
  backgroundColor: "#007bff",
  color: "#fff",
  borderRadius: "5px",
  cursor: "pointer",
};

const tableStyle = {
  width: "100%",
  borderCollapse: "collapse",
  marginTop: "20px",
};

const thStyle = {
  backgroundColor: "#007bff",
  color: "#fff",
  padding: "10px",
  border: "1px solid #ddd",
};

const tdStyle = {
  padding: "10px",
  border: "1px solid #ddd",
};
export default function BookBorrowedList() {
  const [lendingBooks, setLendingBooks] = useState([]);
   const navigate=useNavigate();
  useEffect(() => {
    return async () => {
      const result = await getAllLendingBooks();
      console.log({ result }, "venky");

      setLendingBooks(result);
    };
  }, []);
  const handleReturnClick = (id) => {
    console.log({id},"id")
    navigate(`/returnBook/${id}`); 
};

  return (
    <div className="container" style={containerStyle}>
      <h2>Borrowed Books List</h2>
      {/* <div style={{ marginBottom: '20px' }}>
            <input
                type="text"
                placeholder="Enter User ID"
                value={userId}
                onChange={(e) => setUserId(e.target.value)}
                style={inputStyle}
            />
            <button onClick={fetchBooks} style={buttonStyle}>Fetch Books</button>
        </div> */}
      {lendingBooks.length > 0 ? (
        <table style={tableStyle}>
          <thead>
            <tr>
              <th style={thStyle}>User ID</th>
              <th style={thStyle}>Book ID</th>
              <th style={thStyle}>Borrow Date</th>
              <th style={thStyle}>Due Date</th>
              <th style={thStyle}>Return Date</th>
              <th style={thStyle}>Issued By</th>
              <th style={thStyle}>Fee</th>
              <th style={thStyle}>Action</th>
            </tr>
          </thead>
          <tbody>
            {lendingBooks.map((book, index) => (
              <tr key={index}>
                <td style={tdStyle}>{book.userId}</td>
                <td style={tdStyle}>
                  {book.bookId.length > 0 ? book.bookId.join(", ") : "N/A"}
                </td>

                <td style={tdStyle}>{book.date}</td>
                <td style={tdStyle}>{book.dueDate}</td>
                <td style={tdStyle}>{book.returnDate ?? "Not Returned"}</td>
                <td style={tdStyle}>{book.issuedBy?.name ?? "N/A"}</td>
                <td style={tdStyle}>{book.fee ?? "0"}</td>
                <td style={tdStyle}>
                  <button onClick={() => handleReturnClick(book.id)} style={buttonStyle}>Return</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <div>No books found for this user.</div>
      )}
    </div>
  );
}
