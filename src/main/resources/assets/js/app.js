var app = angular.module("myApp", []);

app.directive('map', function($http) {
	return {
		restrict: 'E',
		replace: true,
		template: '<div></div>',
		link: function(scope, element, attrs) {
			console.log(element);
			var myOptions = {
				zoom: 12,
				center: new google.maps.LatLng(37.426398, -122.16836),
				mapTypeId: google.maps.MapTypeId.ROADMAP
			};
			var map = new google.maps.Map(document.getElementById(attrs.id), myOptions);
			
			addMarker = function(pos){
				var myLatlng = new google.maps.LatLng(pos.lat,pos.lng);
				var marker = new google.maps.Marker({
					position: myLatlng,
					map: map,
					title: scope.mapPin
				});
			}
			
			fillMap = function() {
				$http({
					method: 'GET',
					dataType: 'json',
					url: 'http://localhost:8080/bigdata/v1/users/1/competition?callback=JSON_CALLBACK',
					headers: {
						'Content-Type': 'application/json'
					}
				}).success(function(data, status, headers, config) {
					var y = data;
					for (var i = 0; i < data.length; i++ ){
						addMarker({
							lat: y[i].latitude,
							lng: y[i].longitude
						}); 
					} 
				}).error(function(data, status, headers, config) {
					alert("failure");
				});
			}
			
			fillMap();
		}
	};
});

function MyCtrl($scope) {
	$scope.mapPin = "No pin set yet";
}