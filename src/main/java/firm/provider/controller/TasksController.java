package firm.provider.controller;

import firm.provider.service.MyUserService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TasksController {

    private MyUserService userService;

    @PostMapping("/1")
    public ResponseEntity<String> checkTask1(@RequestParam(name = "file") MultipartFile excelFile) throws IOException {

        File file = ResourceUtils.getFile("classpath:Task1.xlsx");
        InputStream inputStream = new FileInputStream(file);

        String mail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userService.getUser(mail).get().isTask1Completed()) {
            return ResponseEntity.ok("Задание уже выполнено");
        }

        XSSFWorkbook workbookOriginal = (XSSFWorkbook) WorkbookFactory.create(inputStream);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(excelFile.getInputStream());

        if (
                workbook.getSheetAt(0).getRow(0).getCell(2) != null ||
                        workbook.getSheetAt(0).getRow(0).getCell(0) == null ||
                        workbook.getSheetAt(0).getRow(0).getCell(1) == null) {
            return ResponseEntity.ok("Ошибка. Количество столбцов должно быть равным значению «2».");
        }

        for (int i = 0; i < 957; i++) {
            String cell1Value;
            String cell2Value;

            String cell1ValueCompare;
            String cell2ValueCompare;

            cell1Value = extractValue(workbookOriginal.getSheetAt(0).getRow(i).getCell(0));
            cell2Value = extractValue(workbookOriginal.getSheetAt(0).getRow(i).getCell(1));

            cell1ValueCompare = extractValue(workbook.getSheetAt(0).getRow(i).getCell(0));
            cell2ValueCompare = extractValue(workbook.getSheetAt(0).getRow(i).getCell(1));

            if (i == 0) {
                if (cell1Value.equals(cell2ValueCompare) && cell2Value.equals(cell1ValueCompare)) {
                    userService.addMistakeToTask1(mail);
                    return ResponseEntity.status(420).body("Ошибка. Ячейка A1 должна соответствовать значению «Название_товара», а ячейка B1 должна соответствовать значению «Продажи». Проверьте правильность выбора полей при построении визуализации по осям X и Y. Ось X должна строиться на основе данных поля «Название_товара», а ось Y на основе данных поля «Продажи».");
                }
                if (!cell1Value.equals(cell1ValueCompare) && !cell2Value.equals(cell2ValueCompare)) {
                    userService.addMistakeToTask1(mail);
                    return ResponseEntity.status(420).body("Ошибка. Ячейка A1 должна соответствовать значению «Название_товара», а ячейка B1 должна соответствовать значению «Продажи». Проверьте соответствуют ли названия полей в вашем датасете названиям полей в датасете, который представлен в части теории курса «Подключения и датасеты».");
                }
                if (!cell1Value.equals(cell1ValueCompare)) {
                    userService.addMistakeToTask1(mail);
                    return ResponseEntity.status(420).body("Ошибка. Ячейка A1 должна соответствовать значению «Название_товара». Проверьте соответствуют ли названия полей в вашем датасете названиям полей в датасете, который представлен в части теории курса «Подключения и датасеты».");
                }
                if (!cell2Value.equals(cell2ValueCompare)) {
                    userService.addMistakeToTask1(mail);
                    return ResponseEntity.status(420).body("Ошибка. Ячейка B1 должна соответствовать значению «Продажи». Проверьте соответствуют ли названия полей в вашем датасете названиям полей в датасете, который представлен в части теории курса «Подключения и датасеты».");
                }
            }

            if (!cell1Value.equals(cell1ValueCompare) || !cell2Value.equals(cell2ValueCompare)) {
                userService.addMistakeToTask1(mail);
                if (checkReverse(workbookOriginal, workbook)) {
                    return ResponseEntity.status(420).body("Ошибка. Проверьте метод сортировки на соответствие с заданием.");
                }
                return ResponseEntity.status(420).body("Ошибка. Значения ячеек с A2 по A957 и c B2 по B957 неправильные, попробуйте еще раз.");
            }
        }

        userService.completeTask1(mail);

        return ResponseEntity.ok("Задание выполнено");
    }

    @PostMapping("/2")
    public ResponseEntity<String> checkTask2(@RequestParam("file") MultipartFile excelFile) throws IOException {

        File file = ResourceUtils.getFile("classpath:Task2.xlsx");
        InputStream inputStream = new FileInputStream(file);

        String mail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userService.getUser(mail).get().isTask2Completed()) {
            return ResponseEntity.ok("Задание уже выполнено");
        }

        XSSFWorkbook workbookOriginal = (XSSFWorkbook) WorkbookFactory.create(inputStream);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(excelFile.getInputStream());

        if (
                workbook.getSheetAt(0).getRow(0).getCell(2) != null ||
                        workbook.getSheetAt(0).getRow(0).getCell(0) == null ||
                        workbook.getSheetAt(0).getRow(0).getCell(1) == null) {
            return ResponseEntity.ok("Ошибка. Количество столбцов должно быть равным значению «2».");
        }

        for (int i = 0; i < 6; i++) {
            String cell1Value;
            String cell2Value;

            String cell1ValueCompare;
            String cell2ValueCompare;

            XSSFCell cell1 = workbookOriginal.getSheetAt(0).getRow(i).getCell(0);
            cell1Value = extractValue(cell1);
            XSSFCell cell2 = workbookOriginal.getSheetAt(0).getRow(i).getCell(1);
            cell2Value = extractValue(cell2);

            cell1ValueCompare = extractValue(workbook.getSheetAt(0).getRow(i).getCell(0));
            cell2ValueCompare = extractValue(workbook.getSheetAt(0).getRow(i).getCell(1));

            if (i == 0) {
                if (cell1Value.equals(cell2ValueCompare) && cell2Value.equals(cell1ValueCompare)) {
                    userService.addMistakeToTask2(mail);
                    return ResponseEntity.status(420).body("Ошибка. Ячейка A1 должна соответствовать значению «Менеджер», а ячейка B1 должна соответствовать значению «Продажи». Проверьте правильность выбора полей при построении визуализации по осям X и Y. Ось X должна строиться на основе данных поля «Менеджер», а ось Y на основе данных поля «Продажи».");
                }
                if (!cell1Value.equals(cell1ValueCompare) && !cell2Value.equals(cell2ValueCompare)) {
                    userService.addMistakeToTask2(mail);
                    return ResponseEntity.status(420).body("Ошибка. Ячейка A1 должна соответствовать значению «Менеджер», а ячейка B1 должна соответствовать значению «Продажи». Проверьте соответствуют ли названия полей в вашем датасете названиям полей в датасете, который представлен в части теории курса «Подключения и датасеты».");
                }
                if (!cell1Value.equals(cell1ValueCompare)) {
                    userService.addMistakeToTask2(mail);
                    return ResponseEntity.status(420).body("Ошибка. Ячейка A1 должна соответствовать значению «Менеджер». Проверьте соответствуют ли названия полей в вашем датасете названиям полей в датасете, который представлен в части теории курса «Подключения и датасеты».");
                }
                if (!cell2Value.equals(cell2ValueCompare)) {
                    userService.addMistakeToTask2(mail);
                    return ResponseEntity.status(420).body("Ошибка. Ячейка B1 должна соответствовать значению «Продажи». Проверьте соответствуют ли названия полей в вашем датасете названиям полей в датасете, который представлен в части теории курса «Подключения и датасеты».");
                }
            }

            if (!cell1Value.equals(cell1ValueCompare) || !cell2Value.equals(cell2ValueCompare)) {
                userService.addMistakeToTask2(mail);
                if (checkReverse(workbookOriginal, workbook)) {
                    return ResponseEntity.status(420).body("Ошибка. Проверьте метод сортировки на соответствие с заданием.");
                }
                return ResponseEntity.status(420).body("Ошибка. Значения ячеек с A2 по A6 и c B2 по B6 неправильные, попробуйте еще раз.");
            }
        }

        userService.completeTask2(mail);

        return ResponseEntity.ok("Задание выполнено");
    }

    @PostMapping("/3")
    public ResponseEntity<String> checkTask3(@RequestBody String answer) throws IOException {

        String mail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userService.getUser(mail).get().isTask3Completed()) {
            return ResponseEntity.ok("Задание уже выполнено");
        }

        String compare = "10845553542678988945";

        if (!answer.equals(compare)) {
            userService.addMistakeToTask3(mail);
            return ResponseEntity.status(420).body("Ошибка. Попробуйте еще раз.");
        }

        userService.completeTask3(mail);

        return ResponseEntity.ok("Задание выполнено");
    }

    @PostMapping("/4")
    public Map<String,String> checkTask4(@RequestBody Map<Integer, String> answers) {

        String mail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userService.getUser(mail).get().isTask4Completed()) {
            return new HashMap<>();
        }

        if (
                !answers.containsKey(1) ||
                        !answers.containsKey(2) ||
                        !answers.containsKey(3) ||
                        !answers.containsKey(4) ||
                        !answers.containsKey(5) ||
                        !answers.containsKey(6) ||
                        !answers.containsKey(7)) {
            throw new RuntimeException("Json-файл должен содержать ключи 1-7");
        }

        int mistakesCount = 0;
        Map<String, String> rightAnswers = new HashMap<>();

        if (!answers.get(1).toUpperCase().equals("B")) {
            mistakesCount++;
            rightAnswers.put("1", "B");
        }

        if (!answers.get(2).toUpperCase().equals("C")) {
            mistakesCount++;
            rightAnswers.put("2", "C");
        }

        if (!answers.get(3).toUpperCase().equals("B")) {
            mistakesCount++;
            rightAnswers.put("3", "B");
        }

        if (!answers.get(4).toUpperCase().equals("C")) {
            mistakesCount++;
            rightAnswers.put("4", "C");
        }

        if (!answers.get(5).toUpperCase().equals("B")) {
            mistakesCount++;
            rightAnswers.put("5", "B");
        }

        if (!answers.get(6).toUpperCase().equals("D")) {
            mistakesCount++;
            rightAnswers.put("6", "D");
        }

        if (!answers.get(7).toUpperCase().equals("C")) {
            mistakesCount++;
            rightAnswers.put("7", "C");
        }

        userService.completeTask4(mail, mistakesCount);
        return rightAnswers;
    }

    private String extractValue(XSSFCell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType().equals(CellType.NUMERIC)) {
            return String.valueOf(cell.getNumericCellValue());
        }
        else {
            return cell.getStringCellValue();
        }
    }

    private boolean checkReverse(XSSFWorkbook original, XSSFWorkbook compared) {

        for (int i = 1; i < 957; i++) {
            String cell1Value;
            String cell2Value;

            String cell1ValueCompare;
            String cell2ValueCompare;

            cell1Value = extractValue(original.getSheetAt(0).getRow(i).getCell(0));
            cell2Value = extractValue(original.getSheetAt(0).getRow(i).getCell(1));

            cell1ValueCompare = extractValue(compared.getSheetAt(0).getRow(i).getCell(0));
            cell2ValueCompare = extractValue(compared.getSheetAt(0).getRow(i).getCell(1));


            if (!cell1Value.equals(cell2ValueCompare) || !cell2Value.equals(cell1ValueCompare)) {
                return false;
            }
        }

        return true;
    }
}
