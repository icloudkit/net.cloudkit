商品 goods

- id
- name                    商品名称
- serial_no               商品SN
- description             商品描述
- goods_type
- add_time                添加时间
- is_new_arrival          新品上市
- is_hot_sales            热销商品
- is_best                 精选商品


- is_promote              促销商品 promotion
- promote_price
- promote_start_date
- promote_end_date


- is_sold_out             脱销商品
- is_off_shelve|is_marketable           下架商品|上架
- is_list                 是否列出
- is_top                  是否置顶
- 
- brief|summary
- introduction            介绍
- memo                    备注
- keywords                搜索关键词
- score                   评分
- total_score             总评分
- score_count             评分数


- brand
- provider|suppliers
- producer
- stock_number|inventory quantity 库存数量
- allocated_stock         已分配库存
- stock_memo              库存备注
- warn_number
- weight                  重量
- unit                    单位


- market_price
- sales_price
- cost_price


- thumbnail_images
- images
- original_images
- tags


- last_update_time
- consumption_point|bonus_point|integral|bonus_type 赠送积分


- click_count|hits|week_hits|month_hits 点击次数|周点击数更新日期|月点击数更新日期
- sales|week_sales|month_sales 销量|周销量更新日期|月销量更新日期
- favorite_count 收藏次数
- liked_count 喜欢次数
- added_list_count 添加次数


- shared_count
- is_gift 是否为赠品
- sort_order

order_type
置顶降序top_desc
价格升序price_asc
价格降序price_desc
销量降序sales_desc
评分降序score_desc
日期降序date_desc

`cat_id` smallint(5) unsigned NOT NULL default '0',
`goods_name_style` varchar(60) NOT NULL default '+',
`is_real` tinyint(3) unsigned NOT NULL default '1',
`extension_code` varchar(30) NOT NULL default '',
`is_on_sale` tinyint(1) unsigned NOT NULL default '1',
`is_alone_sale` tinyint(1) unsigned NOT NULL default '1',
`is_shipping` tinyint(1) unsigned NOT NULL default '0',
`is_delete` tinyint(1) unsigned NOT NULL default '0',
`seller_note` varchar(255) NOT NULL default '',
`give_integral` int NOT NULL default '-1',
`rank_integral` int NOT NULL default '-1',
`is_check` tinyint(1) unsigned default NULL,


........................................................................................................................

brand 品牌
- name 名称
- logo
- url 网址
- introduction 介绍
- description
- sort_order
- is_show

promotion 促销

shopping cart/shopping bag 购物车
user_id
session_id
goods_id
goods_sn
goods_name
market_price
goods_price
goods_number
goods_attr
is_gift
is_shipping

order 定单
order_log 定单日志

category|categories 分类
parent_id
sort_order
is_show

goods_type

tags 标签

coupon 优惠券

log 日志

user|member 会员profile
- avatar
- username
- nice_name
- true_name
- password
- salt
- sex
- birthday
- email
- mobile
- telephone
- points
- registered_time
- last_login_time
- last_login_ip
- visited_count


账户金额
user_money
frozen_money 冻结金额

安全问
question
answer

user_address 用户地址

message|notification 消息
feedback 反馈

comment|review|tweet

refunds 退款
returns 退货,换货
payment 付款
交易记录

shopping_card 购物卡
- fee
- discount_amount
- description
- thumbnail_image

region|area
article

attribute
bonus_type|rebate

booking_goods
collect_goods

friend_link
name
url
logo
show_order

delivery_order 发货单

products
mail_templates
environment_settings
