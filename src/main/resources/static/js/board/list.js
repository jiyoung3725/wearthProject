//카드 클릭 시 게시글 상세정보 가져오기
		var loginUser = $("#loginUser").text();
		console.log("접속자:"+loginUser);
		var boardno;
		

		
		$(".card").click(function(){
			 //현재접속자와 writer를 비교하기 위해 변수 선언
	        var writerNo = $(this).find("#writerNo").text();
	        //현재접속자와 writer를 비교하기 위해 모달에 작성자 번호 삽입
			$("#board_writerNo").text(writerNo);
		
			console.log("작성자번호:"+writerNo);
			//session.u.userno와 writerNo가 같으면 
			if(writerNo == loginUser){
				$(".modal-footer-left-UpdateDelete").css("display", "flex");
				$(".modal-footer-left-like").css("display", "none");
				//$(".like-button").css("display", "none");
			}else{
				$(".modal-footer-left-UpdateDelete").css("display", "none");
				$(".modal-footer-left-like").css("display", "flex");
				//$(".like-button").css("display", "flex");
			}
			
			boardno = $(this).find("#board_boardno").text();
			console.log("현재 boardno:"+boardno);
	        
			var title = $(this).find("#b_title").text();
	        var content = $(this).find("#b_content").text();
	        var writer = $(this).find("#nickname").text();
	        var imageUrl = $(this).find(".card-img-top").attr("src");
	       
		 	
	        // 모달에 정보 넣기
	        $("#boardno").val(boardno);
	        $("#board_title").text(title);
	        $("#board_content").text(content);
	        $("#board_writer").text(writer);
			$("#board_img").attr("src", imageUrl);
			
			//댓글리스트 가져오기
			// 기존에 있는 내용을 지우고
		    //commentArea.empty(); -> 위치상 왜 안되는지?
			listComment(boardno);
			
		})//카드 클릭 이벤트 끝
		//게시글 삭제 기능
		$("#btnDelete").click(function(event){
			var header = $("meta[name='_csrf_header']").attr('content');
			var token = $("meta[name='_csrf']").attr('content');  
			if(confirm('정말로 삭제하시겠습니까?')==true){
				console.log("삭제 게시글 번호:"+boardno);
				$.ajax({
					url : "/board/delete/"+boardno,
					type : "GET",
					data : {boardno:boardno},
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success : function(){
					 	location.href="/board/list/1";
					}, error: function(){
						alert("다시 시도해주세요.");
					}
				});
			
			}else{
				event.preventDefault();
			}
		});
		
		//게시글 수정
		$("#btnUpdate").click(function(event){
			location.href="/board/update/"+boardno;
		});
		
		//댓글 입력
		$("#btn_addComment").click(function(){
			event.preventDefault();	//페이지 새로고침 막기
			var header = $("meta[name='_csrf_header']").attr('content');
			var token = $("meta[name='_csrf']").attr('content');
			var boardno = $("#boardno").val();
			var userno = $("#userno").val();
			var c_content = $("#c_content").val();
			console.log("boardno:"+boardno);
			console.log("userno:"+userno);
			console.log("c_content:"+c_content);
			if(!c_content){
				alert("댓글을 입력하세요.");
			}else{
				$.ajax({
					url : "/comments/insert",
					type : "POST",
					data : {boardno:boardno, userno:userno, c_content:c_content},
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success : function(data){
						console.log("댓글: "+c_content);
						// 댓글 입력 성공 시 그 모달을 그대로 새로고침해서 띄워놓기 
						// boardno를 가지고 있으니까, 그 boardno의 정보를 띄우면 될거 같음
						$("#c_content").val("");
						listComment(boardno);
						console.log(data);
					}, error: function(){	//	todo : 댓글을 입력하세요.
						swal('댓글 남기기 실패','댓글의 내용을 입력해주세요.','error')
						.then(function(){
							//todo
							console.log("페이지 로딩 시키기");
						})
					}
				});
			}
			
		})//댓글 입력끝
		
		//댓글 불러오는 기능 함수로 정의
		function listComment(boardno){
			
			var header = $("meta[name='_csrf_header']").attr('content');
			var token = $("meta[name='_csrf']").attr('content');
			$.ajax({
				url : "/comments/list",
				type : "POST",
				data : {boardno:boardno},
				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				success : function(data){
					var commentArea = $(".commentArea");
    
			        // 기존에 있는 내용을 지우고
			        commentArea.empty(); //ajax을 성공해야작동해서 그런지 속도가 느림....
			
			        // 상태success : function한 댓글들의 값을 가져온다.
			        $.each(data, function(i, value) {
			            var commentWriter = value.nickname;
			            var commentContent = value.c_content;
			
			            // 댓글 작성자와 내용을 표시하는 div를 만듭니다.
			            var commentContentDiv = $("<div>").addClass("commentContent").text(commentWriter+" : "+commentContent);
			
			            // 댓글 영역에 댓글 요소들을 추가합니다.
			            var commentDiv = $("<div>").addClass("comment").append(commentContentDiv);
			
			            commentArea.append(commentDiv);
			        });
				}, error: function(){
					alert("실패");
				}
			});	//댓글 리스트 가져오기 끝
		}

	//좋아요 기능
	$(document).ready(function () {
		let isLiked = false;
	
		$(".like-button").click(function () {
			event.preventDefault();	//페이지 새로고침 막기
			
			// isLiked 변수를 토글
			isLiked = !isLiked;
			
			// .liked 클래스를 토글하여 좋아요 버튼 스타일 변경
			$(this).toggleClass("liked", isLiked);
		
		var header = $("meta[name='_csrf_header']").attr('content');
		var token = $("meta[name='_csrf']").attr('content');
		$.ajax({
			url : "/liked/insertOrDelete/"+boardno,
			type : "get",
			data : {boardno:boardno},
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(data){
			 	if(data == "false"){
					 alert("좋아요가 취소되었습니다.");
				 }
			}, error: function(){
				alert("다시 시도해주세요.");
			}
		});
	});
});
			