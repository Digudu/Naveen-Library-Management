import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getUserById } from "../backend/apis";

export default function ViewUser() {
  const { id } = useParams();
  const [user, setUser] = useState({});
  const navigate=useNavigate();
  useEffect(() => {
    return async () => {
      if (id) {
        const result = await getUserById(id);
        console.log({ result }, "venky");
        setUser(result);
      }
    };
  }, []);
  const handleBack = () => {
    navigate("/users");
  };
  return (
    <div className="container mt-5" style={styles.container}>
       <div className="d-flex justify-content-end mb-3">
          <button className="btn btn-outline-light" onClick={handleBack}>
            ← Back
          </button>
        </div>
      <h1 className="text-center text-white">User Details</h1>
      <div className="card p-4" style={styles.card}>
        <div className="mb-3 text-white">
          <strong>User ID:</strong> {user.id}
        </div>
        <div className="mb-3 text-white">
          <strong>Name:</strong> {user.name}
        </div>
        <div className="mb-3 text-white">
          <strong>Gender:</strong> {user.gender}
        </div>
        <div className="mb-3 text-white">
          <strong>Date of Birth:</strong>{" "}
          {new Date(user.dob).toLocaleDateString()}
        </div>
        <div className="mb-3 text-white">
          <strong>Email:</strong> {user.email}
        </div>
        <div className="mb-3 text-white">
          <strong>Phone Number:</strong> {user.mobile}
        </div>
        {user.address && (
          <div className="mb-3 text-white">
            <strong>Address:</strong>
            <AddressDetails address={user.address} />
          </div>
        )}
        {user.books && (
          <div className="mb-3">
            <strong>Books Lended:</strong>
            <BooksTable books={user.books} />
          </div>
        )}
      </div>
    </div>
  );
}
const AddressDetails = ({ address }) => (
  <div className="card mt-2">
    <div className="card-body">
      <p>
        <strong>Street:</strong> {address.street}
      </p>
      <p>
        <strong>City:</strong> {address.city}
      </p>
      <p>
        <strong>State:</strong> {address.state}
      </p>
      <p>
        <strong>Country:</strong> {address.country}
      </p>
      <p>
        <strong>Pin Code:</strong> {address.pincode}
      </p>
    </div>
  </div>
);

const BooksTable = ({ books }) => (
  <table className="table table-striped mt-3">
    <thead className="thead-light">
      <tr>
        <th>S.No.</th>
        <th>Book ID</th>
        <th>Title</th>
        <th>Author</th>
      </tr>
    </thead>
    <tbody>
      {books.map((book, index) => (
        <tr key={book.id}>
          <td>{index + 1}</td>
          <td>{book.id}</td>
          <td>{book.title}</td>
          <td>{book.author}</td>
        </tr>
      ))}
    </tbody>
  </table>
);
const styles = {
  container: {
    backgroundImage:
      "url(https://cdn.pixabay.com/photo/2015/11/19/21/10/glasses-1052010_1280.jpg)",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundAttachment: "fixed",
    padding: "20px",
    borderRadius: "10px",
  },
  card: {
    backgroundColor: "rgba(0, 0, 0, 0.7)",
    color: "white",
    border: "none",
    borderRadius: "10px",
  },
};
