$(document).ready(function() {
    // 채팅방 목록 페이지에서 검색 기능 구현
    $('#searchForm').submit(function(event) {
        event.preventDefault();
        var query = $('#search-query').val().toLowerCase();
        $('#chatroom-list li').each(function() {
            var title = $(this).find('a').text().toLowerCase();
            if (title.indexOf(query) !== -1) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });

    // 채팅방 생성 버튼 클릭 시 채팅방 생성 페이지로 이동
    document.getElementById('createRoomBtn').addEventListener('click', function() {
        // 채팅방 생성 페이지로 이동
        window.location.href = '/chatroom/create'; // 수정: '/chatroom/create'로 경로 설정
    });
});