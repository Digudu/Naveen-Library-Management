import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getBookById } from "../backend/apis";

export default function ViewBook() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState({
    id: "",
    title: "",
    author: "",
    type: "",
    price: "",
    isbn: "",
    rating: "",
  });

  useEffect(() => {
    return async () => {
      if (id) {
        const result = await getBookById(id);
        console.log({ result });
        setBook(result);
      }
    };
  }, [id]);

  const handleBack = () => {
    navigate("/");
  };

  return (
    <div style={styles.page}>
      <div className="container" style={styles.container}>
        <div className="d-flex justify-content-end mb-3">
          <button className="btn btn-outline-dark" onClick={handleBack}>
            ← Back
          </button>
        </div>
        <div className="card" style={styles.card}>
          <div className="card-body">
            <h1 className="card-title text-center">Book Details</h1>
            <div className="list-group">
              <div className="list-group-item">
                <strong>ID:</strong> {book.id}
              </div>
              <div className="list-group-item">
                <strong>Title:</strong> {book.title}
              </div>
              <div className="list-group-item">
                <strong>Author:</strong> {book.author}
              </div>
              <div className="list-group-item">
                <strong>Type:</strong> {book.type}
              </div>
              <div className="list-group-item">
                <strong>Price:</strong> Rs{book.price}
              </div>
              <div className="list-group-item">
                <strong>ISBN:</strong> {book.isbn}
              </div>
              <div className="list-group-item">
                <strong>Rating:</strong> {book.rating}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

const styles = {
  page: {
    backgroundImage:
      "url(https://img.freepik.com/premium-photo/many-old-books-wooden-background-source-information-open-book-indoor-home-library-knowledge-is-power_157947-1809.jpg?w=740)",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundAttachment: "fixed",
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  },
  container: {
    backgroundColor: "rgba(255, 255, 255, 0.8)",
    backdropFilter: "blur(10px)",
    padding: "20px",
    borderRadius: "10px",
    width: "100%",
    maxWidth: "600px", // Adjust the maxWidth as needed
  },
  card: {
    backgroundColor: "rgba(255, 255, 255, 0.8)",
    backdropFilter: "blur(10px)",
    boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
  },
};
