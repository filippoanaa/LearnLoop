import './App.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import Navbar from './components/common/Navbar';
import LoginForm from './components/authentication/LoginForm';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SignUpStudentForm from './components/authentication/SignUpStudentForm';
import SignUpProfessorForm from './components/authentication/SignUpProfessorForm'
import CourseForm from './components/professorAccount/CourseForm';
import ProfessorCourses from './components/professorAccount/ProfessorCourses';
import CourseDetails from './components/common/CourseDetails';
import StudentCourses from './components/studentAccount/StudentCourses'
function App() {
  return (
   <Router>
      <Routes>
        <Route path="/login" element={<LoginForm/>}/>
        <Route path="/signup-student" element={<SignUpStudentForm />} />
        <Route path="/signup-professor" element={<SignUpProfessorForm />}/>

        <Route path="/account/:role/:userId/courses/:courseId" element={<CourseDetails />} />

        <Route path= "/account/professor/:professorId/courses/:courseId/edit-course" element={<CourseForm/>} />
        <Route path="/account/professor/:professorId/courses/add-course" element={<CourseForm />} />

        <Route path = "/account/professor/:professorId/courses" element = {<ProfessorCourses/>}/>

        //!!nu merge formul de adaugare curs
        <Route path="/account/student/:studentId/courses" element={<StudentCourses/>} />
      </Routes>
   </Router>
  );
}

export default App;
