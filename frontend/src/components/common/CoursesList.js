import React from 'react';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { Link } from 'react-router-dom';

const CoursesList = ({ courses, role, userId, coursesType, onCourseAction }) => {
    console.log("Role:", role, "CoursesType:", coursesType, "Courses:", courses);

    const showButton = (course) => {
        console.log("Processing course:", course);

        if (role === "student") {
            if (coursesType === "availableCourses") {
                console.log("Rendering Join button for course:", course.title);
                return (
                    <Button
                        variant="success"
                        onClick={() => onCourseAction("join", course.id)}
                    >
                        Join
                    </Button>
                );
            } else if (coursesType === "enrolledCourses") {
                console.log("Rendering Drop button for course:", course.title);
                return (
                    <Button
                        variant="danger"
                        onClick={() => onCourseAction("drop", course.id)}
                    >
                        Drop
                    </Button>
                );
            }
        } else if (role === "professor") {
            console.log("Rendering Updaye button for course:", course.title);
            return (
                <div >
                    <Button
                        variant="secondary"
                        className="me-2"
                        onClick={() => onCourseAction("update", course.id)}
                    >
                        Update
                    </Button> 

                    {course.numberOfEnrollments === 0 &&(
                        <Button
                        variant="danger"
                        onClick={() => onCourseAction("delete", course.id)}
                    >
                        Delete
                    </Button> 
                    )}
                </div>
            );
        }

        return null;
    };

    return (
        <div>
            {courses.map((course) => {
                console.log("Course ID for URL:", course.id); 
                return (
                    <Card key={course.id} className="mb-3">
                        <Card.Header as="h5">{course.title}</Card.Header>
                        <Card.Body>
                            <Card.Text>
                                <strong>Start Date:</strong> {new Date(course.startDate).toLocaleDateString()} <br />
                                <strong>Application Deadline:</strong> {new Date(course.applicationDeadline).toLocaleDateString()}
                            </Card.Text>
                            <div className="d-flex gap-2">
                                <Link to={`/account/${role}/${userId}/courses/${course.id}`}>
                                    <Button variant="primary">View Details</Button>
                                </Link>
                                {showButton(course)}
                            </div>
                        </Card.Body>
                    </Card>
                );
            })}
        </div>
    );
};

export default CoursesList;