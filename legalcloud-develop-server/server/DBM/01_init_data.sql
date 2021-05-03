
-- 表头配置
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (1, 'manage', 'manager.user.table', 'userName', '账号', 'text', b'1', 1, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (2, 'manage', 'manager.user.table', 'name', '姓名', 'text', b'1', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (3, 'manage', 'manager.user.table', 'email', '邮箱', 'text', b'1', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (4, 'manage', 'manager.user.table', 'phonenumber', '电话号码', 'text', b'1', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (5, 'manage', 'manager.user.table', 'sex', '性别', 'text', b'1', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (6, 'manage', 'manager.user.table', 'enable', '是否启用', 'text', b'0', 6, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (7, 'manage', 'manager.user.table', 'status', '状态', 'number', b'0', 7, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (8, 'manage', 'manager.role.table', 'roleName', '角色名', 'text', b'1', 1, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (9, 'manage', 'manager.role.table', 'dataScope', '数据范围', 'text', b'1', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (10, 'manage', 'manager.role.table', 'createTime', '创建时间', 'text', b'1', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (12, 'manage', 'manager.role.table', 'updateTime', '最后更新时间', 'text', b'1', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (13, 'manage', 'manager.role.table', 'status', '状态', 'number', b'0', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (15, 'manage', 'manager.config.table', 'configName', '配置名', 'text', b'1', 1, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (16, 'manage', 'manager.config.table', 'configKey', '键名', 'text', b'1', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (17, 'manage', 'manager.config.table', 'configValue', '键值', 'text', b'0', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (18, 'manage', 'manager.config.table', 'configType', '系统内置', 'text', b'0', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (19, 'manage', 'manager.config.table', 'status', '状态', 'number', b'0', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (20, 'manage', 'manager.job.table', 'jobName', '任务名', 'text', b'1', 1, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (21, 'manage', 'manager.job.table', 'jobGroup', '任务组名', 'text', b'1', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (22, 'manage', 'manager.job.table', 'invokeTarget', '调用目标', 'text', b'0', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (23, 'manage', 'manager.job.table', 'cronExpression', 'cron执行表达式', 'text', b'0', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (24, 'manage', 'manager.job.table', 'misfirePolicy', '计划执行错误策略', 'text', b'0', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (26, 'manage', 'manager.job.table', 'concurrent', '并发执行', 'text', b'0', 6, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (27, 'manage', 'manager.job.table', 'status', '状态', 'number', b'0', 7, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (29, 'manage', 'manager.dict.table', 'dictLabel', '标签', 'text', b'1', 1, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (30, 'manage', 'manager.dict.table', 'dictValue', '值', 'text', b'1', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (31, 'manage', 'manager.dict.table', 'dictType', '类型', 'text', b'1', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (32, 'manage', 'manager.dict.table', 'isDefault', '默认', 'text', b'0', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (33, 'manage', 'manager.dict.table', 'status', '状态', 'number', b'0', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (34, 'manage', 'manager.dept.table', 'deptName', '部门名称', 'text', b'0', 1, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (35, 'manage', 'manager.dept.table', 'leader', '负责人', 'text', b'0', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (36, 'manage', 'manager.dept.table', 'phone', '联系电话', 'text', b'0', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (37, 'manage', 'manager.dept.table', 'email', '邮箱', 'text', b'0', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (38, 'manage', 'manager.dept.table', 'status', '状态', 'number', b'0', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (40, 'manage', 'manager.joblog.table', 'createTime', '执行时间', 'text', b'0', 1, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (41, 'manage', 'manager.joblog.table', 'jobMessage', '日志信息', 'text', b'0', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (42, 'manage', 'manager.joblog.table', 'exceptionInfo', '异常信息', 'text', b'0', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (43, 'manage', 'manager.joblog.table', 'status', '状态', 'number', b'0', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (44, 'manage', 'manager.operlog.table', 'operTime', '操作时间', 'text', b'1', 1, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (45, 'manage', 'manager.operlog.table', 'operUrl', '请求URL', 'text', b'0', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (46, 'manage', 'manager.operlog.table', 'requestMethod', '请求方式', 'text', b'0', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (47, 'manage', 'manager.operlog.table', 'operName', '操作人员', 'text', b'0', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (48, 'manage', 'manager.operlog.table', 'operIp', '操作主机IP', 'text', b'0', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (49, 'manage', 'manager.operlog.table', 'operLocation', '操作地点', 'text', b'0', 6, 0);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (50, 'manage', 'manager.operlog.table', 'method', '执行方法', 'text', b'0', 7, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (51, 'manage', 'manager.operlog.table', 'status', '操作状态', 'text', b'0', 8, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (52, 'manage', 'manager.operlog.table', 'responseCode', '响应状态码', 'text', b'0', 9, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (53, 'manage', 'manager.operlog.table', 'errorMsg', '错误消息', 'text', b'0', 10, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (54, 'manage', 'manager.operlog.table', 'cost', '处理时长', 'number', b'0', 11, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (55, 'law', 'law.case.table', 'startDate', '开始日', 'text', b'0', 1, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (56, 'law', 'law.case.table', 'case_name', '案件名', 'text', b'0', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (57, 'law', 'law.case.table', 'buyerInfoFlag', '买主个人情报登录', 'text', b'0', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (58, 'law', 'law.case.table', 'sellerFaceFalg', '卖主颜情报登录', 'text', b'0', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (59, 'law', 'law.case.table', 'buyerFaceFlag', '买主颜情报登录', 'text', b'0', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (60, 'law', 'law.case.table', 'estateFlag', '不动产情报登录', 'text', b'0', 6, 1);
-- dashboard
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (62, 'dashboard', 'dashboard.user.general.table', 'userName', '顱客名', 'text', b'0', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (63, 'dashboard', 'dashboard.user.general.table', 'gender', '性别', 'text', b'0', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (64, 'dashboard', 'dashboard.user.general.table', 'tel', '携带番号', 'text', b'0', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (65, 'dashboard', 'dashboard.user.general.table', 'email', 'メ一ルアドレス', 'text', b'0', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (66, 'dashboard', 'dashboard.user.general.table', 'age', '年齢', 'text', b'0', 6, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (67, 'dashboard', 'dashboard.user.general.table', 'addr', '住所', 'text', b'0', 7, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (68, 'dashboard', 'dashboard.user.general.table', 'position', '職業', 'text', b'0', 8, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (69, 'dashboard', 'dashboard.user.general.table', 'marriage', '婚姻', 'text', b'0', 9, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (71, 'dashboard', 'dashboard.user.general.table', 'createTime', '新規登録日付', 'text', b'0', 11, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (73, 'dashboard', 'dashboard.jud.case.table', 'caseName', '案件名', 'text', b'0', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (74, 'dashboard', 'dashboard.jud.case.table', 'startDate', '開始日', 'text', b'0', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (75, 'dashboard', 'dashboard.jud.case.table', 'judName', '担当者', 'text', b'0', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (76, 'dashboard', 'dashboard.jud.case.table', 'num', '取引物件数', 'text', b'0', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (77, 'dashboard', 'dashboard.jud.case.table', 'addr', '取引物件', 'text', b'0', 6, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (78, 'dashboard', 'dashboard.jud.case.table', 'stepSellerVerify', '壳主認鉦', 'text', b'0', 7, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (79, 'dashboard', 'dashboard.jud.case.table', 'stepBuyerInput', '買主登錄', 'text', b'0', 8, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (80, 'dashboard', 'dashboard.jud.case.table', 'stepRegFinish', '取引完了', 'text', b'0', 9, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (81, 'dashboard', 'dashboard.jud.case.table', 'stepDealFinish', '登記完了', 'text', b'0', 10, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (82, 'dashboard', 'dashboard.jud.case.table', 'overTime', '終了日', 'text', b'0', 11, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (84, 'dashboard', 'dashboard.user.server.table', 'deductionDate', '決済日', 'text', b'0', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (85, 'dashboard', 'dashboard.user.server.table', 'fullname', '顧客名', 'text', b'0', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (86, 'dashboard', 'dashboard.user.server.table', 'groupName', '所属事務所', 'text', b'0', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (87, 'dashboard', 'dashboard.user.server.table', 'desc', '利用サビース名', 'text', b'0', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (88, 'dashboard', 'dashboard.user.server.table', 'amount', '決済金額', 'text', b'0', 6, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (89, 'dashboard', 'dashboard.user.server.table', 'cardNo', '決済カード', 'text', b'0', 7, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (90, 'dashboard', 'dashboard.user.server.table', 'creditCompanyName', 'カード発行会社', 'text', b'0', 8, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (91, 'dashboard', 'dashboard.user.server.table', 'status', '決済状態', 'text', b'0', 9, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (92, 'dashboard', 'dashboard.user.server.table', 'startDate', '利用開始日', 'text', b'0', 10, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (93, 'dashboard', 'dashboard.user.law.table', 'groupName', '事務所名', 'text', b'0', 1, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (94, 'dashboard', 'dashboard.user.law.table', 'representer', '代表者', 'text', b'1', 2, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (95, 'dashboard', 'dashboard.user.law.table', 'addr', '所在地', 'text', b'1', 3, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (96, 'dashboard', 'dashboard.user.law.table', 'staff', '主担当者', 'text', b'1', 4, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (97, 'dashboard', 'dashboard.user.law.table', 'email', 'メールアドレス', 'text', b'1', 5, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (98, 'dashboard', 'dashboard.user.law.table', 'tel', '連絡先電話番号', 'text', b'1', 6, 1);
INSERT INTO `sys_data_table_header`(`id`, `module`, `data_table_name`, `data_index`, `title_key`, `format`, `sortable`, `order_no`, `status`) VALUES (99, 'dashboard', 'dashboard.user.law.table', 'createDate', '利用開始日付', 'text', b'1', 7, 1);

-- INSERT INTO `sys_job` VALUES (1, '测试任务', 'DEFAULT', 'cn.repeatlink.framework.scheduler.TestJob', '0 0/55 * * * ? ', '3', '1', 1, -1, '2020-08-19 10:10:04', -1, '2020-08-24 17:08:05', '');

-- 系统默认菜单
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, 'ダッシュボード', NULL, 1, 'dashboard', NULL, 1, 'C', 0, NULL, 'home', 1, -1, '2020-08-18 11:29:23', NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, '操作日志', NULL, 8, 'log', NULL, 1, 'C', 0, NULL, 'home', 1, -1, '2020-08-18 11:31:00', NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (3, '设定', NULL, 7, 'setting', NULL, 1, 'M', 0, NULL, 'home', 1, -1, '2020-08-18 11:31:00', NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (4, '系统设定', 3, 1, 'system', NULL, 1, 'C', 0, NULL, 'home', 1, -1, '2020-08-26 13:17:31', NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (5, '定时任务', 3, 2, 'batch', NULL, 1, 'C', 0, NULL, 'home', 1, -1, '2020-08-26 13:22:00', NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (6, '权限', NULL, 6, 'permissions', NULL, 1, 'M', 0, NULL, 'home', 1, -1, '2020-08-26 13:22:09', NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (7, '用户列表', 6, 1, 'user', NULL, 1, 'C', 0, NULL, 'home', 1, -1, '2020-08-26 13:23:52', NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (8, '角色列表', 6, 2, 'role', NULL, 1, 'C', 0, NULL, 'home', 1, -1, '2020-08-26 13:25:06', NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (9, '案件管理', NULL, 2, 'case', NULL, 1, 'C', 0, NULL, 'file-text', 1, -1, NULL, NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (10, 'アカウント管理', NULL, 2, 'account', NULL, 1, 'C', 0, NULL, 'team', 1, -1, NULL, NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (11, '決済一覧', NULL, 4, 'payment', NULL, 1, 'C', 0, NULL, 'money-collect', 1, -1, NULL, NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (12, '設定', NULL, 5, 'permissions', NULL, 1, 'M', 0, NULL, 'setting', 1, -1, NULL, NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (13, '組織設定', 12, 1, 'setting', NULL, 1, 'C', 0, NULL, '', 1, -1, NULL, NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (14, 'アカウント管理', NULL, 3, 'multipleAccount', NULL, 1, 'M', 0, NULL, 'home', 1, -1, NULL, NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (15, '申し込み一覧', 14, 1, 'checklist', NULL, 1, 'C', 0, NULL, '', 1, -1, NULL, NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (16, 'アカウント一覧', 14, 2, 'accountlist', NULL, 1, 'C', 0, NULL, '', 1, -1, NULL, NULL, NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `menu_type`, `visible`, `perms`, `icon`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (17, '決済一覧', NULL, 4, 'PaymentList', NULL, 1, 'C', 0, NULL, 'home', 1, -1, NULL, NULL, NULL, '');

-- 系统默认角色
INSERT INTO `sys_role`(`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `del_flag`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, '系统管理员', 'admin', 1, '1', '0', 1, 1, '2020-08-17 15:24:57', -1, '2020-08-20 13:51:59');
INSERT INTO `sys_role`(`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `del_flag`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (2, '组织（事务所）', 'law', 2, '1', '0', 1, -1, '2020-10-12 13:44:46', -1, '2020-10-12 13:44:50');
INSERT INTO `sys_role`(`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `del_flag`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (3, '司法书士', 'judicial', 3, '1', '0', 1, -1, '2020-10-12 13:41:51', -1, '2020-10-12 13:41:56');

INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, 1, 1, -1, '2020-08-20 13:51:59', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, 14, 1, -1, '2020-08-20 13:51:59', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, 15, 1, -1, '2020-08-20 13:51:59', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, 16, 1, -1, '2020-08-20 13:51:59', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, 17, 1, -1, '2020-08-20 13:51:59', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (2, 1, 1, -1, '2020-10-14 13:12:13', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (2, 9, 1, -1, '2020-10-14 13:12:24', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (2, 10, 1, -1, '2020-10-14 13:12:35', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (2, 12, 1, -1, '2020-10-14 13:12:55', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (2, 13, 1, -1, '2020-10-14 13:13:12', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (3, 1, 1, -1, '2020-08-20 13:51:59', NULL, NULL);
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (3, 9, 1, -1, '2020-08-20 13:51:59', NULL, NULL);

-- 系统默认用户
INSERT INTO `sys_user`(`user_id`, `user_name`, `name`, `user_type`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `login_ip`, `login_date`, `enable`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, 'admin', '系统管理员', '00', 'admin@repeatlink.cn', '', '1', NULL, '$2a$10$LEAHvbD/3pXWwuo29ULDyuIP7vEOJApaOUHca84pZOqK/bJsT/Aqi', NULL, NULL, 1, 1, -1, '2020-08-27 13:46:03', NULL, NULL, '系统管理员');
INSERT INTO `sys_user`(`user_id`, `user_name`, `name`, `user_type`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `login_ip`, `login_date`, `enable`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, 'law', '测试事务所', '10', 'law@repeatlink.cn', '', '0', '', '$2a$10$KnJyXl9DHjubpN.j7FTaB.3Qt0DXpxIWFZ4oaV6CdTvi.DIKfz6N2', '', NULL, 1, 1, -1, '2020-10-14 13:15:30', NULL, NULL, NULL);

INSERT INTO `sys_user_role`(`user_id`, `role_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1, 1, 1, -1, '2020-08-25 17:11:09', -1, '2020-08-26 09:52:38');
INSERT INTO `sys_user_role`(`user_id`, `role_id`, `status`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (2, 2, 1, -1, '2020-10-14 13:16:07', NULL, NULL);


-- 默认邮件发送配置
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, '默认邮箱发送配置', 'mailsender.default.config.host', 'smtp.exmail.qq.com', 'N', 1, -1, '2020-10-09 15:25:10', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, '默认邮箱发送配置', 'mailsender.default.config.port', '465', 'N', 1, -1, '2020-10-09 15:25:29', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (3, '默认邮箱发送配置', 'mailsender.default.config.username', 'test1@repeatlink.cn', 'N', 1, -1, '2020-10-09 15:25:53', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (4, '默认邮箱发送配置', 'mailsender.default.config.password', 'jHEfFFT82rpAn5d8', 'N', 1, -1, '2020-10-09 15:26:29', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (18, '默认邮箱发送配置', 'mailsender.default.config.mail.smtp.socketFactory.class', 'javax.net.ssl.SSLSocketFactory', 'N', 1, -1, '2020-10-09 15:26:29', NULL, NULL, NULL);

-- 邮编更新配置
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (5, '郵便番号定期更新サイト', 'postcode.updatePostCodeRemoteFilePath', 'https://www.post.japanpost.jp/zipcode/dl/oogaki/zip/ken_all.zip', 'N', 1, -1, '2020-10-28 10:12:50', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (6, '事業所郵便番号定期更新サイト', 'postcode.updatePostCodeJigyosyoRemoteFilePath', 'https://www.post.japanpost.jp/zipcode/dl/jigyosyo/zip/jigyosyo.zip', 'N', 1, -1, '2020-10-28 10:13:57', NULL, NULL, NULL);
-- AWS人脸识别配置
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (7, 'AWS', 'aws.face.collection.id', 'repeatlink_test', 'N', 1, -1, '2020-10-29 15:10:31', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (8, 'AWS', 'aws.face.confidence.min', '80', 'N', 1, -1, '2020-10-29 15:11:24', NULL, NULL, '建议99~100');
-- 人脸验证图片储存路径配置
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (9, '人脸验证图片储存路径', 'estate.user.face.verify.img.store.path', '/legalcloud/user_face_verify_img', 'N', 1, -1, '2020-12-01 13:44:43', NULL, NULL, NULL);
-- 一般用户注册验证码服务类型配置
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (10, '一般用户注册验证码服务类型', 'general.user.reg.validatecode.service.type', 'email', 'N', 1, -1, '2020-12-01 15:13:52', NULL, NULL, 'email:邮件,sms_zh:大陆短信,sms_jp:日本短信');
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (21, '一般用户注册验证码极光短信模版ID', 'general.user.reg.validatecode.sms.jiguang.template.id', '', 'N', 1, -1, '2020-12-23 14:47:12', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (22, '一般用户注册验证码有效分钟数', 'general.user.reg.validatecode.valid.minute', '5', 'N', 1, -1, '2020-12-23 14:48:18', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (23, '一般用户注册验证码刷新间隔限制秒数', 'general.user.reg.validatecode.ttl.second', '60', 'N', 1, -1, '2020-12-23 14:49:43', NULL, NULL, NULL);

-- JG推送配置
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (11, 'JGPush',  'jg.push.masterSecret', 'null', 'N', 1, -1, '2020-12-03 11:34:31', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (12, 'JGPush',  'jg.push.appKey', 'null', 'N', 1, -1, '2020-12-03 11:34:31', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (13, 'JGPush',  'jg.push.pushUrl', 'https://api.jpush.cn/v3/push', 'N', 1, -1, '2020-12-03 11:34:31', NULL, NULL, 'JG推送URL');
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (14, 'JGPush',  'jg.push.apns_production', 'false', 'N', 1, -1, '2020-12-03 11:34:31', NULL, NULL, '仅对ios有效：false开发环境,true生产环境');
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (15, 'JGPush',  'jg.push.time_to_live', '86400', 'N', 1, -1, '2020-12-03 11:34:31', NULL, NULL, '默认存活时间1天');

-- 极光短信配置
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (24, '短信极光短信MasterSecret', 'sms.service.jiguang.master.secret', '', 'N', 1, -1, '2020-12-23 14:50:50', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (25, '短信极光短信AppKey', 'sms.service.jiguang.app.key', '', 'N', 1, -1, '2020-12-23 14:51:40', NULL, NULL, NULL);

--RSA密钥（公/私）
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (16, 'RSA',  'image.Encrypt.publicKey', 'null', 'N', 1, -1, '2020-12-10 15:34:31', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (17, 'RSA',  'image.Encrypt.privateKey', 'null', 'N', 1, -1, '2020-12-10 15:34:31', NULL, NULL, NULL);

-- OMISE公钥、私钥
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (19, 'OMISE', 'omise.public.key', '', 'N', 1, -1, '2020-12-23 09:58:36', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (20, 'OMISE', 'omise.secret.key', '', 'N', 1, -1, '2020-12-23 09:59:09', NULL, NULL, NULL);

-- 不动产保护计划订单配置
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (26, '不动产保护计划订单默认金额', 'estate.protection.order.default.amount', '1000', 'N', 1, -1, '2020-12-23 16:01:12', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (27, '不动产保护计划订单默认标题', 'estate.protection.order.default.title', '不動産保護プログラム', 'N', 1, -1, '2020-12-23 16:01:24', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (28, '不动产保护计划订单默认备注', 'estate.protection.order.default.remark', '', 'N', 1, -1, '2020-12-23 16:04:14', NULL, NULL, NULL);

-- twilio短信平台
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (29, '短信Twilio平台ACCOUNT_SID', 'sms.service.twilio.account.sid', '', 'N', 1, -1, '2020-12-29 17:29:34', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (30, '短信Twilio平台AUTH_TOKEN', 'sms.service.twilio.auth.token', '', 'N', 1, -1, '2020-12-29 17:30:03', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (31, '短信Twilio平台FROM', 'sms.service.twilio.from', '', 'N', 1, -1, '2020-12-29 17:30:30', NULL, NULL, '两种方式：phonenum_132xxxxxxxx，servicesid_xxxxx');

-- 不动产fetch
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (32, '不动产fetch帐号', 'estate.spider.fetch.login.username', '', 'N', 1, -1, '2020-12-30 13:27:58', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (33, '不动产fetch帐号', 'estate.spider.fetch.login.password', '', 'N', 1, -1, '2020-12-30 13:28:00', NULL, NULL, NULL);

-- JG推送一般用户端
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (34, 'JGPush', 'jg.push.general.masterSecret', '', 'N', 1, -1, '2020-12-03 11:34:31', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (35, 'JGPush', 'jg.push.general.appKey', '', 'N', 1, -1, '2020-12-03 11:34:31', NULL, NULL, NULL);

-- 不动产fetch下载pdf文件储存目录
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (36, '不动产fetch下载pdf文件储存目录', 'estate.spider.fetch.pdf.store.path', '', 'N', 1, -1, '2021-01-04 15:44:10', NULL, NULL, NULL);
-- 不动产fetch开关
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (37, '不动产fetch开关', 'estate.spider.fetch.switch', 'false', 'N', 1, -1, '2021-01-06 10:07:55', NULL, NULL, '默认：关（随机返回测试数据）');

-- 一般用户帮助中心信息
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (38, '帮助中心 - 法人', 'system.help.info.legal.name', '', 'N', 1, -1, '2021-02-02 11:46:21', NULL, NULL, NULL);
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (39, '帮助中心 - 联系电话', 'system.help.info.contact.tel', '', 'N', 1, -1, '2021-02-02 11:47:05', NULL, NULL, NULL);



-- 邮编更新任务
INSERT INTO `sys_job`(`job_id`, `job_name`, `job_group`, `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, '郵便番号更新', 'DEFAULT', 'cn.repeatlink.module.law.scheduler.UpdatePostCode', '0 0 2 1/1 * ?', '3', '1', 1, -1, '2020-10-28 10:50:05', NULL, NULL, '');
-- 保护计划任务
INSERT INTO `sys_job`(`job_id`, `job_name`, `job_group`, `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, '保护计划', 'DEFAULT', 'cn.repeatlink.module.judicial.scheduler.ProtectJob', '0 5 0 * * ? ', '3', '1', 1, -1, '2020-12-31 11:05:10', NULL, NULL, '');





