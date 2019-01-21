<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freemarker</title>
</head>
<body>
	<form action="/consumer/shop/list">
		店铺名称<input type="text" name="name"/>
		店铺地址<input type="text" name="address"/>
		店铺电话<input type="text" name="phone"/>
		<input type="submit" value="查询"/>
	</form>
	<h1>Login</h1>
	<#if shops??>
	<table border="1">
		<#list shops as shop>
		<tr>
			<td>${shop.id}</td>
			<td>${shop.name}</td>
			<td>${shop.address}</td>
			<td>${shop.phone}</td>
			<td>${shop.introduce}</td>
			<td>${shop.avatar}</td>
			<td><a href="/consumer/shop/detailed?id=${shop.id}">进入</a>
		</tr>
		</#list>
	</table>
	</#if>
</body>
</html>