
// (교육,강연) 버튼그룹 버튼 누를 때 이벤트 작동
function showContent(contentNumber) {
    // 모든 content 클래스 요소를 선택합니다.
    var contents = document.querySelectorAll('.content');

    // 선택한 contentNumber에 해당하는 요소만 보이도록 스타일을 변경합니다.
    for (let i = 0; i < contents.length; i++) {
        if (i === contentNumber - 1) {
            contents[i].style.display = 'block';
        } else {
            contents[i].style.display = 'none';
        }
    }
}
// 교육 댓글 (후기) 
$(function() {

    var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
    var eduNO = $("#eduNO").val();
   
	// 초기화 시에 서버에서 댓글 목록을 가져와 화면에 표시
	 loadComments(eduNO);
	 
	$("#btnReply").on("click", function(event) {
    event.preventDefault();
    
    var commentText = $("#content").val().trim(); // 변경된 부분
    
    if (commentText !== "") {
        $.ajax({
            url: "/school/education/review/insert",
            type: "post",
            data: { eduNO: eduNO, opinionContent: commentText },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(result) {
                if (result === 1) {
                    $("#content").val(""); 
                    // 댓글 등록 후 화면 갱신을 하지 않고 새로운 댓글만 추가
                    loadComments(eduNO); 
                } else {
                    swal('', '댓글 등록에 실패하였습니다.', 'error');
                }
            },
            error: function() {
                swal('', '댓글 등록 중 오류가 발생하였습니다.', 'error');
            }
        });
    }
});

  // 교육 댓글(후기) 목록을 가져와 화면에 표시하는 함수
	function loadComments(eduNO) {
	console.log("loadComment eduNO : " + eduNO)
    $.ajax({
        url: `/school/education/review/list/`+eduNO, // 위에서 작성한 컨트롤러 경로
        success: function(comments) {
				$("#comments").empty();
            $.each(comments, function(){
				var div2 = $("<div></div>").addClass("card").css("width","95%");
				var div = $("<div></div>").addClass("card-body");
				var h4 = $("<h4></h4>").html(this.ID).css("font-weight","bold");
				var p = $("<p></p>").html(this.OPINIONCONTENT);
				var p2 = $("<p></p>").html(this.OPINIONDATE);
				$(div).append(h4,p,p2);
				$(div2).append(div);
				 $("#comments").append(div2);
			})
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
	} 
});	
	
//문의하기 시작합니다.
$(function(){
	// 교육문의 게시글 작성 모달
	var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
	var exampleModal = document.getElementById('exampleModal');
	
	 loadEducationOpinion();
	
	exampleModal.addEventListener('show.bs.modal', function (event) {
	  // Button that triggered the modal
	  var button = event.relatedTarget;
	
	  // Update the modal's content.
	  var modalTitle = exampleModal.querySelector('.modal-title');
	  var modalBodyInput = exampleModal.querySelector('.modal-body input');
	
	  modalTitle.textContent = '문의하기'; // Change modal title
	  modalBodyInput.value = ''; // Clear input fields
	
	  // Handle 등록 버튼 클릭 이벤트
	  $("#btnEduOpinion").click(function () {
		var eduNO = document.getElementById('eduNO').value;
	    var opinionName = document.getElementById('opinionName').value;
	    var opinionContent = document.getElementById('opinionContent').value;
	    var opinionPwd = document.getElementById('opinionPwd').value;
		
	     $.ajax({
	       type: 'POST',
	       url: '/school/education/opinion/insert',
	       data: {
			 eduNO : eduNO,
	         opinionName: opinionName,
	         opinionContent: opinionContent,
	         opinionPwd: opinionPwd
	       },
	       beforeSend: function(xhr) {
	                xhr.setRequestHeader(header, token);
	       },
	       success: function(result) {
			   if(result === -1){
	        	swal("게시글 등록 성공","문의 게시글이 등록되었습니다.","success");
			   }else{
				swal("게시글 등록 실패","문의 게시글 등록에 실패했습니다.","warning");  
			   }
	       },
	       error: function(error) {
	         swal("게시글 등록 실패","문의 게시글 등록중 오류가 발생했습니다.","error");
	       }
	     });
	
	    // 모달 숨기기
	    $(exampleModal).modal('hide');
	  });
	});
	

// 교육문의 게시글 조회
function loadEducationOpinion() {
	var eduNO = document.getElementById("eduNO").value;
    $.ajax({
        url: '/school/education/opinion/list/' + eduNO,
        success: function (data) {
            // 서버로부터 받아온 데이터를 이용하여 페이지 내용 업데이트
            $("#tableList").empty();
            $.each(data, function () {
			    var tr = $("<tr></tr>").addClass("clickable").attr("data-toggle", "collapse").attr("data-target", "#details" + this.opinionNO); // opinionNO에 따라 details 뒤에 번호를 붙여 유니크한 ID를 생성
			    var td1 = $("<td></td>").html(this.opinionNO);
			    var td2 = $("<td></td>").html(this.opinionName);
			    var td3 = $("<td></td>").html(this.id);
			    var td4 = $("<td></td>").html(this.opinionDate);
			    var pwd = $("<span><span>").html(this.opinionPwd).css("display","none");
			    
			    var tr2 = $("<tr></tr>").addClass("collapse").attr("id", "details" + this.opinionNO); // opinionNO에 따라 details 뒤에 번호를 붙여 유니크한 ID를 생성
			    var td5 = $("<td></td>").attr("colspan", 4).addClass("post-details");
			    var label = $("<label>비밀번호를 입력하세요 : </label>").attr("for", "password" + this.opinionNO); // opinionNO에 따라 password 뒤에 번호를 붙여 유니크한 ID를 생성
			    var input = $("<input>").attr("type", "password").attr("id", "password" + this.opinionNO).attr("required", true);
					
				var button = $("<button>확인</button>").attr("data-opinionNO", this.opinionNO); // data-opinionNO 속성에 opinionNO 저장
			    var div = $("<div></div>").attr("id", "postContent" + this.opinionNO).css("display", "none").html(this.opinionContent); // opinionNO에 따라 postContent 뒤에 번호를 붙여 유니크한 ID를 생성
				
				button.on("click", function () {
				    var opinionNO = $(this).attr("data-opinionNO"); // data-opinionNO 속성에서 opinionNO 가져옴
				    var passwordInput = document.getElementById('password' + opinionNO).value;
				    var correctPassword = pwd.text();
				    
				    console.log("passwordInput : " + passwordInput);
				    console.log("correctPassword : " + correctPassword);
				    
				    if (passwordInput === correctPassword) {
				         var postContent = document.getElementById('postContent' + opinionNO);
						    if (postContent) {
						        postContent.style.display = 'block'; // display를 block으로 변경하여 내용이 보이도록 설정
						    }
				    } else {
				        swal('비밀번호 오류', '비밀번호가 올바르지 않습니다.', 'warning');
				    }
				});
		    tr.append(td1, td2, td3, td4);
		    td5.append(label, input, button, div);
		    tr2.append(td5);
		    $("#tableList").append(tr, tr2);
		});
        }
    });
	}
});

	
// 문의 등록글 비밀번호 체크 확인 할 때 비밀번호 입력 창 보이게 하기
document.addEventListener("DOMContentLoaded", function() {
  // 비밀글 체크박스 요소 가져오기
  const secretCheckbox = document.getElementById("flexSwitchCheckDefault");
  
  // 비밀글 체크박스 상태 변경 이벤트 리스너 등록
  secretCheckbox.addEventListener("change", function() {
    // 비밀글 체크박스가 체크되었는지 확인
    if (this.checked) {
      // 체크되었을 때, 비밀번호 작성 창 보이도록 설정
      document.getElementById("mb-3").style.display = "block";
    } else {
      // 체크되지 않았을 때, 비밀번호 작성 창 숨기도록 설정
      document.getElementById("mb-3").style.display = "none";
    }
  });
});  
