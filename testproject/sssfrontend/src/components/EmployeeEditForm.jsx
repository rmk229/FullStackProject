import React, { useState, useEffect } from "react";
import axios from "axios";
import MyInput from "./UI/input/MyInput";
import MyButton from "./UI/button/MyButton";
import MySelectEmployee from "./UI/select/MySelectEmployee";

const EmployeeEditForm = ({
  id,
  positions,
  departments,
  ranks,
  updatePost,
}) => {
  const [post, setPost] = useState({
    secondName: "",
    firstName: "",
    thirdName: "",
    personalNumber: "",
    dateOfBirth: "",
    dateOfSign: "",
    contractPeriod: "",
    department: "",
    rank: "",
    position: "",
  });

  useEffect(() => {
    // Загрузите данные с сервера, используя ID сотрудника
    const fetchEmployee = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8000/employees/${id}`
        );
        response.data.department = response.data.department.id;
        response.data.rank = response.data.rank.id;
        response.data.position = response.data.position.id;
        setPost(response.data);
      } catch (error) {
        console.error("Error fetching employee data:", error);
      }
    };

    fetchEmployee();
  }, [id]);

  console.log(post);

  return (
    <form onSubmit={updatePost}>
      <MyInput
        value={post.personalNumber}
        onChange={(e) =>
          setPost({ ...post, personalNumber: parseInt(e.target.value) })
        }
        type="text"
        placeholder="Персональный номер"
      ></MyInput>
      <MyInput
        value={post.secondName}
        onChange={(e) => setPost({ ...post, secondName: e.target.value })}
        type="text"
        placeholder="Фамилия"
      ></MyInput>
      <MyInput
        value={post.firstName}
        onChange={(e) => setPost({ ...post, firstName: e.target.value })}
        type="text"
        placeholder="Имя"
      ></MyInput>
      <MyInput
        value={post.thirdName}
        onChange={(e) => setPost({ ...post, thirdName: e.target.value })}
        type="text"
        placeholder="Отчество"
      ></MyInput>
      <MyInput
        value={post.dateOfBirth}
        onChange={(e) => setPost({ ...post, dateOfBirth: e.target.value })}
        type="date"
        placeholder="Дата рождения"
      ></MyInput>
      <MyInput
        value={post.dateOfSign}
        onChange={(e) => setPost({ ...post, dateOfSign: e.target.value })}
        type="date"
        placeholder="Дата заключения контракта"
      ></MyInput>
      <MyInput
        value={post.contractPeriod}
        onChange={(e) =>
          setPost({ ...post, contractPeriod: parseInt(e.target.value) })
        }
        type="text"
        placeholder="На сколько лет заключил(а) контракт"
      ></MyInput>
      <MySelectEmployee
        value={post.position}
        defaultValue="Должность"
        onChange={(e) => setPost({ ...post, position: parseInt(e) })}
        options={positions?.map((position) => ({
          value: position.id,
          name: position.positionName,
        }))}
      />
      <MySelectEmployee
        value={post.department}
        defaultValue="Подразделение"
        onChange={(e) => setPost({ ...post, department: parseInt(e) })}
        options={departments?.map((department) => ({
          value: department.id,
          name: department.departmentName,
        }))}
      />
      <MySelectEmployee
        value={post.rank}
        defaultValue="Звание"
        onChange={(e) => setPost({ ...post, rank: parseInt(e) })}
        options={ranks?.map((rank) => ({
          value: rank.id,
          name: rank.rankName,
        }))}
      />
      <MyButton
        style={{ marginTop: 15 }}
        onClick={(e) => updatePost({ ...post })}
      >
        Обновить работника
      </MyButton>{" "}
    </form>
  );
};

export default EmployeeEditForm;
