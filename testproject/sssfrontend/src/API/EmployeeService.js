import axios from "axios";

export default class EmployeeService {
  static async getAll(limit = 10, page = 1) {
    const response = await axios.get("http://localhost:8000/employees", {
      params: {
        _limit: limit,
        _page: page,
      },
    });
    return response;
  }

  static async getPositions(limit = 10, page = 1) {
    const response = await axios.get("http://localhost:8000/positions", {
      params: {
        _limit: limit,
        _page: page,
      },
    });
    return response;
  }

  static async getRanks(limit = 10, page = 1) {
    const response = await axios.get("http://localhost:8000/ranks", {
      params: {
        _limit: limit,
        _page: page,
      },
    });
    return response;
  }

  static async getDepartments(limit = 10, page = 1) {
    const response = await axios.get("http://localhost:8000/departments", {
      params: {
        _limit: limit,
        _page: page,
      },
    });
    return response;
  }

  static async getImages(limit = 10, page = 1) {
    const response = await axios.get("http://localhost:8000/images", {
      params: {
        _limit: limit,
        _page: page,
      },
    });
    return response;
  }
}
