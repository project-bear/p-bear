package info.bear.utils.json;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackjson处理json和object之间的转化
 * 
 * @author zhouchenguang
 */
public class JsonUtils {

	private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

	/**
	 * 获取ObjectMapper对象
	 * 
	 * @return
	 */
	public static ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	/**
	 * 将对象转化成Json格式
	 * 
	 * @param t
	 * @return
	 */
	public static <T> String writeValueAsString(T t) {
		try {
			return getObjectMapper().writeValueAsString(t);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json数据转化成javaBean
	 * 
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T readValue(String json, Class<T> valueType) {
		try {
			return getObjectMapper().readValue(json, valueType);
		} catch (JsonParseException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json数据转化成javaBean, 支持泛型
	 * 
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T readValue(String json, TypeReference<T> typeReference) {
		try {
			return getObjectMapper().readValue(json, typeReference);
		} catch (JsonParseException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json数据转化成pojo
	 * 
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T readValue(String json, JavaType javaType) {
		try {
			return getObjectMapper().readValue(json, javaType);
		} catch (JsonParseException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	

	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param collectionClass
	 *            泛型的Collection 10
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 */

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return getObjectMapper().getTypeFactory().constructParametrizedType(collectionClass, collectionClass,
				elementClasses);
	}
}
