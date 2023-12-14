import React from 'react';
import classes from './MyButton.module.css'
import Button from 'react-bootstrap/Button';
import 'bootstrap/dist/css/bootstrap.min.css';
const MyButton = ({children, ...props}) => {
    return (
    <Button {...props} className={classes.myBtn} variant="outline-primary">
        {children}
    </Button>

);
};

export default MyButton;