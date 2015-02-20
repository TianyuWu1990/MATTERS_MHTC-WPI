$.fn.dataTableExt.sErrMode = 'throw';

$(document).ready(function() {
    $('#sourceTable').dataTable( {
        "paging":   false,
        "ordering": false,
        "info":     false
    } );
} );