/**
 * 
 */
$(document).ready(function() {
	var error = /*[[${ error }]]*/'T';
	console.log("error : ", error);

})

function submitForm() {
	console.log("submitForm 실행");
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	if (username == "" || username == null) {
		swal('로그인 실패', '아이디를 입력해주세요.', 'warning');
	} else if (password == "" || password == null) {
		swal('로그인 실패', '비밀번호를 입력해주세요.', 'warning');
	} else {

		var form = document.formLogin;
		form.submit();

	}

}


function closeModal() {
	document.getElementById('findIdForm').reset();
	document.getElementById('findPwdForm').reset();
	document.getElementById('resultFindId').innerHTML = ' ';
	document.getElementById('resultFindPwd').innerHTML = ' ';
}

function handleRadioChange() {
	var optionValue = document.querySelector('input[name="type"]:checked').value;
	if (optionValue === 'email') {
		document.getElementById('div-email').style.display = 'inline-flex';
		document.getElementById('div-phone').style.display = 'none';
	} else if (optionValue === 'phone') {
		document.getElementById('div-email').style.display = 'none';
		document.getElementById('div-phone').style.display = 'inline-flex';
	}
}

function handleRadioChange2() {
	var optionValue = document.querySelector('input[name="type2"]:checked').value;
	if (optionValue == 'email') {
		document.getElementById('div-email2').style.display = 'inline-flex';
		document.getElementById('div-phone2').style.display = 'none';
	} else if (optionValue == 'phone') {
		document.getElementById('div-email2').style.display = 'none';
		document.getElementById('div-phone2').style.display = 'inline-flex';
	}
}

var radioButtons = document.querySelectorAll('input[name="type"]');
var radioButtons2 = document.querySelectorAll('input[name="type2"]');

radioButtons.forEach(function(radioButton) {
	radioButton.addEventListener('change', handleRadioChange);
});
radioButtons2.forEach(function(radioButton) {
	radioButton.addEventListener('change', handleRadioChange2);
});


function searchId() {
	var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
	var id;
	var optionValue = document.querySelector('input[name="type"]:checked').value;
	if (optionValue === 'email') {
		if (document.getElementById('inputEmail').value == "" || document.getElementById('inputEmail').value == null ||
			document.getElementById('inputEmailUrl').value == 'none') {
			swal('이메일 입력 오류', "올바른 이메일을 입력하세요.", 'warning');
			return;
		}
		var email = document.getElementById('inputEmail').value + '@' + document.getElementById('inputEmailUrl').value;
		console.log(email);
		$.ajax({
			url: "/userinfo/findIdByEmail",
			data: { email: email },
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			method: "POST",
			success: function(result) {
				if (result === null || result == "") {
					document.getElementById('resultFindId').innerHTML = '존재하지 않는 회원입니다.';
				} else {
					document.getElementById('resultFindId').innerHTML = '아이디는 ' + result + '입니다.';
				}
			}
		})
	} else if (optionValue === 'phone') {
		var phone = document.getElementById('inputPhoneId1').value + document.getElementById('inputPhoneId2').value + document.getElementById('inputPhoneId3').value;
		if (phone.length != 11) {
			swal('전화번호 입력 오류', "올바른 전화번호를 입력하세요.", 'warning');
			return;
		}
		console.log(phone);
		$.ajax({
			url: "/userinfo/findIdByPhone",
			data: { phone: phone },
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			method: "POST",
			success: function(result) {
				if (result === null || result == "") {
					document.getElementById('resultFindId').innerHTML = '존재하지 않는 회원입니다.';
				} else {
					document.getElementById('resultFindId').innerHTML = '아이디는 ' + result + '입니다.';
				}
			}
		})

	}
}
var authCode;
function sendCode() {
	var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
	var id;
	var optionValue = document.querySelector('input[name="type2"]:checked').value;
	if (optionValue === 'email') {
		if (document.getElementById('inputEmail2').value == "" || document.getElementById('inputEmail2').value == null ||
			document.getElementById('inputEmailUrl2').value == 'none') {
			swal('이메일 입력 오류', "올바른 이메일을 입력하세요.", 'warning');
			return;
		}
		var email = document.getElementById('inputEmail2').value + '@' + document.getElementById('inputEmailUrl2').value;
		console.log(email);
		$.ajax({
			url: "/userinfo/findIdByEmail",
			data: { email: email },
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			method: "POST",
			success: function(result) {
				if (result === null || result == "") {
					swal('존재하지 않는 회원입니다', '회원가입을 진행해주세요.', 'warning');
				} else {
					swal('인증번호 발송', '이메일을 확인해주세요.', 'info');
					document.getElementById("div-code").style.display="inline";
					$.ajax({
						url: "/userinfo/isVaildEmail",
						data: { email: email },
						method: "get",
						success: function(code) {
							authCode = code;
							swal('이메일 전송 완료', '인증번호를 입력해주세요.', 'info');
						}
					})
				}
			}
		})
	} else if (optionValue === 'phone') {
		var phone = document.getElementById('inputPhoneId21').value + document.getElementById('inputPhoneId22').value + document.getElementById('inputPhoneId23').value;
		if (phone.length != 11) {
			swal('전화번호 입력 오류', "올바른 전화번호를 입력하세요.", 'warning');
			return;
		}
		console.log(phone);
		$.ajax({
			url: "/userinfo/findIdByPhone",
			data: { phone: phone },
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			method: "POST",
			success: function(result) {
				if (result === null || result == "") {
					swal('존재하지 않는 회원입니다', '회원가입을 진행해주세요.', 'warning');
				} else {
					swal('인증번호 발송', '문자를 확인해주세요.', 'info');
					document.getElementById("div-code").style.display="inline";
				}
			}
		})

	}
}
//이메일 인증
function checkCode() {
	var inputCode = document.getElementById('inputCode').value;
	if (authCode == inputCode) {
		swal('이메일 인증 완료', '인증이 완료되었습니다.', 'success');	
	} else {
		swal('이메일 인증 실패', '코드를 다시 확인해주세요.', 'warning');
	}
};

function kakaoLogin() {
	var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
	window.location.href = 'https://kauth.kakao.com/oauth/authorize?client_id=29ba16db25cdb9eb61b39a437825310b&redirect_uri=http://localhost:8080/kakao/callback&response_type=code';
}