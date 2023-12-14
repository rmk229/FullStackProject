import React, { useState, useEffect } from "react";
import axios from "axios";
import MyInput from "./UI/input/MyInput";
import MyButton from "./UI/button/MyButton";
import MySelectEmployee from "./UI/select/MySelectEmployee";

const PositionEditForm = ({ id, updatePosition }) => {
  const [position, setPosition] = useState({
    positionName: "",
  });

  useEffect(() => {
    // Загрузите данные с сервера, используя ID сотрудника
    const fetchPosition = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8000/positions/details/${id}`
        );
        //Если будет ошибка попробуй убрать слово data
        response.data.positionName = response.data.positionName.id;
        setPosition(response.data);
      } catch (error) {
        console.error("Error fetching position data:", error);
      }
    };

    fetchPosition();
  }, [id]);

  return (
    <form>
      <MyInput
        value={position.positionName}
        onChange={(e) =>
          setPosition({ ...position, positionName: e.target.value })
        }
        type="text"
        placeholder="Наименование должности"
      ></MyInput>
      <MyButton onClick={(e) => updatePosition({ ...position })}>
        Обновить должность
      </MyButton>{" "}
    </form>
  );
};

export default PositionEditForm;
