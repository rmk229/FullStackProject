import React, { useState } from "react";
import MyInput from "./UI/input/MyInput";
import MyButton from "./UI/button/MyButton";

const DepartmentForm = ({ create }) => {
  const [department, setDepartment] = useState({ departmentName: "" });

  const addNewDepartment = (e) => {
    e.preventDefault();
    const newDepartment = {
      ...department,
    };
    console.log(newDepartment);

    create(newDepartment);

    setDepartment({ departmentName: "" });
  };

  return (
    <form action="/departments">
      <MyInput
        value={department.departmentName}
        onChange={(e) =>
          setDepartment({ ...department, departmentName: e.target.value })
        }
        type="text"
        placeholder="Подразделение"
      ></MyInput>

      <br />

      <MyButton onClick={addNewDepartment}>Добавить подразделение</MyButton>
    </form>
  );
};

export default DepartmentForm;
