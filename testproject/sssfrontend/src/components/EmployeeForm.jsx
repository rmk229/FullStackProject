import React, { useState } from "react";
import MyInput from "./UI/input/MyInput";
import MyButton from "./UI/button/MyButton";
import MySelectEmployee from "./UI/select/MySelectEmployee";
import { propTypes } from "react-bootstrap/esm/Image";

const EmployeeForm = ({ create, positions, ranks, departments }) => {
  const [post, setPost] = useState({
    secondName: "",
    firstName: "",
    thirdName: "",
    personalNumber: "",
    images: null,
    dateOfBirth: "",
    dateOfSign: "",
    contractPeriod: "",
    department: "",
    rank: "",
    position: "",
  });

  const addNewPost = (e) => {
    e.preventDefault();
    const newPost = {
      ...post,
    };
    console.log(newPost);

    create(newPost);

    setPost({
      secondName: "",
      firstName: "",
      thirdName: "",
      personalNumber: "",
      images: null,
      dateOfBirth: "",
      dateOfSign: "",
      contractPeriod: "",
      department: "",
      rank: "",
      position: "",
    });
  };

  function handleImage(e) {
    setPost({ ...post, images: e.target.files[0] });
  }

  // function handleImage(e) {
  //   const file = e.target.files[0];
  //   setPost((prevPost) => ({
  //     ...prevPost,
  //     images: file,
  //   }));
  // }

  return (
    <form action="/employees" onSubmit={addNewPost}>
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
        // value={post.picture}
        onChange={handleImage}
        type="file"
        placeholder="Фото"
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
      <br />

      <MyButton onClick={addNewPost}>Добавить работника</MyButton>
    </form>
  );
};

export default EmployeeForm;
