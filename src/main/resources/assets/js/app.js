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

app.directive('piePlot', function($http) {
	return {
		restrict: 'EA',
		replace: true,
		template: '<div></div>',
		link: function(scope, element, attrs) {
			console.log(element);
			
			fillMap = function() {
				$http({
					method: 'GET',
					dataType: 'json',
					url: 'http://localhost:8080/bigdata/v1/analytics/sentiment',
					headers: {
						'Content-Type': 'application/json'
					}
				}).success(function(data, status, headers, config) {				
					  
					var width = 480,
					    height = 300,
					    radius = Math.min(width, height) / 2;

					var color = d3.scale.ordinal()
					    .range(["yellow","green","red","orange"]);

					var arc = d3.svg.arc()
					    .outerRadius(radius - 10)
					    .innerRadius(0);

					var pie = d3.layout.pie()
					    .sort(null)
					    .value(function(d) { return d.value; });

					var svg = d3.select("#sentiment").append("svg")
					    .attr("width", width)
					    .attr("height", height)
					  .append("g")
					    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

					var g = svg.selectAll(".arc")
					      .data(pie(data))
					    .enter().append("g")
					      .attr("class", "arc");

					  g.append("path")
					      .attr("d", arc) 
					      .style("fill", function(d) { return color(d.data.sentiment); });

					  g.append("text")
					      .attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")"; })
					      .attr("dy", ".35em")
					      .style("text-anchor", "middle")
					      .text(function(d) { return d.data.sentiment; });
					  
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


