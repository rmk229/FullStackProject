import React, { useState } from "react";
import MyInput from "./UI/input/MyInput";
import MyButton from "./UI/button/MyButton";

const RankForm = ({ create }) => {
  const [rank, setRank] = useState({ rankName: "" });

  const addNewRank = (e) => {
    e.preventDefault();
    const newRank = {
      ...rank,
    };
    console.log(newRank);

    create(newRank);

    setRank({ rankName: "" });
  };

  return (
    <form>
      <MyInput
        value={rank.rankName}
        onChange={(e) => setRank({ ...rank, rankName: e.target.value })}
        type="text"
        placeholder="Звание"
      ></MyInput>

      <br />

      <MyButton onClick={addNewRank}>Добавить звание</MyButton>
    </form>
  );
};

export default RankForm;
