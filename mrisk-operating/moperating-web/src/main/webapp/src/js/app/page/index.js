$(function(){
    var oFrm = document.getElementById('mainContent');
    oFrm.onload = oFrm.onreadystatechange = function() {
        if (this.readyState && this.readyState != 'complete') return;
        else {
            $(".menu-bar a:first").trigger("click");
        }
    }

})

var changeMenu = function(thisObject){
    var url = $(thisObject).attr('url');
    $("#mainContent").height($(window).height()-$('.top').height()-6);
    if (typeof url == 'undefined' || url.length == 0) {
        url = $("#basePath").val() + "/login/sideBar.do?menuId=" + $(thisObject).attr('menuId') + "&userId=" + $("#userId").val();
        window.frames["mainContent"].frames["leftFrame"].location = url;
        window.frames["mainContent"].document.getElementById("framesetBox").cols='188,*';
    }
    else {
        window.frames["mainContent"].document.getElementById("framesetBox").cols='0,*';
        window.frames["mainContent"].frames['contentFrame'].location = url;
    }
}