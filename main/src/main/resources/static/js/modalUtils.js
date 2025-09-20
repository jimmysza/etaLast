// modalUtils.js
export function setupModal(openBtnElements, modalSelector, closeBtnSelector, onOpenCallback) {
    const modal = document.querySelector(modalSelector);
    const closeBtn = modal?.querySelector(closeBtnSelector);

    if (!modal || !closeBtn) return;

    openBtnElements.forEach(openBtn => {
        openBtn.addEventListener('click', (e) => {
            e.preventDefault();
            if (onOpenCallback) onOpenCallback(openBtn, modal);
            modal.classList.add('show');
        });
    });

    closeBtn.addEventListener('click', () => {
        modal.classList.remove('show');
    });

    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.classList.remove('show');
        }
    });
}
