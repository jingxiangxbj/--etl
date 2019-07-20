$(function () {
    var $userTableForm = $(".user-table-form");
    var settings = {
        url: ctx + "etlInfo/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                name: $userTableForm.find("input[name='name']").val().trim(),
                gender: $userTableForm.find("select[name='gender']").val(),
            };
        },
        columns: [
            {
                field: 'id',
                visible: false
            }, {
                field: 'callTime',
                title: '时间'
            },
            {
                field: 'status',
                title: '状态'
            }, {
                field: 'stage',
                title: '阶段'
            }, {
                field: 'detail',
                title: '详情'
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

