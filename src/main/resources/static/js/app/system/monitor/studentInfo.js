$(function () {
    var $userTableForm = $(".user-table-form");
    var settings = {
        url: ctx + "studentInfo/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                name: $userTableForm.find("input[name='name']").val().trim(),
                gender: $userTableForm.find("select[name='gender']").val()
            };
        },
        columns: [

         {
            field: 'id',
            visible: false
        }, {
            field: 'name',
            title: '姓名'
        },
            {
            field: 'age',
            title: '年龄'
        }, {
            field: 'birth',
            title: '出生日期'
        }, {
            field: 'gender',
            title: '性别'
        }, {
            field: 'mobile',
            title: '电话'
        }, {
            field: 'address',
            title: '地址'
        },{
                field: 'idCard',
                title: '身份证号'
            },{
                field: 'hobby',
                title: '兴趣爱好'
            }, {
                field: 'createtime',
                title: '创建时间'
            }
        ]
    };

    $MB.initTable('userTable', settings);
});

function search() {
    $MB.refreshTable('userTable');
}

function refresh() {
    $(".user-table-form")[0].reset();
    search();
}
function exportUserExcel() {
    $.post(ctx + "studentInfo/excel", $(".user-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportUserCsv() {
    $.post(ctx + "studentInfo/csv", $(".user-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}
