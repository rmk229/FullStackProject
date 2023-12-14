package kz.yermek.testproject.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import kz.yermek.testproject.models.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Employee> employeeList;

    public ExcelExporter(List<Employee> employeeDtoList) {
        this.employeeList = employeeDtoList;
        workbook = new XSSFWorkbook();
    }

    public void writeHeaderLine() {
        sheet = workbook.createSheet("Сотрудники");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Персональный номер", style);
        createCell(row, 1, "ФИО", style);
        createCell(row, 2, "Дата рождения", style);
        createCell(row, 3, "Должность", style);
        createCell(row, 4, "Звание", style);
        createCell(row, 5, "Подразделение", style);
        createCell(row, 6, "Дата заключения контракта", style);
        createCell(row, 7, "Срок контракта (в годах)", style);
        createCell(row,8, "Дата завершения контракта", style);


    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Employee employee : employeeList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            //Корректное отображение даты в виде String, использованы
            //1. Переход от LocalDate к виду Date
            //2. Переход от Date к String с использованием DateFormat
            Date dateOfBirthDate = null;
            if (employee.getDateOfBirth() != null) {
                dateOfBirthDate = java.util.Date.from(employee.getDateOfBirth().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
            }

            Date dateOfSignDate = null;
            if (employee.getDateOfSign() != null) {
                dateOfSignDate = java.util.Date.from(employee.getDateOfSign().atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
            }
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            //Автоматическое высчитывание даты завершения контракта
            Date dateEndContractDate = null;
            if (employee.getDateOfSign() != null) {
                LocalDate dateEndContract = employee.getDateOfSign().plusYears(employee.getContractPeriod());
                dateEndContractDate = java.util.Date.from(dateEndContract.atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
            }

            //Заполнение строк, данными конкретного сотрудника
            createCell(row, columnCount++, employee.getPersonalNumber(), style);
            createCell(row, columnCount++, employee.getSecondName() + " " + employee.getFirstName() + " " + employee.getThirdName(), style);
            createCell(row, columnCount++, dateFormat.format(dateOfBirthDate), style);
            createCell(row, columnCount++, employee.getPosition().getPositionName(), style);
            createCell(row, columnCount++, employee.getRank().getRankName(), style);
            createCell(row, columnCount++, employee.getDepartment().getDepartmentName(), style);
            createCell(row, columnCount++, dateFormat.format(dateOfSignDate), style);
            createCell(row, columnCount++, employee.getContractPeriod(), style);
            createCell(row, columnCount++, dateFormat.format(dateEndContractDate), style);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}