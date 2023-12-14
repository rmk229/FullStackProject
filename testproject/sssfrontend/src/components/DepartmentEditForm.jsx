import React, { useState, useEffect } from "react";
import axios from "axios";
import MyInput from "./UI/input/MyInput";
import MyButton from "./UI/button/MyButton";
import MySelectEmployee from "./UI/select/MySelectEmployee";

const DepartmentEditForm = ({ id, updateDepartment }) => {
  const [department, setDepartment] = useState({
    departmentName: "",
  });

  useEffect(() => {
    // Загрузите данные с сервера, используя ID сотрудника
    const fetchDepartment = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8000/departments/details/${id}`
        );
        //Если будет ошибка попробуй убрать слово data
        response.data.departmentName = response.data.departmentName.id;
        setDepartment(response.data);
      } catch (error) {
        console.error("Error fetching department data:", error);
      }
    };

    fetchDepartment();
  }, [id]);

  return (
    <form>
      <MyInput
        value={department.departmentName}
        onChange={(e) =>
          setDepartment({ ...department, departmentName: e.target.value })
        }
        type="text"
        placeholder="Наименование подразделения"
      ></MyInput>
      <MyButton onClick={(e) => updateDepartment({ ...department })}>
        Обновить подразделение
      </MyButton>{" "}
    </form>
  );
};

export default DepartmentEditForm;
