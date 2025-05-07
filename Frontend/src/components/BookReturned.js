import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Select from 'react-select';
import { getBookLendingById, getAllBooks, updateBookLending } from '../backend/apis';

const formContainerStyle = {
  maxWidth: '900px',
  margin: '0 auto',
  padding: '40px',
  borderRadius: '10px',
  boxShadow: '0 0 20px rgba(0, 0, 0, 0.2)',
  backgroundColor: '#f0f8ff',
};

const formGroupStyle = {
  marginBottom: '20px',
};

const buttonStyle = {
  padding: '15px 30px',
  fontSize: '18px',
  border: 'none',
  backgroundColor: '#007bff',
  color: '#fff',
  borderRadius: '5px',
  cursor: 'pointer',
};

export default function BookReturned() {
  const { id } = useParams();
  const [bookLending, setBookLending] = useState(null);
  const [filteredBooks, setFilteredBooks] = useState([]);
  const [selectedBooks, setSelectedBooks] = useState([]);
  const [penalty, setPenalty] = useState(0);
  const [currentDate, setCurrentDate] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBookLending = async () => {
      try {
        const result = await getBookLendingById(id);
        setBookLending(result);

        // Set the current date
        const today = new Date().toISOString().split('T')[0];
        setCurrentDate(today);

        // Calculate penalty if return date is after due date
        const dueDate = new Date(result.dueDate);
        const returnDate = new Date(result.returnDate || today);
        if (returnDate > dueDate) {
          const daysLate = Math.ceil((returnDate - dueDate) / (1000 * 60 * 60 * 24));
          setPenalty(daysLate * 5);
        }

        // Fetch all books for filtering
        const allBooks = await getAllBooks();

        // Filter and set books that are part of the lending record
        const filteredBooksList = allBooks.filter(book => result.bookId.includes(book.id));
        setFilteredBooks(filteredBooksList);

        // Set the dropdown options with filtered books
        const options = filteredBooksList.map(book => ({
          value: book.id,
          label: book.title || `Book ${book.id}`,
        }));
        setSelectedBooks(options);
      } catch (error) {
        console.error('Error fetching book lending:', error);
      }
    };

    fetchBookLending();
  }, [id]);

  const handleBookChange = (selectedOptions) => {
    setSelectedBooks(selectedOptions || []);
  };

  const handleReturn = async () => {
    const selectedBookIds = selectedBooks.map(option => option.value);

    const updatedBookLending = {
      ...bookLending,
      bookId: selectedBookIds,
      returnDate: currentDate,
      penalty: penalty,
    };

    try {
      await updateBookLending(id, updatedBookLending);
      alert('Book lending record updated successfully!');
      navigate(`/viewUser/${bookLending.userId}`);
    } catch (error) {
      console.error('Error updating book lending:', error);
      alert('Failed to update book lending record.');
    }
  };

  if (!bookLending) return <div>Loading...</div>;

  return (
    <div style={formContainerStyle}>
      <h2>Book Returned</h2>
      <div style={formGroupStyle}>
        <label>User ID:</label>
        <div>{bookLending.userId}</div>
      </div>
      <div style={formGroupStyle}>
        <label>Book IDs:</label>
        <Select
          isMulti
          value={selectedBooks}
          onChange={handleBookChange}
          options={filteredBooks.map(book => ({
            value: book.id,
            label: book.title || `Book ${book.id}`,
          }))}
          placeholder="Select books..."
          styles={{
            container: (provided) => ({
              ...provided,
              marginTop: '8px',
              fontSize: '16px',
            }),
            menu: (provided) => ({
              ...provided,
              zIndex: 9999,
            }),
          }}
        />
      </div>
      <div style={formGroupStyle}>
        <label>Borrow Date:</label>
        <div>{bookLending.date}</div>
      </div>
      <div style={formGroupStyle}>
        <label>Due Date:</label>
        <div>{bookLending.dueDate}</div>
      </div>
      <div style={formGroupStyle}>
        <label>Return Date:</label>
        <div>{currentDate}</div>
      </div>
      <div style={formGroupStyle}>
        <label>Penalty:</label>
        <div>{penalty}</div>
      </div>
      <button onClick={handleReturn} style={buttonStyle}>Confirm Return</button>
    </div>
  );
}
