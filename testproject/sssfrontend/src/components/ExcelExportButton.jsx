import React from "react";

const ExcelExportButton = () => {
  return (
    <a
      style={{ marginTop: 30 }}
      className="btn btn-primary"
      href="http://localhost:8000/employees/excelExport"
      download
    >
      Экспортировать данные
    </a>
  );
};

export default ExcelExportButton;
