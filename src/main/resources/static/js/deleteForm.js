document.addEventListener('DOMContentLoaded', function() {
    // 메세지 삭제
    var deleteMessage=document.getElementById('deleteMessage');
    if(deleteMessage){
        deleteMessage.addEventListener('click', function (){

        });
    }

    //

    // 채팅방 삭제 버튼 클릭 시 비밀번호 입력 폼 표시
    var showDeleteFormButton = document.getElementById('showDeleteFormButton');
    if (showDeleteFormButton) {
        showDeleteFormButton.addEventListener('click', function() {
            var deleteForm = document.getElementById('deleteForm');
            var passwordInput = document.getElementById('deletePassword');
            if (deleteForm) {
                deleteForm.style.display = 'block';
            }
            this.style.display = 'none'; // 삭제 버튼 숨김
            passwordInput.focus();
        });
    }

    // 비밀번호 입력 폼 취소 버튼 클릭 시 폼 숨기기
    var cancelDelete = document.getElementById('cancelDelete');
    if (cancelDelete) {
        cancelDelete.addEventListener('click', function() {
            var deleteForm = document.getElementById('deleteForm');
            if (deleteForm) {
                deleteForm.style.display = 'none';
            }
            var showDeleteFormButton = document.getElementById('showDeleteFormButton');
            if (showDeleteFormButton) {
                showDeleteFormButton.style.display = 'block'; // 삭제 버튼 다시 표시
            }
        });
    }

    // 비밀번호 입력 폼 제출 시 처리
    var deleteForm = document.getElementById('deleteForm');
    if (deleteForm) {
        deleteForm.addEventListener('submit', function(event) {
            event.preventDefault();
            var passwordInput = document.getElementById('deletePassword');
            var errorMessage = document.getElementById('errorMessage');

            // AJAX 요청을 통해 서버에서 비밀번호 검증
            var xhr = new XMLHttpRequest();
            xhr.open('POST', this.action, true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function() {
                if (xhr.status === 200) {
                    // 비밀번호가 맞으면 페이지 리로드
                    window.location.href = '/chatroom/manage';
                } else {
                    // 비밀번호가 틀리면 오류 메시지 표시하고 입력 필드 비우기 및 포커스 이동
                    errorMessage.style.display = 'block';
                    passwordInput.value = ''; // 입력 필드 비우기
                    passwordInput.focus(); // 커서 포커스 이동
                }
            };
            xhr.send('password=' + encodeURIComponent(passwordInput.value));
        });
    }
});