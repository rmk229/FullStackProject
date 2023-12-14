import React from 'react';
import Form from 'react-bootstrap/Form';
import 'bootstrap/dist/css/bootstrap.min.css';

const MySelectEmployee = ({options, defaultValue,value,onChange}) => {
    return (
        <Form.Select aria-label="Default select example"
            value = {value}
            onChange = {event => onChange(event.target.value)}
        >
            <option disabled value="">{defaultValue}</option>
            {options.map(option =>
                <option key={option.value} value = {option.value}>
                    {option.name}
                </option>
            )}
        </Form.Select>
    );
};

export default MySelectEmployee;