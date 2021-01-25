$('document').ready(function(){
    $('table #deleteButton').on('click',function(event){
        event.preventDefault();
        var href=$(this).attr('href');
        $('#deleteModal #userDeleteForm').attr('action', href);
        $('#deleteModal').modal();
    });
});

type = ['primary', 'info', 'success', 'warning', 'danger'];
demo = {
    initPickColor: function() {
        $('.pick-class-label').click(function() {
            var new_class = $(this).attr('new-class');
            var old_class = $('#display-buttons').attr('data-class');
            var display_div = $('#display-buttons');
            if (display_div.length) {
                var display_buttons = display_div.find('.btn');
                display_buttons.removeClass(old_class);
                display_buttons.addClass(new_class);
                display_div.attr('data-class', new_class);
            }
        });
    },

    showNotification: function(from, align) {
        color = Math.floor((Math.random() * 4) + 1);
        $.notify({
            icon: "nc-icon nc-app",
            message: "Welcome to <b>Light Bootstrap Dashboard</b> - a beautiful freebie for every web developer."

        }, {
            type: type[color],
            timer: 8000,
            placement: {
                from: from,
                align: align
            }
        });
    }
}