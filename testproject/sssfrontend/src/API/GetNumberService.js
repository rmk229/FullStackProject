import axios from "axios";

export default class PostService {
  static async getEmployeesNumber() {
    const response = await axios.get("http://localhost:8000/employees");
    return response;
  }

  static async getPositionsNumber() {
    const response = await axios.get("http://localhost:8000/positions");
    return response;
  }

  static async getRanksNumber() {
    const response = await axios.get("http://localhost:8000/ranks");
    return response;
  }

  static async getDepartmentsNumber() {
    const response = await axios.get("http://localhost:8000/departments");
    return response;
  }
}
