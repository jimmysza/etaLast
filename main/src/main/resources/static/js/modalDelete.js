import { setupModal } from './modalUtils.js';

document.addEventListener('DOMContentLoaded', () => {
    const deleteBtns = document.querySelectorAll('.text-open-delete');
    setupModal(
        deleteBtns,
        '.modal-delete',
        '.close-btn-modal',
        (openBtn, modal) => {
            const id = openBtn.getAttribute('data-id');
            const deleteForm = modal.querySelector('#delete-form');
            if (deleteForm && id) {
                deleteForm.setAttribute('action', `/actividades/eliminar/${id}`);
            }

        }
    );
});
