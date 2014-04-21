

$(document).ready(function() {



    $.getJSON(
            'categories/',
            function(data) {
                $('#tview-div').tree({
                    data: data
                });
            }
    );

    /*$('#tview-div').tree({
        data: data
    });*/

    $('#tview-div').bind(
            'tree.click',
            function(event) {
                // The clicked node is 'event.node'
                var node = event.node;
                $('#selectedItem').html('Selected Category: <h3 class="in">' + node.name + '</h3> | Id:' + node.id);
                getMetrics(node.id);
                //alert(node.name+'\nId:'+node.id);
            }
    );

    $('.ui-button').button();

    $('#addNewCategBtn').click(function() {
        var node = $('#tview-div').tree('getSelectedNode');
        if (node === false)
        {
            $('#selectedCateg').html('root');
        }
        else
        {
            $('#selectedCateg').html(node.name);
        }
        $("#addCategPopup").dialog({
            width: 400,
            height: 270,
            show: "slide",
            hide: "slide",
            title: 'Add New Category',
            modal: true,
            buttons: {"Save": function() {
                    storeCategory(node.id, $("#newCategName").val(), $("#newSourceName").val());
                    $(this).dialog("close");
                }, "Cancel": function() {
                    $(this).dialog("close");
                }
            }
        });
    });

    $('#editCategBtn').click(function() {
        var node = $('#tview-div').tree('getSelectedNode');
        if (node === false)
        {
            $('#err_msg').html('Select a category please.');
            $('#err_msg').show('slow').delay(5000).fadeOut('slow');
        }
        else
        {
            $('#e_selectedCateg').html(node.name);
            $('#editCategName').val(node.name);

            $("#editCategPopup").dialog({
                width: 400,
                height: 280,
                show: "slide",
                hide: "slide",
                title: 'Edit Category',
                modal: true,
                buttons: {"Save": function() {
                        $(this).dialog("close");
                    }, "Cancel": function() {
                        $(this).dialog("close");
                    }
                }
            });
        }
    });

    $('#addNewMetricBtn').click(function() {
        var node = $('#tview-div').tree('getSelectedNode');

        if (node === false)
        {
            $('#err_msg').html('Select a category please.');
            $('#err_msg').show('slow').delay(5000).fadeOut('slow');
        }
        else
        {
            $('#m_selectedCateg').html(node.name);

            $("#addMetricPopup").dialog({
                width: 400,
                height: 280,
                show: "slide",
                hide: "slide",
                title: 'Add New Metric',
                modal: true,
                buttons: {"Save": function() {
                        storeMetric(node.id, $("#newMetricName").val(), $("#newMetricCalc").is(':checked'), $("#dataTypes").val());
                        $(this).dialog("close");
                    }, "Cancel": function() {
                        $(this).dialog("close");
                    }
                }
            });
        }
    });


    $('.msg').bind('click', function() {
        $(this).html('');
        $(this).fadeOut('slow');
    });


    



});


function storeCategory(parentId, name, source) {
    $.ajax({
        type: "POST",
        url: 'categories/new',
        data: { parentid: parentId, name: name, source: source},
        dataType: "html",
        success: function(data) {
            refreshTree();
        }
        
    });
}

function storeMetric(categoryId, name, isCalculated, type) {
    $.ajax({
        type: "POST",
        url: 'categories/' + categoryId + '/metrics/new',
        data: { name: name, iscalculated: isCalculated, type: type},
        dataType: "html",
        success: function(data) {
            getMetrics(categoryId);
        }
        
    });
}

function refreshTree() {
    $.getJSON(
            'categories/',
            function(data) {
                $('#tview-div').tree('loadData', data);
            }
    );
}


function getMetrics(categoryId) {
    $.ajax({
        type: "GET",
        url: 'categories/' + categoryId + '/metrics/table',
        dataType: "html",
        success: function(data) {
            //to do
            //$('#metrics_div').html(
            //        '<table class="tbl"> <tr><th>Id</th><th>Metric Name</th><th>Visible</th><th>Calculated</th><th></th></tr> ' +
            //        '<tr> <td>1</td><td>Total Taxes</td><td>True </td><td> False</td> <td><a href="#" class="editCategCLS">Edit</a></td> </tr>' +
            //        '<tr> <td>2</td><td>Sales and Gross Receipts Taxes</td><td>True </td><td> False</td> <td><a href="#" class="editCategCLS">Edit</a></td></tr>' +
            //        '<tr> <td>3</td><td>Income Taxes</td><td>True </td><td> False</td> <td><a href="#" class="editCategCLS">Edit</a></td></tr>' +
            //        '</table>'
            //        );
            $('#metrics_div').html(data);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $('#metrics_div').html(
                    '<table class="tbl"> <tr><th>Id</th><th>Metric Name</th><th>Visible</th><th>Calculated</th><th></th></tr> ' +
                    '<tr> <td>1</td><td>Total Taxes</td><td>True </td><td>False</td> <td><a href="#" class="editCategCLS">Edit</a></td> </tr>' +
                    '<tr> <td>2</td><td>Sales and Gross Receipts Taxes</td><td>True </td><td> False</td> <td><a href="#" class="editCategCLS">Edit</a></td></tr>' +
                    '<tr> <td>3</td><td>Income Taxes</td><td>True </td><td> False</td> <td><a href="#" class="editCategCLS">Edit</a></td></tr>' +
                    '</table>'
                    );
            $('.editCategCLS').click(function() {
                alert('yes');
            });
        }
    });
    
}
