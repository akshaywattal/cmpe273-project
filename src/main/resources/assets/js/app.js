var app = angular.module("myApp", []);

app.directive('map', function() {
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
			
			fillMap = function(){
				for (var i = 0; i < scope.biz.length; i++ ){
					addMarker({
						lat: scope.biz[i].latitude,
						lng: scope.biz[i].longitude
					});
				}
			}
			fillMap();
		}
	};
});

function MyCtrl($scope) {
	$scope.mapPin = "No pin set yet";
	$scope.biz = [{
	"_id" : "5286c4e37c144e6bcf4af253",
	"business_id" : "PBqjmOB7Yjti3cFqy8-Ctw",
	"full_address" : "Cubberley Education Building\n485 Lasuen Mall\nStanford, CA 94305",
	"schools" : [
		"Stanford University"
	],
	"open" : true,
	"categories" : [
		"Cafes",
		"Sandwiches",
		"Restaurants"
	],
	"photo_url" : "http://s3-media3.ak.yelpcdn.com/bphoto/cymSVOW-Mn4c4j9wSJ-V4A/ms.jpg",
	"city" : "Stanford",
	"review_count" : 10,
	"name" : "Cubberley Cafe",
	"neighborhoods" : [ ],
	"url" : "http://www.yelp.com/biz/cubberley-cafe-stanford",
	"longitude" : -122.16836,
	"state" : "CA",
	"stars" : 3.5,
	"latitude" : 37.426398,
	"type" : "business"
},{
	"_id" : "5286c4e37c144e6bcf4af308",
	"business_id" : "VFslQjSgrw4Mu5_Q1xk1KQ",
	"full_address" : "529 Alma St\nPalo Alto, CA 94301",
	"schools" : [
		"Stanford University"
	],
	"open" : true,
	"categories" : [
		"Steakhouses",
		"Brazilian",
		"Restaurants"
	],
	"photo_url" : "http://s3-media4.ak.yelpcdn.com/bphoto/Wn0OeAXGHZ5SWJX1fooA0A/ms.jpg",
	"city" : "Palo Alto",
	"review_count" : 1146,
	"name" : "Pampas",
	"neighborhoods" : [ ],
	"url" : "http://www.yelp.com/biz/pampas-palo-alto",
	"longitude" : -122.1630475,
	"state" : "CA",
	"stars" : 4,
	"latitude" : 37.442975,
	"type" : "business"
}
];
}