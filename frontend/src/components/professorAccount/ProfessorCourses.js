import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import CoursesList from '../common/CoursesList';
import CourseForm from './CourseForm';
import { Button } from 'react-bootstrap';
const ProfessorCourses = () => {
    const { role, professorId } = useParams();
    const [courses, setCourses] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await fetch(`http://localhost:8080/professors/${professorId}/courses`);
                if (response.ok) {
                    const data = await response.json();
                    setCourses(data);
                } else {
                    setError('Failed to fetch courses.');
                }
            } catch (err) {
                setError('Network error or server not responding.');
            }
        };

        if (professorId) {
            fetchCourses();
        }
    }, [professorId]);

    const handleOnCourseAction = async (courseAction, courseId) => {
        var url = '';

        if(courseAction === 'update' || courseAction === 'add'){
            navigate(`/account/${role}/${professorId}/courses/${courseId}/courseForm`);
        }
        else{
            url = `http://localhost:8080/professors/${professorId}/courses/${courseId}`;
        }
        try{

        
            const response = await fetch(url, {
                method: 'DELETE',
                headers: {'Content-Type' : 'application/json'},
            });

            if(response.ok){
                setCourses(courses.filter((course) => course.id !== courseId));

            }else {
                setError('Failed to delete the course.');
            }
        }catch(err){
            setError('Failed to delete the course. Network error or server not responding.');

        }


        

    }
    return (
        <div>
            <h1>Your Courses</h1>

            {error && <p style={{ color: 'red' }}>{error}</p>}
            
                <Link to={`/account/${role}/${professorId}/courses/courseForm`}>
                    <Button variant="success" size="lg">
                        Add new course
                    </Button>
                </Link>
               
            <CoursesList courses={courses} role="professor" userId={professorId} coursesType="" onCourseAction={handleOnCourseAction} />
        </div>
    );
};

export default ProfessorCourses;