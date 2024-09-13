document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.delete-message').forEach(function(icon) {
        icon.addEventListener('click', function() {
            var chatRoomId = document.getElementById('chatRoomId').value; // Hidden input에서 값 읽기
            var messageId = this.getAttribute('data-message-id'); // 클릭된 아이콘에서 메시지 ID 가져오기
            var url = `/chatroom/${chatRoomId}/message/${messageId}/delete`;

            if (confirm('이 메시지를 삭제하시겠습니까?')) {
                fetch(url, {
                    method: 'DELETE',
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`네트워크 응답이 정상이 아닙니다. 상태 코드: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('성공:', data);
                    // 성공적으로 삭제된 후 UI 업데이트
                    var messageElement = document.getElementById(`message-${messageId}`);
                    if (messageElement) {
                        messageElement.remove();
                    }
                })
                .catch((error) => {
                    console.error('실패:', error);
                });
            }
        });
    });
});