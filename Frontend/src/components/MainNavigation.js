import { Link, useNavigate } from "react-router-dom";
import { FaSignOutAlt } from "react-icons/fa";

export default function MainNavigation() {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Clear the librarian object from localStorage
    localStorage.removeItem('librarian');

    // Navigate to the login page
    navigate('/login');
  };

  return (
    <nav className="navbar navbar-expand navbar-dark bg-dark">
      <div className="container">
        <a href="#" className="navbar-brand">
          Library Management
        </a>
        <ul className="navbar-nav">
          <li className="nav-item">
            <Link to="/borrowBook" className="nav-link">Borrow Book</Link>
          </li>
          <li className="nav-item">
            <Link to="/borrowedBookList" className="nav-link">Borrowed Book List</Link>
          </li>
          <li className="nav-item">
            <Link to="/" className="nav-link">List Books</Link>
          </li>
          <li className="nav-item">
            <Link to="/users" className="nav-link">List Users</Link>
          </li>
          <li className="nav-item ms-auto">
            <button 
              className="btn btn-link nav-link text-light" 
              onClick={handleLogout} 
              style={{ fontSize: '1.0rem', border: 'none', background: 'none' }}
            >
              <FaSignOutAlt />
            </button>
          </li>
        </ul>
      </div>
    </nav>
  );
}
