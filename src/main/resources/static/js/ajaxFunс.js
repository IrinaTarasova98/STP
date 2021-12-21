// нажатие на кнопку "Удалить"
function delEl(id)
{
    let house = document.getElementById('house').value;
    let room = document.getElementById('room').value;
    let sensor = document.getElementById('sensor').value;
    let tableName = document.getElementById('tableName').value;

    $.ajax({
        url: '/del', // отправить запрос на адрес /del
        type: "POST", // метод отправки данных
        data: {
            house: house, room: room, sensor: sensor, tableName: tableName, id: id
        },
        success : function(url) {
            window.location.replace(url);
        },
        error: function () {
            alert("Не удалось удалить запись.");
        }
    })
}

// нажатие на кнопку "Перейти"
function next(currId)
{
    let house = document.getElementById('house').value;
    let room = document.getElementById('room').value;
    let sensor = document.getElementById('sensor').value;
    let tableName = document.getElementById('tableName').value;

    $.ajax({
        url:'/next',
        type: "GET",
        data: {
            house: house, room: room, sensor:sensor, tableName: tableName, id: currId
        },
        success : function(url) {
            window.location.replace(url);
        },
        error: function () {
            alert("Невозможно перейти дальше.");
        }
    })
}

// нажатие на кнопку "Назад"
function back()
{
    let house = document.getElementById('house').value;
    let room = document.getElementById('room').value;
    let sensor = document.getElementById('sensor').value;

    $.ajax({
        url: '/back',
        type: "GET",
        data: {
            house: house, room: room, sensor: sensor
        },
        success : function(url) {
            window.location.replace(url);
        },
        error: function () {
            alert("Невозможно перейти назад.");
        }
    })
}

// спрятать ненужные кнопки при загрузке страницы
document.addEventListener('DOMContentLoaded', function() {
    let tableName = document.getElementById('tableName').value;
    switch(tableName)
    {
        // на странице ДОМОВ убрать кнопку перехода "назад"
        case "House":
            document.getElementById("goBack").remove();
            break;
        // на странице ЗНАЧЕНИЙ убрать все кнопки перехода "вперед"
        case "Value":
            const elements = document.getElementsByClassName("btn btn-primary");
            while(elements.length > 0){
                elements[0].parentNode.removeChild(elements[0]);
            }
            break;
    }
}, false);