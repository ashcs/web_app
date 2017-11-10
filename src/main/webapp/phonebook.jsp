<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>Тестовое задание</title>
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>

</head>

<body>
<h3>Справочник "Телефонная книга" <span><a href="index.jsp" class="btn btn-primary btn-xs">Место работы</a></span></h3>

<div>

  <!-- Nav tabs -->
  <ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active"><a href="#new-item-form" aria-controls="new-item-form" role="tab" data-toggle="tab">Заполнение справочника</a></li>
    <li role="presentation"><a href="#search-items-form" aria-controls="search-items-form" role="tab" data-toggle="tab">Поиск</a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="new-item-form">
    
	<br />
    
	<form class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-5" id="alert_place">
			</div>
		</div>
  		<div class="form-group">
    		<label for="lastname" class="col-sm-2 control-label">Фамилия</label>
    		<div class="col-sm-5">
      			<input type="text" class="form-control" id="lastname" name="lastname">
    		</div>
  		</div>
  		<div class="form-group">
    		<label for="firstname" class="col-sm-2 control-label">Имя</label>
    		<div class="col-sm-5">
      			<input type="text" class="form-control" id="firstname" name="firstname">
    		</div>
  		</div>
		<div class="form-group">
    		<label for="workphone" class="col-sm-2 control-label">Рабочий телефон</label>
    		<div class="col-sm-5">
      			<input type="text" class="form-control" name="workphone" id="workphone">
    		</div>
  		</div>
  		
 		<div class="form-group">
    		<label for="mobilephone" class="col-sm-2 control-label">Мобильный телефон</label>
    		<div class="col-sm-5">
      			<input type="text" class="form-control" name="mobilephone" id="mobilephone">
    		</div>
  		</div> 		
  		
  		<div class="form-group">
    		<label for="email" class="col-sm-2 control-label">Email</label>
    		<div class="col-sm-5">
      			<input type="email" class="form-control" name="email">
    		</div>
  		</div> 	
  		 		
 		<div class="form-group">
    		<label for="birthdate" class="col-sm-2 control-label">Дата рождения</label>
    		<div class="col-sm-5">
      			<input type="text" class="form-control" name="birthdate" id="birthdate">
    		</div>
  		</div> 		
  		  		
  		
		<div class="form-group">
    		<div class="col-sm-offset-2 col-sm-1">
    			<button type="reset" class="btn btn-danger" id="clear-item-btn">Очистить</button>
    		</div>
    		<div class="col-sm-4" style="text-align:right">
      			<button type="button" class="btn btn-primary" id="add-item-btn">Добавить</button>
&nbsp;&nbsp;&nbsp;
      			<button type="button" class="btn btn-success" id="send-item-btn">Отправить</button>
    		</div>  
  		</div>
	</form>    
    
    </div>
    
    
    <div role="tabpanel" class="tab-pane" id="search-items-form">
    <br />

    
	    <form class="form-horizontal">
    
    		<div class="form-group">
				<div class=" col-sm-6">
<div class="alert alert-success">
	Для поиска можно использовать частичное совпадение.
</div>				
				</div>
			</div>
    		<div class="form-group">
    			<label for="lastname" class="col-sm-1 control-label">Фамилия</label>
    			<div class="col-sm-2">
      				<input type="text" class="form-control" name="lastname">
    			</div>

    			<label for="firstname" class="col-sm-1 control-label">Имя</label>
    			<div class="col-sm-2">
      				<input type="text" class="form-control" name="firstname">
    			</div>
  			</div>

  			<div class="form-group">
    			<label for="workphone" class="col-sm-1 control-label">Рабочий телефон</label>
    			<div class="col-sm-2">
      				<input type="text" class="form-control" name="workphone">
    			</div>
			    <label for="mobilephone" class="col-sm-1 control-label">Мобильный телефон</label>
    			<div class="col-sm-2">	
					<input type="text" class="form-control" name="mobilephone">
			    </div>
  			</div>
  			
  			<div class="form-group">
    			<label for="email" class="col-sm-1 control-label">Email</label>
    			<div class="col-sm-2">
      				<input type="text" class="form-control" name="email">
    			</div>
			    <label for="birthdate" class="col-sm-1 control-label">Дата рождения</label>
    			<div class="col-sm-2">	
					<input type="text" class="form-control" name="birthdate">
			    </div>
  			</div>  			
  			
  			<div class="form-group">
    			<div class="col-sm-offset-5 col-sm-1">
      				<button type="button" class="btn btn-success" id="search-btn">Искать</button>
    			</div>
  			</div>	
  		
	    </form>
		<div id="result_place">
		</div>    	    
    </div>
  </div>

</div>
<script>
function clear_form_elements(ele) {
    $(ele).find(':input').each(function() {
        switch(this.type) {
            case 'password':
            case 'select-multiple':
            case 'select-one':
            case 'text':
            case 'number':
            case 'textarea':
            case 'email':
                $(this).val('');
                break;
            case 'checkbox':
            case 'radio':
                this.checked = false;
        }
    });
}

$('#add-item-btn').click(function(){
	var form =  $('#alert_place');
	$('#alert_container').remove();
	$.ajax({
		url: "phone/add",
		type: "POST",
		data: $('#new-item-form form').serialize(),
		dataType: "json",
		timeout: 8000,
		success: function(res){
			var alertbox = $('<div id="alert_container" class="alert"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button></div>');
			alertbox.hide();
			if (res.result == 1) {
				alertbox.addClass('alert-success').append(res.message);
				$('#send-item-btn').data('filename', res.filename);
				$('#add-item-btn').prop( "disabled", true );;
				//clear_form_elements($('#new-item-form form'));
			}
			if (res.result == 0){
				for (var i in res.errors) {
					alertbox.append(res.errors[i]+'<br />');
				}				
				alertbox.addClass('alert-danger');
			}
			form.append(alertbox);
			alertbox.slideDown(300).delay(12000).fadeTo(700, 0).slideUp(300, function() {
			    $(this).alert('close');
			});
		},
		error : function (xhr, status) {
		}
	}).always(function() { });	
	
})

$('#search-btn').click(function(){
	var form =  $('#result_place');
	$('#find_result').remove();
	$.ajax({
		url: "phone/find",
		type: "POST",
		data: $('#search-items-form form').serialize(),
		dataType: "json",
		timeout: 8000,
		success: function(res){
			var table = $('<table id="find_result" class="table table-bordered table-striped"><tr><th>ID</th><th>Фамилия</th><th>Имя</th><th>Рабочий телефон</th><th>Мобильный телефон</th><th>Email</th><th>Дата рождения</th></tr></table>');
			table.hide();
			if (res.result == 1) {
				for (var i in res.rows) {
					table.append('<tr>' + res.rows[i]+'</tr>');
				}	
				form.append(table.slideDown(300));

			}
			if (res.result == 0){
				var alertbox = $('<div id="find_result" class="alert"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button></div>');
				alertbox.hide().addClass('alert-danger').append(res.message);
				form.append(alertbox);
				alertbox.slideDown(300);
			}

		},
		error : function (xhr, status) {
		}
	}).always(function() { });		
})

$('#clear-item-btn').click(function(){ 
	if (!$('#send-item-btn').data('filename'))
		 return;
	
	$('#alert_container').remove();
	
	$.ajax({
		url: "phone/clear",
		type: "POST",
		data: {filename: $('#send-item-btn').data('filename')},
		dataType: "json",
		timeout: 8000,
		success: function(res){
			$('#send-item-btn').data('filename', '');
			$('#add-item-btn').prop( "disabled", false );
		},
		error : function (xhr, status) {
		}
	}).always(function() { });	
})


$('#send-item-btn').click(function(){ 
	$.ajax({
		url: "phone/send",
		type: "POST",
		data: {filename: $('#send-item-btn').data('filename')},
		dataType: "json",
		timeout: 8000,
		success: function(res){

		},
		error : function (xhr, status) {
		}
	}).always(function() { 
		$('#send-item-btn').data('filename', '');
		$('#add-item-btn').prop( "disabled", false );
		$('#clear-item-btn').click();
	});	
})
$('#add-item-btn').prop( "disabled", false );
</script>

</body>
</html>
