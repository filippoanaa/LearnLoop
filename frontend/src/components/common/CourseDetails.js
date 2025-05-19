import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Card, Container, Row, Col, Alert, Spinner, Button } from 'react-bootstrap';

const CourseDetails = () => {
    const { role, userId, courseId } = useParams();
    const [course, setCourse] = useState(null);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchCourseDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/courses/${courseId}`);
                if (response.ok) {
                    const data = await response.json();
                    setCourse(data);
                } else {
                    setError('Failed to fetch course details.');
                }
            } catch (err) {
                setError('Network error or server not responding.');
            }
        };

        if (courseId) {
            fetchCourseDetails();
        }
    }, [courseId]);

    if (error) {
        return (
            <Container className="mt-4">
                <Alert variant="danger">{error}</Alert>
            </Container>
        );
    }

    if (!course) {
        return (
            <Container className="mt-4 text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
                <p>Loading course details...</p>
            </Container>
        );
    }

    return (
        <Container fluid className="mt-4">
            <Row className="justify-content-center">
                <Col md={8}>
                    <Card className="shadow-lg">
                        <Card.Header className="bg-primary text-white text-center py-4">
                            <h2 className="mb-0">{course.title}</h2>
                        </Card.Header>
                        <Card.Body>
                            <Card.Text className="mb-4">
                                <strong>Description:</strong> {course.description}
                            </Card.Text>
                            <Row className="mb-3">
                                <Col md={6}>
                                    <strong>Start Date:</strong>
                                    <p>{new Date(course.startDate).toLocaleDateString()}</p>
                                </Col>
                                <Col md={6}>
                                    <strong>Duration:</strong>
                                    <p>{course.duration} days</p>
                                </Col>
                            </Row>
                            <Row className="mb-3">
                                <Col md={6}>
                                    <strong>Maximum Students:</strong>
                                    <p>{course.maximumNumberOfStudents}</p>
                                </Col>
                                <Col md={6}>
                                    <strong>Enrollments:</strong>
                                    <p>{course.numberOfEnrollments}</p>
                                </Col>
                            </Row>
                            <Row>
                                <Col md={6}>
                                    <strong>Application Deadline:</strong>
                                    <p>{new Date(course.applicationDeadline).toLocaleDateString()}</p>
                                </Col>
                            </Row>
                        </Card.Body>
                        <Card.Footer className="text-center">
                            <Link to={`/account/${role}/${userId}/courses`}>
                                <Button variant="primary" className="mt-2">
                                    Back to Courses
                                </Button>
                            </Link>
                        </Card.Footer>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default CourseDetails;