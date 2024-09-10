
$(document).ready(function() {


    // 채팅방 생성 페이지에서 사용자의 이름을 로컬 스토리지에서 가져와 사용
    $('#createChatRoomForm').submit(function(event) {
        event.preventDefault();
        var userName = localStorage.getItem('userName');
        if (!userName) {
            alert('이름이 저장되지 않았습니다. 이름을 등록해 주세요.');
            return;
        }
        var title = $('#title').val();
        var password = $('#password').val();
        $.post('/chatroom/create', { title: title, password: password, creator: userName }, function() {
            window.location.href = '/chatroom';
        });
    });
});