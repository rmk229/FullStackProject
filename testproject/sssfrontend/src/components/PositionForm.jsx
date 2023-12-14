import React, { useState } from "react";
import MyInput from "./UI/input/MyInput";
import MyButton from "./UI/button/MyButton";

const PositionForm = ({ create }) => {
  const [position, setPosition] = useState({ positionName: "" });

  const addNewPosition = (e) => {
    e.preventDefault();
    const newPosition = {
      ...position,
    };
    console.log(newPosition);

    create(newPosition);

    setPosition({ positionName: "" });
  };

  return (
    <form>
      <MyInput
        value={position.positionName}
        onChange={(e) =>
          setPosition({ ...position, positionName: e.target.value })
        }
        type="text"
        placeholder="Должность"
      ></MyInput>

      <br />

      <MyButton onClick={addNewPosition}>Добавить должность</MyButton>
    </form>
  );
};

export default PositionForm;
