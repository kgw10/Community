document.addEventListener('DOMContentLoaded', function() {
    var searchQuery = document.getElementById('search-query');
    var chatroomListItems = Array.from(document.querySelectorAll('#chatroom-list li'));
    var itemsPerPage = 10; // 한 페이지에 표시할 목록 개수
    var currentPage = 1;
    var totalPages;

    // 검색 기능
    if (searchQuery) {
        searchQuery.addEventListener('input', function() {
            var query = searchQuery.value.toLowerCase();
            var filteredItems = chatroomListItems.filter(function(item) {
                var title = item.querySelector('a').textContent.toLowerCase();
                return title.includes(query) || query === '';
            });

            renderPage(filteredItems);
            updatePagination(filteredItems);
        });
    }

    // 페이지네이션 업데이트 함수
    function updatePagination(items) {
        totalPages = Math.ceil(items.length / itemsPerPage);
        var pagination = document.getElementById('pagination');
        pagination.innerHTML = '';

        if (totalPages > 1) {
            var prevButton = document.createElement('button');
            prevButton.textContent = '이전';
            prevButton.disabled = currentPage === 1;
            prevButton.addEventListener('click', function() {
                if (currentPage > 1) {
                    currentPage--;
                    renderPage(items);
                }
            });
            pagination.appendChild(prevButton);

            var pageInfo = document.createElement('span');
            pageInfo.textContent = currentPage + ' / ' + totalPages;
            pagination.appendChild(pageInfo);

            var nextButton = document.createElement('button');
            nextButton.textContent = '다음';
            nextButton.disabled = currentPage === totalPages;
            nextButton.addEventListener('click', function() {
                if (currentPage < totalPages) {
                    currentPage++;
                    renderPage(items);
                }
            });
            pagination.appendChild(nextButton);
        }
    }

    // 페이지 렌더링 함수
    function renderPage(items) {
        var start = (currentPage - 1) * itemsPerPage;
        var end = start + itemsPerPage;

        chatroomListItems.forEach(function(item) {
            item.style.display = 'none'; // 모든 아이템 숨기기
        });

        items.slice(start, end).forEach(function(item) {
            item.style.display = ''; // 현재 페이지의 아이템만 표시
        });

        updatePagination(items);
    }

    // 초기 렌더링
    renderPage(chatroomListItems);
});