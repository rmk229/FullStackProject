import React, { useEffect, useMemo, useRef, useState } from "react";
import "../styles/App.css";
import MyButton from "../components/UI/button/MyButton";
import MyModal from "../components/UI/MyModal/MyModal";
import { useDepartments } from "../hooks/useDepartments";

import axios, { HttpStatusCode } from "axios";
import EmployeeService from "../API/EmployeeService";
import GetNumberService from "../API/GetNumberService";
import Loader from "../components/UI/Loader/Loader";
import { useFetching } from "../hooks/useFetching";
import { getPagesArray, getPagesCount } from "../utils/pages";
import Pagination from "../components/UI/pagination/Pagination";
import DepartmentList from "../components/DepartmentList";
import DepartmentFilter from "../components/DepartmentFilter";
import DepartmentForm from "../components/DepartmentForm";
import DepartmentEditForm from "../components/DepartmentEditForm";

function Departments() {
  const [departments, setDepartments] = useState([]);
  const [filter, setFilter] = useState({ sort: "", query: "" });
  const [modal, setModal] = useState(false);
  const [updateModal, setUpdateModal] = useState(false);
  const [totalPages, setTotalPages] = useState(0);
  const [limit, setLimit] = useState(10);
  const [page, setPage] = useState(1);

  const sortedAndSearchedPosts = useDepartments(
    departments,
    filter.sort,
    filter.query
  );
  const [fetchPosts, isPostsLoading, postError] = useFetching(
    async (limit, page) => {
      const response = await EmployeeService.getDepartments(limit, page);
      setDepartments(response.data);
      const totalCount = (await GetNumberService.getDepartmentsNumber()).data;
      setTotalPages(getPagesCount(totalCount, limit));
    }
  );

  useEffect(() => {
    fetchPosts(limit, page);
  }, [page]);

  //???
  const createDepartment = async (newDepartment) => {
    const departmentData = new FormData();

    for (let key in newDepartment) {
      departmentData.append(key, newDepartment[key]);
    }

    try {
      await axios.post(
        "http://localhost:8000/departments/add",
        departmentData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      setDepartments([...departments, newDepartment]);
      setModal(false);
    } catch (error) {
      console.error("Error creating department:", error);
    }
  };

  //???
  const removeDepartment = async (department) => {
    setDepartments([...departments.filter((d) => d.id !== department.id)]);
    await axios.delete("http://localhost:8000/departments/" + department.id);
  };

  //???
  const updateDepartment = async (newDepartment) => {
    const response = await axios.post(
      "http://localhost:8000/departments/details/" + newDepartment.id,
      newDepartment
    );

    if (response.status === HttpStatusCode.Ok) {
      const newDepartments = departments.filter(
        (e) => e.id !== newDepartment.id
      );
      setDepartments([...newDepartments, newDepartment]);
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
        Добавить подразделение
      </MyButton>

      <MyModal visible={modal} setVisible={setModal}>
        <DepartmentForm create={createDepartment} />
      </MyModal>

      <MyModal visible={updateModal} setVisible={setUpdateModal}>
        <DepartmentEditForm
          updateDepartment={updateDepartment}
          id={updateModal.id}
        />
      </MyModal>

      <hr style={{ margin: "15px 0 " }} />
      <DepartmentFilter filter={filter} setFilter={setFilter} />
      {postError && <h1>Произошла ошибка &{postError}</h1>}
      {isPostsLoading ? (
        <div
          style={{ display: "flex", justifyContent: "center", marginTop: 50 }}
        >
          <Loader />
        </div>
      ) : (
        <DepartmentList
          update={openEditForm}
          remove={removeDepartment}
          posts={sortedAndSearchedPosts}
          title={"Список подразделений"}
        />
      )}
      <Pagination totalPages={totalPages} changePage={changePage} page={page} />
    </div>
  );
}

export default Departments;
