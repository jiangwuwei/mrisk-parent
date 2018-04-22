$(function(){

	//保存或者更新场景-策略集
	$(".save_btn").on("click", function(){
		var formId = $(this).closest('td').find('form').attr('id');
		var url = '';
		if(formId != 'addForm'){
			url = $("#updateUrl").val();
		}else{
			url = $("#insertUrl").val();
		}
		var form_exp = "#"+formId;

		if(!validateScenes(form_exp)){
			return;
		}
		submitAjaxForm(form_exp,url,'reload');
	});

	$(".close_btn").on("click", function(){
		$(this).parents('td').find(".tablewrapper").slideToggle();
	});

	$(".to_add_btn, .add_close_btn").on("click", function(){
		$("#add_tr").slideToggle();
	});

})

function savePolicies(){
		var formId = 'editPoliciesForm';
		var form_exp = "#"+formId;
		var url = '';
		if($("#editType").val() == 1){
			url = $("#insertUrl").val();
		}else{
			url = $("#updateUrl").val();
		}
		if(!validatePolicies(form_exp)){
			return;
		}
		submitAjaxForm(form_exp,url,'reload');
}

var validateScenes= function validateScenesPolicies(form_exp){
	for (var i=0;i<$(form_exp)[0].length;i++)
	{
		if(!$(form_exp)[0].elements[i].value){
			showTempErrorPop("所有信息都不能为空!");
			return false;
		}
	}
	var sceneNo = $(form_exp+" input[name='sceneNo']").val();
	if(sceneNo.length !== 4){
		showTempErrorPop('场景号必须为4位数字');
		return false;
	}
	return true;
}


var validatePolicies= function validateScenesPolicies(form_exp){
	for (var i=0;i<$(form_exp)[0].length;i++)
	{
		if(!$(form_exp)[0].elements[i].value){
			showTempErrorPop("所有信息都不能为空!");
			return false;
		}
	}
	return true;
}
