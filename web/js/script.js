const addOrgBtns = document.querySelectorAll("#add-org-btn");
addOrgBtns.forEach(btn => btn.addEventListener("click", addOrganizationEmptyHtml));

function addOrganizationEmptyHtml(event) {
    const orgContainer = event.currentTarget.closest("dl");
    const index = orgContainer.querySelectorAll("dd").length;
    event.currentTarget.insertAdjacentHTML('beforebegin', "<dd class='job-card' data-section='" + orgContainer.id + index + "'>" +
        "<input type='text' placeholder='Название' name='" + orgContainer.id + index + "' size='30' value='' required>" +
        "<input type='text' placeholder='Ссылка' name='" + orgContainer.id + index + "link'" + " size='30' value=''>" +
        getJobHtml(orgContainer.id + index) +
        "<div class='org-buttons'>" +
        "<button id='add-job-btn' onclick='addJobToHtml(event)' type='button'>" +
        "Добавить позицию<img class='icon' src='assets/icons/add.png' alt='Add job section'>" +
        "</button>" +
        "<button id='delete-org-btn' onclick='deleteOrganizationFromHtml(event)' type='button'>" +
        "Удалить организацию<img class='icon' src='assets/icons/trash.png' alt='Delete organization section'>" +
        "</button>" +
        "</div>" +
        "</dd>");
}

function deleteOrganizationFromHtml(event) {
    const orgContainer = event.currentTarget.closest("dl");
    const orgWrapper = event.currentTarget.closest("dd");
    orgWrapper.remove();
    const cards = orgContainer.querySelectorAll("dd");
    cards.forEach((card, index) => {
        card.dataset.section = card.dataset.section.replace(/\d/g, index);
        const inputs = card.querySelectorAll("input");
        inputs.forEach(input => {
            input.name = input.name.replace(/\d/g, index);
        });
        const texts = card.querySelectorAll("textarea");
        texts.forEach(text => {
            text.name = text.name.replace(/\d/g, index);
        });
    })
}

function addJobToHtml(event) {
    const jobContainer = event.currentTarget.closest("dd");
    const sectionName = jobContainer.dataset.section;
    const buttonsContainer = jobContainer.querySelector(".org-buttons");
    buttonsContainer.insertAdjacentHTML('beforebegin', getJobHtml(sectionName));
}

function getJobHtml(sectionNameWithIndex) {
    return "<div class='job'>" +
        "<div class='flex'>" +
        "<input type='text' placeholder='Начало, ММ/ГГГГ' name='" + sectionNameWithIndex + "startDate'" + " size='30' value='' required>" +
        "<input type='text' placeholder='Окончание, ММ/ГГГГ' name='" + sectionNameWithIndex + "endDate'" + " size='30' value='' required>" +
        "</div>" +
        "<input type='text' placeholder='Заголовок' name='" + sectionNameWithIndex + "title'" + " size='30' value='' required>" +
        "<textarea placeholder='Описание' name='" + sectionNameWithIndex + "description'" + " rows='3' cols='56'></textarea>" +
        "<button id='delete-job-btn' onclick='deleteJobFromHtml(event)' type='button'>" +
        "Удалить позицию<img class='icon' src='assets/icons/trash.png' alt='Delete job section'>" +
        "</button>" +
        "</div>"
}

function deleteJobFromHtml(event) {
    const jobCard = event.currentTarget.closest("dd");
    const jobContainers = jobCard.querySelectorAll(".job");
    if (jobContainers.length === 1) {
        deleteOrganizationFromHtml(event);
        return;
    }
    const jobContainer = event.currentTarget.closest(".job");
    jobContainer.remove();
}
