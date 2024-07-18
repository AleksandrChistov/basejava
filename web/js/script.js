const addOrgBtns = document.querySelectorAll("#add-org-btn");
addOrgBtns.forEach(btn => btn.addEventListener("click", addOrganizationEmptyHTML));

function addOrganizationEmptyHTML(event) {
    const orgContainer = event.target.closest("dl");
    const index = orgContainer.querySelectorAll("dd").length;
    event.currentTarget.insertAdjacentHTML('beforebegin', "<dd>" +
        "<input type='text' placeholder='Название' name='" + orgContainer.id + index + "' size='30' value='' required>" +
        "<input type='text' placeholder='Ссылка' name='" + orgContainer.id + index + "link'" + " size='30' value=''>" +
        "<div class='flex'>" +
        "<input type='text' placeholder='Начало, ММ/ГГГГ' name='" + orgContainer.id + index + "startDate'" + " size='30' value='' required>" +
        "<input type='text' placeholder='Окончание, ММ/ГГГГ' name='" + orgContainer.id + index + "endDate'" +  " size='30' value='' required>" +
        "</div>" +
        "<input type='text' placeholder='Заголовок' name='" + orgContainer.id + index + "title'" +  " size='30' value='' required>" +
        "<textarea placeholder='Описание' name='" + orgContainer.id + index + "description'" + " rows='3' cols='56'></textarea>"
        +
        "</dd>");
}
