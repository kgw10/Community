$(document).ready(function() {
    $('.delete-message').click(function() {
        var chatRoomId = $('#chatRoomId').val();
        var messageId = $(this).data('message-id');
        var url = `/chatroom/${chatRoomId}/message/${messageId}/delete`;

        if (confirm('이 메시지를 삭제하시겠습니까?')) {
            $.ajax({
                url: url,
                type: 'DELETE',
                success: function(result) {
                    console.log('성공:', result);
                    var messageElement = $(`#message-${messageId}`);
                    if (messageElement.length) {
                        messageElement.remove();
                    }
                },
                error: function(jqXHR) {
                    console.error('실패:', jqXHR.responseText);
                }
            });
        }
    });
});