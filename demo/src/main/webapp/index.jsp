<%@page import="com.example.demo.domain.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
 <!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>

<script type="text/javascript">
	
	$(function() {

		
		
		$('button:contains("로그인")').on('click', function() {
			
			$('form[name="login"]').attr('method', 'post').attr('action', '/user/login').submit();
		});
		
		$('button:contains("추가")').on('click', function() {

			$('form[name="crud"]').attr('method', 'post').attr('action', '/user/addUser').submit();
		});
		
		$('button:contains("수정")').on('click', function() {
			
			$('form[name="crud"]').attr('method', 'post').attr('action', '/user/updateUser').submit();
		});
	});
</script>

</head>
<body>

	<%
		// request는 현재 연결에서만 유효한 객체이므로, 타 사용자와 겹치지 않는다.
		User temp = (User) request.getSession().getAttribute("user");
		if (temp != null) { %>
		<p><%=temp.getNickname() %>님, 로그인을 환영합니다.</p>
		<a href="http://localhost:8080/user/logout">로그아웃</a>
	<%
		}
	%>

	user login
	<form name="login">
	
		<input name="userId" type="text" placeholder="userId" />
		<br/>
		<input name="password" type="text" placeholder="password" />
		<br/>
		<hr/>
		
		<button type="button">로그인</button>
	</form>

	<br/>
	<br/>
	<br/>

	user CRUD
	<form name="crud">
	
		<input name="userId" type="text" placeholder="userId" />
		<br/> 
		<input name="password" type="text" placeholder="password" />
		<br/>
		<input name="name" type="text" placeholder="name" />
		<br/>
		<input name="nickname" type="text" placeholder="nickname" />
		<br/>
		<hr/>
		
		<button type="button">추가</button>
		<button type="button">수정</button>
	</form>
	
	<br/>
	<br/>
	<br/>
	
	user list
	<table>
		<thead>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>닉네임</th>
				<th>가입일자</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${requestScope.userList}">
				<tr>
					<td>${user.userId }</td>
					<td>${user.name }</td>
					<td>${user.nickname }</td>
					<td>${user.regDate }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>