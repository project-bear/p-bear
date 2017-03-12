package info.bear.utils.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xml工具类
 * 
 * @author zhouchenguang
 */
public class XmlUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(XmlUtils.class);
	
	/**
	 * 获取Document
	 * @param xml
	 * @return
	 */
	public static Document getDocument(String xml){
		try {
			return DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将document转化成Map
	 * @param doc
	 * @return
	 */
	public static Map<String, Object> Dom2Map(Document doc) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (doc == null)
			return resultMap;
		Element root = doc.getRootElement();
		for (Iterator<?> iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List<?> list = e.elements();
			if (list.size() > 0) {
				resultMap.put(e.getName(), Dom2Map(e));
			} else
				resultMap.put(e.getName(), e.getText());
		}
		return resultMap;
	}

	
	public static Map<String, Object> Dom2Map(Element e) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<?> list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List<Object> mapList = new ArrayList<Object>();

				if (iter.elements().size() > 0) {
					Map<String, Object> m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList<Object>();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List<Object>) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList<Object>();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List<Object>) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}
}
