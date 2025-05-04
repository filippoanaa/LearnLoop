import logo from './logo.svg';
import './App.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import Navbar from './components/common/Navbar';
import LoginForm from './components/authentication/LoginForm';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SignUpStudentForm from './components/authentication/SignUpStudentForm';
import SignUpProfessorForm from './components/authentication/SignUpProfessorForm'
function App() {
  return (
   <Router>
      <Routes>
        <Route path="/login" element={<LoginForm/>}/>
        <Route path="/signup-student" element={<SignUpStudentForm />} />
        <Route path="/signup-professor" element={<SignUpProfessorForm />}/>

      </Routes>
   </Router>
  );
}

export default App;
