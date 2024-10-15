<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table {
	border-collapse: collapse;
}

th, td {
	padding: 5px;
}
</style>
</head>
<body>
<form id="uploadDelet">
<table border="1" frame="hsides" rules="rows">
	<tr>
		<th width="100"><input type="checkbox" name="all" id="all">번호</th>
		<th width="200">이미지</th>
		<th width="200">상품명</th>
	</tr>
	
	<c:forEach var="userUploadDTO" items="${list }">
		<tr>
			<td align="center"><input type="checkbox" name="check" id="check" value="${userUploadDTO.seq }">
			${userUploadDTO.seq }</td>
			<td align="center">
			
				     <!-- Object Storage -->
				     <a href="/spring/user/uploadView?seq=${userUploadDTO.seq }">
					     <img src="https://kr.object.ncloudstorage.com/bitcamp-9th-bucket-131/storage/${userUploadDTO.imageFileName }" 
					     alt="userUploadDTO.imageName" width="50" height="50" />
				     </a>
			</td>
			<td align="center">${userUploadDTO.imageName }</td>
		</tr>
	</c:forEach>
</table>
<div style="margin-left: 30px">
	<input type="button" value="삭제" id="deletBtn" name="deletBtn">
</div>
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/uploadDelet.js"></script>
<script type="text/javascript">
   
    <!-- 
    $('#all').change(function() {
        $('input[name="check"]').prop('checked', this.checked);
    });

    $('input[name="check"]').change(function() {
        var allChecked = $('input[name="check"]').length === $('input[name="check"]:checked').length;
        $('#all').prop('checked', allChecked);
    });

 -->
</script>
</body>
</html>










