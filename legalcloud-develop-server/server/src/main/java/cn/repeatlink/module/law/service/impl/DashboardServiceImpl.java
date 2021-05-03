/**
 *
 */
package cn.repeatlink.module.law.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.engine.enjoy.EnjoyEngine;
import cn.repeatlink.framework.common.Constant.UserType;
import cn.repeatlink.framework.util.MessageUtil;
import cn.repeatlink.module.general.common.Constant.GeneralMarriage;
import cn.repeatlink.module.law.mapper.DashboardMapper;
import cn.repeatlink.module.law.service.IDashboardService;
import cn.repeatlink.module.law.vo.LawLoginUserInfo;
import cn.repeatlink.module.law.vo.SeachVo;
import cn.repeatlink.module.law.vo.cases.CaseInfo;
import cn.repeatlink.module.law.vo.dash.DashInfoVo;
import cn.repeatlink.module.law.vo.dash.LawGroupItemVo;
import cn.repeatlink.module.law.vo.dash.SaleRecordInfo;
import cn.repeatlink.module.law.vo.user.GeneralInfo;

/**
 * @author LAI
 * @date 2020-10-12 15:53
 */

@Service
public class DashboardServiceImpl implements IDashboardService {

    @Autowired
    private DashboardMapper dashboardMapper;

    private final String JUDEGE_ZERO = "0";
    private final String JUDEGE_ONE = "1";

    @Override
    public DashInfoVo getNewNumberAndPercent(LawLoginUserInfo userInfo) {
        DashInfoVo vo = new DashInfoVo();
        vo.setId("customer");
        vo.setType("number");
        String month = DateUtil.format(new Date(), "yyyy-MM");
        String premonth = DateUtil.format(DateUtil.offsetMonth(new Date(), -1), "yyyy-MM");
        Long n1 = 0L, n2 = 0L;
        if (UserType.SYS.equals(userInfo.getUserType())) {
            String sql = "select count(*) from user_general where DATE_FORMAT( create_time, '%Y-%m' ) = ? and `status` = 1";
            n1 = Db.queryLong(sql, month);
            n2 = Db.queryLong(sql, premonth);
        } else if (UserType.LAW.equals(userInfo.getUserType())) {
            String sql = "select count(*) from user_general where DATE_FORMAT( create_time, '%Y-%m' ) = ? and law_group_id=? and `status` = 1";
            n1 = Db.queryLong(sql, month, userInfo.getGroup_id());
            n2 = Db.queryLong(sql, premonth, userInfo.getGroup_id());
        } else if(UserType.JUDICIAL.equals(userInfo.getUserType())) {
        	String sql = "select count(*) from user_general where DATE_FORMAT( create_time, '%Y-%m' ) = ? and law_group_id=? and judicial_id=? and `status` = 1";
            n1 = Db.queryLong(sql, month, userInfo.getGroup_id(), userInfo.getJudicial_user_id());
            n2 = Db.queryLong(sql, premonth, userInfo.getGroup_id(), userInfo.getJudicial_user_id());
        }

        vo.setNum(n1.intValue());
        vo.setPercent(n2 == 0 ? "-" : ((int) (((n1 - n2) * 1D / n2) * 100)) + "");
        return vo;
    }

    @Override
    public List<Kv> getNewNumberTran(LawLoginUserInfo userInfo, String type) {
        List<Kv> list = new ArrayList<>();
        Date nowdate = new Date();
//		String startMonth = DateUtil.format(, "yyyy-MM");
//		String endMonth = DateUtil.format(DateUtil.endOfYear(nowdate), "yyyy-MM");
        String[] cates = null;
        String[] cateAlias = null;
        StringBuffer sql = new StringBuffer("select count( distinct ug.user_id) n, DATE_FORMAT( ug.create_time,");
        String fM = null, pTime = null, cJoin = null, cWhere = null;
        List<Object> params = new ArrayList<>();
        if ("week".equals(type)) {
        	fM = " '%w' ";
        	pTime = " '%Y-%m' ";
        	params.add(DateUtil.format(nowdate, "yyyy-MM"));
        	cates = new String[] {"1", "2", "3", "4", "5", "6", "0"};
        	cateAlias = new String[] {"月", "火", "水", "木", "金", "土", "日"};
        } else if ("day".equals(type)) {
        	fM = " '%e' ";
        	pTime = " '%Y-%m' ";
        	params.add(DateUtil.format(nowdate, "yyyy-MM"));
        	int maxDays = DateUtil.dayOfMonth(DateUtil.endOfMonth(nowdate));
        	cates = new String[maxDays];
        	cateAlias = new String[maxDays];
        	for (int i = 1; i <= maxDays; i++) {
        		cates[i-1] = "" + i;
        		cateAlias[i-1] = cates[i-1];
        	}
        } else {
        	fM = " '%Y-%m' ";
        	pTime = " '%Y' ";
        	params.add(DateUtil.format(nowdate, "yyyy"));
        	Date startDate = DateUtil.beginOfYear(nowdate);
        	cates = new String[12];
        	cateAlias = new String[12];
        	for (int i = 0; i < 12; i++) {
        		cates[i] = DateUtil.format(DateUtil.offsetMonth(startDate, i), "yyyy-MM");
        		cateAlias[i] = cates[i];
        	}
        }
        
        sql.append(" #(fM) ");
        sql.append(" ) m from user_general ug #(cJoin) where DATE_FORMAT( ug.create_time,  ");
        sql.append(" #(pTime) ");
        sql.append("  ) = ? and ug.`status` = 1 ");
        if(UserType.LAW.equals(userInfo.getUserType())) {
        	sql.append(" and ug.law_group_id=? ");
        	params.add(userInfo.getGroup_id());
        } else if (UserType.JUDICIAL.equals(userInfo.getUserType())) {
        	sql.append(" and ug.judicial_id=? ");
        	params.add(userInfo.getJudicial_user_id());
        }
        sql.append(" #(cWhere) ");
        
        sql.append(" GROUP BY m ");
        
        // アプリインストール人数
        List<Record> list3 = Db.find(new EnjoyEngine().getTemplate(sql.toString())
        		.render(Kv.by("fM", fM).set("pTime", pTime).set("cJoin", cJoin).set("cWhere", cWhere)), params.toArray());
        String name = MessageUtil.getMessage("law.dashboard.customer.type.reg.login");
        for(int i = 0 ; i < cates.length ; i++) {
        	Kv kv = Kv.by("label", cateAlias[i]).set("name", name);
        	for(Record r : list3) {
        		String m = r.getStr("m");
        		if(cates[i].equals(m)) {
        			kv.set("value", r.getLong("n"));
        			break;
        		}
        	}
        	if(kv.get("value") == null) {
        		kv.set("value", 0);
        	}
        	list.add(kv);
        }
        
        // 案件登録人数
        cJoin = " left join case_buyer cb on cb.user_general_id = ug.user_id and cb.`status`=1 ";
        cWhere = " and cb.id is not null ";
        list3 = Db.find(new EnjoyEngine().getTemplate(sql.toString())
        		.render(Kv.by("fM", fM).set("pTime", pTime).set("cJoin", cJoin).set("cWhere", cWhere)), params.toArray());
        name = MessageUtil.getMessage("law.dashboard.customer.type.case.login");
        for(int i = 0 ; i < cates.length ; i++) {
        	Kv kv = Kv.by("label", cateAlias[i]).set("name", name);
        	for(Record r : list3) {
        		String m = r.getStr("m");
        		if(cates[i].equals(m)) {
        			kv.set("value", r.getLong("n"));
        			break;
        		}
        	}
        	if(kv.get("value") == null) {
        		kv.set("value", 0);
        	}
        	list.add(kv);
        }
        return list;
    }

    @Override
    public DashInfoVo getProcessCaseCountAndPercent(LawLoginUserInfo userInfo) {
        DashInfoVo vo = new DashInfoVo();
        vo.setId("case");
        vo.setType("number");
        String month = DateUtil.format(new Date(), "yyyy-MM");
        String premonth = DateUtil.format(DateUtil.offsetMonth(new Date(), -1), "yyyy-MM");
        Long n1 = 0L, n2 = 0L;
        if (UserType.JUDICIAL.equals(userInfo.getUserType())) {
            n1 = this.dashboardMapper.getProcessCaseCountByMonthByJudicial(userInfo.getJudicial_user_id(), month);
            n2 = this.dashboardMapper.getProcessCaseCountByMonthByJudicial(userInfo.getJudicial_user_id(), premonth);
        } else if (UserType.LAW.equals(userInfo.getUserType())) {
            n1 = this.dashboardMapper.getProcessCaseCountByMonthByGroup(userInfo.getGroup_id(), month);
            n2 = this.dashboardMapper.getProcessCaseCountByMonthByGroup(userInfo.getGroup_id(), premonth);
        }

        vo.setNum(n1.intValue());
        vo.setPercent(n2 == 0 ? "-" : ((int) (((n1 - n2) * 1D / n2) * 100)) + "");
        return vo;
    }
    
    @Override
    public DashInfoVo getLawGroupDashInfo(LawLoginUserInfo userInfo) {
        DashInfoVo vo = new DashInfoVo();
        vo.setId("law");
        vo.setType("number");
        String month = DateUtil.format(new Date(), "yyyy-MM");
        String premonth = DateUtil.format(DateUtil.offsetMonth(new Date(), -1), "yyyy-MM");
        Long n1 = 0L, n2 = 0L;
        if (UserType.SYS.equals(userInfo.getUserType())) {
        	String sql = "select count(*) from law_group where `status` = 1 and DATE_FORMAT( create_time, '%Y-%m' ) = ? ";
            n1 = Db.queryLong(sql, month);
            n2 = Db.queryLong(sql, premonth);
        }

        vo.setNum(n1.intValue());
        vo.setPercent(n2 == 0 ? "-" : ((int) (((n1 - n2) * 1D / n2) * 100)) + "");
        return vo;
    }

    @Override
    public String getProcessCaseCompletePercent(LawLoginUserInfo userInfo) {
        String percent = "-";
        Long n1 = 0L, n2 = 0L;
        String sql = "select ifnull(sum(if(`status`=1,1,0)),0) n1, sum(if(`status`=2,1,0)) n2 from case_estate where `status` != 0 and DATE_FORMAT( create_time, '%Y-%m' ) = ? ";
        Record record = Db.findFirst(sql, DateUtil.format(new Date(), "yyyy-MM"));
        n1 = record.getLong("n1");
        n2 = record.getLong("n2");
        percent = (n1 == 0 ? 0 : (int) (1F * n2 / n1 * 100)) + "";
        return percent;
    }

    @Override
    public List<Kv> getCaseCompletedRank(LawLoginUserInfo userInfo) {
        List<Kv> list = new ArrayList<>();
        int max = 6;
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<>();
        sql.append(" SELECT                                                                        ");
        sql.append(" 	ce.count,                                                                  ");
        sql.append(" 	ce.complete_count,                                                         ");
        sql.append(" 	ce.process_count,                                                          ");
        sql.append(" 	ifnull( u.fullname, u.fullname_kana ) fullname                             ");
        sql.append(" FROM                                                                          ");
        sql.append(" 	(                                                                          ");
        sql.append(" 	SELECT                                                                     ");
        sql.append(" 		user_judicial_id,                                                      ");
        sql.append(" 		COUNT( * ) AS count,                                                   ");
        sql.append(" 		IFNULL( SUM( IF ( step_reg_finish = 1, 1, 0 ) ), 0 ) complete_count,   ");
        sql.append(" 		IFNULL( SUM( IF ( step_reg_finish = 0, 1, 0 ) ), 0 ) process_count     ");
        sql.append(" 	FROM                                                                       ");
        sql.append(" 		case_estate                                                            ");
        sql.append(" 	WHERE                                                                      ");
        sql.append(" 		`status` != 0                                                          ");
        sql.append(" 		AND DATE_FORMAT( create_time, '%Y-%m' ) = ?                    ");
        params.add(DateUtil.format(new Date(), "yyyy-MM"));
        sql.append(" 	GROUP BY                                                                   ");
        sql.append(" 		user_judicial_id                                                       ");
        sql.append(" 	ORDER BY                                                                   ");
        sql.append(" 		count DESC                                                             ");
        sql.append(" 		LIMIT " + max + "                                                                ");
        sql.append(" 	) ce                                                                       ");
        sql.append(" 	LEFT JOIN user_judicial u ON u.user_id = ce.user_judicial_id               ");
        sql.append(" WHERE  1=1                                                                       ");
        if(!UserType.SYS.equals(userInfo.getUserType())) {
        	sql.append(" 	AND u.group_id = ?                                                             ");
        	params.add(userInfo.getGroup_id());
        }
        sql.append(" ORDER BY                                                                      ");
        sql.append(" 	ce.count DESC,                                                             ");
        sql.append(" 	fullname DESC                                                              ");
        List<Record> list2 = Db.find(sql.toString(), params.toArray());
        
        int index = 1;
        String name1 = MessageUtil.getMessage("law.dashboard.case.type.process");
        String name2 = MessageUtil.getMessage("law.dashboard.case.type.completed");
        for (int i = 0; i < max; i++) {
        	Long pCount = 0L, cCount = 0L;
        	String fullname = null;
            if (list2 != null && list2.size() > i) {
                Long count = list2.get(i).getLong("complete_count");
                cCount = count == null ? 0L : count;
                count = list2.get(i).getLong("process_count");
                pCount = count == null ? 0L : count;
                fullname = list2.get(i).getStr("fullname");
            }
            if(StringUtils.isBlank(fullname)) {
            	fullname = MessageUtil.getMessage("law.dashboard.name.empty.replace", "" + index++);
            }
            list.add(Kv.by("name", name1).set("label", fullname).set("value", pCount));
            list.add(Kv.by("name", name2).set("label", fullname).set("value", cCount));
        }

        return list;
    }

    @Override
    public DashInfoVo getSaleCountAndPercent(LawLoginUserInfo userInfo) {
        DashInfoVo vo = new DashInfoVo();
        vo.setId("sale");
        vo.setType("money");
        String month = DateUtil.format(new Date(), "yyyy-MM");
        String premonth = DateUtil.format(DateUtil.offsetMonth(new Date(), -1), "yyyy-MM");
        Long n1 = 0L, n2 = 0L;
        if (UserType.SYS.equals(userInfo.getUserType())) {
            String sql = "select ifnull(sum(ifnull(actual_price,0)),0) amount from estate_protection_order where DATE_FORMAT( pay_time, '%Y-%m' ) = ? and `status` = 1 ";
            n1 = Db.queryLong(sql, month);
            n2 = Db.queryLong(sql, premonth);
        }

        vo.setNum(n1.intValue());
        vo.setPercent(n2 == 0 ? "-" : ((int) (((n1 - n2) * 1D / n2) * 100)) + "");
        return vo;
    }

    @Override
    public List<Kv> getSaleTran(LawLoginUserInfo userInfo, String type) {
        List<Kv> list = new ArrayList<>();
        Date nowdate = new Date();
        
        String[] cates = null;
        String[] cateAlias = null;
        StringBuffer sql = new StringBuffer("select ifnull(sum(ifnull(actual_price,0)),0) n, DATE_FORMAT( pay_time, ");
        String fM = null, pTime = null;
        List<Object> params = new ArrayList<>();
        if ("week".equals(type)) {
        	fM = " '%w' ";
        	pTime = " '%Y-%m' ";
        	params.add(DateUtil.format(nowdate, "yyyy-MM"));
        	cates = new String[] {"1", "2", "3", "4", "5", "6", "0"};
        	cateAlias = new String[] {"月", "火", "水", "木", "金", "土", "日"};
        } else if ("day".equals(type)) {
        	fM = " '%e' ";
        	pTime = " '%Y-%m' ";
        	params.add(DateUtil.format(nowdate, "yyyy-MM"));
        	int maxDays = DateUtil.dayOfMonth(DateUtil.endOfMonth(nowdate));
        	cates = new String[maxDays];
        	cateAlias = new String[maxDays];
        	for (int i = 1; i <= maxDays; i++) {
        		cates[i-1] = "" + i;
        		cateAlias[i-1] = cates[i-1];
        	}
        } else {
        	fM = " '%Y-%m' ";
        	pTime = " '%Y' ";
        	params.add(DateUtil.format(nowdate, "yyyy"));
        	Date startDate = DateUtil.beginOfYear(nowdate);
        	cates = new String[12];
        	cateAlias = new String[12];
        	for (int i = 0; i < 12; i++) {
        		cates[i] = DateUtil.format(DateUtil.offsetMonth(startDate, i), "yyyy-MM");
        		cateAlias[i] = cates[i];
        	}
        }
        
        sql.append(" #(fM) ");
        sql.append(" ) m from estate_protection_order where DATE_FORMAT( pay_time,  ");
        sql.append(" #(pTime) ) =? ");
        sql.append("  and `status` = 1 GROUP BY m ");
        
        List<Record> list2 = Db.find(new EnjoyEngine().getTemplate(sql.toString())
        		.render(Kv.by("fM", fM).set("pTime", pTime)), params.toArray());
        String name = MessageUtil.getMessage("law.dashboard.sale.type.amount");
        for(int i = 0 ; i < cates.length ; i++) {
        	Kv kv = Kv.by("label", cateAlias[i]).set("name", name);
        	for(Record r : list2) {
        		String m = r.getStr("m");
        		if(cates[i].equals(m)) {
        			kv.set("value", r.getLong("n"));
        			break;
        		}
        	}
        	if(kv.get("value") == null) {
        		kv.set("value", 0);
        	}
        	list.add(kv);
        }
        

        return list;
    }

    @Override
    public List<GeneralInfo> getGeneralInfos(LawLoginUserInfo userInfo, SeachVo date) {
        //查询数据
        List<GeneralInfo> list = this.dashboardMapper.getGeneralInfos(userInfo.getGroup_id(), userInfo.getJudicial_user_id(), date.getStar_time(), date.getEnd_time());
        for (GeneralInfo gen : list) {
            gen.setId(IdUtil.simpleUUID().substring(0, 8));
            if (StrUtil.equals(gen.getGender(),JUDEGE_ZERO)){
                gen.setGender("女");
            }else if (StrUtil.equals(gen.getGender(),JUDEGE_ONE)){
                gen.setGender("男");
            }else {
                gen.setGender("未知");
            }
            if (StrUtil.equals(gen.getMarriage(), GeneralMarriage.MARRIED + "")){
                gen.setMarriage("既婚");
            }else if (StrUtil.equals(gen.getMarriage(), GeneralMarriage.DIVORCE + "")) {
                gen.setMarriage("離婚");
            } else {
            	gen.setMarriage("未婚");
            }
        }
        return list;
    }

    @Override
    public List<CaseInfo> getCaseInfos(LawLoginUserInfo userInfo, SeachVo data) {
        //查询数据
        List<CaseInfo> list = this.dashboardMapper.getCaseInfos(userInfo.getGroup_id(), userInfo.getJudicial_user_id(), data.getStar_time(), data.getEnd_time());
        for (CaseInfo caseInfo : list) {
            if(StrUtil.equals(caseInfo.getStepBuyerInput(),JUDEGE_ONE)){
                caseInfo.setStepBuyerInput("済み");
            }else {
                caseInfo.setStepBuyerInput("未済");
            }
            if(StrUtil.equals(caseInfo.getStepSellerVerify(), "-1")) {
            	caseInfo.setStepSellerVerify("無し");
            }
            else if(StrUtil.equals(caseInfo.getStepSellerVerify(),JUDEGE_ONE)){
                caseInfo.setStepSellerVerify("済み");
            }else {
                caseInfo.setStepSellerVerify("未済");
            }
            if(StrUtil.equals(caseInfo.getStepDealFinish(),JUDEGE_ONE)){
                caseInfo.setStepDealFinish("済み");
            }else {
                caseInfo.setStepDealFinish("未済");
            }
            if(StrUtil.equals(caseInfo.getStepRegFinish(),JUDEGE_ONE)){
                caseInfo.setStepRegFinish("済み");
            }else {
                caseInfo.setStepRegFinish("未済");
            }
            caseInfo.setId(IdUtil.simpleUUID().substring(0, 8));
        }
        return list;
    }

    @Override
    public List<SaleRecordInfo> getServerInfos(LawLoginUserInfo userInfo, SeachVo data) {
        final String str = "**** **** **** ";
        //查询数据
        List<SaleRecordInfo> list = this.dashboardMapper.getServerInfos(data.getStar_time(), data.getEnd_time());
        for (SaleRecordInfo serverInfo : list) {
            if (StrUtil.isNotBlank(serverInfo.getCardNo()))
                serverInfo.setCardNo(str + serverInfo.getCardNo());
            if (StrUtil.equals(serverInfo.getStatus(), "5")) {
                serverInfo.setStatus("成功");
            } else {
                serverInfo.setStatus("失敗");
            }
            serverInfo.setId(IdUtil.simpleUUID().substring(0, 8));
        }
        return list;
    }
    
	@Override
    public List<Kv> getLawNumberTran(LawLoginUserInfo userInfo, String type) {
        List<Kv> list = new ArrayList<>();
        Date nowdate = new Date();
//		String startMonth = DateUtil.format(, "yyyy-MM");
//		String endMonth = DateUtil.format(DateUtil.endOfYear(nowdate), "yyyy-MM");
        String[] cates = null;
        String[] cateAlias = null;
        StringBuffer sql = new StringBuffer("select count( distinct group_id) n, DATE_FORMAT( create_time,");
        String fM = null, pTime = null, cJoin = null, cWhere = null;
        List<Object> params = new ArrayList<>();
        if ("week".equals(type)) {
        	fM = " '%w' ";
        	pTime = " '%Y-%m' ";
        	params.add(DateUtil.format(nowdate, "yyyy-MM"));
        	cates = new String[] {"1", "2", "3", "4", "5", "6", "0"};
        	cateAlias = new String[] {"月", "火", "水", "木", "金", "土", "日"};
        } else if ("day".equals(type)) {
        	fM = " '%e' ";
        	pTime = " '%Y-%m' ";
        	params.add(DateUtil.format(nowdate, "yyyy-MM"));
        	int maxDays = DateUtil.dayOfMonth(DateUtil.endOfMonth(nowdate));
        	cates = new String[maxDays];
        	cateAlias = new String[maxDays];
        	for (int i = 1; i <= maxDays; i++) {
        		cates[i-1] = "" + i;
        		cateAlias[i-1] = cates[i-1];
        	}
        } else {
        	fM = " '%Y-%m' ";
        	pTime = " '%Y' ";
        	params.add(DateUtil.format(nowdate, "yyyy"));
        	Date startDate = DateUtil.beginOfYear(nowdate);
        	cates = new String[12];
        	cateAlias = new String[12];
        	for (int i = 0; i < 12; i++) {
        		cates[i] = DateUtil.format(DateUtil.offsetMonth(startDate, i), "yyyy-MM");
        		cateAlias[i] = cates[i];
        	}
        }
        
        sql.append(" #(fM) ");
        sql.append(" ) m from law_group where DATE_FORMAT( create_time,  ");
        sql.append(" #(pTime) ");
        sql.append("  ) = ? and `status` = 1 ");
        
        sql.append(" GROUP BY m ");
        
        // 登録事務所数
        List<Record> list3 = Db.find(new EnjoyEngine().getTemplate(sql.toString())
        		.render(Kv.by("fM", fM).set("pTime", pTime).set("cJoin", cJoin).set("cWhere", cWhere)), params.toArray());
        String name = MessageUtil.getMessage("law.dashboard.law.type.amount");
        for(int i = 0 ; i < cates.length ; i++) {
        	Kv kv = Kv.by("label", cateAlias[i]).set("name", name);
        	for(Record r : list3) {
        		String m = r.getStr("m");
        		if(cates[i].equals(m)) {
        			kv.set("value", r.getLong("n"));
        			break;
        		}
        	}
        	if(kv.get("value") == null) {
        		kv.set("value", 0);
        	}
        	list.add(kv);
        }
        
        return list;
    }
	
	@Override
    public List<LawGroupItemVo> getLawGorupInfos(LawLoginUserInfo userInfo, SeachVo vo) {
		List<LawGroupItemVo> datas = this.dashboardMapper.getLawGorupInfos(vo.getStar_time(), vo.getEnd_time());
		
		
		return datas;
    }

}
