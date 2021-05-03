package generator;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.config.rules.PropertyInfo;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
    public class CodeGenerator {

        /**
         * <p>
         * 读取控制台内容
         * </p>
         */
        public static String scanner(String tip) {
            Scanner scanner = new Scanner(System.in);
            StringBuilder help = new StringBuilder();
            help.append("请输入" + tip + "：");
            System.out.println(help.toString());
            if (scanner.hasNext()) {
                String ipt = scanner.next();
                if (StringUtils.isNotEmpty(ipt)) {
                    return ipt;
                }
            }
            throw new MybatisPlusException("请输入正确的" + tip + "！");
        }

        public static void main(String[] args) {
            // 代码生成器
            AutoGenerator mpg = new AutoGenerator();
            // 全局配置
            GlobalConfig gc = new GlobalConfig();
            /*这里如果说使用下面的这行代码就会在我们当前的项目目录下生成一个null目录*/
            /*String projectPath = System.getProperty("E:\\IDEA projects\\Test");*/
            final String projectPath = "E:\\IdeaProjects\\Mybatis-plus-Generator";
            gc.setOutputDir(projectPath + "/src/main/java");
            //设置作者
            gc.setAuthor("lxy");
            //是否打开输出目录 默认值:true
            gc.setOpen(false);
            //开启 Swagger2模式 默认false  实体属性 Swagger2 注解
            gc.setSwagger2(true);
            //开启 baseResultMap 默认false
            gc.setBaseResultMap(true);
            //开启 baseColumnList 默认false
            gc.setBaseColumnList(true);
            //是否覆蓋已有文件 默认值：false
            gc.setFileOverride(true);

            mpg.setGlobalConfig(gc);

            // 数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setUrl("jdbc:mysql://localhost:3306/crm?useUnicode=true&useSSL=false&characterEncoding=utf8");
            dsc.setDriverName("com.mysql.jdbc.Driver");
            dsc.setUsername("root");
            dsc.setPassword("123");
            //设置数据库和java实体类的属性对应关系。这里我们如果是mysql数据库我们的这个
           //参数就是 MySqlTypeConvert，如果是Oracle数据库，参数是OracleTypeConvert
            dsc.setTypeConvert(new MySqlTypeConvert(){
                   @Override
                    public PropertyInfo processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                    String t = fieldType.toLowerCase();
                    if (t.contains("char")) {
                        return DbColumnType.STRING;
                    } else {
                        if (!t.contains("date") && !t.contains("timestamp")) {
                            if (t.contains("number")) {
                                if (t.matches("number\\(+\\d\\)")) {
                                    return DbColumnType.INTEGER;
                                }
                                if (t.matches("number\\(+\\d{2}+\\)")) {
                                    return DbColumnType.LONG;
                                }
                                return DbColumnType.DOUBLE;
                            }
                            if (t.contains("float")) {
                                return DbColumnType.FLOAT;
                            }
                            if (t.contains("clob")) {
                                return DbColumnType.STRING;
                            }
                            if (t.contains("blob")) {
                                return DbColumnType.BLOB;
                            }
                            if (t.contains("binary")) {
                                return DbColumnType.BYTE_ARRAY;
                            }
                            if (t.contains("raw")) {
                                return DbColumnType.BYTE_ARRAY;
                            }
                        } else {
                            switch(globalConfig.getDateType()) {
                                case ONLY_DATE:
                                    return DbColumnType.DATE;
                                case SQL_PACK:
                                    return DbColumnType.TIMESTAMP;
                                case TIME_PACK:
                                    return DbColumnType.LOCAL_DATE_TIME;
                            }
                        }
                        return DbColumnType.STRING;
                    }

                     //return super.processTypeConvert(globalConfig, fieldType);
                }
            });
            mpg.setDataSource(dsc);


            // 包配置
            final PackageConfig pc = new PackageConfig();
            //pc.setModuleName(scanner("模块名"));
            pc.setParent("com.lxy");
            pc.setEntity("model");
            pc.setMapper("dao");
            mpg.setPackageInfo(pc);

            // 自定义配置
            InjectionConfig cfg = new InjectionConfig() {
                //注入自定义配置对象
                @Override
                public void initMap() {
                    // to do nothing
                }
            };

            // 如果模板引擎是 freemarker
            //String templatePath = "/templates/mapper.xml.ftl";
            // 如果模板引擎是 velocity
           String templatePath = "/templates/mapper.xml.vm";

            // 自定义输出配置
            List<FileOutConfig> focList=new ArrayList<>();
            focList.add(new FileOutConfig(templatePath) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    //自定义输入文件名称
                    return projectPath+"/src/main/resources/mapper/"+
                            "/"+tableInfo.getEntityName()+"Mapper"+ StringPool.DOT_XML;
                }
            });
            cfg.setFileOutConfigList(focList);
            mpg.setCfg(cfg);

            // 配置模板
            TemplateConfig templateConfig = new TemplateConfig();

            // 配置自定义输出模板
            //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
            // templateConfig.setEntity("templates/entity2.java");
            // templateConfig.setService();
            // templateConfig.setController();

            templateConfig.setXml(null);
            mpg.setTemplate(templateConfig);

            // 策略配置，数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
            StrategyConfig strategy = new StrategyConfig();
            //表名生成策略
            strategy.setNaming(NamingStrategy.underline_to_camel);
            ///数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
            strategy.setColumnNaming(NamingStrategy.underline_to_camel);
            //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
            //实体是否为lombok模型（默认 false)
            strategy.setEntityLombokModel(true);
            //生成 @RestController 控制器
            strategy.setRestControllerStyle(true);
            // 公共父类
            //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
            // 写于父类中的公共字段
            //strategy.setSuperEntityColumns("id");
            strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
            //strategy.setControllerMappingHyphenStyle(true);
           // strategy.setTablePrefix(pc.getModuleName() + "_");
            mpg.setStrategy(strategy);
            mpg.setTemplateEngine(new VelocityTemplateEngine());
            mpg.execute();
        }

    }

