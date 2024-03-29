<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<style></style>
</head>
<body>
	<h3>회원 리스트</h3>
	<table>
		<thead>
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>이메일</th>
		</tr>
		</thead>
		<tbody id="list">
		<!-- 여기에 그려주면 된다. -->
		</tbody>
	</table>
</body>
<script>
	// 1. member 들의 리스트를 ajax 로 요청해 온다.
	// 2. 그리고 console.log() 로 찍어 본다.
	$.ajax({
		type:'get',
		url:'getMemberList',
		data:{},
		dataType:'JSON',
		success:function(data){
			console.log(data); // 3. 그 다음 그려야 한다.
			if(data.success == -1){
				alert('이 페이지의 권한이 없습니다.');
				location.href = './';
			}else{
				var content = '';
				for(var i=0; i < data.size;i++){
					content += '<tr>';
					content += '<td>'+data.list[i].id+'</td>';
					content += '<td>'+data.list[i].name+'</td>';
					content += '<td>'+data.list[i].email+'</td>';
					content += '</tr>';
				}
				$('#list').append(content);
			}
			
		},
		error:function(e){
			console.log(e);
		}
	});
	
	

</script>
</html>