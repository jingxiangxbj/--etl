var validator;
var $userProfileForm = $("#update-profile-form");
$(function () {

    validateRule();
    $("#update-profile .btn-save").click(function () {
        var validator = $userProfileForm.validate();
        var flag = validator.form();
        if (flag) {
            $.post(ctx + "user/updateUserProfile", $userProfileForm.serialize(), function (r) {
                if (r.code === 0) {
                    $MB.n_success(r.msg);
                    refreshUserProfile();
                } else $MB.n_danger(r.msg);
            });
        }
    });
});


function refreshUserProfile() {
    $.post(ctx + "user/profile", function (r) {
        $main_content.html("").append(r);
    });
}

function editUserProfile() {
    $.post(ctx + "user/getUserProfile", {"userId": userId}, function (r) {
        if (r.code === 0) {
            var $form = $('#update-profile');
            $form.modal();
            var user = r.msg;
            $form.find("input[name='username']").val(user.username).attr("readonly", true);
            $form.find("input[name='oldusername']").val(user.username);
            $form.find("input[name='userId']").val(user.userId);
            $form.find("input[name='email']").val(user.email);
            $form.find("input[name='mobile']").val(user.mobile);
            $("input:radio[value='" + user.ssex + "']").attr("checked", true);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $userProfileForm.validate({
        rules: {
            email: {
                email: true
            },
            mobile: {
                checkPhone: true
            },
            ssex: {
                required: true
            },
            description: {
                maxlength: 100
            }
        },
        errorPlacement: function (error, element) {
            if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
            email: icon + "邮箱格式不正确",
            ssex: icon + "请选择性别",
        }
    });
}

