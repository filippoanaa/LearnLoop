import React, { useState } from 'react'
import { Alert, Container, Row, Col, Card, Form, Button } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import './customStyle.css'

const SignUpStudentForm = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState('');
    const [educationLevel, setEducationLevel] = useState('');
    const [schoolName, setSchoolName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordConfirmation, setPasswordConfirmation] = useState('');

    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        if (!firstName || !lastName || !dateOfBirth || !educationLevel || !schoolName || !email || !password || !passwordConfirmation) {
            e.preventDefault();
            setError('All fields must be completed.');
            return;
        }
        e.preventDefault();
        setError('');
    
        if (password !== passwordConfirmation) {
            setError('Passwords do not match.');
            return;
        }
    
        const studentData = {
            userType: "student",
            firstName,
            lastName,
            dateOfBirth,
            educationLevel,
            schoolName,
            email,
            password,
        };
    
        try {
            const response = await fetch('http://localhost:8080/users/newAccount', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(studentData)
            });
    
            if (response.status == 201) {
                const createdStudent = await response.json();
                navigate(`/account/student/${createdStudent.id}/courses`);
            } else {
                const errorData = await response.json();
                setError(errorData.message || 'Signup failed.');
            }
        } catch (err) {
            setError('Network error or server not responding.');
        }
    };
    
    return (
        <div>
            <Container className="mt-4">
                <Row className="justify-content-center">
                    <Col md={6}>
                        <Card>
                            <Card.Header className="bg-primary text-white text-center">
                                <h4>Create account</h4>
                            </Card.Header>
                            <Card.Body>
                            
                                <Form onSubmit={handleSubmit}>
                                    <Form.Group className="mb-3">
                                        <Form.Label>First Name</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter first name"
                                            value={firstName}
                                            onChange={(e) => setFirstName(e.target.value)}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label>Last Name</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter last name"
                                            value={lastName}
                                            onChange={(e) => setLastName(e.target.value)}
                                        />
                                    </Form.Group>


                                    <Form.Group className="mb-3">
                                        <Form.Label>Date of birth</Form.Label>
                                        <Form.Control
                                            type="date"
                                            max={new Date().toISOString().split('T')[0]}
                                            value={dateOfBirth}
                                            onChange={(e) => setDateOfBirth(e.target.value)}
                                        />
                                    </Form.Group>


                                    <Form.Group className="mb-3">
                                        <Form.Label>Education Level</Form.Label>
                                        <Form.Select
                                            value={educationLevel}
                                            onChange={(e) => setEducationLevel(e.target.value)}
                                        >
                                            <option value="">Select Education Level</option>
                                            <option value="preSchool">Pre-School</option>
                                            <option value="primarySchool">Primary School</option>
                                            <option value="middleSchool">Middle School</option>
                                            <option value="highSchool">High School</option>
                                            <option value="bachelorsDegree">Bachelor's Degree</option>
                                            <option value="mastersDegree">Master's Degree</option>
                                            <option value="phd">PhD</option>
                                            <option value="other">Other</option>
                                        </Form.Select>
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label>School name</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter school name"
                                            value={schoolName}
                                            onChange={(e) => setSchoolName(e.target.value)}
                                        />
                                    </Form.Group>


                                    <Form.Group className="mb-3">
                                        <Form.Label>Email</Form.Label>
                                        <Form.Control
                                            type="email"
                                            placeholder="Enter email"
                                            value={email}
                                            onChange={(e) => setEmail(e.target.value)}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label>Password</Form.Label>
                                        <Form.Control
                                            type="password"
                                            placeholder="Enter password"
                                            value={password}
                                            onChange={(e) => setPassword(e.target.value)}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label>Confirm Password</Form.Label>
                                        <Form.Control
                                            type="password"
                                            placeholder="Enter password again"
                                            value={passwordConfirmation}
                                            onChange={(e) => setPasswordConfirmation(e.target.value)}
                                        />
                                    </Form.Group>

                                    <Button type="submit" className="w-100" variant="success">
                                        Sign up
                                    </Button>
                                    <Link to="/" className="btn btn-danger w-100 mt-2">
                                        Cancel
                                    </Link>
                                </Form>
                                {error && <Alert variant="danger">{error}</Alert>}
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
        </div>
    )
}

export default SignUpStudentForm
