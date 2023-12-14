import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Card from "react-bootstrap/Card";
import MyButton from "./UI/button/MyButton";

const EmployeeItem = (props) => {
  return (
    <Card style={{ width: "50rem", margin: "10px" }}>
      <Card.Body>
        <Card.Title>{`${props.post.secondName} ${props.post.firstName} ${props.post.thirdName}`}</Card.Title>
        <Card.Text
          style={{
            display: "flex",
            alignItems: "flex-start",
            columnGap: "10px",
          }}
        >
          <div>
            <strong>Персональный номер:</strong> {props.post.personalNumber}
            <br />
            <strong>Дата рождения:</strong> {props.post.dateOfBirth}
            <br />
            <strong>Дата заключения контракта:</strong> {props.post.dateOfSign}
            <br />
            <strong>Период контракта:</strong> {props.post.contractPeriod}
            <br />
            <strong>Должность:</strong> {props.post.position.positionName}
            <br />
            <strong>Звание:</strong> {props.post.rank.rankName}
            <br />
            <strong>Подразделение:</strong>{" "}
            {props.post.department.departmentName}
          </div>

          {props.post.images.id && (
            <div>
              <img
                src={`http://localhost:8000/images/${props.post.images.id}`}
                alt="Фото"
                style={{ maxWidth: "300px", maxHeight: "500px" }}
              />
            </div>
          )}
        </Card.Text>
        <div className="post__btns">
          <MyButton
            style={{ marginTop: 30, marginBottom: 10 }}
            onClick={() => props.remove(props.post)}
          >
            Удалить
          </MyButton>
        </div>
        <div className="post__btns">
          <MyButton onClick={() => props.update(props.post)}>Детали</MyButton>
        </div>
      </Card.Body>
    </Card>
  );
};

export default EmployeeItem;
