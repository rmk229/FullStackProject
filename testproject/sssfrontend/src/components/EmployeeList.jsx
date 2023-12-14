import React from "react";
import EmployeeItem from "./EmployeeItem";
import { CSSTransition, TransitionGroup } from "react-transition-group";

import "bootstrap/dist/css/bootstrap.min.css";
import Card from "react-bootstrap/Card";

const EmployeeList = ({ posts, secondName, remove, update }) => {
  if (!posts.length) {
    return <h1 style={{ textAlign: "center" }}>Работники не найдены!</h1>;
  }
  return (
    <div>
      <h1 style={{ textAlign: "center" }}>{secondName}</h1>
      <TransitionGroup>
        {posts.map((post, index) => (
          <CSSTransition key={post.id} timeout={500} classNames="post">
            <EmployeeItem
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

export default EmployeeList;
