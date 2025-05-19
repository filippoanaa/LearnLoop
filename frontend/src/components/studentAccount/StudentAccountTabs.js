import React from 'react';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import CoursesList from '../common/CoursesList';
import './StudentAccountTabs.css';

const StudentAccountTabs = ({ availableCourses, enrolledCourses, userId, onCourseAction }) => {
    return (
        <Tabs defaultActiveKey="availableCourses" id="student-account-tabs" className="mt-3 custom-tabs">
            <Tab eventKey="availableCourses" title="Available Courses">
                <CoursesTabContent
                    courses={availableCourses}
                    emptyMessage="No available courses found."
                    role={"student"}
                    userId={userId}
                    coursesType={"availableCourses"}
                    onCourseAction={onCourseAction}
                />
            </Tab>
            <Tab eventKey="enrolledCourses" title="Enrolled Courses">
                <CoursesTabContent
                    courses={enrolledCourses}
                    emptyMessage="No enrolled courses. Enroll now."
                    role={"student"}
                    userId={userId}
                    coursesType={"enrolledCourses"}
                    onCourseAction={onCourseAction}
                />
            </Tab>
            <Tab eventKey="grades" title="Grades">
                <p>Here you will see grades in the future.</p>
            </Tab>
        </Tabs>
    );
};

const CoursesTabContent = ({ courses, emptyMessage, role, userId, coursesType, onCourseAction}) => {
    return courses?.length > 0 ? (
        <CoursesList courses={courses} role={role} userId={userId} coursesType={coursesType} onCourseAction={onCourseAction}  />
    ) : (
        <p>{emptyMessage}</p>
    );
};

export default StudentAccountTabs;
