<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
</head>
<body>
	<button onclick="location.href='writeForm'">글쓰기</button>
	<button onclick="del()">삭제</button>
	<table>
		<thead>
		<tr>
			<th><input type="checkbox" id="all"/></th>
			<th>번호</th>
			<th>img</th>
			<th>제목</th>
			<th>작성자</th>
			<th>조회수</th>
			<th>작성일</th>
		</tr>
		</thead>
		<!-- 내용 -->
		<tbody id="list">		
		</tbody>		
	</table>
</body>
<script>
listCall();

$('#all').on('click',function(){
	// #all 이 체크가 되어있으면 다른 녀석들도 모두 체크
	// 안되어 있으면 다른녀석들도 모두 체크 해제
	var $chk = $('input[type="checkbox"]');
	console.log($chk);
	
	//attr: 정적 속성(처음부터 그려져 있는 상태 & jsp 에서 그려진 상태)
	//prop: 동적 속성(자바스크립트에 의해서 나중에 그려진 상태)
	if($(this).is(":checked")){
		$chk.prop("checked",true);
	}else{
		$chk.prop("checked",false);
	}
	
});

function del(){
	// 체크된 번호 가져오기
	var chkArr = [];
	$('input[type="checkbox"]:checked').each(function(idx,item){
		var val = $(item).val();
		if(val != 'on'){
			chkArr.push(val);
		}		
	});
	console.log(chkArr);
	
	$.ajax({
		type:'get',
		url:'delete',
		data:{'delList':chkArr},
		dataType:'JSON',
		success:function(data){
			console.log(data);
			if(data.del_cnt>0){
				alert('요청하신 '+data.del_cnt+' 개의 게시물이 삭제 되었습니다.');
				// location.href='boardList'; // 새로고침 방식인데, 아예 페이지를 다시 호출하므로 비동기에 적합치 않다.
				// 다시 리스트를 호출해 그려준다.
				listCall();
			}
		},
		error:function(e){
			console.log(e);
		}
	});
}


function listCall(){
	$.ajax({
		type:'get',
		url:'listCall',
		data:{},
		dataType:'json',
		success:function(data){
			console.log(data);
			if(!data.login){
				alert('로그인이 필요한 서비스 입니다.');
				location.href='./';
			}else{
				drawList(data.list);
			}		
		},
		error:function(e){
			console.log(e);
		}
	});	
}


function drawList(list){
	console.log(list);
	/*for(var i=0;i<list.length;i++){}*/
	// 배열의 값을 하나씩 빼서 함수를 실행(개별값,인덱스)
	var content = '';
	list.forEach(function(item, idx){
		content += '<tr>';
		content += '<td><input type="checkbox" value="'+item.idx+'"/></td>';
		content += '<td>'+item.idx+'</td>';
		content += '<td>';
		if(item.img>0){
			content += '<img src="resources/img/image.png" width="20px"/>';	
		}else{
			content += '<img src="resources/img/no_image.png" width="20px"/>';	
		}
		content += '</td>';
		
		content += '<td>'+item.subject+'</td>';
		content += '<td>'+item.user_name+'</td>';
		content += '<td>'+item.bHit+'</td>';
		// java.sql.Date 는 jsp 에서는 정상동작 되나 js 에서는 밀리세컨드를 반환 한다.
		// 방법 1. DTO 에서 reg_date 를 String 으로 반환하는 방법
		// content += '<td>'+item.reg_date+'</td>';
		// 방법 2. js 에서 직접 변환
		var date = new Date(item.reg_date);
		var dateStr = date.toLocaleDateString("ko-KR"); //en-US
		content += '<td>'+dateStr+'</td>';		
		content += '</tr>';
	});
	$('#list').empty();
	$('#list').append(content);
	
}

</script>
</html>