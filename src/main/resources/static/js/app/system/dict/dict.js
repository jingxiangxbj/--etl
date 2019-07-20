$(function() {
    var $dictTableForm = $(".dict-table-form");
    var settings = {
        url: ctx + "email/list",
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
            };
        },
        columns: [{
                checkbox: true
            },
            {
                title: '编号',
                field: 'id',
                visible: false
            },
            {
                title: '收件人',
                field: 'user'
            },
            {
                title: '收件人邮箱',
                field: 'toemail'
            },
            {
                title: '发件人邮箱',
                field: 'fromemail'
            },
            {
                title: '邮件名称',
                field: 'title'
            },
            {
                title: '创建时间',
                field: 'createtime'
            }
        ]
    };

    $MB.initTable('dictTable', settings);
});

function search() {
    $MB.refreshTable('dictTable');
}

function refresh() {
    $(".dict-table-form")[0].reset();
    search();
}

function deleteDicts() {
    var selected = $("#dictTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的邮件！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].dictId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的邮件？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'email/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

