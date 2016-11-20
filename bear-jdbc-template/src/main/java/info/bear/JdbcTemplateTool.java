package info.bear;

import info.bear.exception.NoColumnAnnotationFoundException;
import info.bear.impl.BatchUpdateSetter;
import info.bear.page.Page;
import info.bear.page.Pagination;
import info.bear.page.Sort;
import org.apache.commons.lang.StringUtils;
import info.bear.exception.NoDefinedGetterException;
import info.bear.exception.NoIdAnnotationFoundException;
import info.bear.model.SqlParamsPairs;
import info.bear.utils.IdUtils;
import info.bear.utils.ModelSqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Enhance JdbcTemplate
 * @author alexy
 *
 */
public class JdbcTemplateTool {

	private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateTool.class);
	
	private JdbcTemplate jdbcTemplate;
	
	//JdbcTemplateTool use proxy instead of directly use jdbcTemplate, cause it can do some AOP function in proxy. That makes code more clear.
	private JdbcTemplateProxy _proxy;
	
	//return the singleton proxy
	private JdbcTemplateProxy getProxy(){
		if(_proxy == null){
			_proxy = new JdbcTemplateProxy();
			_proxy.setJdbcTemplate(jdbcTemplate);
		}
		return _proxy;
	}
	
	// --------- select ------------//

	/**
	 * 获取对象列表
	 * get a list of clazz
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return
	 */
	public <T> List<T> findList(String sql, Object[] params, Class<T> clazz) {
		
		return this.findList(sql, params, clazz, null);
	}
	
	/**
	 * 获取对象列表
	 * get a list of clazz
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findList(String sql, Object[] params, Class<T> clazz, Sort[] sorts) {
		
		StringBuilder sqlSb = new StringBuilder(sql);
		sqlSb = parseSort(sqlSb, sorts);
		List<T> list = null;
		if (params == null || params.length == 0) {
			list = getProxy().query(sqlSb.toString(), new BeanPropertyRowMapper(clazz));
		} else {
			list = getProxy().query(sqlSb.toString(), params, new BeanPropertyRowMapper(clazz));
		}

		return list;
	}

	
	/**
	 * 分页查询对象列表
	 * @param sql
	 * @param params
	 * @param clazz
	 * @param page
	 * @param sorts
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Pagination<T> findListWithPage(String sql, Object[] params, Class<T> clazz, Page page, Sort[] sorts){
		
		String countSql = "SELECT COUNT(1) FROM (" + sql +") t";
		long totalCount = this.findCount(countSql, params);
		page.setTotalCount(totalCount);
		
		StringBuilder pageSqlSb = new StringBuilder(sql);
		
		pageSqlSb = parseSort(pageSqlSb, sorts);
		
		pageSqlSb.append(" LIMIT ").append(page.getStart()).append(", ").append(page.getPageSize());
		
		Pagination<T> pagination = new Pagination<>(page.getPageNo(), totalCount);
		if (params == null || params.length == 0) {
			pagination.setResult(getProxy().query(pageSqlSb.toString(), new BeanPropertyRowMapper(clazz)));
		} else {
			pagination.setResult(getProxy().query(pageSqlSb.toString(), params, new BeanPropertyRowMapper(clazz)));
		}
		
		return pagination;
	}
		
	/**
	 * 分页查询对象列表
	 * @param sql
	 * @param params
	 * @param clazz
	 * @param page
	 * @return
	 */
	public <T> Pagination<T> findListWithPage(String sql, Object[] params, Class<T> clazz, Page page){
		return this.findListWithPage(sql, params, clazz, page, null);
	}
	
	/**
	 * 解析SQL排序
	 * @param pageSqlSb
	 * @param sorts
	 * @return
	 */
	private StringBuilder parseSort(StringBuilder pageSqlSb, Sort[] sorts){
		if(sorts == null){
			return pageSqlSb;
		}
		pageSqlSb.append(" ORDER BY ");
		for(int i=0; i<sorts.length; i++){
			Sort sort = sorts[i];
			if(StringUtils.isBlank(sort.getField())){
				continue;
			}
			pageSqlSb.append(sort.getField()).append(" ").append(sort.getType().name());
			if(i != sorts.length-1){
				pageSqlSb.append(", ");
			}
		}
		return pageSqlSb;
	}
	
	/**
	 * 获取总行数
	 * get count
	 * @param sql
	 * @param params
	 * @return
	 */
	public long findCount(String sql, Object[] params) {
				
		long rowCount = 0;
		try{
			Map<String, Object> resultMap = null;
			if (params == null || params.length == 0) {
				resultMap = getProxy().queryForMap(sql);
			} else {
				resultMap = getProxy().queryForMap(sql, params);
			}
			Iterator<Map.Entry<String, Object>> it = resultMap.entrySet().iterator();
			if(it.hasNext()){
				Map.Entry<String, Object> entry = it.next();
				rowCount = ((Long)entry.getValue()).intValue();
			}
		}catch(EmptyResultDataAccessException e){
			logger.error(e.getMessage());
		}
		
		return rowCount;
	}

	/**
	 * 获取一个对象
	 * get object by id
	 * @param clazz
	 * @return
	 * @throws NoIdAnnotationFoundException
	 * @throws NoColumnAnnotationFoundException
	 * @throws NoDefinedGetterException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T get(Class clazz, Object id) throws NoIdAnnotationFoundException, NoColumnAnnotationFoundException {
		
		//turn class to sql
		SqlParamsPairs sqlAndParams = ModelSqlUtils.getGetFromObject(clazz, id);
		
		//query for list
		List<T> list = this.findList(sqlAndParams.getSql(), sqlAndParams.getParams(), clazz);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// ---------------------------- update -----------------------------------//

	/**
	 * 更新某个对象
	 * update object
	 * @param po
	 * @throws Exception
	 */
	public int update(Object po) throws Exception {
		SqlParamsPairs sqlAndParams = ModelSqlUtils.getUpdateFromObject(po);
		
		return getProxy().update(sqlAndParams.getSql(), sqlAndParams.getParams());
	}
	
	/**
	 * 批量执行更新操作
	 * 
	 * @param sql
	 * @param paramsList
	 */
	public int[] batchUpdate(String sql,List<Object[]> paramsList){
		
		BatchUpdateSetter batchUpdateSetter = new BatchUpdateSetter(paramsList);
		
		return getProxy().batchUpdate(sql, batchUpdateSetter);
	}
	
	public void batchUpdate(String sql,BatchPreparedStatementSetter batchUpdateSetter){
		getProxy().batchUpdate(sql, batchUpdateSetter);
	}

	/**
	 * 保存对象的快捷方法
	 * 如果Id标定的是自增会将自增长的主键自动设置回对象
	 * save object
	 * @param po
	 * @throws Exception
	 */
	public void save(Object po) throws Exception {
		String autoGeneratedColumnName = IdUtils.getAutoGeneratedId(po);
		if(!"".equals(autoGeneratedColumnName)){
			//有自增字段
			int idValue = save(po, autoGeneratedColumnName);
			//把自增的主键值再设置回去
			IdUtils.setAutoIncreamentIdValue(po,autoGeneratedColumnName,IdUtils.castAutoIncreamentIdType(po, autoGeneratedColumnName, idValue));
		}else{
			SqlParamsPairs sqlAndParams = ModelSqlUtils.getInsertFromObject(po);
			
			getProxy().update(sqlAndParams.getSql(), sqlAndParams.getParams());
		}		
	}
	
	/**
	 * 保存对象并返回自增长主键的快捷方法
	 * 
	 * @param po
	 * @param autoGeneratedColumnName
	 *            自增长的主键的列名 比如 user_id
	 * @throws Exception
	 */
	private int save(Object po, String autoGeneratedColumnName) throws Exception {

		SqlParamsPairs sqlAndParams = ModelSqlUtils.getInsertFromObject(po);

		//动态切换库名
		String sql = sqlAndParams.getSql();
		
		return getProxy().insert(sql, sqlAndParams.getParams(), autoGeneratedColumnName);
	}
	
	//-------------------delete-----------------//
	public int delete(Object po) throws Exception{
		
		SqlParamsPairs sqlAndParams = ModelSqlUtils.getDeleteFromObject(po);
		//动态切换库名
		String sql = sqlAndParams.getSql();
		
		return getProxy().update(sql, sqlAndParams.getParams());	
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
