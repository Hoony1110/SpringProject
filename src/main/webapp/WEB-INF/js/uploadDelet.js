$(function() {

    $('#all').click(function() {
        if ($(this).prop('checked')) {
            $('input[name="check"]').prop('checked', true); // 전체 선택
        } else {
            $('input[name="check"]').prop('checked', false); // 전체 해제
        }
    });
    
    $('input[name="check"]').click(function(){
    	 $('#all').prop('checked', $('input[name="check"]').length === $('input[name="check"]:checked').length);
    	
    });
    
    
    $('#deletBtn').click(function() {
        // AJAX 요청
        $.ajax({
            type: 'POST',
            url: '/spring/user/uploadDelet',
            data: $('#uploadDelet').serialize(), 
            success: function() {
            	alert('이미지 삭제 완료')
                location.href= '/spring/user/uploadList';
            },
            error: function(error) {
                alert('삭제에 실패했습니다. 다시 시도해 주세요.');
            }
        });
    });
});
