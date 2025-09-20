// Espera a que el DOM esté listo
document.addEventListener('DOMContentLoaded', () => {

    const setupModal = (openBtnElements, modalSelector, closeBtnSelector) => {
        const modal = document.querySelector(modalSelector);
        const closeBtn = modal?.querySelector(closeBtnSelector);

        if (!modal || !closeBtn) return;

        // Recorre cada botón que abre el modal
        openBtnElements.forEach(openBtn => {
            openBtn.addEventListener('click', (e) => {
                e.preventDefault();
                modal.classList.add('show'); // 🔓 abrir
            });
        });

        // Cierra con el botón X
        closeBtn.addEventListener('click', () => {
            modal.classList.remove('show'); // 🔒 cerrar
        });

        // Cierra si se hace click fuera del modal
        window.addEventListener('click', (e) => {
            if (e.target === modal) {
                modal.classList.remove('show'); // 🔒 cerrar
            }
        });
    };

    // ⚡ Configura los modales que quieras abrir
    /*setupModal(document.querySelectorAll('.text-open-delete'), '.modal-delete', '.close-btn-modal');*/
    setupModal([document.querySelector('#open-add-modal')], '.modal-add', '.close-btn-modal');
    setupModal([document.querySelector('.edit-btn')], '.modal-edit', '.close-edit');

});


const sidebar = document.querySelector('.side-bar');
const sidebarToggle = document.getElementById('hideBar');

sidebarToggle.addEventListener("click", () => {
    sidebar.classList.toggle("translate-bar");
    sidebarToggleDashboard.classList.toggle("show");
});





const dropdowns = document.querySelectorAll('.dropdown');

dropdowns.forEach(dropdown => {
    const container = dropdown.querySelector('.dropdown-grafica-container');
    dropdown.addEventListener('click', function (e) {
        if (e.target.tagName === 'A') e.preventDefault();

        // Cerrar todos los dropdowns excepto el actual
        dropdowns.forEach(d => {
            if (d !== dropdown) {
                d.classList.remove('dropdownUp');
                const c = d.querySelector('.dropdown-grafica-container');
                if (c) c.classList.remove('flexing');
            }
        });

        // Alternar el dropdown clicado
        dropdown.classList.toggle('dropdownUp');
        if (container) container.classList.toggle('flexing');
    });
});


document.addEventListener("DOMContentLoaded", () => {
    const items = document.querySelectorAll(".descripcion-hover");
    const modal = document.getElementById("descripcion-modal");
    const modalText = document.getElementById("descripcion-text");

    let timer;

    items.forEach(item => {
        item.addEventListener("mouseenter", (e) => {
            timer = setTimeout(() => {
                // Texto dinámico
                modalText.textContent = item.getAttribute("data-descripcion");

                // Posición cerca del elemento hovered
                const rect = item.getBoundingClientRect();
                modal.style.top = `${rect.bottom + window.scrollY + 10}px`; // debajo de la celda
                modal.style.left = `${rect.left + window.scrollX}px`;

                modal.classList.remove("hidden");
            }, 2000); // 2 segundos
        });

        item.addEventListener("mouseleave", () => {
            clearTimeout(timer);
            modal.classList.add("hidden");
        });
    });
});


