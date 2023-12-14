import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Card from "react-bootstrap/Card";
import MyButton from "./UI/button/MyButton";

const PositionItem = (props) => {
  return (
    <Card style={{ width: "50rem", margin: "10px" }}>
      <Card.Body>
        <Card.Title>{"Должность: "}</Card.Title>
        <Card.Text>
          <strong>Наименование должности:</strong> {props.post.positionName}
          <br />
        </Card.Text>
        <div className="post__btns">
          <MyButton onClick={() => props.remove(props.post)}>Удалить</MyButton>
        </div>
        <div className="post__btns">
          <MyButton
            style={{ marginTop: 10, marginBottom: 5 }}
            onClick={() => props.update(props.post)}
          >
            Детали
          </MyButton>
        </div>
      </Card.Body>
    </Card>
  );
};

export default PositionItem;
