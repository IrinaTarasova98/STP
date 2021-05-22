$('#addDynamicExtraFieldButton').click(function(e) {
 //необходима только 1 переменная DIV в которую мы собираем все елементы
  var div = $('<div/>', {
    'class' : 'DynamicExtraField'
  });

  $('<br/>').appendTo(div);
  $('<label/>').html("Картира ").appendTo(div);
  $('<input/>', {
    value : 'Удаление',
    type : 'button',
    'class' : 'DeleteDynamicExtraField'
  })
    .appendTo(div)
    .click(function(e) {
      $(this).parent().remove();

      e.preventDefault();
      return false;
  });
  
  $('<input/>', {
    value : 'Редактировать',
    type : 'button',
    'class' : 'EditDynamicExtraField'
  }).appendTo(div);

  //Добавляем уже собранный DIV в DynamicExtraFieldsContainer
  div.appendTo($('#DynamicExtraFieldsContainer'));
  
  e.preventDefault();
  return false;
});
 
//Для удаления первого поля
$('.DeleteDynamicExtraField').click(function(e) {
  $(this).parent().remove();

  e.preventDefault();
  return false;
});