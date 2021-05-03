/**
 * 
 */
package cn.repeatlink.module.law.controller;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.repeatlink.common.bean.HeaderVo;
import cn.repeatlink.common.mapper.SysUserMapper;
import cn.repeatlink.framework.aspectj.annotation.RLPermission;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.controller.base.BaseController;
import cn.repeatlink.module.law.vo.LawLoginUserInfo;

/**
 * @author LAI
 * @date 2020-09-28 14:34
 */

@RLPermission(userType = {UserType.LAW, UserType.JUDICIAL, UserType.SYS})
public class BaseLawController extends BaseController {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	protected LawLoginUserInfo loginUserInfo() {
		LawLoginUserInfo info = new LawLoginUserInfo();
		Long userId = super.loginUserId();
		info.setUser_id(userId);
		Record sysUser = Db.findFirst("select * from sys_user where user_id = ?", userId);
		info.setUserType(UserType.userType(sysUser.getStr("user_type")));
		if(UserType.JUDICIAL.equals(info.getUserType())) {
			Record record = Db.findFirst("select * from user_judicial where login_id = ?", sysUser.getStr("user_name"));
			info.setJudicial_user_id(record.getStr("user_id"));
			info.setGroup_id(record.getStr("group_id"));
		} else if(UserType.LAW.equals(info.getUserType())) {
			Record record = Db.findFirst("select * from law_group_user where sys_user_id = ? ", userId);
			info.setGroup_id(record.getStr("group_id"));
		} else if(UserType.SYS.equals(info.getUserType())) {
			info.setAll(true);
		}
		
		return info;
	}
	
	protected String judicialUserId() {
		return loginUserInfo().getJudicial_user_id();
	}
	
	protected String groupId() {
		return loginUserInfo().getGroup_id();
	}
	
	protected void export(OutputStream out, String type, Map<String, String> titles, List<?> datas) {
		if(datas == null) {
			datas = new ArrayList<>();
		}
		if("xlsx".equals(type)) {
			ExcelWriter writer = ExcelUtil.getWriter(true);
			if(titles != null) {
				for(String title : titles.keySet()) {
					writer.addHeaderAlias(title, titles.get(title));
				}
				writer.setOnlyAlias(true);
			}
			writer.write(datas);
			writer.flush(out);
		}
		else if("xls".equals(type)) {
			ExcelWriter writer = ExcelUtil.getWriter();
			if(titles != null) {
				for(String title : titles.keySet()) {
					writer.addHeaderAlias(title, titles.get(title));
				}
				writer.setOnlyAlias(true);
			}
			writer.write(datas);
			writer.flush(out);
		}
		else if("csv".equals(type)) {
			OutputStreamWriter writer;
			try {
				writer = new OutputStreamWriter(out, "UTF-8");
				CsvWriter csvWriter = CsvUtil.getWriter(writer);
				Set<String> keys = null;
				if(titles != null) {
					Collection<String> values = titles.values();
					keys = titles.keySet();
					csvWriter.write(values.toArray(new String[0]));
				} else {
					if(datas != null && datas.size() > 0) {
						Map<String, Object> map = BeanUtil.beanToMap(datas.get(0));
						keys = map.keySet();
					}
				}
				Kv f = Kv.create();
				for(Object obj : datas) {
					Map<String, Object> map = BeanUtil.beanToMap(obj);
					List<String> values = new ArrayList<>();
					for(String key : keys) {
						Object value = map.get(key);
						if(value instanceof Date) {
							String format = f.getStr(key);
							if(StringUtils.isBlank(format)) {
								Field field = ClassUtil.getDeclaredField(obj.getClass(), key);
								JsonFormat annotation = field.getAnnotation(JsonFormat.class);
								if(annotation != null) {
									format = annotation.pattern();
								}
								if(StringUtils.isBlank(format)) {
									format = "yyyy-MM-dd HH:mm:ss";
								}
								f.put(key, format);
							}
							value = DateUtil.format((Date)value, format);
						}
						values.add(value != null ? value.toString() : null);
					}
					csvWriter.write(values.toArray(new String[0]));
				}
				csvWriter.flush();
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
	protected Map<String, String> headerToMap(List<HeaderVo> headerList) {
		Map<String, String> map = null;
		if(headerList != null && headerList.size() > 0) {
			map = new LinkedHashMap<>();
			for (HeaderVo headerVo : headerList) {
				map.put(headerVo.getDataIndex(), headerVo.getTitle());
			}
		}
		return map;
	}

}
