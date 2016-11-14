var currentMarkerId = {};
jQuery(function($) {'use strict',

	//#main-slider
	$(function(){
        var dontCheckLocation = sessionStorage.dontCheckLocation;
        if(dontCheckLocation) {
            $.getJSON('https://geoip-db.com/json/geoip.php?jsonp=?') 
                 .done (function(location)
                 {
                     $('#address').val(location.city+", "+location.state+", "+location.country_name);               
                 });
                 $('#address_submit').on('click',showMap);
                 $('#okBtn').on('click',onOk);
                // bankDuniya code starting
                $('.chkbox').click(function(){
                    $(':checkbox').attr('checked',false);
                    $('#'+$(this).attr('id')).prop( "checked", true );
                    search_types(map.getCenter());
                });
                $('#updateBankButton').on('click', onUpdateModal);       
        } 
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

	// portfolio filter
	$(window).load(function(){'use strict';
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

	// Contact form
	var form = $('#main-contact-form');
	form.submit(function(event){
		event.preventDefault();
		var form_status = $('<div class="form_status"></div>');
		$.ajax({
			url: $(this).attr('action'),

			beforeSend: function(){
				form.prepend( form_status.html('<p><i class="fa fa-spinner fa-spin"></i> Email is sending...</p>').fadeIn() );
			}
		}).done(function(data){
			form_status.html('<p class="text-success">' + data.message + '</p>').delay(3000).fadeOut();
		});
	});

	
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

	var map;
    var infowindow;
    var markersArray = [];
    var pyrmont = new google.maps.LatLng(20.268455824834792, 85.84099235520011);
    var marker;
    var geocoder = new google.maps.Geocoder();
    var infowindow = new google.maps.InfoWindow();
    // var waypoints = [];                  
    function initialize() {
        map = new google.maps.Map(document.getElementById('map'), {
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            center: pyrmont,
            zoom: 14
        });
        infowindow = new google.maps.InfoWindow();
        //document.getElementById('directionsPanel').innerHTML='';
        search_types();
       }

     function onUpdateModal(){
     	debugger;
     	var cashStatus = $("#cashStatus").val(Number(currentMarkerId['cashAvailable']));
    	var waitTime =  $("#avgWaitTime").val(Number(currentMarkerId['avgWaitTime'] ? currentMarkerId['avgWaitTime'] : -1));
    	var nextAvblTime = $("#nxtAvblDateTime").val((currentMarkerId['nextAvailabilty'] ? new Date(currentMarkerId['nextAvailabilty']) : new Date()));
     }
    function createMarker(place,icon, placeData) {

    	if(placeData){
    		var type = placeData.type;
    		var status = [-1,0].indexOf(placeData.cashAvailable)!= -1? "unavble" :"avble";
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
        var  markerContent = "<b>Name: </b>"+place.name+"<br><b>Cash Status: </b>"+(placeData.cashAvailable == 1 ? "Available" : "Not Available")+"<br>";
        if(placeData.cashAvailable != 1){
        	markerContent += "<b>Next Availability:</b>"+(placeData.nextAvailabilty ? placeData.nextAvailabilty : infoNotAvbl)+"<br>";
        }else{
        	markerContent += "<b>Average Waiting Time: </b>"+(placeData.avgWaitTime!= -1 ? placeData.avgWaitTime : infoNotAvbl) +"<br>";
        }
        markerContent += "<b>Address: </b>"+placeData.address+"<br><b> Status: </b>"+(placeData.bankOpenStatus ? "Open" : "Closed")+"<br>";
        markerContent += "<a href='#' id='updateBankButton' class='btn btn-danger btn-xs' data-toggle='modal' data-target='#myModal'><em class='fa fa-trash'>Update Bank Details</a>";

        google.maps.event.addListener(marker, 'click', function() {
            infowindow.setContent(markerContent);
            infowindow.open(map, this);
            debugger;
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
        clearOverlays(); 
      
        if(!latLng){
            var latLng = pyrmont;
        }
        
        var type = [];
        $('.chkbox:checked').each(function( index , val ) {
        					type.push(val.id);							  
						});
        var icon = "images/bank_duniya_img/"+type+".png";
        
        if(type.length){
	        var request = {
	            location: latLng,
	            radius: 2000,
	            types: [type] //e.g. school, restaurant,bank,bar,city_hall,gym,night_club,park,zoo
	        };
	       
	        var service = new google.maps.places.PlacesService(map);
	        service.search(request, function(results, status) {
	            map.setZoom(14);
	            if (status == google.maps.places.PlacesServiceStatus.OK) {
	            	var mapList = [];
	            	var atmBankMap = {};
	                for (var i = 0; i < results.length; i++) {
	                	//debugger;
	                    //results[i].html_attributions='';
	                    //createMarker(results[i],icon);
	                    atmBankMap[results[i].id ] =  results[i];
	                    mapList.push({'id' : results[i].id});
	                }
	                debugger;
	                var dataToSave = saveMapData(results);
	                $.ajax({
					  url: '/api/findbank/ws/findbank/creates',
					  type:'POST',
					 // crossDomain: true,
				      dataType: 'json',
					  data: JSON.stringify(dataToSave),
					  contentType: 'application/json',
					  success: function(result){
						  	debugger;
						  	 $.ajax({url: "/api/findbank/ws/findbank/ids",
					        //crossDomain: true,
					        type:'POST',
					        dataType: 'json',
					        data:JSON.stringify(mapList),
					        contentType: 'application/json',
					        success: function(response){
					            debugger;
		                    	
		                    	for (var i = 0; i < response.length; i++) {
						        	//debugger;
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
    	debugger;
    }

    function saveMapData(dataList){
    	debugger;
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
        if (markersArray) {
            for (i in markersArray) {
                markersArray[i].setVisible(false)
            }
            //markersArray.length = 0;
        }
    }
    google.maps.event.addDomListener(window, 'load', initialize);
    
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
    	debugger;
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
		            debugger;
		        }});
    }

    function showMap(){
        var imageUrl = 'https://chart.apis.google.com/chart?cht=mm&chs=24x32&chco=FFFFFF,008CFF,000000&ext=.png&key=AIzaSyClM9KkifxJZgbSAKWQGz8QhUnAzNGEkuU';
        var markerImage = new google.maps.MarkerImage(imageUrl,new google.maps.Size(24, 32));
        var input_addr=$('#address').val();
        geocoder.geocode({address: input_addr}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                var latitude = results[0].geometry.location.lat();
                var longitude = results[0].geometry.location.lng();
                var latlng = new google.maps.LatLng(latitude, longitude);
                if (results[0]) {
                    map.setZoom(14);
                    map.setCenter(latlng);
                    marker = new google.maps.Marker({
                        position: latlng, 
                        map: map,
                        icon: markerImage,
                        draggable: true 
                        
                    }); 
                    $('#btn').hide();
                    $('#latitude,#longitude').show();
                    $('#address').val(results[0].formatted_address);
                    $('#latitude').val(marker.getPosition().lat());
                    $('#longitude').val(marker.getPosition().lng());
                    sessionStorage.dontCheckLocation = "true";
                    infowindow.setContent(results[0].formatted_address);
                    infowindow.open(map, marker);
                    search_types(marker.getPosition());
                    google.maps.event.addListener(marker, 'click', function() {
                        infowindow.open(map,marker);
                        
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
                                
                                infowindow.setContent(results[0].formatted_address);
                                var centralLatLng = marker.getPosition();
                                search_types(centralLatLng);
                                infowindow.open(map, marker);
                            }
                        });
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