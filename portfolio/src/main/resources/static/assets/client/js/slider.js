$(document).ready(function () {
	
	var s = 0;
	var delay = 3500;
	var length = $(".demnodey-wrapper div").length;
	var bullet = $(".demnodey-pagination span");
	var pause = false;
	
	var loop = setInterval(function () {
		if (!pause) {
			s = s + 1;
			s = s == length ? 0 : s;
//			var slide = 1040 * s;
            var slide = 1000 * s;
			
			pagination(bullet[s])
			
			$(".demnodey-wrapper").css("transform", "translateX(-"+slide+"px)");
		}
	}, delay);
	
	$(".demnodey-pagination span").on("click", function () {
		
		s = $(this).attr("idx") - 1;
		s = s == length ? 0 : s;
//		var slide = s * 1040;
        var slide = 1000 * s;
		
		pause = true; // bullet 클릭시 auto slider 잠시 멈춤
		
		pagination(this);
		
		$(".demnodey-wrapper").css("transform", "translateX(-"+slide+"px)");
		
		setTimeout(function () {
			pause = false;
		}, delay);
	})
	
	// pagination :: bullet 자동 이동
	function pagination (target) {
		$(bullet).each(function (i, d) {
			if ($(d).hasClass("active")) {
				$(d).removeClass("active");
			}
		})
		
		$(target).addClass("active");
	}
	
})