import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import StudentAccountTabs from './StudentAccountTabs';

const StudentCourses = () => {
    const { role, studentId} = useParams();
    const [enrolledCourses, setEnrolledCourses] = useState([]);
    const [availableCourses, setAvailableCourses] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchEnrolledCourses = async () => {
            try {
                const response = await fetch(`http://localhost:8080/students/${studentId}/courses?coursesType=enrolledCourses`);
                if (response.ok) {
                    const data = await response.json();
                    setEnrolledCourses(data);
                } else {
                    setError('Failed to fetch enrolled courses.');
                }
            } catch (err) {
                setError('Network error or server not responding.');
            }
        };

        const fetchAvailableCourses = async () => {
            try {
                const response = await fetch(`http://localhost:8080/students/${studentId}/courses?coursesType=availableCourses`);
                if (response.ok) {
                    const data = await response.json();
                    setAvailableCourses(data);
                } else {
                    setError('Failed to fetch available courses.');
                }
            } catch (err) {
                setError('Network error or server not responding.');
            }
        };

        if (studentId) {
            fetchEnrolledCourses();
            fetchAvailableCourses();
        }
    }, [studentId]);

    const handleOnCourseAction = async (courseAction, courseId) => {
        try{
            let url  = `http://localhost:8080/students/${studentId}/courses/${courseId}`;
            

            const response = await fetch(url, {
                method: courseAction === 'join' ? 'POST' : 'DELETE',
                headers: {'Content-Type' : 'application/json'},
            });



            if (response.ok) {
                if (courseAction === 'join') {
                    const joinedCourse = availableCourses.find((course) => course.id === courseId);
                   if(joinedCourse){
                        const updatedEnrolledCourses = [...enrolledCourses, joinedCourse];
                        setEnrolledCourses(updatedEnrolledCourses);

                        const updatedAvailableCourses = availableCourses.filter((course) => course.id !== courseId);
                        setAvailableCourses(updatedAvailableCourses);
                   }
                } else if (courseAction === 'drop') {
                    const droppedCourse = enrolledCourses.find((course) => course.id === courseId);
                    if(droppedCourse){
                        const updatedAvailableCourses = [...availableCourses, droppedCourse];
                        setAvailableCourses(updatedAvailableCourses);

                        const updatedEnrolledCourses = enrolledCourses.filter((course) => course.id !== courseId);
                        setEnrolledCourses(updatedEnrolledCourses);
                    }                    
                }
            } else {
                alert(`Failed to ${courseAction} the course.`);
            }
        } catch (error) {
            alert(`An error occurred while trying to ${courseAction} the course.`);
        }


    };
    

    return (
        <div>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {!error && (
                <StudentAccountTabs
                    enrolledCourses={enrolledCourses}
                    availableCourses={availableCourses}
                    role={role}
                    userId={studentId}
                    onCourseAction={handleOnCourseAction}
                    
                    
                />
            )}
        </div>
    );
};

export default StudentCourses;
