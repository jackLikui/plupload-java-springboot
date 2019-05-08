$(function($) {
    $.get("/uploadProject/download/getFiles", function(data){
        shtml = "";
        $.each(data,function (i, item) {
            var id = item["id"];
            var name = item["name"]
            var downUrl = "/uploadProject/download/download/" + id;
            shtml += "<a href='"+downUrl+"'>"+name+"</a><br>"
        })
        $("#fileList").html(shtml)
    });


});