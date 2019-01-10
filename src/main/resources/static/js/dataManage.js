$('#editDataTable').bootstrapTable(
    {
        cache: false,
        method: 'post',
        escape:true,
        sortable: false,
        pageList: [5,10,15,20,30],
        pagination: true,
        pageNumber:1,
        pageSize:10,
        sidePagination: 'server',
        sortOrder:'dataId',

        //sidePagination: 'client',
        //  queryParamsType: queryParams,
        // queryParams :$("#userId").val(),
        queryParams: function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                offset: params.limit,   //页面大小
                limit: params.offset,  //偏移量
                // pageSize:10,
                // pageNumber:1,
                // length: 6,
                // order:'desc'
            };
            // console.log(temp);
            return temp;

        },
        //queryParamsType: 'limit',
        striped : true,
        // pageNumber:1,
        // pageSize:15,
        //pageSize:9999,
        //limit:20,
        // idField:"userId",
        search: true,
        // undefinedText:"",
        showRefresh: true,
        url: '/getDataForEdit',
        columns: [
            {   field:'dataId',
                title:'dataId',
                align:'center',
                showSelectTitle:true,

                // visible:false,
                /*               formatter:function(value,row,index){
                                  //通过formatter可以自定义列显示的内容
                                  //value：当前field的值，即id
                                  //row：当前行的数据
                                  value='<a href="/testPlan/getCaseIndex?id='+row.id+'"'+">"+value+"</a>";
                                  return value
                              } */
            }, {
                field:'a1',
                title:'a1',
                align:'center',
                // formatter:function (value, row, index) {
                //     value='<a href="/article/edit?id='+row.articleId+'"'+">"+value+"</a>";
                //     return value;
                //     // return [
                //     //     '<a href="/article/edit?articleId='+'+row.articleId+'+"></a>'
                //     // ].join("")
                // },


                visible:true,
            }, {
                field:'a2',
                title:'a2',
                visible:true,
                align:'center',
            }, {
                field:'a3',
                title:'a3',
                visible:true,
                align:'center',
            },
            {
                field:'a4',
                title:'a4',
                visible:true,
                align:'center',
            },

            {
                field:'a5',
                title:'a5',
                visible:true,
                align:'center',
            },

            {
                field:'a6',
                title:'a6',
                visible:true,
                align:'center',
            },

            {
                field:'a7',
                title:'a7',
                visible:true,
                align:'center',
            },

            {
                field:'a8',
                title:'a8',
                visible:true,
                align:'center',
            },

            {
                field:'a9',
                title:'a9',
                visible:true,
                align:'center',
            },

            {
                field:'a10',
                title:'a10',
                visible:true,
                align:'center',
            },

            {
                field:'a11',
                title:'a11',
                visible:true,
                align:'center',
            },

            {
                field:'a12',
                title:'a12',
                visible:true,
                align:'center',
            },

            {
                field:'quality',
                title:'quality',
                visible:true,
                align:'center',
            },

            {
                field: 'operation',
                title: '操作',
                align:'center',
                formatter : function(cell, row, index) {
                    // btnEdit = '<a class="btn-success btn-sm"  onclick="viewArticle('+row.articleId+')">查看</a>';
                    btnView = '<a class="btn-primary btn-sm"  onclick="editData('+row.dataId+')">编辑</a>';
                    btnDel = '<button type="button" class="btn btn-danger btn-xs" onclick="deleteData('+row.dataId+')">删除</button>';
                    cell =  btnView +" "+ btnDel;
                    return cell;
                },
            }],
    });






function editData(dataId) {



    var data = {};
    data.dataId = dataId;
    $.ajax({
        type: "POST",
        url: "/getDataInfo",
        async:false,
        data: data,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            $("#dataId").val(data.dataId);
            $("#a1").val(data.a1);
            $("#a2").val(data.a2);
            $("#a3").val(data.a3);
            $("#a4").val(data.a4);
            $("#a5").val(data.a5);
            $("#a6").val(data.a6);
            $("#a7").val(data.a7);
            $("#a8").val(data.a8);
            $("#a9").val(data.a9);
            $("#a10").val(data.a10);
            $("#a11").val(data.a11);
            $("#a12").val(data.a12);
            $("#quality").val(data.quality);

        }
    });
    $('#con-close-modal').modal('show');

}
$("#updateDataBtn").click(function () {



    var data = {};
    data.dataId = $("#dataId").val();
    data.a1 = $("#a1").val();
    data.a2 = $("#a2").val();
    data.a3 = $("#a3").val();
    data.a4 = $("#a4").val();
    data.a5 = $("#a5").val();
    data.a6 = $("#a6").val();
    data.a7 = $("#a7").val();
    data.a8 = $("#a8").val();
    data.a9 = $("#a9").val();
    data.a10 = $("#a10").val();
    data.a11 = $("#a11").val();
    data.a12 = $("#a12").val();
    data.quality = $("#quality").val();



    $.ajax({
        type: "POST",
        url: "/update",
        async:false,
        data: data,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {

            if (data.status == "failed") {
                swal({title: '修改失败！',
                    type:"error",
                    timer:1000,
                    showConfirmButton:false});

            }
            // else if (data.status == "illegalArgument") {
            //     swal({
            //         title: '请检查数据类型！',
            //         type: "error",
            //         timer: 1000,
            //         showConfirmButton: false
            //     });
            // }
            else{
                $("#editDataTable").bootstrapTable('refresh',data);
                $("#title").html("");
                $("#content").html("");
                swal({title: '修改成功！',
                    type:"success",
                    timer:1000,
                    showConfirmButton:false});



            }
        }
    });
})



function deleteData(dataId) {
    var data = {};
    data.dataId = dataId;
    swal({
        title: '确定删除吗？',
        text: '你将无法恢复它！',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '确定删除！',
    }).then(function(isConfirm){
        if (isConfirm.dismiss != 'cancel') {
            $.ajax({
                type: "POST",
                url: "/delete",
                // async:false,
                data: data,

                //type、contentType必填,指明传参方式
                // dataType: "json",

                contentType: "application/x-www-form-urlencoded",
                // contentType: "application/json;charset=utf-8",
                success: function (data) {
                    if (data.status == "failed") {
                        swal({title: '删除失败！',
                            type:"error",
                            timer:1000,
                            showConfirmButton:false});

                    } else {
                        $("#editDataTable").bootstrapTable('refresh',data);
                        swal(
                            '删除！',
                            '该条数据已经被删除。',
                            'success'
                        );
                        // swal({title: '删除成功！',
                        //     type:"success",
                        //     timer:1000,
                        //     showConfirmButton:false});


                    }
                }
            });

        }


    })




}

