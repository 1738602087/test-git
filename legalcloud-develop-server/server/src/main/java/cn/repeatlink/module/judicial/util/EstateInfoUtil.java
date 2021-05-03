/**
 * 
 */
package cn.repeatlink.module.judicial.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jfinal.kit.Kv;

import cn.hutool.core.convert.Convert;

/**
 * 房产情报解析工具
 * @author LAI
 * @date 2021-01-05 10:37
 */
public class EstateInfoUtil {
	/** 空白字符 */
	private static final String[] BLANK_STR = {"", "　", "", "", " "};
	
	public static Kv parse(String text) {
		// 一行一行处理
		String[] ss = text.split(System.lineSeparator());
		Kv kv = Kv.create();
		boolean record_flag = false;
		boolean info_flag = false;
		boolean info_name_flag = false;
		List<String> titles = null;
		List<String> values = null;
		List<String> presrcvalues = null;
		for(String str : ss) {
			str = trimBlank(str);
			if(str.startsWith("┃") && str.endsWith("┃")) {
				str = str.substring(1, str.length() - 1);
				String src = str;
				str = replaceBlank(src);
				// 不動産番号
				if(str.contains("不動産番号")) {
					kv.set("不動産番号", queryEstateNum(str));
				}
				// 基本信息
				else if(info_flag || str.contains("種類") || str.contains("原因及びその日付")) {
					if(record_flag) {
						continue;
					}
					if(!info_flag) {
						titles = queryInfoTitles(str);
						values = Arrays.asList(new String[titles.size()]);
						info_flag = true;
					}
					else {
						List<String> _values = queryInfoValues(str);
						List<String> _srcvalues = queryInfoValues(src);
						if(_values == null) {
							info_flag = false;
							for(int i = 0; i < titles.size(); i++) {
								kv.set(titles.get(i), values.get(i));
							}
							// 结束解析
							record_flag = true;
							continue;
						} else {
							values = mergeValues(values, _values, _srcvalues, presrcvalues);
						}
						presrcvalues = _srcvalues;
						// 每一行解析设置内容
						for(int i = 0; i < titles.size(); i++) {
							kv.set(titles.get(i), values.get(i));
						}
					}
				}
				// 权利者分析
				else if(info_name_flag || str.contains("登記の目的") || str.contains("権利者その他の事項")) {
					if(!info_name_flag) {
						titles = queryInfoTitles(str);
						values = Arrays.asList(new String[titles.size()]);
						info_name_flag = true;
					}
					else {
						List<String> _values = queryInfoValues(str);
						List<String> _srcvalues = queryInfoValues(src);
						if(_values == null) {
							info_name_flag = false;
							for(int i = 0; i < titles.size(); i++) {
								kv.set(titles.get(i), values.get(i));
							}
							// 结束解析
							break;
						} else {
							values = mergeValues(values, _values, _srcvalues, presrcvalues);
						}
						presrcvalues = _srcvalues;
						// 每一行解析设置内容
						for(int i = 0; i < titles.size(); i++) {
							kv.set(titles.get(i), values.get(i));
						}
					}
				}
			}
			// 
			else if(str.startsWith("┗")) {
				if(info_flag) {
					info_flag = false;
					for(int i = 0; i < titles.size(); i++) {
						kv.set(titles.get(i), values.get(i));
					}
				}
			}
		}
		 
		return kv;
	}
	
	private static String queryEstateNum(String str) {
		String[] ss = str.split("│");
		boolean flag = false;
		for(String s : ss) {
			s = s.trim();
			if(flag) {
				return Convert.toDBC(s);
			}
			if(s.equals("不動産番号")) {
				flag = true;
			}
		}
		return null;
	}
	
	private static List<String> queryInfoTitles(String str) {
		List<String> titles = new ArrayList<>();
		String[] ss = str.split("│");
		for(String s : ss) {
			s = s.replace("①", "").replace("②", "").replace("③", "");
			titles.add(s);
		}
		return titles;
	}
	
	private static List<String> queryInfoValues(String str) {
		if(str.contains("表題部") || str.contains("所有者│") || str.contains("権利部")) return null;
		if(str.startsWith("┗")) return null;
		List<String> values = new ArrayList<>();
		String[] ss = str.split("│");
		for(String s : ss) {
			values.add(s);
		}
		return values;
	}
	
	private static List<String> mergeValues(List<String> values, List<String> _values, List<String> _srcvalues, List<String> presrcvalues) {
		for(int i = 0 ; i < values.size(); i++) {
			String v = values.get(i);
			if(v == null) v= "";
			if( i < _values.size() && i < _srcvalues.size()) {
				String _v = _values.get(i);
				String _srcv = _srcvalues.get(i);
				String _srcvpre = presrcvalues != null && presrcvalues.size() > i ? presrcvalues.get(i) : null;
				if(_v != null && _v.trim().length() > 0 && !_v.trim().equals("：")) {
					if(v.length() > 0) {
						v = v + ((_srcv != null && replaceBlank(_srcv).length() > 0 && startsWithBlank(_srcv) || (_srcvpre != null && (replaceBlank(_srcvpre).length() == 0 || endsWithBlank(_srcvpre)))) ? "\r\n" : "") +  _v.trim();
					} else {
						v = _v.trim();
					}
				}
				values.set(i, v);
			}
		}
		return values;
	}
	
	
	private static boolean startsWithBlank(String str) {
		for(String b : BLANK_STR) {
			if(str.startsWith(b)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean endsWithBlank(String str) {
		for(String b : BLANK_STR) {
			if(str.endsWith(b)) {
				return true;
			}
		}
		return false;
	}
	
	public static String replaceBlank(String str) {
		for(String b : BLANK_STR) {
			str = str.trim().replace(b, "");
		}
		return str;
	}

	public static String trimBlank(String str) {
		str = str.trim();
		for(String b : BLANK_STR) {
			while(str.length() > 0 && str.charAt(0) == b.charAt(0)) {
				str = str.substring(1);
			}
			while(str.length() > 0 && str.charAt(str.length() - 1) == b.charAt(0)) {
				str = str.substring(0, str.length() - 1);
			}
		}
		return str;
	}
	
	public static void main(String[] args) {
		String text = PdfUtil.getFullText(new File("E:\\12_gitlab_projects\\07_legalcloud\\server\\src\\main\\resources\\demo\\estate_pdf\\4.pdf"));
		Kv kv = parse(text);
		System.out.println(kv);
	}
}
