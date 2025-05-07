import React, { useEffect, useState } from "react";
import Select from "react-select";
import {
  addBookLending,
  getAllAvailableBooks,
  getAllBooks,
} from "../backend/apis";
import { useNavigate } from "react-router-dom";

const formContainerStyle = {
  maxWidth: "900px",
  margin: "0 auto",
  padding: "40px",
  borderRadius: "10px",
  boxShadow: "0 0 20px rgba(0, 0, 0, 0.2)",
  backgroundColor: "#f0f8ff",
};

const formGroupStyle = {
  marginBottom: "20px",
};

const buttonStyle = {
  padding: "15px 30px",
  fontSize: "18px",
  border: "none",
  backgroundColor: "#007bff",
  color: "#fff",
  borderRadius: "5px",
  cursor: "pointer",
};

export default function BookBorrowed() {
  const [userId, setUserId] = useState("");
  const [bookId, setBookId] = useState([]);
  const [dueDate, setDueDate] = useState("");
  const [fee, setFee] = useState("");
  const [message, setMessage] = useState("");
  const [books, setBooks] = useState([]);
  const navigate = useNavigate();

  const MAX_SELECTION = 3;

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const result = await getAllAvailableBooks();
        setBooks(result);
      } catch (error) {
        console.error("Error fetching books:", error);
      }
    };
    fetchBooks();
    const today = new Date().toISOString().split("T")[0];
    document.getElementById("dueDate").setAttribute("min", today);
  }, []);

  const bookOptions = books.map((book, index) => ({
    value: book.id,
    label: book.title || `Book ${index + 1}`,
  }));

  const handleBookChange = (selectedOptions) => {
    if (selectedOptions.length <= MAX_SELECTION) {
      setBookId(
        selectedOptions ? selectedOptions.map((option) => option.value) : []
      );
    } else {
      alert(`You can only select up to ${MAX_SELECTION} books.`);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Retrieve the librarian from localStorage
    const librarian = JSON.parse(localStorage.getItem("librarian"));
    console.log({ librarian });

    if (!librarian) {
      setMessage("No librarian is logged in.");
      return;
    }

    const newBookLending = {
      userId,
      bookId,
      dueDate,
      fee,
      issuedBy: librarian, // Add the logged-in librarian's details
    };
    console.log({ newBookLending });
    try {
      await addBookLending(newBookLending);
      setMessage("Books borrowed successfully!");
      navigate(`/viewUser/${userId}`);
    } catch (error) {
      console.error("Error adding book lending:", error);
      setMessage("Failed to borrow books.");
    }

    setUserId("");
    setBookId([]);
    setDueDate("");
    setFee("");
  };

  return (
    <div
      className="container"
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
      }}
    >
      <div style={formContainerStyle}>
        <h2>Borrow Books</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group" style={formGroupStyle}>
            <label htmlFor="userId">User ID:</label>
            <input
              type="text"
              className="form-control"
              id="userId"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
              style={{ fontSize: "16px" }}
              required
            />
          </div>
          <div className="form-group" style={formGroupStyle}>
            <label htmlFor="bookId">Book IDs:</label>
            <Select
              id="bookId"
              isMulti
              value={bookOptions.filter((option) =>
                bookId.includes(option.value)
              )}
              onChange={handleBookChange}
              options={bookOptions}
              placeholder="Select books..."
              styles={{
                container: (provided) => ({
                  ...provided,
                  marginTop: "8px",
                  fontSize: "16px",
                }),
                menu: (provided) => ({
                  ...provided,
                  zIndex: 9999,
                }),
              }}
            />
          </div>
          <div className="form-group" style={formGroupStyle}>
            <label htmlFor="dueDate">Due Date:</label>
            <input
              type="date"
              className="form-control"
              id="dueDate"
              value={dueDate}
              onChange={(e) => setDueDate(e.target.value)}
              style={{ fontSize: "16px" }}
            />
          </div>
          <div className="form-group" style={formGroupStyle}>
            <label htmlFor="fee">Fee:</label>
            <input
              type="number"
              className="form-control"
              id="fee"
              value={fee}
              onChange={(e) => setFee(e.target.value)}
              style={{ fontSize: "16px" }}
              required
            />
          </div>
          <button type="submit" style={buttonStyle}>
            Borrow
          </button>
        </form>
        {message && (
          <div
            style={{
              marginTop: "20px",
              color: message.startsWith("Failed") ? "red" : "green",
              fontSize: "18px",
            }}
          >
            {message}
          </div>
        )}
      </div>
    </div>
  );
}
