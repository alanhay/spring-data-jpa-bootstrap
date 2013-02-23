package uk.co.certait.spring.data.repository;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang.time.DateUtils;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.InsertOperation;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Base class to faciliate testing of database operation. Leverages Spring
 * functionality to ensure that each individual test runs in it's own
 * transaction with any changes to the database during the test rolled back when
 * the test completes. This ensures that all tests are executed against a known
 * dataset.
 * 
 * To define a dataset to be used for the execution of tests specified for a
 * test class, simply override the getDataSetPaths() to return the location of
 * the dbunit xml files required for the test.
 * 
 * To view the state of the database after a text execution - call setComplete()
 * after the save operation to commit the transaction and prevent rollback at
 * the end of the test.
 * 
 * @author alanhay
 * 
 */
@ContextConfiguration(value = { "classpath:applicationContextRepository.xml", "classpath:applicationContextDatasource.xml",
		"classpath:applicationContextProfiles.xml" })
public abstract class AbstractBaseDatabaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	private static final String MYSQL_PROFILE_NAME = "mysql";
	private static final String SQL_SERVER_PROFILE_NAME = "mssql";
	private static final String HSQLDB_PROFILE_NAME = "hsql";

	@Autowired
	private ApplicationContext context;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	protected JdbcTemplate template;

	@Before
	public void loadTestData() throws Exception {
		template = new JdbcTemplate(dataSource);
		Connection con = DataSourceUtils.getConnection(dataSource);
		IDatabaseConnection dbUnitCon = new DatabaseConnection(con);

		try {
			if (isDefaultProfile() || isMySqlProfile()) {
				dbUnitCon.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
				InsertOperation.CLEAN_INSERT.execute(dbUnitCon, loadClasspathDataSet(getDataSetPaths()));
			}
			else if (isHsqlDbProfile()) {
				dbUnitCon.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
				InsertOperation.CLEAN_INSERT.execute(dbUnitCon, loadClasspathDataSet(getDataSetPaths()));
			}
			else if (isSqlServerProfile()) {
				dbUnitCon.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MsSqlDataTypeFactory());
				InsertIdentityOperation.CLEAN_INSERT.execute(dbUnitCon, loadClasspathDataSet(getDataSetPaths()));
			}
		}
		finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}

	protected void flushPersistenceContext() {
		EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory).flush();
	}
	
	protected void clearPersistenceContext(){
		EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory).clear();
	}

	/**
	 * 
	 * @return Subclasses should override to return the paths to the files
	 *         required to populate the Database for the current set of tests.
	 * 
	 * @throws Exception
	 */
	public abstract String[] getDataSetPaths();

	public IDataSet loadClasspathDataSet(String[] names) {
		IDataSet dataset = null;
		FlatXmlDataSet[] dataSets = new FlatXmlDataSet[names.length];

		try {
			for (int i = 0; i < names.length; ++i) {
				dataSets[i] = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(names[i]));
			}

			dataset = new CompositeDataSet(dataSets);
		}
		catch (Exception ex) {
			try {
				for (int i = 0; i < names.length; ++i) {
					dataSets[i] = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/" + names[i]));
				}

				dataset = new CompositeDataSet(dataSets);
			}
			catch (Exception e) {
				logger.debug("Error loading XML DataSet", e);
			}
		}

		Map<String, Object> objectMap = new HashMap<String, Object>(1);
		objectMap.put("(NULL)", null);

		return new ReplacementDataSet(dataset, objectMap, null);
	}

	private boolean isDefaultProfile() {
		return context.getEnvironment().getActiveProfiles().length == 0;
	}

	private boolean isMySqlProfile() {
		return isProfile(MYSQL_PROFILE_NAME);
	}

	private boolean isHsqlDbProfile() {
		return isProfile(HSQLDB_PROFILE_NAME);
	}

	private boolean isSqlServerProfile() {
		return isProfile(SQL_SERVER_PROFILE_NAME);
	}

	private boolean isProfile(String profileName) {
		return context.getEnvironment().getActiveProfiles()[0].equals(profileName);
	}

	public Date createDate(int date, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);

		return DateUtils.truncate(cal.getTime(), Calendar.DATE);
	}

	public Date createDate(int hour, int minute, int date, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date, hour, minute);

		return DateUtils.truncate(cal.getTime(), Calendar.MINUTE);
	}
}
