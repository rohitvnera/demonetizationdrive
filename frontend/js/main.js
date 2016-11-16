
jQuery(function($) {
    var paintedMapid = {};

    var currentMarkerId = {
                        'markerEvent' : false,
                        'updateDetailsForm' : ""
                    };
    var markersMap = {};
    var avgWaitTimeValueMap = {
        15 : 'Immediate',
        30: '30 mins',
        60 : '1 hour',
        120: '2 hours',
        240 : '4 hours',
        241 : 'More than 4 hours'
    };
    var map;
    var infoWindow;
   
    var markers = [];
    var pyrmont = new google.maps.LatLng(18.5204303, 73.8567437);
    var marker;
    var geocoder = new google.maps.Geocoder();


    $(function(){
        firstLoad();
        $('#okBtn').on('click', onOk);
        $('#updateBankButton').on('click', onUpdateModal);

         $('#address').keypress(function(e){
        if(e.which == 13) {
          //dosomething
          e.preventDefault();
          showMap();
          $("#address").blur(); 
        }});
         $("#cashStatus").on('change', updatePopUp);
        $('input[type=radio][name=type]').on('change', function () {
            clearOverlays();
            showMap(map.getCenter());
        });

        var $portfolio_selectors = $('.portfolio-filter >li>a');
        var $portfolio = $('.portfolio-items');
        $portfolio.isotope({
            itemSelector : '.portfolio-item',
            layoutMode : 'fitRows'
        });

        $portfolio_selectors.on('click', function(){
            $portfolio_selectors.removeClass('active');
            $(this).addClass('active');
            var selector = $(this).attr('data-filter');
            $portfolio.isotope({ filter: selector });
            return false;
        });
    });

    // accordian
    $('.accordion-toggle').on('click', function(){
        $(this).closest('.panel-group').children().each(function(){
        $(this).find('>.panel-heading').removeClass('active');
         });

        $(this).closest('.panel-heading').toggleClass('active');
    });

    //Initiat WOW JS
    new WOW().init();


    //goto top
    $('.gototop').click(function(event) {
        event.preventDefault();
        $('html, body').animate({
            scrollTop: $("body").offset().top
        }, 500);
    });

    //Pretty Photo
    $("a[rel^='prettyPhoto']").prettyPhoto({
        social_tools: false
    });

    function changeDetails(){
    $('#updateDetailsContainer').addClass('show').removeClass('hidden');
    $('#cashStatusDiv').addClass('hidden').removeClass('show');
    $('#lastUpdatedStatus').addClass('hidden').removeClass('show');
}

    // var waypoints = [];
    function firstLoad() {
        map = new google.maps.Map(document.getElementById('map'), {
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            center: pyrmont,
            zoom: 15,
            mapTypeControl: false
        });
        //infoWindow = new google.maps.InfoWindow({map: map});
        initMap();

        // Try HTML5 geolocation.
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };

                //infoWindow.setPosition(pos);
                map.setCenter(pos);
                showMap(pos);
            }, function() {
                //handleLocationError(true, //infoWindow, map.getCenter());
                showMap();
            });
        } else {
            // Browser doesn't support Geolocation
            //handleLocationError(false, //infoWindow, map.getCenter());
            showMap();
        }
       }

     function onUpdateModal(){
        var cashStatus = $("#cashStatus").val(Number(currentMarkerId['cashAvailable']));
        var waitTime =  $("#avgWaitTime").val(Number(currentMarkerId['avgWaitTime'] ? currentMarkerId['avgWaitTime'] : 15));
        var nextAvblTime = currentMarkerId['nextAvailabilty'] ? new Date(currentMarkerId['nextAvailabilty']).toISOString() : new Date().toISOString();
        $("#nxtAvblDateTime").val(nextAvblTime.substr(0, maxDate.indexOf('.')));

        if(Number(currentMarkerId['cashAvailable']) != 1){
            $('#avgWaitTimeDiv').hide();
            $("#nxtAvblDateTimeDiv").show();
        }else{
            $("#nxtAvblDateTimeDiv").hide();
            $('#avgWaitTimeDiv').show();
        }
     }

     function updatePopUp(){
        if($("#cashStatus").val() != 1){
            $('#avgWaitTimeDiv').hide();
            $("#nxtAvblDateTimeDiv").show();
        }else{
            $("#nxtAvblDateTimeDiv").hide();
            $('#avgWaitTimeDiv').show();
        }
     }
    function createMarker(location, placeData) {
        var icon = undefined;
        var type = placeData.type;
        if (placeData) {
            var status = "";
            switch (placeData.cashAvailable) {
                case -1:
                    status = "no_info";
                    break;
                case  0:
                    status = "unavble";
                    break;
                case 1:
                    status = "avble";
                    break;
            }
            icon = "images/bank_duniya_img/" + type + "_" + status + ".png";
        }
        var marker = new google.maps.Marker({
            map: map,
            position: location,
            icon: icon,
            visible:true
        });
        
        markersMap[placeData.mapId] = marker;
        markers.push(marker);
        var infoNotAvbl = "Cash availability information not available";
        var cashStatusInfo = (getCashStatus(placeData.cashAvailable));
        var cashAvaibleTimeStatus = "";
        if(placeData.cashAvailable == 1 && placeData.avgWaitTime > 0){
           cashAvaibleTimeStatus ="Average wait "+ placeData.avgWaitTime +" Mins";
        }
        var updateDetailsForm = getUpdateDetailsFormFields();
        var markerContent = "<div><div>"
                                +" <h3><strong>"+placeData.name+"</strong></h3>"
                                +" <h4 class='text-muted'></h4>"
                                +" <h5 class='text-muted'>"+placeData.address+"</h5>"
                                +"<div id='cashStatusDiv'>"                                    
                                        +"<h4 class="+cashStatusInfo.cashStatusCSS+">"+cashStatusInfo.cashStatus+"</h4>"
                                        +"<h4><strong>"+cashAvaibleTimeStatus+"</strong></h4>"                                    
                                        +"<a href='#' id='updateBankButton' class='btn btn-info'>Change Status</a>"
                                +"</div>"
                                +"<h5 class='text-muted'>Last Updated on "+new Date(placeData.whenModified).toLocaleString()+"</h5></div>"
                                +updateDetailsForm                            
                            +"</div>";

       /* var  markerContent = "<b>Name: </b>"+placeData.name+"<br><b>Cash Status: </b>"+(getCashStatus(placeData.cashAvailable))+"<br>";
        if(placeData.cashAvailable != 1){
            markerContent += "<b>Next Availability: </b>"+(placeData.nextAvailabilty ? new Date(placeData.nextAvailabilty) : infoNotAvbl)+"<br>";
        }else{
            markerContent += "<b>Average Waiting Time: </b>"+(placeData.avgWaitTime!= -1 ? avgWaitTimeValueMap[placeData.avgWaitTime] : avgWaitTimeValueMap[29]) +"<br>";
        }
        markerContent += "<b>Address: </b>"+placeData.address+"<br><b> Status:</b>"+(placeData.bankOpenStatus ? "Open Now" : "Closed Now")+"<br>";
        
        markerContent += "<a href='#' id='updateBankButton' class='btn btn-danger btn-xs' data-toggle='modal' data-target='#myModal'><em class='fa fa-trash'>Update Bank Details</a>";
        */
        if(!infoWindow){
            infoWindow = new google.maps.InfoWindow({map: map});
            infoWindow.close();
        }
        var me = this;
        google.maps.event.addListener(marker, 'click', function() {
            currentMarkerId['markerEvent'] = true;
            infoWindow.setContent(markerContent);
            infoWindow.open(map, this);
            currentMarkerId['mapId'] = placeData.mapId;
            currentMarkerId['cashAvailable'] = placeData.cashAvailable;
            currentMarkerId['avgWaitTime'] = placeData.avgWaitTime;
            currentMarkerId['nextAvailabilty'] = placeData.nextAvailabilty;
            onUpdateModal();
            $('#updateBankButton').on('click', changeDetails);
            $('#cashAvailableStatus').on('click', updateAvailableStatus);
            $('#cashNotAvailableStatus').on('click', updateNotAvailableStatus);
            $('#submitCashAvailibity').on('click', submitAvailableStatus);
            $('#cancelInfoWindow').on('click', cancelAvailableStatus);
        });
    }
    
    function getUpdateDetailsFormFields(){
        if(currentMarkerId['updateDetailsForm'] == ""){
            var updateDetailForm = "<div class='hidden' id='updateDetailsContainer'>"
                                        +"<h5>Select cash status</h5>"
                                        +"<div class='btn-group btn-group-lg' role='group' aria-label='...'>"
                                            +"<div class='btn-group' role='group'>"
                                                  +"<a href='#' id='cashAvailableStatus' class='btn btn-success'>Cash Available</a>"
                                            +"</div>"
                                            +"<div class='btn-group' role='group'>"
                                                  +"<a href='#' id='cashNotAvailableStatus' class='btn btn-danger'>Cash Not Available</a>"
                                            +"</div>"      
                                        +"</div>"
                                        +"<div id='avgWaitTimeSubmitDiv' class='hidden'>"
                                            +"<div id='avgWaitTimeDiv' class='form-group show'>"
                                                    +"<label for='avgWaitTimeField'>Average wait time</label>"
                                                    +"<select class='form-control' id='avgWaitTimeField'>"
                                                         +"<option value=15 selected='selected'>Immediate</option>"   
                                                         +"<option value=30>30 mins</option>"
                                                         +"<option value=60>1 hour</option>"
                                                         +"<option value=120>2 hours</option>"
                                                         +"<option value=240>4 hours</option>"
                                                         +"<option value=241>More than 4 hours</option>"
                                                    +"</select>"
                                            +"</div> <div class='btn-toolbar'>"
                                            +"<a href='#' id='submitCashAvailibity' class='btn btn-info' >Submit</a>"
                                            +"<a href='#' id='cancelInfoWindow' class='btn btn-default'>Cancel</a></div>"
                                                    
                                        +"</div>"
                                    +"</div>";
            currentMarkerId['updateDetailsForm'] = updateDetailForm;
        }
        return currentMarkerId['updateDetailsForm'];
    }
    function getAverageWaitTimeString(waitTime){
        switch(waitTime) {
                case 1:
                    return {"cashStatus" : "Cash Available", "cashStatusCSS" : "text-success"};
                case 0:
                    return {"cashStatus" : "Cash Not Available", "cashStatusCSS" : "text-danger"};
                default:
                    return {"cashStatus" : "Cash availability information Not Available", "cashStatusCSS" : "text-warning"};
            }
    }
    function getCashStatus(cashStatus) {
            switch(cashStatus) {
                case 1:
                    return {"cashStatus" : "Cash Available", "cashStatusCSS" : "text-success"};
                case 0:
                    return {"cashStatus" : "Cash Not Available", "cashStatusCSS" : "text-danger"};
                default:
                    return {"cashStatus" : "Cash availability information Not Available", "cashStatusCSS" : "text-warning"};
            }
    }

    function processSearchRes(results, typeOption, newMapIds) {
        var mapList = [];
        var atmBankMap = {};
        $.each(results, function(ind, data){
            var found = false;
            $.each(data.types, function(ind2, type){
                if (type === typeOption) {
                    found = true;
                    return false;
                }
            });
            if (found) {
                atmBankMap[data.id] = data;
                mapList.push({'id': data.id});
            }
        });
        var dataToSave = getDataToSave(atmBankMap, typeOption);
        $.ajax({
            url: '/api/findbank/ws/findbank/creates',
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(dataToSave),
            contentType: 'application/json',
            success: function (result) {
                $.ajax({
                    url: "/api/findbank/ws/findbank/ids",
                    type: 'POST',
                    dataType: 'json',
                    data: JSON.stringify(mapList),
                    contentType: 'application/json',
                    success: function (response) {
                        //clearOverlays();
                        $.each(response, function (ind, data) {
                            newMapIds[data.mapId] = true;
                            if (paintedMapid[data.mapId]) {
                                return true;
                            }
                            atmBankMap[data.mapId].html_attributions = '';
                            createMarker(atmBankMap[data.mapId].geometry.location, data);
                        });
                        $.extend(paintedMapid, newMapIds);
                    }
                });
            }
        });
    }

    function searchAndPaint(latLng, typeOption) {
        var type = [];
        type.push(typeOption);

        var request = {
            location: latLng,
            radius: 2500,
            types: [typeOption]
        };

        var newMapIds = {};
        var service = new google.maps.places.PlacesService(map);
        service.nearbySearch(request, function (results, status, pagination) {
            if (status === google.maps.places.PlacesServiceStatus.OK) {
                processSearchRes(results, typeOption, newMapIds);
                if (pagination.hasNextPage) {
                    pagination.nextPage();
                }
            }
        });
    }

    function search_types(latLng){
        if(!latLng){
            latLng = pyrmont;
        }

        var typeOption = "";
        $('input[name="type"]:checked').each(function( index , val ) {
                            typeOption = val.id;                            
                        });
        searchAndPaint(latLng, typeOption);
    }

    function getDataToSave(dataMap, typeOption){
        var mapDataList = [];
        $.each(dataMap, function(ind, data){
            var bankStatus = -1;
            if (data.opening_hours) {
                bankStatus = data.opening_hours.open_now ? 1 : 0;
            }
            var mapData = {
                "mapId": data.id,
                "mapReference": data.reference,
                "latX": data.geometry.location.lat(),
                "latY": data.geometry.location.lng(),
                "type": typeOption,
                "name": data.name,
                "contactNumber": null,
                "bankOpenStatus": bankStatus,
                "cashAvailable": -1,
                "avgWaitTime": -1,
                "nextAvailabilty": null,
                "address": data.vicinity,
                "placeId": data.place_id
            };
            mapDataList.push(mapData);
            //results[i].html_attributions='';
            //createMarker(results[i],icon);
            //mapList.push(results[i].id);
        });
        return mapDataList;
    }
    // Deletes all markers in the array by removing references to them
    function clearOverlays() {
        if (infoWindow) {
            infoWindow.close();
        }
        if (markers) {
            $.each(markers, function (id, marker) {
                marker.setVisible(false);
            });
        }
        markersMap = {};
        paintedMapid = {};
    }

    function clearMarkers(){
        $('#show_btn').show();
        $('#hide_btn').hide();
        clearOverlays()
    }
    function showMarkers(){
        $('#show_btn').hide();
        $('#hide_btn').show();
        if (markersMap) {
            for (i in markersMap) {
                markersMap[i].setVisible(true)
            }

        }
    }
    function onOk(data, marker){
        $.ajax({
            url: "/api/findbank/ws/findbank/update2",
            //crossDomain: true,
            type: 'PUT',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (response) {
                createMarker(marker.position, response);
                infoWindow.close();
            }
        });
    }

    function initMap() {
        var input = /** @type {!HTMLInputElement} */(
            document.getElementById('address'));

        var types = document.getElementById('type-selector');
        //map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
        //map.controls[google.maps.ControlPosition.TOP_LEFT].push(types);

        var autocomplete = new google.maps.places.Autocomplete(input);
        autocomplete.bindTo('bounds', map);

        //var marker = new google.maps.Marker({
        //    map: map,
        //    anchorPoint: new google.maps.Point(0, -29)
        //});

        autocomplete.addListener('place_changed', function() {
            //infoWindow.close();
            //marker.setVisible(false);
            var place = autocomplete.getPlace();
            if (!place.geometry) {
                // User entered the name of a Place that was not suggested and
                // pressed the Enter key, or the Place Details request failed.
                window.alert("No details available for input: '" + place.name + "'");
                return;
            }

            map.setCenter(place.geometry.location);
            //// If the place has a geometry, then present it on a map.
            //if (place.geometry.viewport) {
            //    map.fitBounds(place.geometry.viewport);
            //} else {
            //    map.setCenter(place.geometry.location);
            //    map.setZoom(17);  // Why 17? Because it looks good.
            //}
            //marker.setPosition(place.geometry.location);
            //marker.setVisible(true);

            //var address = '';
            //if (place.address_components) {
            //    address = [
            //        (place.address_components[0] && place.address_components[0].short_name || ''),
            //        (place.address_components[1] && place.address_components[1].short_name || ''),
            //        (place.address_components[2] && place.address_components[2].short_name || '')
            //    ].join(' ');
            //}

            //infoWindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
            //infoWindow.open(map, marker);
        });
    }

    //function handleLocationError(browserHasGeolocation, //infoWindow, pos) {
        //infoWindow.setPosition(pos);
        //infoWindow.setContent(browserHasGeolocation ?
        //    'Error: The Geolocation service failed.' :
        //    'Error: Your browser doesn\'t support geolocation.');
    //};
    function clearAllSearch() {

    }

    function updateNotAvailableStatus(){
        submitAvailableStatus(true);
    }
    function updateAvailableStatus(){
        $('#avgWaitTimeSubmitDiv').addClass('show').removeClass('hidden');
        //$('#cashStatusDiv').addClass('hidden').removeClass('show');
    }
    function submitAvailableStatus(status){
         var cashStatus = Number($("#cashStatus").val());
            var waitTime = $("#avgWaitTimeField").val() > 0 ? Number($("#avgWaitTimeField").val()) : 15;
            var nextAvblTime =  null;
            var markerId = currentMarkerId['mapId'];
            var marker = markersMap[markerId];
            marker.setVisible(false);
            var data = {
                "cashAvailable": status == true ? 0 : 1,
                "avgWaitTime": waitTime,
                "nextAvailabilty": nextAvblTime ? new Date(nextAvblTime).getTime() : null,
                "mapId": markerId
            };
            onOk(data, marker);
    }

    function cancelAvailableStatus(){
        infoWindow.close();
    }

    function showMap(inputLatLong){
        var locationData = {};
        var imageUrl = 'https://chart.apis.google.com/chart?cht=mm&chs=24x32&chco=FFFFFF,008CFF,000000&ext=.png';
        var markerImage = new google.maps.MarkerImage(imageUrl,new google.maps.Size(24, 32));
        if (!inputLatLong) {
            var addresFromSearch = $('#address').val();
            locationData.address = addresFromSearch;
        }
        else {
            locationData.location = inputLatLong;
        }
        geocoder.geocode(locationData, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                var latitude = results[0].geometry.location.lat();
                var longitude = results[0].geometry.location.lng();
                var latlng = new google.maps.LatLng(latitude, longitude);
                if (results[0]) {
                   // map.setZoom(15);
                    map.setCenter(latlng);
                    if(!marker){
                        marker = new google.maps.Marker({
                            position: latlng, 
                            map: map,
                            icon: markerImage,
                            draggable: true ,
                            animation: google.maps.Animation.DROP,
                            
                        }); 
                    }else{
                        marker.setPosition(latlng);
                    }
                    marker.setVisible(false);
                    $('#btn').hide();
                    $('#latitude,#longitude').show();
                    $('#address').val(results[0].formatted_address);
                    $('#latitude').val(marker.getPosition().lat());
                    $('#longitude').val(marker.getPosition().lng());
                    //infoWindow.setContent(results[0].formatted_address);
                    //infoWindow.open(map, marker);
                    search_types(marker.getPosition());
                    google.maps.event.addListener(marker, 'click', function() {
                        //infoWindow.open(map,marker);

                    });


                    google.maps.event.addListener(marker, 'dragend', function() {

                        geocoder.geocode({'latLng': marker.getPosition()}, function(results, status) {
                            if (status == google.maps.GeocoderStatus.OK) {
                                if (results[0]) {
                                    $('#btn').hide();
                                    $('#latitude,#longitude').show();
                                    $('#address').val(results[0].formatted_address);
                                    $('#latitude').val(marker.getPosition().lat());
                                    $('#longitude').val(marker.getPosition().lng());
                                }

                                //infoWindow.setContent(results[0].formatted_address);
                                var centralLatLng = marker.getPosition();
                                search_types(centralLatLng);
                                //infoWindow.open(map, marker);
                            }
                        });
                    });

                    map.addListener('idle', function(event) {
                        if(!currentMarkerId['markerEvent']){
                            marker.setPosition(map.getCenter());
                            search_types(map.getCenter());
                        }
                         currentMarkerId['markerEvent']  = false;
                      });

                    google.maps.event.addListener(map, 'click', function(event) {
    
                            marker.setPosition(map.getCenter())
                            map.setCenter(event.latLng);
                            search_types(map.getCenter());
                    });

                    
                
                } else {
                    alert("No results found");
                }
            } else {
                alert("Please enable location services");
            }
        });                
    }   
});