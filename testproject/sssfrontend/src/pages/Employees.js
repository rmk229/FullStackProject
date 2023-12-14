import React, { useEffect, useMemo, useRef, useState } from "react";
import "../styles/App.css";
import EmployeeList from "../components/EmployeeList";
import MyButton from "../components/UI/button/MyButton";
import EmployeeForm from "../components/EmployeeForm";
import EmployeeEditForm from "../components/EmployeeEditForm";
import EmployeeFilter from "../components/EmployeeFilter";
import MyModal from "../components/UI/MyModal/MyModal";
import { useEmployees } from "../hooks/useEmployees";
import axios, { HttpStatusCode } from "axios";
import EmployeeService from "../API/EmployeeService";
import GetNumberService from "../API/GetNumberService";
import Loader from "../components/UI/Loader/Loader";
import { useFetching } from "../hooks/useFetching";
import { getPagesArray, getPagesCount } from "../utils/pages";
import Pagination from "../components/UI/pagination/Pagination";
import MyNavbar from "../components/UI/navbar/MyNavbar";
import ExcelExportButton from "../components/ExcelExportButton";

function Employees({ departments, ranks, positions }) {
  const [posts, setPosts] = useState([]);
  const [filter, setFilter] = useState({ sort: "", query: "" });
  const [modal, setModal] = useState(false);
  const [updateModal, setUpdateModal] = useState(false);
  const [totalPages, setTotalPages] = useState(0);
  const [limit, setLimit] = useState(10);
  const [page, setPage] = useState(1);

  const sortedAndSearchedPosts = useEmployees(posts, filter.sort, filter.query);
  const [fetchPosts, isPostsLoading, postError] = useFetching(
    async (limit, page) => {
      const response = await EmployeeService.getAll(limit, page);
      setPosts(response.data);
      const totalCount = (await GetNumberService.getEmployeesNumber()).data;
      setTotalPages(getPagesCount(totalCount, limit));
    }
  );

  useEffect(() => {
    fetchPosts(limit, page);
  }, [page]);

  const createPost = async (newPost) => {
    if (!newPost.images) {
      console.error("Image is required");
      return;
    }

    const formData = new FormData();
    const pictureData = new FormData();

    for (let key in newPost) {
      if (key === "images") {
        pictureData.append("images", newPost[key]);
      } else {
        formData.append(key, newPost[key]);
      }
    }

    const response = await axios.post(
      "http://localhost:8000/images",
      pictureData
    );

    console.log(response);

    formData.append("images", response.data.id);

    try {
      await axios.post("http://localhost:8000/employees/add", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      setPosts([...posts, newPost]);
      setModal(false);
    } catch (error) {
      console.error("Error creating employee:", error);
    }
  };

  const removePost = async (post) => {
    setPosts([...posts.filter((p) => p.id !== post.id)]);
    await axios.delete("http://localhost:8000/employees/" + post.id);
  };

  const updatePost = async (newPost) => {
    const newPostWOImage = { ...newPost };
    delete newPostWOImage.images;

    const response = await axios.post(
      "http://localhost:8000/employees/details/" + newPostWOImage.id,
      newPostWOImage
    );

    // console.log(newPost)

    if (response.status === HttpStatusCode.Ok) {
      const newPosts = posts.filter((e) => e.id !== newPost.id);
      setPosts([...newPosts, newPost]);
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
        Добавить работника
      </MyButton>
      <ExcelExportButton />

      <MyModal visible={modal} setVisible={setModal}>
        <EmployeeForm
          create={createPost}
          positions={positions}
          ranks={ranks}
          departments={departments}
        />
      </MyModal>

      <MyModal visible={updateModal} setVisible={setUpdateModal}>
        <EmployeeEditForm
          updatePost={updatePost}
          id={updateModal.id}
          positions={positions}
          ranks={ranks}
          departments={departments}
        />
      </MyModal>

      <hr style={{ margin: "15px 0 " }} />
      <EmployeeFilter filter={filter} setFilter={setFilter} />
      {postError && <h1>Произошла ошибка &{postError}</h1>}
      {isPostsLoading ? (
        <div
          style={{ display: "flex", justifyContent: "center", marginTop: 50 }}
        >
          <Loader />
        </div>
      ) : (
        <EmployeeList
          update={openEditForm}
          remove={removePost}
          posts={sortedAndSearchedPosts}
          title={"Список работников"}
        />
      )}
      <Pagination totalPages={totalPages} changePage={changePage} page={page} />
    </div>
  );
}

export default Employees;
