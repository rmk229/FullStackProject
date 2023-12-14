import React from "react";
import RankItem from "./RankItem";
import { CSSTransition, TransitionGroup } from "react-transition-group";

import "bootstrap/dist/css/bootstrap.min.css";
import Card from "react-bootstrap/Card";

const RankList = ({ posts, rankName, remove, update }) => {
  if (!posts.length) {
    return <h1 style={{ textAlign: "center" }}>Звания не найдены!</h1>;
  }
  return (
    <div>
      <h1 style={{ textAlign: "center" }}>{rankName}</h1>
      <TransitionGroup>
        {posts.map((post, index) => (
          <CSSTransition key={post.id} timeout={500} classNames="post">
            <RankItem
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

export default RankList;
