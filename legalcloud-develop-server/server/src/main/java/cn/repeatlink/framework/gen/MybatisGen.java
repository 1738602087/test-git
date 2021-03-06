package cn.repeatlink.framework.gen;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import sun.nio.cs.Surrogate;

public class MybatisGen {
	 public static void main(String[] args) throws Exception {
	        List<String> warnings = new ArrayList<String>();
	        boolean overwrite = true;
	        ConfigurationParser cp = new ConfigurationParser(warnings);
	        Configuration config = cp.parseConfiguration(
	                Surrogate.Generator.class.getResourceAsStream("/mybatis-generator/generatorConfig.xml"));
	        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
	        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
	        myBatisGenerator.generate(null);
	        System.out.println("finish");
	    }
}
