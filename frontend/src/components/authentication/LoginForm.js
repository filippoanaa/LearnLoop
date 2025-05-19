import React, { useState } from 'react';
import { Container, Row, Col, Form, Button, Modal, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import './customStyle.css';

export default function LoginForm() {
    const [showRoleSelection, setShowRoleSelection] = useState(false);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const[error, setError] = useState('');

    const navigate = useNavigate();

    const handleRegisterClick = () => {
        setShowRoleSelection(true);
    };

    const handleRoleSelection = (role) => {
        if (role === 'student') {
            navigate('/signup-student');
        } else if (role === 'professor') {
            navigate('/signup-professor');
        }
    };

    const handleLogin = async (e) => {
        e.preventDefault(); 
        setError(''); 
    
        if (!email || !password) {
            setError('All fields must be completed.');
            return;
        }
    
        const loginRequest = { email, password };
    
        try {
            const response = await fetch('http://localhost:8080/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(loginRequest),
            });
    
                if (response.status === 200) {

                const loginResponse = await response.json(); 
                const userId = loginResponse.user.id;
                const userRole = loginResponse.userType;
                navigate(`/account/${userRole}/${userId}/courses`); 
            } else if (response.status === 404) {
                setError('No account found with this email address.');
            } else if (response.status === 401) {
                setError('Incorrect password. Please try again.');
            } else if (response.status === 500) {
                setError('An unexpected error occurred on the server.');
            } else {
                setError('An unknown error occurred.');
            }
        } catch (err) {
            console.error('Error:', err);
            setError('Network error or server not responding.');
        }
    };

    return (
        <Container fluid className="vh-100 d-flex align-items-center">
            <Row className="justify-content-center w-100">
                <Col md={6} lg={5} className="text-center mb-4">
                    <img
                        src="https://www.valamis.com/wp-content/uploads/2022/09/life-long-learning.png"
                        className="img-fluid"
                        alt="Sample"
                    />
                </Col>
                <Col md={6} lg={4}>
                    <Form onSubmit={handleLogin}>
                        <Form.Group className="mb-3">
                            {error && (
                                <Alert variant="danger" className="mb-3">
                                    {error}
                                </Alert>
                            )}

                            <Form.Label >Email</Form.Label>
                            <Form.Control
                                type="email"
                                placeholder="Enter a valid email address"
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

                        <Button type="submit" variant="primary" className="w-100 mb-3">
                            Login
                        </Button>

                        <p className="text-center">
                            Don't have an account?{' '}
                            <Button
                                variant="link"
                                className="p-0 text-danger"
                                onClick={handleRegisterClick}
                            >
                                Register
                            </Button>
                        </p>
                    </Form>
                </Col>
            </Row>

            <Modal
                show={showRoleSelection}
                onHide={() => setShowRoleSelection(false)}
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title>Select your role</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Please choose your role to proceed with registration.
                </Modal.Body>
                <Modal.Footer>
                    <Button
                        variant="primary"
                        onClick={() => handleRoleSelection('student')}
                    >
                        Student
                    </Button>
                    <Button
                        variant="primary"
                        onClick={() => handleRoleSelection('professor')}
                    >
                        Professor
                    </Button>
                    <Button
                        variant="secondary"
                        onClick={() => setShowRoleSelection(false)}
                    >
                        Cancel
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
}