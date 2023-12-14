import React from "react";
import PositionItem from "./PositionItem";
import { CSSTransition, TransitionGroup } from "react-transition-group";

import "bootstrap/dist/css/bootstrap.min.css";
import Card from "react-bootstrap/Card";

const PositionList = ({ posts, positionName, remove, update }) => {
  if (!posts.length) {
    return <h1 style={{ textAlign: "center" }}>Должности не найдены!</h1>;
  }
  return (
    <div>
      <h1 style={{ textAlign: "center" }}>{positionName}</h1>
      <TransitionGroup>
        {posts.map((post, index) => (
          <CSSTransition key={post.id} timeout={500} classNames="post">
            <PositionItem
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

export default PositionList;
