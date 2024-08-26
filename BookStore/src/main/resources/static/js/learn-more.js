document.addEventListener('DOMContentLoaded', function() {
    const description = document.getElementById('description');
    const readMoreLink = document.getElementById('readMore');

    readMoreLink.addEventListener('click', function() {
        description.classList.toggle('expanded');
        if (description.classList.contains('expanded')) {
            readMoreLink.textContent = 'Read less';
        } else {
            readMoreLink.textContent = 'Read more';
        }
    });
});