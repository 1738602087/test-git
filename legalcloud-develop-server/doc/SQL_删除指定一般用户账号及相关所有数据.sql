-- 一般用户

set autocommit = 0;

-- ！！！需要删除的一般用户账号的邮箱
SET @email = '';

-- 删除用户通知消息记录
delete from app_notify where (receiver_id in (select user_id from user_general where email = @email)) or (sender_id in (select user_id from user_general where email = @email));

-- 删除关联案件买主变更记录
delete from case_buyer_change_record where case_id in (select case_id from case_buyer where user_general_id in (select user_id from user_general where email = @email) union select case_id from case_seller where user_general_id in (select user_id from user_general where email = @email));

-- 删除关联案件物件名义人记录
delete from case_estate_record_user where estate_record_id in (select record_id from (select record_id from case_estate_record where case_id in (select case_id from case_buyer where user_general_id in (select user_id from user_general where email = @email) union select case_id from case_seller where user_general_id in (select user_id from user_general where email = @email))) t);

-- 删除关联案件物件记录
delete from case_estate_record where case_id in (select case_id from case_buyer where user_general_id in (select user_id from user_general where email = @email) union select case_id from case_seller where user_general_id in (select user_id from user_general where email = @email));

-- 删除关联案件
delete from case_estate where case_id in (select case_id from case_buyer where user_general_id in (select user_id from user_general where email = @email) union select case_id from case_seller where user_general_id in (select user_id from user_general where email = @email));

-- 删除关联案件买主记录
delete from case_buyer where case_id in (select case_id from (select case_id from case_buyer where user_general_id in (select user_id from user_general where email = @email) union select case_id from case_seller where user_general_id in (select user_id from user_general where email = @email)) t);

-- 删除关联案件卖主记录
delete from case_seller where case_id in (select case_id from  (select case_id from case_buyer where user_general_id in (select user_id from user_general where email = @email) union select case_id from case_seller where user_general_id in (select user_id from user_general where email = @email)) t);


-- 删除用户不动产保护记录
delete from estate_protection where customer_id in (select user_id from user_general where email = @email);
delete from estate_protection_order where customer_id in (select user_id from user_general where email = @email);

-- 删除用户不动产记录
delete from estate_user where user_general_id in (select user_id from user_general where email = @email);

-- 删除信用卡记录
delete from om_customer_credit where customer_id in (select user_id from user_general where email = @email);
delete from om_deduction_record where customer_id in (select user_id from user_general where email = @email);

-- 删除绑定设备
delete from user_registration where type = 2 and user_id in (select user_id from user_general where email = @email);

-- 删除人脸记录
delete from user_general_face where user_general_id in (select user_id from user_general where email = @email);

-- 删除用户属性记录
delete from user_general_attribute where user_id in (select user_id from user_general where email = @email);

-- 删除一般用户
delete from user_general where email = @email;

set autocommit = 1;