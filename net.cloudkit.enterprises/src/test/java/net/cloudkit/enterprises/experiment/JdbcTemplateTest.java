//package net.cloudkit.ecological.enterprises.experiment;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowCallbackHandler;
//import org.springframework.stereotype.Component;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class JdbcTemplateTest {
//
//    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateTest.class);
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public void test() {
//
//        Map<String, List<TableColumn>> tableMap = new HashMap<>();
//
//        // String sqlStr = "INSERT INTO TEST(USERNAME, PASSWORD) VALUES(?, ?)";
//        // Object[] params = new Object[]{"", ""};
//        // jdbcTemplate.update(sqlStr, params);
//
//        StringBuffer sql = new StringBuffer();
//        // List<Object> params = new ArrayList<Object>();
//        sql.append("SELECT " +
//                "TABLE_NAME" +
//                ", COLUMN_NAME" +
//                ", DATA_TYPE" +
//                ", IS_NULLABLE" +
//                ", CHARACTER_MAXIMUM_LENGTH" +
//                ", NUMERIC_PRECISION" +
//                ", NUMERIC_SCALE" +
//                ", COLUMN_COMMENT" +
//                ", COLUMN_DEFAULT" +
//                ", COLUMN_KEY" +
//                ", ORDINAL_POSITION");
//        sql.append("  FROM INFORMATION_SCHEMA.COLUMNS ");
//        sql.append(" WHERE TABLE_SCHEMA = ? ");
//
//        jdbcTemplate.query(sql.toString(), new Object[]{"cloudkit"}, new RowCallbackHandler() {
//
//            @Override
//            public void processRow(ResultSet resultSet) throws SQLException {
//
//                TableColumn tableColumn = new TableColumn();
//                tableColumn.setTableName(resultSet.getString("TABLE_NAME"));
//                tableColumn.setColumnName(resultSet.getString("COLUMN_NAME"));
//                tableColumn.setDataType(resultSet.getString("DATA_TYPE"));
//                boolean isNullable = resultSet.getString("IS_NULLABLE").equalsIgnoreCase("YES")? true : false;
//                tableColumn.setIsNullable(isNullable);
//                tableColumn.setCharacterMaximumLength(resultSet.getLong("CHARACTER_MAXIMUM_LENGTH"));
//                tableColumn.setNumericPrecision(resultSet.getInt("NUMERIC_PRECISION"));
//                tableColumn.setNumericScale(resultSet.getInt("NUMERIC_SCALE"));
//                tableColumn.setColumnComment(resultSet.getString("COLUMN_COMMENT"));
//                tableColumn.setColumnDefault(resultSet.getString("COLUMN_DEFAULT"));
//                boolean isPrimaryKey = resultSet.getString("COLUMN_KEY").equalsIgnoreCase("PRI")? true : false;
//                tableColumn.setIsPrimaryKey(isPrimaryKey);
//                tableColumn.setOrdinalPosition(resultSet.getInt("ORDINAL_POSITION"));
//                // String tableName = resultSet.getString("TABLE_NAME");
//                // logger.info("tableName:" + tableName);
//            }
//        });
//    }
//}
