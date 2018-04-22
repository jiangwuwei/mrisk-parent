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

function saveBusiTypeForm(){
		var formExp = "#editBusiTypeForm";
		var url =  $("#saveBusiTypeUrl").val();
		if(!validateScenes(formExp)){
			return;
		}
		submitAjaxForm(formExp,url,'reload');
}

var validateScenes= function validateScenesPolicies(form_exp){
	for (var i=0;i<$(form_exp)[0].length;i++)
	{
		if(!$(form_exp)[0].elements[i].value){
			showTempErrorPop("所有信息都不能为空!");
			return false;
		}
	}
	var sceneNo = $(form_exp+" input[name='id']").val();
	if(sceneNo.length !== 4){
		showTempErrorPop('编号必须为4位数字,参考其他类型');
		return false;
	}
	return true;
}


function saveDefiniForm(){
    var formExp = "#editDefinitionForm";
    var url =  $("#saveDefinitionUrl").val();
    if(!validateDefini(formExp)){
        return;
    }
    submitAjaxForm(formExp,url,'reload');
}

var validateDefini= function validateScenesPolicies(form_exp){
    for (var i=0;i<$(form_exp)[0].length;i++)
    {
    	if ( $(form_exp)[0].elements[i].name == 'id')
    		continue;
        if(!$(form_exp)[0].elements[i].value){
            showTempErrorPop("所有信息都不能为空!");
            return false;
        }
    }
    return true;
}