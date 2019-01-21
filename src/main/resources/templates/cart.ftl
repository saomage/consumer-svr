<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freemarker</title>
<script type="text/javascript" src="/js/jsUtil.js"></script>
<script type="text/javascript" src="/js/jquery-2.1.3.min.js"></script>
</head>
<body>
	<h1>cart</h1>
	<#if cart??>
	<table border="1">
		<#list cart as cartMsg>
		<tr>
			<td id="cartMsg_id">${cartMsg.id}</td>
			<td>${cartMsg.commodityName}</td>
			<td>${cartMsg.shopName}</td>
			<td>${cartMsg.type}</td>
			<td>${cartMsg.price}</td>
			<td><input type="button" id="cart_button" value="删除"/></td>
		</tr>
		</#list>
	</table>
	</#if>
	<a href="/consumer/submit">购买</a>
</body>
</html>