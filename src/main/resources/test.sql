
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
i.brand ILIKE '%nike%'
AND 
p.ancestor_id IN (
    SELECT MIN(ancestor_id)
    FROM category_tree_paths
    GROUP BY descendant_id
);

--
select
o.id as o_id
,o.name as o_name
,o.condition as o_condition
,o.brand as o_brand
,o.price as o_price
,o.shipping as o_shipping
,o.description as o_description
,c.id As c_id 
FROM original as o 
left outer join categories as c 
on o.category_name = c.name_all 
where o.id < 100
order by o.id;
