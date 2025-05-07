import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getBookById, updateBook } from "../backend/apis";

export default function EditBook(){
    const { id } = useParams();
    const [book, setBook] = useState({
        title: '',
        author: '',
        type: '',
        price: '',
        isbn: '',
        rating: ''
    });

    const navigate = useNavigate();

    useEffect(() => {
        return async () => {
            const result=await getBookById(id);
            setBook(result)
        };
      }, []);

    const handleChange = (e) => {
        setBook({ ...book, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const newBook={
            ...book
        }
        await updateBook(id,newBook)
        navigate('/');
    };

    const styles = {
        container: {
            backgroundColor: '#b5d9e5',
            borderRadius: '8px',
            boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
            maxWidth: '600px',
            margin: '20px auto',
            padding: '20px'
        },
        heading: {
            textAlign: 'center',
            color: '#333',
            marginBottom: '20px'
        },
        form: {
            display: 'flex',
            flexDirection: 'column'
        },
        label: {
            fontWeight: 'bold',
            marginBottom: '5px',
            color: '#333'
        },
        input: {
            padding: '10px',
            border: '1px solid #ddd',
            borderRadius: '4px',
            marginBottom: '15px',
            fontSize: '16px',
            width: '100%',
            boxSizing: 'border-box'
        },
        button: {
            backgroundColor: '#007bff',
            color: '#fff',
            border: 'none',
            borderRadius: '4px',
            padding: '10px 20px',
            fontSize: '16px',
            cursor: 'pointer',
            marginTop: '10px',
            transition: 'background-color 0.3s',
            alignSelf: 'flex-start'
        },
        cancelButton: {
            backgroundColor: '#dc3545'
        },
        buttonHover: {
            backgroundColor: '#0056b3'
        },
        cancelButtonHover: {
            backgroundColor: '#c82333'
        }
    };
    return (
        <div style={styles.container}>
            <h2 style={styles.heading}>Edit Book</h2>
            <form style={styles.form} onSubmit={handleSubmit}>
                <div>
                    <label style={styles.label}>Title:</label>
                    <input name="title" value={book.title} onChange={handleChange} required style={styles.input} />
                </div>
                <div>
                    <label style={styles.label}>Author:</label>
                    <input name="author" value={book.author} onChange={handleChange} required style={styles.input} />
                </div>
                <div>
                    <label style={styles.label}>Type:</label>
                    <input name="type" value={book.type} onChange={handleChange} required style={styles.input} />
                </div>
                <div>
                    <label style={styles.label}>Price:</label>
                    <input name="price" value={book.price} onChange={handleChange} type="number" required style={styles.input} />
                </div>
                <div>
                    <label style={styles.label}>ISBN:</label>
                    <input name="isbn" value={book.isbn} onChange={handleChange} required style={styles.input} />
                </div>
                <div>
                    <label style={styles.label}>Rating:</label>
                    <input name="rating" value={book.rating} onChange={handleChange} type="number" min="1" max="5" required style={styles.input} />
                </div>
                <button
                    type="submit"
                    style={{ ...styles.button, ...(id && styles.buttonHover) }}
                >
                    Save
                </button>
                <button
                    type="button"
                    onClick={() => navigate('/')}
                    style={{ ...styles.button, ...styles.cancelButton, ...(id && styles.cancelButtonHover) }}
                >
                    Cancel
                </button>
            </form>
        </div>
    );
};
