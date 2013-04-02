package uk.co.certait.spring.data.util;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * Addresses a bug in Hibernate 4.1 when ddl.auto set to validate.
 * 
 * @author alanhay
 * 
 */
public class Mysql5BitBooleanDialect extends MySQL5Dialect {

	public Mysql5BitBooleanDialect() {
		super();
		registerColumnType(java.sql.Types.BOOLEAN, "bit");
	}
}
