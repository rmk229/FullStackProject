import React from "react";
import DepartmentItem from "./DepartmentItem";
import { CSSTransition, TransitionGroup } from "react-transition-group";

import "bootstrap/dist/css/bootstrap.min.css";
import Card from "react-bootstrap/Card";

const DepartmentList = ({ posts, departmentName, remove, update }) => {
  if (!posts.length) {
    return <h1 style={{ textAlign: "center" }}>Подразделения не найдены!</h1>;
  }
  return (
    <div>
      <h1 style={{ textAlign: "center" }}>{departmentName}</h1>
      <TransitionGroup>
        {posts.map((post, index) => (
          <CSSTransition key={post.id} timeout={500} classNames="post">
            <DepartmentItem
              update={update}
              remove={remove}
              number={index + 1}
              post={post}
            />
          </CSSTransition>
        ))}
      </TransitionGroup>
    </div>
  );
};

export default DepartmentList;
