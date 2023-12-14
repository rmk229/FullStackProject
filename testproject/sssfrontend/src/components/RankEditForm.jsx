import React, { useState, useEffect } from "react";
import axios from "axios";
import MyInput from "./UI/input/MyInput";
import MyButton from "./UI/button/MyButton";
import MySelectEmployee from "./UI/select/MySelectEmployee";

const RankEditForm = ({ id, updateRank }) => {
  const [rank, setRank] = useState({
    rankName: "",
  });

  //????????????????????????????????????????????????????????????
  useEffect(() => {
    // Загрузите данные с сервера, используя ID сотрудника
    const fetchRank = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8000/ranks/details/${id}`
        );
        //Если будет ошибка попробуй убрать слово data
        response.data.rankName = response.data.rankName.id;
        setRank(response.data);
      } catch (error) {
        console.error("Error fetching rank data:", error);
      }
    };

    fetchRank();
  }, [id]);

  return (
    <form>
      <MyInput
        value={rank.rankName}
        onChange={(e) => setRank({ ...rank, rankName: e.target.value })}
        type="text"
        placeholder="Наименование звания"
      ></MyInput>
      <MyButton onClick={(e) => updateRank({ ...rank })}>
        Обновить звание
      </MyButton>{" "}
    </form>
  );
};

export default RankEditForm;
