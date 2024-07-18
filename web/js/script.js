const addOrgBtns = document.querySelectorAll("#add-org-btn");
addOrgBtns.forEach(btn => btn.addEventListener("click", addOrganizationEmptyHtml));

function addOrganizationEmptyHtml(event) {
    const orgContainer = event.currentTarget.closest("dl");
    const index = orgContainer.querySelectorAll("dd").length;
    event.currentTarget.insertAdjacentHTML('beforebegin', "<dd>" +
        "<input type='text' placeholder='Название' name='" + orgContainer.id + index + "' size='30' value='' required>" +
        "<input type='text' placeholder='Ссылка' name='" + orgContainer.id + index + "link'" + " size='30' value=''>" +
        "<div class='flex'>" +
        "<input type='text' placeholder='Начало, ММ/ГГГГ' name='" + orgContainer.id + index + "startDate'" + " size='30' value='' required>" +
        "<input type='text' placeholder='Окончание, ММ/ГГГГ' name='" + orgContainer.id + index + "endDate'" + " size='30' value='' required>" +
        "</div>" +
        "<input type='text' placeholder='Заголовок' name='" + orgContainer.id + index + "title'" + " size='30' value='' required>" +
        "<textarea placeholder='Описание' name='" + orgContainer.id + index + "description'" + " rows='3' cols='56'></textarea>" +
        "<button id='delete-org-btn' onclick='deleteOrganizationFromHtml(event)' type='button'>" +
        "Удалить организацию<img class='icon' src='assets/icons/trash.png' alt='Delete organization section'>" +
        "</button>" +
        "</dd>");
}

function deleteOrganizationFromHtml(event) {
    const orgContainer = event.currentTarget.closest("dl");
    const orgWrapper = event.currentTarget.closest("dd");
    orgWrapper.remove();
    const cards = orgContainer.querySelectorAll("dd");
    cards.forEach((card, index) => {
        const inputs = card.querySelectorAll("input");
        inputs.forEach(input => {
            input.name = input.name.replace(/\d/g, index);
        });
        const textarea = card.querySelector("textarea");
        textarea.name = textarea.name.replace(/\d/g, index);
    })
}
