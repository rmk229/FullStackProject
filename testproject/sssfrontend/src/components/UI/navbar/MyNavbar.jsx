import React from "react";
import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import NavDropdown from "react-bootstrap/NavDropdown";

const MyNavbar = () => {
  return (
    <Navbar className="bg-body-tertiary" expand="lg">
      <Container>
        <Navbar.Brand href="/">
          <img
            alt=""
            src="https://upload.wikimedia.org/wikipedia/commons/a/a1/State_Security_Service_of_Kazakhstan_seal.png"
            width="30"
            height="30"
            className="d-inline-block align-top"
          />{" "}
          Test project
        </Navbar.Brand>

        {/* Add a toggle button for responsive design */}
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ml-auto">
            <Nav.Link href="/">Home</Nav.Link>

            {/* Add a dropdown menu */}
            <NavDropdown title="ДЗП" id="basic-nav-dropdown">
              <NavDropdown.Item href="/positions">Должности</NavDropdown.Item>
              <NavDropdown.Item href="/ranks">Звания</NavDropdown.Item>
              <NavDropdown.Item href="/departments">
                Подразделения
              </NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default MyNavbar;
