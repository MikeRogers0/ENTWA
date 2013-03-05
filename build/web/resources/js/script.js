$(document).on('change', 'select.typeSecltor', function(e){
    if(e.target.value == "Organisation"){
        $('.org_details').show();
    }else{
        $('.org_details').hide();
    }
}); 