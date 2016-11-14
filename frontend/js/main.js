var currentMarkerId = {};
jQuery(function($) {
    var map;
    var infoWindow;
    var markersArray = [];
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

         $('input[type=radio][name=type]').on('change', function() {
           showMap();
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

    // var waypoints = [];
    function firstLoad() {
        map = new google.maps.Map(document.getElementById('map'), {
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            center: pyrmont,
            zoom: 15,
            mapTypeControl: false
        });
        //infoWindow = new google.maps.InfoWindow({map: map});

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
        var waitTime =  $("#avgWaitTime").val(Number(currentMarkerId['avgWaitTime'] ? currentMarkerId['avgWaitTime'] : -1));
        var nextAvblTime = $("#nxtAvblDateTime").val((currentMarkerId['nextAvailabilty'] ? new Date(currentMarkerId['nextAvailabilty']) : new Date()));
     }
    function createMarker(place,icon, placeData) {

        if(placeData){
            var type = placeData.type;
             var status = "";
            switch(placeData.cashAvailable){
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
            var icon = "images/bank_duniya_img/"+type+"_"+status+".png";
        } 
        var placeLoc = place.geometry.location;
        var marker = new google.maps.Marker({
            map: map,
            position: place.geometry.location,
            icon: icon,
            visible:true  
            
        });
        
        markersArray.push(marker);
        var infoNotAvbl = "Information not available";
        var  markerContent = "<b>Name:</b>"+place.name+"<br><b>Cash Status:</b>"+(placeData.cashAvailable == 1 ? "Available" : "Not Available")+"<br>";
        if(placeData.cashAvailable != 1){
            markerContent += "<b>Next Availability:</b>"+(placeData.nextAvailabilty ? new Date(placeData.nextAvailabilty) : infoNotAvbl)+"<br>";
        }else{
            markerContent += "<b>Average Waiting Time : </b>"+(placeData.avgWaitTime!= -1 ? placeData.avgWaitTime : infoNotAvbl) +"<br>";
        }
        markerContent += "<b>Address :</b>"+placeData.address+"<br><b> Status:</b>"+(placeData.bankOpenStatus ? "Open" : "Closed")+"<br>";
        markerContent += "<a href='#' id='updateBankButton' class='btn btn-danger btn-xs' data-toggle='modal' data-target='#myModal'><em class='fa fa-trash'>Update Bank Details</a>";
        if(!infoWindow){
            infoWindow = new google.maps.InfoWindow({map: map});
            infoWindow.close();
        }
        google.maps.event.addListener(marker, 'click', function() {
            infoWindow.setContent(markerContent);
            infoWindow.open(map, this);
            currentMarkerId['mapId'] = placeData.mapId;
            currentMarkerId['cashAvailable'] = placeData.cashAvailable;
            currentMarkerId['avgWaitTime'] = placeData.avgWaitTime;
            currentMarkerId['nextAvailabilty'] = placeData.nextAvailabilty;
            onUpdateModal();
        });
       
    }
    var source="";
    var dest='';
    
    
    function search_types(latLng){      
        if(!latLng){
            latLng = pyrmont;
        }
        
        var type = [];
        var typeOption = "";
        $('input[name="type"]:checked').each(function( index , val ) {
                            typeOption = val.id;                            
                        });
        switch(typeOption){
            case 'bank':
                type = ['bank'];
                break;
            case  'atm':
                type = ['atm'];
                break;
            case 'changetype_all':
                type = ['bank', 'atm'];
                break;
        }
        var icon = "images/bank_duniya_img/"+type+".png";
        
        if(type.length){
            var request = {
                location: latLng,
                radius: 5000,
                types: [type] //e.g. school, restaurant,bank,bar,city_hall,gym,night_club,park,zoo
            };
           
            var service = new google.maps.places.PlacesService(map);
            service.search(request, function(results, status) {
                //map.setZoom(15);
                if (status == google.maps.places.PlacesServiceStatus.OK) {
                    var mapList = [];
                    var atmBankMap = {};
                    for (var i = 0; i < results.length; i++) {
                        atmBankMap[results[i].id ] =  results[i];
                        mapList.push({'id' : results[i].id});
                    }
                    var dataToSave = saveMapData(results);
                    $.ajax({
                      url: '/api/findbank/ws/findbank/creates',
                      type:'POST',
                      dataType: 'json',
                      data: JSON.stringify(dataToSave),
                      contentType: 'application/json',
                      success: function(result){
                            $.ajax({url: "/api/findbank/ws/findbank/ids",
                            type:'POST',
                            dataType: 'json',
                            data:JSON.stringify(mapList),
                            contentType: 'application/json',
                            success: function(response){
                                clearOverlays();
                                for (var i = 0; i < response.length; i++) {
                                    atmBankMap[response[i].mapId].html_attributions='';
                                    createMarker(atmBankMap[response[i].mapId], icon, response[i]);
                                }
                            }});
                      }
                    });

                }
            });
        }
     }

    function onUpdateBank(){

    }

    function saveMapData(dataList){

        if(dataList.length > 0){
        var mapDataList = [];
        for (var i = 0; i < dataList.length; i++) {
                var bankStatus = -1;
                if(dataList[i].opening_hours){
                    bankStatus = dataList[i].opening_hours.open_now ? 1 : 0;
                }
                var mapData = {
                    "mapId" : dataList[i].id,
                    "mapReference": dataList[i].reference,
                    "latX": dataList[i].geometry.location.lat(),
                    "latY": dataList[i].geometry.location.lng(),
                    "type": dataList[i].types[0],
                    "name": dataList[i].name,
                    "contactNumber": null,
                    "bankOpenStatus": bankStatus,
                    "cashAvailable": -1,
                    "avgWaitTime": -1,
                    "nextAvailabilty": null,
                    "address":dataList[i].vicinity,
                    "placeId" : dataList[i].place_id
                };
                mapDataList.push(mapData);
                //results[i].html_attributions='';
                //createMarker(results[i],icon);
                //mapList.push(results[i].id);
            }
        }
        return mapDataList;
    }
    // Deletes all markers in the array by removing references to them
    function clearOverlays() {
        if(infoWindow)
        {
            infoWindow.close();
        }
        if (markersArray) {
            for (i in markersArray) {
                markersArray[i].setVisible(false)
            }
            //markersArray.length = 0;
        }
    }

    function clearMarkers(){
        $('#show_btn').show();
        $('#hide_btn').hide();
        clearOverlays()
    }
    function showMarkers(){
        $('#show_btn').hide();
        $('#hide_btn').show();
        if (markersArray) {
            for (i in markersArray) {
                markersArray[i].setVisible(true)
            }

        }
    }
    function onOk(){
        var cashStatus = Number($("#cashStatus").val());
        var waitTime =  $("#avgWaitTime").val()? Number($("#avgWaitTime").val()) : -1;
        var nextAvblTime = $("#nxtAvblDateTime").val() ? $("#nxtAvblDateTime").val() : null;
        var markerId = currentMarkerId['mapId'];
        var data = {
            "cashAvailable": cashStatus,
            "avgWaitTime": waitTime,
            "nextAvailabilty": nextAvblTime ? new Date(nextAvblTime).getTime() : null,
              "mapId" : markerId
            };
        $.ajax({url: "/api/findbank/ws/findbank/update2",
                //crossDomain: true,
                type:'PUT',
                dataType: 'json',
                data:JSON.stringify(data),
                contentType: 'application/json',
                success: function(response){
                    search_types(map.getCenter());

                }});
    }

    //function handleLocationError(browserHasGeolocation, //infoWindow, pos) {
        //infoWindow.setPosition(pos);
        //infoWindow.setContent(browserHasGeolocation ?
        //    'Error: The Geolocation service failed.' :
        //    'Error: Your browser doesn\'t support geolocation.');
    //};

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
                        marker.setPosition(map.getCenter())
                        search_types(map.getCenter());
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
                alert("Geocoder failed due to: " + status);
            }
        });                
    }   
});