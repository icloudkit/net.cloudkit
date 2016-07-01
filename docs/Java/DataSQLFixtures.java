import javax.sql.DataSource;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

/**
 * 初始化数据脚本
 */
public class DataSQLFixtures {

	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	public static void executeScript(DataSource dataSource, String... sqlResourcePaths) throws DataAccessException {
		SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);

		for (String sqlResourcePath : sqlResourcePaths) {
			SimpleJdbcTestUtils.executeSqlScript(simpleJdbcTemplate, resourceLoader, sqlResourcePath, true);
		}
	}
}
