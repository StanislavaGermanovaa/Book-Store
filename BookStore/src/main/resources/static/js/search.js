document.addEventListener('DOMContentLoaded', function () {

    const searchInput = document.getElementById('search');
    const searchForm = document.querySelector('.field-search');

    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const query = searchInput.value.trim();

        if (query) {
            window.location.href = `/books/search?query=${encodeURIComponent(query)}`;
        }
    });
});

