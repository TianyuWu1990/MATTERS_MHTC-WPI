/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {

    var data = [
        {
            label: 'node1', id: 1,
            children: [
                {label: 'child1', id: 2},
                {label: 'child2', id: 3}
            ]
        },
        {
            label: 'node2', id: 4,
            children: [
                {label: 'child3', id: 5, children: [{label: 'test1', id: 6}, {label: 'test2', id: 7}]}, {label: 'child4', id: 8}
            ]
        }];

    /**
     * Populate treeview from some_url with JSON data.
     
     $.getJSON(
     '/some_url/',
     function(data) {
     $('#tview').tree({
     data: data
     });
     }
     );
     */

    $('#tview-div').tree({
        data: data
    });

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
            $('#info_msg').html('Selected category: ' + node.name);
            $('#info_msg').show('slow').delay(5000).fadeOut('slow');
        }
    });

    $('.msg').bind('click', function() {
        $(this).html('');
        $(this).fadeOut('slow');
    });

    function getMetrics(categoryId) {
        $.ajax({
            type: "POST",
            url: '#',
            dataType: "html",
            success: function(data) {
                //to do
                $('#metrics_div').html(
                        '<table class="tbl"> <tr><th>Id</th><th>Metric Name</th><th>Visible</th><th>Calculated</th><th></th></tr> ' +
                        '<tr> <td>1</td><td>Total Taxes</td><td>True </td><td> False</td> <td><a href="#">Edit</a></td> </tr>' +
                        '<tr> <td>2</td><td>Sales and Gross Receipts Taxes</td><td>True </td><td> False</td> <td><a href="#">Edit</a></td></tr>' +
                        '<tr> <td>3</td><td>Income Taxes</td><td>True </td><td> False</td> <td><a href="#">Edit</a></td></tr>' +
                        '</table>'
                        );
            }
        });
    }

});
