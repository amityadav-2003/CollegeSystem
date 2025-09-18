package com.amstech.std.system.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amstech.std.system.entity.City;
import com.amstech.std.system.entity.User;
import com.amstech.std.system.repo.CityRepo;
import com.amstech.std.system.repo.UserRepo;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Service
public class ExcelService {

	 public void usersToExcel(List<User> users, OutputStream outputStream) throws IOException {
	        log.info("Excel export initiated");
	        
	        String[] columns = { "First Name", "Last Name", "Email", "Mobile Number", "Address", "Date of Birth", "Is Active", "City Id" };
	        
	        try (Workbook workbook = new XSSFWorkbook()) {
	            Sheet sheet = workbook.createSheet("Users");
	            
	            // Create header row
	            Row headerRow = sheet.createRow(0);
	            for (int col = 0; col < columns.length; col++) {
	                Cell cell = headerRow.createCell(col);
	                cell.setCellValue(columns[col]);
	            }
	            
	            // Create data rows
	            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	            int rowIdx = 1;
	            for (User user : users) {
	                Row row = sheet.createRow(rowIdx++);
	                row.createCell(0).setCellValue(user.getFirstName() != null ? user.getFirstName() : "");
	                row.createCell(1).setCellValue(user.getLastName() != null ? user.getLastName() : "");
	                row.createCell(2).setCellValue(user.getEmail() != null ? user.getEmail() : "");
	                row.createCell(3).setCellValue(user.getMobileNumber() != null ? user.getMobileNumber() : "");
	                row.createCell(4).setCellValue(user.getAddress() != null ? user.getAddress() : "");
	                row.createCell(5).setCellValue(user.getDateOfBirth() != null ? dateFormatter.format(user.getDateOfBirth()) : "");
	                row.createCell(6).setCellValue(user.getIsActive() != null && user.getIsActive() == 1 ? "Yes" : "No");
	                row.createCell(7).setCellValue(user.getCity() != null ? user.getCity().getId() : null);
	            }
	            
	            // Auto-size columns
	            for (int i = 0; i < columns.length; i++) {
	                sheet.autoSizeColumn(i);
	            }
	            
	            // Write the workbook to the provided OutputStream
	            workbook.write(outputStream);
	            log.info("Excel export successful");
	        }
	    }
    
    
    @Autowired
    private UserRepo userRepository;
 
    @Autowired
    private CityRepo cityRepository;
    public void importUsersFromExcel(InputStream is) throws Exception {
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();
        // Skip header row
        if (rows.hasNext()) {
            rows.next();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        List<User> users = new ArrayList<>();
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            User user = new User();
            user.setFirstName(getCellValueAsString(currentRow.getCell(0)));
            user.setLastName(getCellValueAsString(currentRow.getCell(1)));
            user.setEmail(getCellValueAsString(currentRow.getCell(2)));
            user.setMobileNumber(getCellValueAsString(currentRow.getCell(3)));
            user.setAddress(getCellValueAsString(currentRow.getCell(4)));
            String dobStr = getCellValueAsString(currentRow.getCell(5));
            if (dobStr != null && !dobStr.isEmpty()) {
                try {
                    java.util.Date dob = dateFormatter.parse(dobStr);
                    user.setDateOfBirth(dob);
                } catch (Exception e) {
                    user.setDateOfBirth(null);
                }
            }
            String isActiveStr = getCellValueAsString(currentRow.getCell(6));
            if ("Yes".equalsIgnoreCase(isActiveStr)) {
                user.setIsActive(1);
            } else {
                user.setIsActive(0);
            }
            String cityIdStr = getCellValueAsString(currentRow.getCell(7));
            if (cityIdStr != null && !cityIdStr.isEmpty()) {
                try {
                    int cityId = Integer.parseInt(cityIdStr);
                    City city = cityRepository.findById(cityId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid CityId: " + cityId + " at row " + currentRow.getRowNum()));
                    user.setCity(city);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid CityId format at row " + currentRow.getRowNum());
                }
            } else {
                throw new IllegalArgumentException("CityId cannot be null or empty at row " + currentRow.getRowNum());
            }
            users.add(user);
        }
        workbook.close();
        // Save all users to DB
        userRepository.saveAll(users);
    }
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Format date as yyyy-MM-dd
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    return dateFormat.format(cell.getDateCellValue());
                } else {
                    // For numeric cells, remove .0 if integer
                    double d = cell.getNumericCellValue();
                    if (d == (int) d) {
                        return String.valueOf((int) d);
                    } else {
                        return String.valueOf(d);
                    }
                }
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}
