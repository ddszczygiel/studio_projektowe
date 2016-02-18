$(document).ready(function(){
    var changeContainer = function(){
        var containerId = location.hash;
        $('#content .container').hide();
        $(containerId).show();
    }

    $(window).on('hashchange', changeContainer);
    changeContainer();

    $('#uploadConfigError').hide();
    $('#uploadConfigSuccess').hide();

    $('#uploadConfigForm').fileupload({
        url: '/api/load/conffile',
        dataType: 'json',
        done: function (e, data) {
            var response = data.response().result;
            if(response.errorMessage != null){
                $('#uploadConfigError').show();
                $('#uploadConfigSuccess').hide();
                $('#uploadConfigError').html(response.errorMessage);
            }else{
                $('#uploadConfigError').hide();
                $('#uploadConfigSuccess').show();
            }

        }
    });


    $('#uploadDiagramError').hide();
    $('#uploadDiagramSuccess').hide();

    $('#uploadDiagramForm').fileupload({
        url: '/api/load/activitydiagram',
        dataType: 'json',
        done: function (e, data) {
            var response = data.response().result;
            if(response.errorMessage != null){
                $('#uploadDiagramError').show();
                $('#uploadDiagramSuccess').hide();
                $('#uploadDiagramError').html(response.errorMessage);
            }else{
                $('#uploadConfigError').hide();
                $('#uploadDiagramSuccess').show();
            }

        }
    });


    $('#uploadLTLModelError').hide();
    $('#uploadLTLModelSuccess').hide();

    $('#uploadLTLModelForm').fileupload({
        url: '/api/load/ltlmodels',
        dataType: 'json',
        done: function (e, data) {
            var response = data.response().result;
            if(response.errorMessage != null){
                $('#uploadLTLModelError').show();
                $('#uploadLTLModelSuccess').hide();
                $('#uploadLTLModelError').html(response.errorMessage);
            }else{
                $('#uploadLTLModelError').hide();
                $('#uploadLTLModelSuccess').show();
                $('#ltlModel').html(response.payload.replace(/\\r\\n/g, "<br/>"));
            }

        }
    });


    $('#ltlError').hide();

    $('#getLtlPattern').click(function() {
        $.ajax({ type: "GET",
                 url : '/api/data/ltlpattern',
                 dataType: 'json',
                 success: function(result) {
                    var payload = result.payload;
                    if (payload != null) {
                        $('#ltlError').hide();
                        $('#ltlPattern').html(payload.replace(/\\r\\n/g, "<br/>"));
                    } else {
                        $('#ltlError').html(result.errorMessage);
                        $('#ltlError').show();
                    }
                 }
        })
    });


    $('#getLtlSpec').click(function() {
        $.ajax({ type: "GET",
                 url : '/api/data/ltlspec',
                 dataType: 'json',
                 success: function(result) {
                    var payload = result.payload;
                    var buffer = "";
                    if (payload != null) {
                        $('#ltlError').hide();
                        for (var i in payload){
                            buffer += payload[i] + '<br/>';
                        }
                        $('#ltlSpec').html(buffer);
                    } else {
                        $('#ltlError').html(result.errorMessage);
                        $('#ltlError').show();
                    }
                 }
        })
    });

})

