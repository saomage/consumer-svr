<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freemarker</title>
<script type="text/javascript" src="/js/jsUtil.js"></script>
<script type="text/javascript" src="/js/jquery-2.1.3.min.js"></script>
</head>
<body>
	<h1>shop</h1>
	<#if shop??>
	<#if shop.productor??>
	<table border="1">
	<#assign productor = shop.productor />
		<tr>
			<td>${productor.id}</td>
			<td>${productor.name}</td>
			<td>${productor.email}</td>
			<td>${productor.phone}</td>
			<td>${productor.aboutme}</td>
			<td>${productor.avatar}</td>
		</tr>
	</table>
	</#if>
	<table border="1">
	<input type="hidden" id="shop_id"  name="id" value="${shop.id}">
	<input type="hidden" id="shop_name"  name="name" value="${shop.name}">
		<tr>
			<td>${shop.id}</td>
			<td>${shop.name}</td>
			<td>${shop.address}</td>
			<td>${shop.phone}</td>
			<td>${shop.introduce}</td>
			<td>${shop.avatar}</td>
		</tr>
	</table>
	<table border="1">
		<#list shop.commoditys as commodity>
		<input type="hidden" id="commodity_id"  name="id" value="${commodity.id}">
		<tr>
			<td>${commodity.id}</td>
			<td>${commodity.name}</td>
			<td>${commodity.introduce}</td>
			<td>${commodity.avatar}</td>
			<td>${commodity.type}</td>
			<td>${commodity.price}</td>
			<td><input type="button" id="commodity_button" value="加入购物车"/></td>
		</tr>
		</#list>
	</table>
	</#if>
</body>
</html>