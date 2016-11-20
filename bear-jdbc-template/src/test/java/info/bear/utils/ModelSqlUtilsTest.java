package info.bear.utils;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import info.bear.exception.NoColumnAnnotationFoundException;
import info.bear.exception.NoIdAnnotationFoundException;
import info.bear.model.Employee;
import info.bear.model.SqlParamsPairs;
import org.junit.Test;

public class ModelSqlUtilsTest {

	@Test
	public void testGetGetFromObject() throws NoIdAnnotationFoundException, NoColumnAnnotationFoundException {
		
		SqlParamsPairs sqlAndParams = ModelSqlUtils.getGetFromObject(Employee.class, 3);
		
		assertThat(sqlAndParams.getSql(),is("select * from employee where id = ?"));
		
	}

}
