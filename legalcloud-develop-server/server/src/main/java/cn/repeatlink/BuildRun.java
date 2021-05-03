/**
 * 
 */
package cn.repeatlink;

import java.util.Date;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @author LAI
 * @date 2020-12-17 11:02
 */
public class BuildRun {
	
	public static void main(String[] args) throws Exception {
		exec();
	}
	
	private static void exec() {
		
		String str = RuntimeUtil.execForStr("git log --pretty=format:'%h' --oneline -1");
		
		String path = ClassUtil.getLocationPath(BuildRun.class);
		path = FileUtil.file(path).getParentFile().getParentFile().getAbsolutePath();
		
		
		String destPath = path + "/src/main/resources/build.properties";
		FileUtil.touch(destPath);
		
		Props props = new Props(destPath, "UTF-8");
		props.clear();
		
		String git = str.split(" ")[0];
		props.setProperty("git", git);
		props.setProperty("time", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
		
		Document pom = XmlUtil.readXML(path + "/pom.xml");
		NodeList node = pom.getElementsByTagName("project");
		Node item = node.item(0);
		Map<String, Object> map = XmlUtil.xmlToMap(item);
		String version = map.get("version").toString();
		
		props.setProperty("version", version);
		
		props.store(destPath);
		
	}

}
