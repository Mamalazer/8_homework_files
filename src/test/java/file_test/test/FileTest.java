package file_test.test;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import file_test.model.Order;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipFile;

import static com.codeborne.pdftest.PDF.containsText;
import static file_test.helper.Helper.getFileStreamByName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTest {
    static ZipFile zipFile;
    static ClassLoader cl;

    @BeforeAll
    static void getZipFile() throws IOException {
        zipFile = new ZipFile("src/test/resources/Docs.zip");
        cl = FileTest.class.getClassLoader();
    }

    @DisplayName("Проверка содержания pdf файла")
    @Test
    public void pdfParseTest() throws IOException {
        PDF pdf = new PDF(getFileStreamByName(zipFile, "Reglament.pdf"));
        assertThat(pdf, containsText("ОАО «ВымпелКом»"));
    }

    @DisplayName("Проверка содержания excel файла")
    @Test
    public void excelParseTest() throws IOException {
        XLS xls = new XLS(getFileStreamByName(zipFile, "GeoMDMS.xlsx"));
        Row row = xls.excel.getSheetAt(0).getRow(0);
        assertEquals("№", row.getCell(0).getStringCellValue());
        assertEquals("Проверки", row.getCell(1).getStringCellValue());
        assertEquals("Тип проверки", row.getCell(2).getStringCellValue());
        assertEquals("Кол-во ТК", row.getCell(3).getStringCellValue());
    }

    @DisplayName("Проверка содержания csv файла")
    @Test
    public void csvParseTest() throws IOException, CsvException {
        CSVReader csvReader = new CSVReader(
                new InputStreamReader(getFileStreamByName(zipFile, "Coordinates.csv"), StandardCharsets.UTF_8));
        List<String[]> strings = csvReader.readAll();
        assertEquals("Широта", strings.get(0)[0]);
        assertEquals("Долгота", strings.get(0)[1]);
    }

    @DisplayName("Проверка содержания json файла")
    @Test
    public void jsonParseTest() throws IOException {
        Order order = new ObjectMapper()
                .readValue(cl.getResourceAsStream("order.json"), Order.class);
        assertEquals("ООО \"ЭВОТОР\"", order.getItems().get(0).getSupplier().getName());
        assertEquals("AccountV2:6670384603:667001001", order.getPaymentMethodId());
        assertEquals("6670384603", order.getRequisites().getInn());
    }
}
