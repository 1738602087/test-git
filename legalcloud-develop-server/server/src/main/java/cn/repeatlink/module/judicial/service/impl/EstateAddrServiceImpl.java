/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.repeatlink.module.judicial.service.IEstateAddrService;
import cn.repeatlink.module.judicial.vo.estate.EstateAddrTagVo;
import cn.repeatlink.module.judicial.vo.estate.EstateAddrVo;

/**
 * @author LAI
 * @date 2020-12-08 10:33
 */

@Service
public class EstateAddrServiceImpl implements IEstateAddrService {
	
	@Override
	public List<EstateAddrTagVo> getAllAreas() {
		List<EstateAddrTagVo> voList = new ArrayList<>();
		List<Record> rList = Db.find("select * from estate_addr where `status` = ? and type = ? ", 1, "addr1");
		if(rList != null && rList.size() > 0) {
			Map<String, EstateAddrTagVo> tags = new HashMap<>();
			for(Record record : rList) {
				String tag = record.getStr("tag");
				EstateAddrTagVo tagVo = tags.get(tag);
				if(tagVo == null) {
					tagVo = new EstateAddrTagVo();
					tagVo.setTag(tag);
					tagVo.setAddrs(new ArrayList<>());
					voList.add(tagVo);
					tags.put(tag, tagVo);
				}
				String text = record.getStr("text");
				String name = record.getStr("name");
				String name_code = record.getStr("name_backup");
				EstateAddrVo vo = new EstateAddrVo();
				vo.setText(text);
				vo.setName(name);
				vo.setName_code(name_code);
				vo.setFulltext(record.getStr("fulltext"));
				vo.setCode(record.getStr("code"));
				vo.setNext(true);
				tagVo.getAddrs().add(vo);
			}
		}
		return voList;
	}
	
	@Override
	public List<EstateAddrTagVo> getNext(String code, String text, String fulltext) {
		List<EstateAddrTagVo> voList = new ArrayList<>();
		Record p = Db.findFirst("select * from estate_addr where `code`=? and `text`=? and `fulltext`=?", code, text, fulltext);
		if(p != null) {
			Long pid = p.getLong("id");
			List<Record> rList = Db.find("select * from estate_addr where `status` = ? and pid = ? ", 1, pid);
			if(rList != null && rList.size() > 0) {
				Map<String, EstateAddrTagVo> tags = new HashMap<>();
				for(Record record : rList) {
					String tag = record.getStr("tag");
					EstateAddrTagVo tagVo = tags.get(tag);
					if(tagVo == null) {
						tagVo = new EstateAddrTagVo();
						tagVo.setTag(tag);
						tagVo.setAddrs(new ArrayList<>());
						voList.add(tagVo);
						tags.put(tag, tagVo);
					}
					Long id = record.getLong("id");
					String text1 = record.getStr("text");
					String name = record.getStr("name");
					String name_code = record.getStr("name_backup");
					EstateAddrVo vo = new EstateAddrVo();
					vo.setText(text1);
					vo.setName(name);
					vo.setName_code(name_code);
					vo.setFulltext(record.getStr("fulltext"));
					vo.setCode(record.getStr("code"));
					vo.setNext(Db.queryInt("select count(id) from estate_addr where pid = ? and `status` = 1",id) > 0);
					tagVo.getAddrs().add(vo);
				}
			}
		}
		return voList;
	}

}
