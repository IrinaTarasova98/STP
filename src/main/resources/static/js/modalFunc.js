// Получить модальное окно
const modal = document.getElementById("myModal");

// Получить кнопку добавления
const btnAdd = document.getElementById("myBtnAdd");

// Получить элемент <span>, который закрывает модальное окно
const span = document.getElementsByClassName("close")[0];

// При нажатии кнопки c "close" - закрыть модальное окно
span.onclick = function() {
    modal.style.display = "none";
}

// При нажатии вне модального окна - закрыть модальное окно
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

// открыть модальное окно для добавления
btnAdd.onclick = function() {
    // очистить поля
    const arr = document.getElementsByName('array[]');
    for (let i = 0; i < arr.length; i++) {
        arr[i].setAttribute("value", "");
    }
    // установить функцию кнопки
    document.getElementById("modalbtn").setAttribute("onclick", "addEl()");
    // установить заголовок модального окна
    document.getElementById("modalTitle").textContent = "Добавить";
    modal.style.display = "block";
}

// нажатие на кнопку "Сохранить" при добавлении
function addEl()
{
    // параметры для отправки
    let house = document.getElementById('house').value;
    let room = document.getElementById('room').value;
    let sensor = document.getElementById('sensor').value;
    let tableName = document.getElementById('tableName').value;
    // сбор значений из полей ввода
    let inf = $("input[name='array[]']")
        .map(function(){return $(this).val();}).get();

    for (let i = 0; i < inf.length; i++) {
        inf[i] = encodeURIComponent(inf[i]);
    }

    $.ajax({
        url: '/add',
        type: "POST",
        data: {
            house: house, room: room, sensor: sensor,
            tableName: tableName, setParams: inf
        },
        success : function(url) {
            window.location.replace(url);
        },
        error: function (url) {
            alert("Не удалось добавить элемент.");
        }
    })
}

// получить элемент для редактирования
function getEl(idBtn) {
    // параметры для отправки
    let tableName = document.getElementById('tableName').value;
    let house = document.getElementById('house').value;
    let room = document.getElementById('room').value;
    let sensor = document.getElementById('sensor').value;

    // отправить GET запрос в БД и получить данные элемента с id = idBtn
    $.ajax({
        url:'/getElement',
        type: "GET",
        dataType: "json",
        data: {
            house: house, room: room, sensor:sensor, tableName: tableName, id: idBtn
        },
        success: function(resp) {
            // записать полученные данные в ячейка
            const arr = document.getElementsByName('array[]');
            for (let i = 0; i < arr.length; i++) {
                arr[i].setAttribute("value", resp.params[i]);
            }

            // сохранить id изменяемого элемента (для функции update)
            document.getElementById("updateId").setAttribute("value", idBtn);
            // установить функцию кнопки
            document.getElementById("modalbtn")
                .setAttribute("onclick", "updateEl()");
            // установить заголовок модального окна
            document.getElementById("modalTitle").textContent = "Редактировать";
            // запустить модальное окно
            modal.style.display = "block";
        },
        error: function () {
            alert("Ошибка получения элемента.");
        }
    })
}

// нажатие на кнопку "Сохранить" при редактировании
function updateEl()
{
    let tableName = document.getElementById('tableName').value;
    let house = document.getElementById('house').value;
    let room = document.getElementById('room').value;
    let sensor = document.getElementById('sensor').value;
    let id = document.getElementById("updateId").value;
    // сбор значений из полей ввода
    let inf = $("input[name='array[]']")
        .map(function(){return $(this).val();}).get();

    for (let i = 0; i < inf.length; i++) {
        inf[i] = encodeURIComponent(inf[i]);
    }

    $.ajax({
        url: '/updateElement',
        type: "POST",
        data: {
            house: house, room: room, sensor: sensor, tableName: tableName, setParams: inf, id : id
        },
        success: function(url) {
            window.location.replace(url);
        },
        error: function () {
            alert("Возникла ошибка обновления записи.");
        }
    })
}
