$.fn.dataTableExt.sErrMode = 'throw';

$(document).ready(function() {
    $('#sourceTable').dataTable( {
        "paging":   false,
        "info":     false
    } );
} );