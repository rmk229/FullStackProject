import React, { useEffect, useMemo, useRef, useState } from "react";
import "../styles/App.css";
import MyButton from "../components/UI/button/MyButton";
import MyModal from "../components/UI/MyModal/MyModal";
import { useRanks } from "../hooks/useRanks";

import axios, { HttpStatusCode } from "axios";
import EmployeeService from "../API/EmployeeService";
import GetNumberService from "../API/GetNumberService";
import Loader from "../components/UI/Loader/Loader";
import { useFetching } from "../hooks/useFetching";
import { getPagesArray, getPagesCount } from "../utils/pages";
import Pagination from "../components/UI/pagination/Pagination";
import MyNavbar from "../components/UI/navbar/MyNavbar";
import RankList from "../components/RankList";
import RankFilter from "../components/RankFilter";
import RankForm from "../components/RankForm";
import RankEditForm from "../components/RankEditForm";

function Ranks() {
  const [ranks, setRanks] = useState([]);
  const [filter, setFilter] = useState({ sort: "", query: "" });
  const [modal, setModal] = useState(false);
  const [updateModal, setUpdateModal] = useState(false);
  const [totalPages, setTotalPages] = useState(0);
  const [limit, setLimit] = useState(10);
  const [page, setPage] = useState(1);

  const sortedAndSearchedPosts = useRanks(ranks, filter.sort, filter.query);
  const [fetchPosts, isPostsLoading, postError] = useFetching(
    async (limit, page) => {
      const response = await EmployeeService.getRanks(limit, page);
      setRanks(response.data);
      const totalCount = (await GetNumberService.getRanksNumber()).data;
      setTotalPages(getPagesCount(totalCount, limit));
    }
  );

  useEffect(() => {
    fetchPosts(limit, page);
  }, [page]);

  const createRank = async (newRank) => {
    const rankData = new FormData();

    for (let key in newRank) {
      rankData.append(key, newRank[key]);
    }

    try {
      await axios.post("http://localhost:8000/ranks/add", rankData, {
        headers: {
          "Content-Type": "application/json",
        },
      });

      setRanks([...ranks, newRank]);
      setModal(false);
    } catch (error) {
      console.error("Error creating rank:", error);
    }
  };

  const removeRank = async (rank) => {
    setRanks([...ranks.filter((r) => r.id !== rank.id)]);
    await axios.delete("http://localhost:8000/ranks/" + rank.id);
  };

  //???
  const updateRank = async (newRank) => {
    const response = await axios.post(
      "http://localhost:8000/ranks/details/" + newRank.id,
      newRank
    );

    if (response.status === HttpStatusCode.Ok) {
      const newRanks = ranks.filter((e) => e.id !== newRank.id);
      setRanks([...newRanks, newRank]);
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
        Добавить звание
      </MyButton>

      <MyModal visible={modal} setVisible={setModal}>
        <RankForm create={createRank} />
      </MyModal>

      <MyModal visible={updateModal} setVisible={setUpdateModal}>
        <RankEditForm updateRank={updateRank} id={updateModal.id} />
      </MyModal>

      <hr style={{ margin: "15px 0 " }} />
      <RankFilter filter={filter} setFilter={setFilter} />
      {postError && <h1>Произошла ошибка &{postError}</h1>}
      {isPostsLoading ? (
        <div
          style={{ display: "flex", justifyContent: "center", marginTop: 50 }}
        >
          <Loader />
        </div>
      ) : (
        <RankList
          update={openEditForm}
          remove={removeRank}
          posts={sortedAndSearchedPosts}
          title={"Список званий"}
        />
      )}
      <Pagination totalPages={totalPages} changePage={changePage} page={page} />
    </div>
  );
}

export default Ranks;
