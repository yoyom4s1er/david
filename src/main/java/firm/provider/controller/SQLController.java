package firm.provider.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/sql")
@AllArgsConstructor
public class SQLController {

    DataSource dataSource;

    @PostMapping
    public ResponseEntity executeQuery(@RequestBody String query) {
        try (Connection conn = dataSource.getConnection()) {

            List<Map<String, String>> extractingData = new ArrayList<>();

            Statement statement = conn.createStatement();
            statement.execute(query);

            if (query.startsWith("select") || query.startsWith("SELECT")) {
                ResultSet resultSet = statement.executeQuery(query);

                ResultSetMetaData rsMetaData = resultSet.getMetaData();
                int count = rsMetaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, String> params = new HashMap<>();
                    for (int i = 1; i <= count; i++) {
                        params.put(rsMetaData.getColumnName(i), resultSet.getString(i));
                    }
                    extractingData.add(params);
                }
            }

            if (!extractingData.isEmpty()) {
                return ResponseEntity.ok(extractingData);
            }

            return ResponseEntity.ok().build();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }
}
