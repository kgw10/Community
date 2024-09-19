
document.addEventListener('DOMContentLoaded', function() {
    // 홈 버튼 클릭 시 홈 페이지로 이동
    var goHomeButton = document.getElementById('goHomeButton');
    if (goHomeButton) {
        goHomeButton.addEventListener('click', function() {
            window.location.href = '/chatroom/manage';
        });
    }

    // 채팅방 생성 버튼 클릭 시 채팅방 생성 페이지로 이동
    var createRoomBtn = document.getElementById('createRoomBtn');
    if (createRoomBtn) {
        createRoomBtn.addEventListener('click', function() {
            window.location.href = '/chatroom/create'; // 채팅방 생성 페이지로 이동
        });
    }
});
