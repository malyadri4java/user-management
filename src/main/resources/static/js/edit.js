$('document').ready(function(){
    $('table #deleteButton').on('click',function(event){
        event.preventDefault();
        var href=$(this).attr('href');
        $('#deleteModal #userDeleteForm').attr('action', href);
        $('#deleteModal').modal();
    });
});