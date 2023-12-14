import React, { useEffect, useMemo, useRef, useState } from "react";
import "./styles/App.css";
import { useEmployees } from "./hooks/useEmployees";
import axios, { HttpStatusCode } from "axios";
import EmployeeService from "./API/EmployeeService";
import GetNumberService from "./API/GetNumberService";
import { useFetching } from "./hooks/useFetching";
import { getPagesArray, getPagesCount } from "./utils/pages";
import MyNavbar from "./components/UI/navbar/MyNavbar";
import Employees from "./pages/Employees";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import Departments from "./pages/Departments";
import Ranks from "./pages/Ranks";
import Positions from "./pages/Positions";

function App() {
  const [posts, setPosts] = useState([]);
  const [filter, setFilter] = useState({ sort: "", query: "" });
  const [modal, setModal] = useState(false);
  const [updateModal, setUpdateModal] = useState(false);
  const [totalPages, setTotalPages] = useState(0);
  const [limit, setLimit] = useState(10);
  const [page, setPage] = useState(1);
  const [positions, setPositions] = useState([]);
  const [ranks, setRanks] = useState([]);
  const [departments, setDepartments] = useState([]);
  const sortedAndSearchedPosts = useEmployees(posts, filter.sort, filter.query);
  const [fetchPosts, isPostsLoading, postError] = useFetching(
    async (limit, page) => {
      const response = await EmployeeService.getAll(limit, page);
      setPosts(response.data);
      const totalCount = (await GetNumberService.getEmployeesNumber()).data;
      setTotalPages(getPagesCount(totalCount, limit));
    }
  );

  const [fetchPositions, isPositionsLoading, positionError] = useFetching(
    async (limit, page) => {
      const response = await EmployeeService.getPositions(limit, page);
      setPositions(response.data);
      console.log(response);
    }
  );

  const [fetchRanks, isRanksLoading, rankError] = useFetching(
    async (limit, page) => {
      const response = await EmployeeService.getRanks(limit, page);
      setRanks(response.data);
      console.log(response);
    }
  );

  const [fetchDepartments, isDepartmentsLoading, departmentError] = useFetching(
    async (limit, page) => {
      const response = await EmployeeService.getDepartments(limit, page);
      setDepartments(response.data);
      console.log(response);
    }
  );

  useEffect(() => {
    fetchPosts(limit, page);
  }, [page]);

  useEffect(() => {
    fetchPositions(limit, page);
  }, [page]);

  useEffect(() => {
    fetchRanks(limit, page);
  }, [page]);

  useEffect(() => {
    fetchDepartments(limit, page);
  }, [page]);
  return (
    <div className="App">
      <MyNavbar></MyNavbar>
      <BrowserRouter>
        <Switch>
          <Route path="/departments" component={Departments} />
          <Route path="/ranks" component={Ranks} />
          <Route path="/positions" component={Positions} />
          <Route
            path="/"
            component={() => Employees({ positions, departments, ranks })}
          />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
