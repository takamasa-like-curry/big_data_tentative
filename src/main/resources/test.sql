
--親カテゴリIDと階層を指定してカテゴリ情報を取得
SELECT 
c.id AS c_id
,c.name AS c_name
,c.name_all AS c_name_all
FROM category_tree_paths AS p 
LEFT OUTER JOIN categories AS c
ON c.id = p.descendant_id 
WHERE 
p.ancestor_id = 1
AND 
c.level = 1
GROUP BY c.id,c.name,c.name_all
ORDER BY c.name;


--商品検索

SELECT 
i.item_id AS i_item_id
,i.name AS i_name
,i.condition AS i_condition
,i.brand AS i_brand
,i.price AS i_price
,i.shipping AS i_shipping
,i.description AS i_description
,c.name_all AS c_name_all
FROM category_tree_paths AS p 
LEFT OUTER JOIN items AS i
ON p.descendant_id = i.category_id
LEFT OUTER JOIN categories AS c
ON i.category_id = c.id
WHERE
i.name LIKE '%MLB%'
AND
p.ancestor_id = 3
AND
i.brand LIKE '%%'
GROUP BY i.item_id,i.name,i.condition,i.brand,i.price,i.shipping,i.description,c.name_all
ORDER BY item_id;


LIMIT :limit
OFFSET :offset;


--商品検索の件数を取得
select count(*) 
FROM category_tree_paths AS p 
LEFT OUTER JOIN items AS i
ON p.descendant_id = i.category_id
LEFT OUTER JOIN categories AS c
ON i.category_id = c.id
WHERE
i.name LIKE '%%'
-- AND
-- p.ancestor_id = 3
AND
i.brand ILIKE '%%'
AND 
p.ancestor_id IN (
    SELECT MIN(ancestor_id)
    FROM category_tree_paths
    GROUP BY descendant_id
);


--
SELECT
item_id
,name
,condition
,category_id
,brand
,price
,shipping
,description
FROM items
WHERE 
name ILIKE '%%'
AND		
brand ILIKE '%%'
AND
category_id IN (
    SELECT
    descendant_id
    FROM category_tree_paths
    WHERE 
    ancestor_id = 1
)	
ORDER BY item_id
LIMIT 30		
OFFSET 0;		
		

--
SELECT 
o.id AS o_id
,o.name AS o_name
,o.condition AS o_condition 
,o.brand AS o_brand
,o.price AS o_price
,o.shipping AS o_shipping
,o.description AS o_description
,c.name_all AS c_name_all
FROM original AS o 
LEFT OUTER JOIN categories AS c 
ON o.category_name = c.name_all
ORDER BY o.id;


--
SELECT
o.name
,o.condition_id
,o.category_name
,o.brand
,o.price
,o.shipping
,o.description
,c.id
FROM original AS o 
INNER JOIN category c 
ON o.category_name = c.name_all 
ORDER BY o.id ASC;