<%@page import="org.omg.CORBA.Request"%>
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
<h3>Справочник "Место работы" <span><a href="phonebook.jsp" class="btn btn-primary btn-xs">Телефонная книга</a></span></h3>

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
    		<label for="workplace" class="col-sm-2 control-label">Место работы</label>
    		<div class="col-sm-5">
      			<input type="text" class="form-control" name="workplace" id="workplace">
    		</div>
  		</div>
  		
 		<div class="form-group">
    		<label for="workaddress" class="col-sm-2 control-label">Адрес работы</label>
    		<div class="col-sm-5">
      			<input type="text" class="form-control" name="workaddress" id="workaddress">
    		</div>
  		</div> 		
  		
		<div class="form-group">
    		<div class="col-sm-offset-2 col-sm-5">
      			<button type="button" class="btn btn-primary" id="add-item-btn">Добавить</button>
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
    			<label for="workplace" class="col-sm-1 control-label">Место работы</label>
    			<div class="col-sm-2">
      				<input type="text" class="form-control" name="workplace">
    			</div>
			    <label for="workaddress" class="col-sm-1 control-label">Адрес работы</label>
    			<div class="col-sm-2">	
					<input type="text" class="form-control" name="workaddress">
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
		url: "add",
		type: "POST",
		data: $('#new-item-form form').serialize(),
		dataType: "json",
		timeout: 8000,
		success: function(res){
			var alertbox = $('<div id="alert_container" class="alert"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button></div>');
			alertbox.hide();
			if (res.result == 1) {
				alertbox.addClass('alert-success').append(res.message);
				clear_form_elements($('#new-item-form form'));
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
		url: "find",
		type: "POST",
		data: $('#search-items-form form').serialize(),
		dataType: "json",
		timeout: 8000,
		success: function(res){
			var table = $('<table id="find_result" class="table table-bordered table-striped"><tr><th>ID</th><th>Фамилия</th><th>Имя</th><th>Место работы</th><th>Адрес работы</th></tr></table>');
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

</script>

</body>
</html>
