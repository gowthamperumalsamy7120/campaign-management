package org.management.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.management.entity.Recipient;
import org.management.interfaces.SubscriptionStatus;
import org.management.repository.RecipientRepository;
import org.management.utils.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class RecipientBulkUploadService {

    @Autowired
    private RecipientService service;
    @Autowired
    private RecipientRepository repository;

    public ResponseModel uploadExcelData(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            List<Map<String, Object>> jsonList = new ArrayList<>();
            for (int i = 3; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, Object> rowData = new HashMap<>();
                    rowData.put("Name", getCellValue(row.getCell(1)));
                    rowData.put("Email", getCellValue(row.getCell(2)));
                    rowData.put("Subscription Status", getCellValue(row.getCell(3)));
                    jsonList.add(rowData);
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writeValueAsString(jsonList);
            ResponseModel response = this.processData(jsonData);
            return response;

        } catch (Exception e) {
            return new ResponseModel(false, "Error: " + e.getMessage());
        }
    }

    public ResponseModel processData(String jsonData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> rackList = mapper.readValue(jsonData, new TypeReference<>() {
            });

            for (Map<String, Object> data : rackList) {
                Recipient recipient = convertToRecipient(data);
                saveFunction(recipient);
            }
            return new ResponseModel(true, "Rack data uploaded successfully.", null);
        } catch (Exception e) {
            return new ResponseModel(false, "JSON processing failed: " + e.getMessage(), null);
        }
    }

    private Recipient convertToRecipient(Map<String, Object> data) {

        String name = (String) data.getOrDefault("Name", null);
        String emailAddress = (String) data.getOrDefault("Email", null);
        String subscriptionStatus = (String) data.getOrDefault("Subscription Status", null);

        Recipient recipient = null;
        if (repository.existsByEmail(emailAddress)) {
            recipient.setName(name);
            recipient.setEmail(emailAddress);
            recipient.setSubscriptionStatus(SubscriptionStatus.valueOf(subscriptionStatus));
        } else {
            recipient = recipient.builder()
                    .name(name)
                    .email(emailAddress)
                    .subscriptionStatus(SubscriptionStatus.valueOf(subscriptionStatus))
                    .build();
        }
        return recipient;
    }

    public void saveFunction(Recipient recipient) {
        this.repository.save(recipient);
    }

    //---------------------------- Common Function---------------------------------------

    private Boolean parseBooleanFlexible(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        String str = value.toString().trim().toLowerCase();
        return str.equals("true") || str.equals("1") || str.equals("yes");
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    dateFormat.setTimeZone(TimeZone.getDefault());
                    yield dateFormat.format(cell.getDateCellValue());
                } else {
                    yield String.valueOf(cell.getNumericCellValue());
                }
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> null;
        };
    }
}
