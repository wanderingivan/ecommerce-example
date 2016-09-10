/**
 * 
 */

function getInputHtml(idx){
	return  '<textarea name="product.details['+idx+']" id="detailInput" class="form-control" placeholder="Add more details"></textarea>'
}

function getTasksHtml(task){
	return "<li>" +
	          "<div id='task"+task.id+"' class='media' style='display:none'>" +
				  "<div class='media-body'>" +
				    "<p class='media-heading'>"+task.assigner+"</p>" +
				    "<p class='text-muted'>"+task.description+"</p>" +
                    "<p class='text-muted'>"+task.created+"</p>" +
				  "</div>" +
			  "</div>" +
		   "</li>" +
		   "</li><li class='divider'></li>";
}

function getMessageHtml(message){
	return "<li>" +
	          "<div id='message"+message.id+"' class='media' style='display:none'>" +
				  "<div class='media-left'><img src='data:image/jpeg;base64,"+message.sender.imagePath+"'></img></div>" +
				  "<div class='media-body'>" +
				    "<p class='media-heading'>"+message.sender.username+"</p>" +
				    "<p class='text-muted'>"+message.message+"</p>" +
                    "<p class='text-muted'>"+message.sent+"</p>" +
				  "</div>" +
			  "</div>" +
		   "</li>" +
		   "</li><li class='divider'></li>";
}

function getProductHtml(product){
	return "<li>" +
	          "<div id='product"+product.id+"' class='media' style='display:none'>" +
				  "<div class='media-left'><img src='data:image/jpeg;base64,"+product.imagePath+"'></img></div>" +
				  "<div class='media-body'>" +
				    "<p class='media-heading'>"+product.productName+"</p>" +
				    "<p class='text-muted'>$"+product.price+"</p>" +
                    "<p class='text-muted'>Qty:"+product.quantity+"</p>" +
				  "</div>" +
			  "</div>" +
		   "</li><li class='divider'></li>";
}

function populateCart(event){
	var target = $("#cartDropdown");
     if($("#cartDropdown li").length === 0){
    	 	$.getJSON(window.location.origin +'/Ecommerce/cart/cart',function(jd){// XXX FIX THIS URL !!!
    	 		target.empty();
    	 		if(jd.items == null){
    	 			target.html("<li class='divider'></li>");
    	 			target.append("<p style='margin-left:15px;color:#22a17d;'>Your cart is empty</p>");
    	 		}else{
    	 			$.each(jd.items,function(index,data){
    	 				console.log(data); //XXX Debug message to remove
    	 				target.append(getProductHtml(data));
    	 				$("#product"+data.id).toggle('slide',{direction:'right'},750);
    	 				$('#product'+data.id).effect("highlight", {color:"rgba(34, 209, 163, 0.5)"}, 1000);
    	 			});
    	 			target.append("<li class='text-center'>Total: <span style='color:#22a17a'>"+jd.total+"</li>");
    	 		}
    	 		target.append("<li class='divider'></li><li class='text-center'><a class='btn-alt' href='/Ecommerce/order/checkout'>Checkout</a></li>");
		});
     }
}

function populateUnreadMessages(){
	var target = $("#messagesDropdown");
     if($("#messagesDropdown li").length === 0){
    	 	$.getJSON('/Ecommerce/message/unread-messages',function(jd){// XXX FIX THIS URL!!!
    	 		target.empty();
    	 		console.log("JD");
    	 		console.log(jd);
    	 		if(jd.messages.length == 0){
    	 			target.html("<li class='divider'></li>");
    	 			target.append("<p style='margin-left:15px;color:#22a17d;'>You have no unread messages</p>");
    	 		}else{
    	 			$.each(jd.messages,function(index,data){
    	 				target.append(getMessageHtml(data));
    	 				$("#message"+data.id).toggle('slide',{direction:'right'},750);
    	 				$('#message'+data.id).effect("highlight", {color:"rgba(34, 209, 163, 0.5)"}, 1000);
    	 			});
    	 		}
    	 		target.append("<li class='divider'></li><li class='text-center'><a class='btn-alt' href='/Ecommerce/message/user-messages'>Your Messages</a></li>");
		});
     }
}

function populatePendingTasks(){
	var target = $("#dropdownTasks");
     if($("#dropdownTasks li").length === 0){
    	 	$.getJSON('/Ecommerce/admin/pendingTasks',function(jd){// XXX FIX THIS URL!!!
    	 		target.empty();
    	 		if(jd.tasks.length == 0){
    	 			target.html("<li class='divider'></li>");
    	 			target.append("<p style='margin-left:15px;color:#22a17d;'>You have no pending tasks</p>");
    	 		}else{
    	 			$.each(jd.tasks,function(index,data){
    	 				target.append(getTasksHtml(data));
    	 				$("#task"+data.id).toggle('slide',{direction:'right'},750);
    	 				$('#task'+data.id).effect("highlight", {color:"rgba(34, 209, 163, 0.5)"}, 1000);
    	 			});
    	 		}
		});
     }
}

function createInput(node){
	var count = $('#detailsGroup #detailInput').length;
	$('#detailsGroup').append(getInputHtml(count));
}

function setUpForm(){
	$('#addDetail').click(function(){
		createInput(parent);
	});
}

$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
    var token;
    if (!options.crossDomain) {
      token = $('meta[name="_csrf"]').attr('content');
      if (token) {
        return jqXHR.setRequestHeader('X-CSRF-Token', token);
      }
    }
  });

$(document).ready(function(){
	


	//Task & Unread Messages count functions
	$.subscribe("addUnreadCount",function(event,data){
		var jd = JSON.parse(event.originalEvent.data);
		$("#unread").html(jd.unread + " Unread");
		}
	);

	$.subscribe("addTasksCount",function(event,data){
		var jd = JSON.parse(event.originalEvent.data);
		$("#tasks").html(jd.message);
		}
	);
	
	$.subscribe('loadLog',function(event,data){
        var result = event.originalEvent.request.responseText;
        $('#log').html(result);
	 });
	
	// Ajax Dropdowns
	$("#dropdownMenuCart").click(populateCart);
	$("#dropdownMenuMessages").click(populateUnreadMessages);
	$("#dropdownMenuTasks").click(populatePendingTasks);
	
	//Buttons
	$('#addDetail').click(function(){
		createInput(parent);
	});
	
	$("#msgButton").click(function(){
	    $("#msgButton").toggle(200);
	    	$("#msgForm").toggle(300);
	});
	
	//  Check Radio-box
    $(".rating input:radio").attr("checked", false);
    $('.rating input:radio').click(function () {
				console.log("radio clicked");
        $(".rating span").removeClass('checked');
        $(this).parent().addClass('checked');
    });
    
    $(".fa").click(function(){
    	$(this).closest('.panel').find('.panel-body')
                                 .slideToggle(700);
    });	
});
