jsf.ajax.addOnEvent(function(data) {

	//$('#content').enhanceWithin();

	var ajaxstatus = data.status;
    switch (ajaxstatus) {
        case 'begin':
            $('.loading').addClass('active');
            break;
        case 'complete':
            $('.loading').removeClass('active');
            break;
        case 'success':
            break;
    }
});

$('#messages').click(function() {
	$('#messages').fadeOut();
});

