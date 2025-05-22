import React, { useState } from 'react'
import { Alert, Container, Row, Col, Card, Form, Button } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import './customStyle.css'

const SignUpProfessorForm = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState('');
    const [currentPosition, setCurrentPosition] = useState('');
    const [experienceYears, setExperienceYears] = useState('');
    const [instituteName, setInstituteName] = useState('');
    const [expertiseDomains, setExpertiseDomains] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordConfirmation, setPasswordConfirmation] = useState('');

    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        if (!firstName || !lastName || !dateOfBirth || !currentPosition || !instituteName || !expertiseDomains || !email || !password || !passwordConfirmation) {
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
    
        const expertiseDomainsArray = expertiseDomains.split(',').map((domain) => domain.trim());

        const professorData = {
            userType: "professor",
            firstName,
            lastName,
            dateOfBirth,
            currentPosition,
            experienceYears,
            instituteName,
            expertiseDomains : expertiseDomainsArray,
            email,
            password,
        };
    
        try {
            const response = await fetch('http://localhost:8080/users/newAccount', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(professorData)
            });
    
            if (response.status == 201) {
                const createdProfessor = await response.json();
                navigate(`/acount/professor/${createdProfessor.id}/courses`);
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
                                        <Form.Label>Current Position</Form.Label>
                                        <Form.Select
                                            value={currentPosition}
                                            onChange={(e) => setCurrentPosition(e.target.value)}
                                        >
                                            <option value="">Select Current Position</option>
                                            <option value="univeristyProfessor">Pre-School Professor</option>
                                            <option value="highSchoolProfessor">High School Professor</option>
                                            <option value="middleSchoolProfessor">Middle School Professor</option>
                                            <option value="primarySchoolProfessor">Primary School Professor</option>
                                            <option value="preSchoolProfessor">Pre-School Professor</option>
                                            <option value="independentProfessor">Independent professor</option>
                                            <option value="other">Other</option>
                                        </Form.Select>
                                    </Form.Group>
                                    
                                    <Form.Group className="mb-3">
                                        <Form.Label>Experience Years</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter experience years"
                                            value={experienceYears}
                                            onChange={(e) => setExperienceYears(e.target.value)}
                                        >
                                            
                                        </Form.Control>
                                    </Form.Group>



                                    <Form.Group className="mb-3">
                                        <Form.Label>Institute name</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="Enter institute name"
                                            value={instituteName}
                                            onChange={(e) => setInstituteName(e.target.value)}
                                        />
                                    </Form.Group>

                                    <Form.Group className="mb-3">
                                        <Form.Label>Expertise Domains</Form.Label>
                                        <Form.Control
                                            as="textarea"
                                            placeholder="Enter expertise domains separated by commas (e.g., Mathematics, Physics)"
                                            value={expertiseDomains}
                                            onChange={(e) => setExpertiseDomains(e.target.value)}
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
                                    <Link to="/login" className="btn btn-danger w-100 mt-2">
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

export default SignUpProfessorForm
