<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freemarker</title>
</head>
<body>
<h1>Login</h1>
  <form action="/con/register" method="post">
    <input type="text" name="name" placeholder="用户名" required="required">
    <input type="text" name="phone" placeholder="电话号码" required="required">
    <input type="text" name="email" placeholder="邮箱" required="required">
    <input type="text" name="aboutme" placeholder="自我介绍" required="required">
    <input type="text" name="avatar" placeholder="头像地址" required="required">
    <input type="password" name="passwd" placeholder="密  码" required="required">
   <button type="submit">注册</button>
  </form>
</body>
</html>