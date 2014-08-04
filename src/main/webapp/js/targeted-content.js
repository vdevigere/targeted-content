//Tag-it
$(document).ready(function() {
    $(".tags").tagit({
    	fieldName: "tags"
    });

    $("#addContentBtn").click(function(e){
    	var contentHtml=$("#content-template").html();
    	$("#content-form-group").append(contentHtml);
    	return false;
    });

    $("#content-form-group").on("click",".deleteContent",function(e){
    	console.log("button clicked");
    	$(this).parents("#content-form").remove();
    	return false;
    });

});


