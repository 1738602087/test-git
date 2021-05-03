

-- 司法书士

set autocommit = 0;

-- ！！！需要删除的司法书士账号的邮箱
SET @email = '';

-- 删除通知消息记录
delete from app_notify where (receiver_id in (select user_id from user_judicial where email = @email)) or (sender_id in (select user_id from user_judicial where email = @email));

-- 删除案件买主变更记录
delete from case_buyer_change_record where case_id in (select case_id from case_estate where user_judicial_id in (select user_id from user_judicial where email = @email));

-- 删除案件买主记录
delete from case_buyer where case_id in (select case_id from case_estate where user_judicial_id in (select user_id from user_judicial where email = @email));

-- 删除案件卖主记录
delete from case_seller where case_id in (select case_id from case_estate where user_judicial_id in (select user_id from user_judicial where email = @email));

-- 删除案件相关物件名义人记录
delete from case_estate_record_user where estate_record_id in (select record_id from case_estate_record where case_id in (select case_id from case_estate where user_judicial_id in (select user_id from user_judicial where email = @email)));

-- 删除案件相关物件记录
delete from case_estate_record where case_id in (select case_id from case_estate where user_judicial_id in (select user_id from user_judicial where email = @email));

-- 删除案件记录
delete from case_estate where user_judicial_id in (select user_id from user_judicial where email = @email);

-- 删除绑定设备
delete from user_registration where type = 1 and user_id in (select user_id from user_judicial where email = @email);

-- 删除系统用户
delete from sys_user where user_id in (select sys_user_id from user_judicial where email = @email);

-- 删除司法书士注册码记录
delete from law_group_code where email = @email;

-- 删除司法书士
delete from user_judicial where email = @email;

set autocommit = 1;