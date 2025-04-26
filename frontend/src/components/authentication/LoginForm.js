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
        } else if (role === 'teacher') {
            navigate('/signup-teacher');
        }
    };

    const handleLogin = async (e) => {
        e.preventDefault(); // Previne refresh-ul paginii
        setError(''); 
        if(!email || !password){
            e.preventDefault();
            setError('All fields must be completed.');
            return;
        }

        const loginReuest = {email, password};
        
        try{
            const response = await fetch('http://localhost:8080/authentication/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(loginReuest)
            });

            console.log(response);

            if(response.ok){
                const user = await response.json();
                const userId = user.id;
                navigate(`/account/${userId}`)
            }
        }catch(err){
            setError('Network error or server not responding.', err)
        }

    }

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

                            <Form.Label>Email</Form.Label>
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
                        onClick={() => handleRoleSelection('teacher')}
                    >
                        Teacher
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