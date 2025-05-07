import { useState, useEffect } from 'react';
import { createBrowserRouter, RouterProvider, Route, Navigate } from 'react-router-dom';
import BookList from "./components/BookList";
import "bootstrap/dist/css/bootstrap.min.css";
import RootLayout from "./components/Rootlayout";
import UserList from "./components/UserList";
import AddBook from "./components/AddBook";
import ViewBook from "./components/ViewBook";
import ViewUser from "./components/ViewUser";
import EditUser from "./components/EditUser";
import AddUser from "./components/AddUser";
import EditBook from "./components/EditBook";
import Login from "./components/Login";
import BookBorrowed from './components/BookBorrowed';
import BookReturned from './components/BookReturned';
import BookBorrowedList from './components/BookBorrowedList';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    // Check if the librarian object is present in localStorage
    const storedLibrarian = localStorage.getItem('librarian');
    if (storedLibrarian) {
      setIsAuthenticated(true);
    }
  }, []); // Empty dependency array ensures this runs only on initial mount

  const handleLogin = () => {
    setIsAuthenticated(true);
  };

  const router = createBrowserRouter([
    {
      path: "/",
      element: isAuthenticated ? <RootLayout /> : <Navigate to="/login" />,
      children: [
        { path: "/", element: <BookList /> },
        { path: "/users", element: <UserList /> },
        { path: "/addBook", element: <AddBook /> },
        { path: "/viewBook/:id", element: <ViewBook /> },
        { path: "/viewUser/:id", element: <ViewUser /> },
        { path: "/editUser/:id", element: <EditUser /> },
        { path: "/addUser", element: <AddUser /> },
        { path: "/editBook/:id", element: <EditBook /> },
        { path: "/borrowBook", element: <BookBorrowed /> },
        { path: "/returnBook/:id", element: <BookReturned /> },
        { path: "/borrowedBookList", element: <BookBorrowedList /> }
      ],
    },
    {
      path: "/login",
      element: <Login onLogin={handleLogin} />,
    },
  ]);

  return (
    <div>
      <RouterProvider router={router} />
    </div>
  );
}

export default App;
