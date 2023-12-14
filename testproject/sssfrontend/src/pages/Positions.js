import React, { useEffect, useMemo, useRef, useState } from "react";
import "../styles/App.css";
import MyButton from "../components/UI/button/MyButton";
import MyModal from "../components/UI/MyModal/MyModal";
import { usePositions } from "../hooks/usePositions";

import axios, { HttpStatusCode } from "axios";
import EmployeeService from "../API/EmployeeService";
import GetNumberService from "../API/GetNumberService";
import Loader from "../components/UI/Loader/Loader";
import { useFetching } from "../hooks/useFetching";
import { getPagesArray, getPagesCount } from "../utils/pages";
import Pagination from "../components/UI/pagination/Pagination";
import MyNavbar from "../components/UI/navbar/MyNavbar";
import PositionList from "../components/PositionList";
import PositionFilter from "../components/PositionFilter";
import PositionForm from "../components/PositionForm";
import PositionEditForm from "../components/PositionEditForm";

function Positions() {
  const [positions, setPositions] = useState([]);
  const [filter, setFilter] = useState({ sort: "", query: "" });
  const [modal, setModal] = useState(false);
  const [updateModal, setUpdateModal] = useState(false);
  const [totalPages, setTotalPages] = useState(0);
  const [limit, setLimit] = useState(10);
  const [page, setPage] = useState(1);

  const sortedAndSearchedPosts = usePositions(
    positions,
    filter.sort,
    filter.query
  );
  const [fetchPosts, isPostsLoading, postError] = useFetching(
    async (limit, page) => {
      const response = await EmployeeService.getPositions(limit, page);
      setPositions(response.data);
      const totalCount = (await GetNumberService.getPositionsNumber()).data;
      setTotalPages(getPagesCount(totalCount, limit));
    }
  );

  useEffect(() => {
    fetchPosts(limit, page);
  }, [page]);

  const createPosition = async (newPosition) => {
    const positionData = new FormData();

    for (let key in newPosition) {
      positionData.append(key, newPosition[key]);
    }

    try {
      await axios.post("http://localhost:8000/positions/add", positionData, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      setPositions([...positions, newPosition]);
      setModal(false);
    } catch (error) {
      console.error("Error creating position:", error);
    }
  };

  const removePosition = async (position) => {
    setPositions([...positions.filter((p) => p.id !== position.id)]);
    await axios.delete("http://localhost:8000/positions/" + position.id);
  };

  const updatePosition = async (newPosition) => {
    const response = await axios.post(
      "http://localhost:8000/positions/details/" + newPosition.id,
      newPosition
    );

    if (response.status === HttpStatusCode.Ok) {
      const newPositions = positions.filter((e) => e.id !== newPosition.id);
      setPositions([...newPositions, newPosition]);
    }

    setUpdateModal(false);
  };

  const changePage = (page) => {
    setPage(page);
    fetchPosts(limit, page);
  };

  const openEditForm = (post) => {
    setUpdateModal(post);
  };

  return (
    <div className="App">
      <MyButton
        style={{ marginTop: 30, marginRight: 10 }}
        onClick={() => setModal(true)}
      >
        Добавить должность
      </MyButton>

      <MyModal visible={modal} setVisible={setModal}>
        <PositionForm create={createPosition} />
      </MyModal>

      <MyModal visible={updateModal} setVisible={setUpdateModal}>
        <PositionEditForm updatePosition={updatePosition} id={updateModal.id} />
      </MyModal>

      <hr style={{ margin: "15px 0 " }} />
      <PositionFilter filter={filter} setFilter={setFilter} />
      {postError && <h1>Произошла ошибка &{postError}</h1>}
      {isPostsLoading ? (
        <div
          style={{ display: "flex", justifyContent: "center", marginTop: 50 }}
        >
          <Loader />
        </div>
      ) : (
        <PositionList
          update={openEditForm}
          remove={removePosition}
          posts={sortedAndSearchedPosts}
          title={"Список должностей"}
        />
      )}
      <Pagination totalPages={totalPages} changePage={changePage} page={page} />
    </div>
  );
}

export default Positions;
