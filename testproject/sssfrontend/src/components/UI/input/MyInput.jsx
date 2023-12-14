import React from 'react';
import classes from './MyInput.module.css'
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';

const MyInput = React.forwardRef((props, ref) => {
    return (
        <Form.Control
            ref={ref} className={classes.myInput} {...props}
        />
    );
});

export default MyInput;