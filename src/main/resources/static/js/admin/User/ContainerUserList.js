$(document).ready(function() {
    
    //토큰선언
    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');

    /* 유저 검색 기능 */
    $(".btn-primary-search").click(function() {
        // 입력된 데이터 수집
        var u_name = $('#searchU_name').val();
        var id = $('#searchId').val();
        var age = $('#searchDate_birth').val();
        var phone = $('#searchPhone').val();
        var residence = $('#searchResidence').val();
        var gender = $('.dropdown-toggle').attr("value");

        // 값이 없는 조건에 대해 공백으로 처리
        if (u_name === null) u_name = "";
        if (id === null) id = "";
        if (age === null) age = "";
        if (phone === null) phone = "";
        if (residence === null) residence = "";

        var url = "/adminUserList/1"; // 기본 URL을 설정해주세요.

        // 데이터를 서버로 전송
        $.ajax({
            url: url,
            type: "GET",
            data: {
                u_name: u_name,
                id: id,
                age: age,
                phone: phone,
                residence: residence,
                gender: gender
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token); // 요청 헤더에 토큰 설정 (헤더와 토큰 변수는 이전에 정의되어야 함)
            },
            success: function(data) {
                console.log('data값 확인 ' + data); // 받은 데이터 출력

                $('#ContainerUserList').html($(data).find('#ContainerUserList').html()); //
            },
            error: function(xhr, status, error) {
                // 에러 처리 코드
            }
        });
    });

    /* 체크박스 전체 선택 기능 */
    $('#selectAllCheckbox').change(function() {
        $('tbody input[type="checkbox"]').prop('checked', $(this).prop('checked'));
        updateDeleteButton(); // 삭제 버튼 상태 업데이트
    });

    $('input[name="userCheckbox"]').on('change', function() {
        if ($(this).prop('checked')) {
            const checkUserId = $(this).data('user-id');
            console.log("Checked user ID:", checkUserId);
        }
        updateDeleteButton(); // 삭제 버튼 상태 업데이트
    });

    //************************************아이디 생성 ***************************//
    // 아이디 중복확인 checkId
    $('#checkIdButton').click(function() {
        var userId = $('#inputId').val();
        if (userId.trim() === '') {
            alert('아이디를 입력해주세요.');
            return;
        }

        $.ajax({
            url: '/checkId',
            type: 'POST',
            data: { id: userId },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token); // CSRF 토큰을 헤더에 추가
            },
            success: function(response) {
                if (response === "success") {
                    window.location.href = '/adminUserList/1';
                } else {

                }
            },
            error: function(error) {
                alert('서버 오류로 중복확인에 실패했습니다.');
            }
        });
    });

    //아이디 생성 saveButton
    $('#saveButton').click(function() {
        // 확인 메시지 띄우기

        const isConfirmed = confirm("저장하시겠습니까?");
        if (isConfirmed) {
            var formData = {
                id: $('#inputId').val(),
                pwd: $('#inputPwd').val(),
                u_name: $('#inputName').val(),
                date_birth: $('#inputDateOfBirth').val(),
                gender: $('#inputGender').val(),
                email: $('#inputEmail').val(),
                nickname: $('#inputNickname').val(),
                residence: $('#inputResidence').val(),
                phone: $('#inputPhoneNumber').val(),
                point: $('#inputPoint').val()
            };

            console.log(formData);

                $.ajax({
                    url: '/insertUser',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(formData),
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function(response) {
                        if (response === "success") {
                            alert('저장되었습니다');

                            window.location.href = '/adminUserList/1';

                        } else {
                            alert('저장에 실패했습니다');
                        }
                    },
                error: function(error) {
                    console.error(error); // 오류 정보 출력
                    alert('서버 오류로 저장에 실패했습니다.');
                }
            });
        }
    });

    // 수정 버튼 클릭 시
    $(document).on("click", ".btn-primary-update", function() {
        // 현재 클릭한 수정 버튼에 대한 사용자 번호 가져오기
        console.log('수정버튼클릭됨');

        const userId = $(this).data('user-id');

        console.log('userId' + userId);

        // 상세 정보 모달 숨기기
        $("#model-" + userId).modal("hide");

        // 해당 사용자의 수정 모달 보이기
        $("#updateModal-" + userId).modal("show");

        // 상세 정보 모달 레이어 제거
        $("body").removeClass("modal-open");
        $(".modal-backdrop").remove();
    });

    // 수정 내용 저장
    $(document).on("click", ".btn-primary-save", function() {
        const userId = $(this).data('user-id');
        const isConfirmed2 = confirm("수정사항을 저장하시겠습니까?");

        if (isConfirmed2) {
            var formData2 = {
                pwd: $('#updatePwd-' + userId).val(),
                u_name: $('#updateName-' + userId).val(),
                date_birth: $('#updateDateOfBirth-' + userId).val(),
                gender: $('#updateGender-' + userId).val(),
                email: $('#updateEmail-' + userId).val(),
                nickname: $('#updateNickname-' + userId).val(),
                residence: $('#updateResidence-' + userId).val(),
                phone: $('#updatePhone-' + userId).val(),
                point: $('#updatePoint-' + userId).val()
            };
            console.log('formData2의 값은', formData2);


            $.ajax({
                url: '/updateUser',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(formData2),
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function(response) {
                    if (response === "success") {
                        alert('저장되었습니다');
                        window.location.href = '/adminUserList/1';
                    } else {
                        alert('저장에 실패했습니다');
                        window.location.href = '/adminUserList/1';

                    }
                },
                error: function(error) {
                    console.error(error);
                    alert('서버 오류로 저장에 실패했습니다.');
                    window.location.href = '/adminUserList/1';

                }
            });
        }
    });

        /* 회원정보 삭제기능 */
    $(".btn-primary-delete").on('click', function() {
        console.log('회원정보 삭제기능 동작');
        const isConfirmed = confirm("정말로 삭제하겠습니까?");
        if (isConfirmed) {
            const userId = $(this).data('user-id');
            console.log("userID야?", userId);
            deleteUser(userId);
        }
    });

    //선택 삭제 버튼
    // 선택된 회원 삭제
    $(".btn-primary-delete-user").on('click', function() {
        const checkedUserIds = []; // 체크된 user ID를 담을 배열
        $('input[name="userCheckbox"]:checked').each(function () {
            const checkedUserId = $(this).data('user-id');
            checkedUserIds.push(checkedUserId);
        });
        console.log("체크된 UserNO 확인 ", checkedUserIds);

        const isConfirmed = confirm("정말로 삭제하겠습니까?");
        if (isConfirmed) {
            deleteSelectedUser(checkedUserIds);
        }
    });


    // 삭제 버튼 상태 업데이트
    function updateDeleteButton() {
        const checkedUserIds = getCheckedUserIds();
        const deleteButton = $(".btn-primary-delete-user");

        if (checkedUserIds.length > 0) {
            deleteButton.prop('disabled', false); // 선택된 회원이 있을 때 활성화
        } else {
            deleteButton.prop('disabled', true); // 선택된 회원이 없을 때 비활성화
        }
    }
});

function deleteSelectedUser(checkedUserIds){
    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');

    $.ajax({
        url: `/deleteSelectedUser`,
        type: 'DELETE',
        data: JSON.stringify(checkedUserIds),
        contentType: 'application/json',
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token); // CSRF 토큰을 헤더에 추가
        },
        success: function(response) {
            if (response == 'success') {
                location.reload(); // 테이블 새로 고침
            } else {
                alert('회원 삭제에 실패했습니다.');
            }
        },
        error: function(error) {
            alert('서버 오류로 회원 삭제에 실패했습니다.');
        }
    });
}



function deleteUser(userId) {

    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');

    $.ajax({
        url: `/deleteUser/${userId}`,
        type: 'DELETE',
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token); // CSRF 토큰을 헤더에 추가
        },
        success: function(response) {
            if (response == 'success') {
                location.reload(); // 테이블 새로 고침
            } else {
                alert('회원 삭제에 실패했습니다.');
            }
        },
        error: function(error) {
            alert('서버 오류로 회원 삭제에 실패했습니다.');
        }
    });

}

// 선택된 User Id 가져오기
function getCheckedUserIds() {
    return $('input[name="userCheckbox"]:checked').map(function() {
        return $(this).data('user-id');
    }).get();
}