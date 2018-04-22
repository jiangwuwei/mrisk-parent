$(function () {
    $("dt").click(
        function () {
            if ($(this).closest("dl").hasClass("selected")) {
                $(this).closest("dl").removeClass("selected");
            } else {
                $(this).closest("dl").addClass("selected");
            }

        });

    $("dd a").click(
        function () {
            $("dd a").removeClass("selected");
            $(this).addClass("selected");
            var url = $(this).attr('url');
            if(typeof url != 'undefined' && url.length > 0){
                parent.window.frames["contentFrame"].location = url;
            }
        });

    $("dt:first").trigger("click");
    $("dd a:first").trigger("click");
});