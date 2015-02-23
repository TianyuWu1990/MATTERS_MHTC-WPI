$.fn.dataTableExt.sErrMode = 'throw';

$(document).ready(function() {
    $('#sourceTable').DataTable( {
        "paging":   false,
        "info":     false
    } );
} );