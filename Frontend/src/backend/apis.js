import axios from "axios";


async function getAllAvailableBooks() {
  const response = await axios.get("http://localhost:8080/api/books");
  return response.data;
}

async function getAllBooks() {
  const response = await axios.get("http://localhost:8080/api/books/all");
  return response.data;
}

async function addBook(book) {
  const response = await axios.post("http://localhost:8080/api/books", book);
  return response.data;
}

async function getBookById(id) {
  const response = await axios.get(`http://localhost:8080/api/books/${id}`);
  return response.data;
}

async function updateBook(id, book) {
  const response = await axios.put(`http://localhost:8080/api/books/${id}`, book);
  return response.data;
}

async function deleteBookById(id) {
  await axios.delete(`http://localhost:8080/api/books/${id}`);
}

async function getAllUsers() {
  const response = await axios.get("http://localhost:8080/api/users");
  return response.data;
}

async function addUser(user) {
  const response = await axios.post("http://localhost:8080/api/users", user);
  return response.data;
}

async function addAddress(address) {
  const response = await axios.post("http://localhost:8080/api/address", address);
  return response.data;
}

async function editAddress(id, address) {
  const response = await axios.put(`http://localhost:8080/api/address/${id}`, address);
  return response.data;
}

async function deleteAddress(id) {
  await axios.delete(`http://localhost:8080/api/address/${id}`);
}

async function getUserById(id) {
  const response = await axios.get(`http://localhost:8080/api/users/${id}`);
  return response.data;
}

async function updateUser(id, user) {
  const response = await axios.put(`http://localhost:8080/api/users/${id}`, user);
  return response.data;
}

async function deleteUserById(id) {
  await axios.delete(`http://localhost:8080/api/users/${id}`);
}

async function addBookLending(bookLending) {
  await axios.post("http://localhost:8080/api/bookLending", bookLending);
}

async function updateBookLending(id, bookLending) {
 const response= await axios.put(`http://localhost:8080/api/bookLending/${id}`, bookLending);
  return response.data;
}
async function getAllLendingBooks(){
  const response=await axios.get("http://localhost:8080/api/bookLending");
  return response.data;
}

async function getBookLendingById(id){
  console.log(id,"in api")
  const response=await axios.get(`http://localhost:8080/api/bookLending/${id}`);
  console.log({response});
  console.log(response.data)
  return response.data;
}

export {
  getAllAvailableBooks, addBook, getBookById, updateBook, deleteBookById,
  getAllUsers, addUser, getUserById, updateUser, deleteUserById,getAllBooks,
  addAddress, editAddress, deleteAddress, addBookLending, updateBookLending,getAllLendingBooks,getBookLendingById
};
