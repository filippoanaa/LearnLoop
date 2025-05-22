import React, { useState, useEffect } from 'react';
import { Alert, Container, Row, Col, Card, Form, Button } from 'react-bootstrap';
import { useNavigate, useLocation, useParams } from 'react-router-dom';

const CourseForm = () => {
    const { role, professorId, courseId } = useParams();
    console.log(role+ " " + professorId +" " + courseId);
    const location = useLocation();
    const initialData = location.state?.initialData || {};
    console.log("Initial data: " + initialData);

    const [title, setTitle] = useState(initialData.title || '');
    const [description, setDescription] = useState(initialData.description || '');
    const [duration, setDuration] = useState(initialData.duration || '');
    const [maximumNumberOfStudents, setMaximumNumberOfStudents] = useState(initialData.maximumNumberOfStudents || '');
    const [startDate, setStartDate] = useState(initialData.startDate ? initialData.startDate.split('T')[0] : '');
    const [applicationDeadline, setApplicationDeadline] = useState(initialData.applicationDeadline ? initialData.applicationDeadline.split('T')[0] : '');

    const [error, setError] = useState('');
    const navigate = useNavigate();

     useEffect(() => {
        const fetchCourseDetails = async () => {
            if (courseId) {
                try {
                    const response = await fetch(`http://localhost:8080/courses/${courseId}`);
                    if (response.ok) {
                        const data = await response.json();

                        setTitle(data.title || '');
                        setDescription(data.description || '');
                        setDuration(data.duration || '');
                        setMaximumNumberOfStudents(data.maximumNumberOfStudents || '');
                        setStartDate(data.startDate ? data.startDate.split('T')[0] : '');
                        setApplicationDeadline(data.applicationDeadline ? data.applicationDeadline.split('T')[0] : '');
                    } else {
                        setError('Failed to fetch course details.');
                    }
                } catch (err) {
                    setError('Network error or server not responding.');
                }
            }
        };

        fetchCourseDetails();
    }, [courseId, professorId]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if (!title || !description || !startDate || !duration || !maximumNumberOfStudents || !applicationDeadline) {
            setError('All fields must be completed.');
            return;
        }

        const courseData = {
            courseId,
            professorId,
            title,
            description,
            startDate,
            duration,
            maximumNumberOfStudents,
            applicationDeadline,
        };

        console.log("Trimit id ul: "+ courseId)

        try {
            const url = courseId
                ? `http://localhost:8080/professors/${professorId}/courses/${courseId}` 
                : `http://localhost:8080/professors/${professorId}/courses`; 

            const method = courseId ? 'PUT' : 'POST'; 
            console.log("Metoda: " + method);

            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(courseData),
            });

            if (!response.ok) {
                throw new Error('Failed to submit the course.');
            }

            navigate(`/account/professor/${professorId}/courses`);
        } catch (err) {
            setError('Failed to submit the course. Please try again.');
        }
    };

    return (
        <div>
            <Container className="mt-4">
                <Row className="justify-content-center">
                    <Col md={6}>
                        <Card>
                            <Card.Header className="bg-primary text-white text-center">
                                <h4>{courseId ? 'Edit Course' : 'Add Course'}</h4>
                            </Card.Header>
                            <Card.Body>
                                <Form onSubmit={handleSubmit}>
                                    <Form.Group className="mb-3">
                                        <Form.Label>Title</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter course title"
                                            value={title}
                                            onChange={(e) => setTitle(e.target.value)}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label>Description</Form.Label>
                                        <Form.Control
                                            as="textarea"
                                            placeholder="Enter course description"
                                            value={description}
                                            onChange={(e) => setDescription(e.target.value)}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label>Start Date</Form.Label>
                                        <Form.Control
                                            type="date"
                                            value={startDate}
                                            onChange={(e) => setStartDate(e.target.value)}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label>Application Deadline</Form.Label>
                                        <Form.Control
                                            type="date"
                                            value={applicationDeadline}
                                            onChange={(e) => setApplicationDeadline(e.target.value)}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label>Duration (days)</Form.Label>
                                        <Form.Control
                                            type="number"
                                            placeholder="Enter course duration in days"
                                            value={duration}
                                            onChange={(e) => setDuration(e.target.value)}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label>Maximum Number of Students</Form.Label>
                                        <Form.Control
                                            type="number"
                                            placeholder="Enter maximum number of students"
                                            value={maximumNumberOfStudents}
                                            onChange={(e) => setMaximumNumberOfStudents(e.target.value)}
                                        />
                                    </Form.Group>

                                    

                                    <Button type="submit" className="w-100" variant="success">
                                        {courseId ? 'Update Course' : 'Create Course'}

                                    </Button>

                                    <Button
                                        variant="secondary"
                                        className="w-100 mt-2"
                                        onClick={() => navigate(`/account/professor/${professorId}/courses`)}
                                    >
                                        Cancel
                                    </Button>
                                </Form>
                                {error && <Alert variant="danger" className="mt-3">{error}</Alert>}
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
        </div>
    );
};

export default CourseForm; 