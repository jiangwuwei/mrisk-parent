function clearAction(){
	document.getElementById("loginName").value="";
	document.getElementById("loginPwd").value="";
}

var loginFlag = false;

function verifyAction(){
    var loginName = document.getElementById("loginName").value;
 	if ( loginName == ""){
 		showTempErrorPop("温馨提示：请您输入【登录用户名】");
    	return;
    }
    var loginPwd = document.getElementById("loginPwd").value;
 	if ( loginPwd == ""){
        showTempErrorPop("温馨提示：请您输入【登录密码】");
    	return;
    }

	var pwd = hex_md5(loginPwd).substring(8,24);
	$("#loginPwd").val(pwd);
	$("#submitForm").submit();
}
function onKeyDonwAction(){
     switch (event.keyCode){
         case 13:
         	verifyAction();
     }        
}

function findInitCookies(){
	var box = getCookie("box_user_passw");
	var username = getCookie("loginName");
	var password = getCookie("loginPwd");
	if( box == 'yes' ){
		submitForm.cookieCheck.checked = true;
		submitForm.loginName.value = username;
		submitForm.loginPwd.value = password;
	}
}

function saveCookie(name,value){
   var exp  = new Date();
   exp.setTime(exp.getTime() + 2*24*60*60*1000);
   document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

function checkCookie(){
   if(submitForm.cookieCheck.checked){
	   saveCookie("loginName",submitForm.loginName.value);
	   saveCookie("loginPwd",submitForm.loginPwd.value);
	   saveCookie("box_user_passw","yes");
   }else{
	   delCookie("box_user_passw");
   }
}

function getCookie(name){
   var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr != null) {
    	return (arr[2]); 
    }
    return null;
}

function delCookie(name){
   var exp = new Date();
   exp.setTime(exp.getTime() - 1);
   var cval = getCookie(name);
   if(cval != null) {
	   document.cookie= name + "="+cval+";expires="+exp.toGMTString();
   }
}
