window.onload = function() {
	alert(1);
	document.getElementById("commodity_button").onclick = function() {
		shopId = document.getElementById("shop_id").value;
		shopName = document.getElementById("shop_name").value;
		commodityId = document.getElementById("commodity_id").value;
		$.ajax({
			url : "/consumer/addCommodity?shopId=" + shopId + "&commodityId="
					+ commodityId + "&shopName=" + shopName,
			type : "GET",
			contentType : "application/json;charset=utf-8",
			success : function(response) {
				if (response.success) {
					alert(response.message);
				}
			}
		});
	}
	document.getElementById("cart_button").onclick = function() {
		alert(1);
		cartMsgId = document.getElementById("cartMsg_id").value;
		$.ajax({
			url : "/consumer/delete?cartMsgId=" + cartMsgId,
			type : "GET",
			contentType : "application/json;charset=utf-8",
			success : function(response) {
				if (response.success) {
					alert(response.message);
				}
			}
		});
	}
}
