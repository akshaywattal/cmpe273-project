var app = angular.module("myApp", []);

app.directive('map', function($http) {
	return {
		restrict: 'E',
		replace: true,
		template: '<div></div>',
		link: function(scope, element, attrs) {
			console.log(element);
			var myOptions = {
				zoom: 14,
				center: new google.maps.LatLng(37.442975, -122.1630475),
				mapTypeId: google.maps.MapTypeId.ROADMAP
			};
			var map = new google.maps.Map(document.getElementById(attrs.id), myOptions);
			
			var infowindow = new google.maps.InfoWindow();
			
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
						var z = y[i];
						var myLatlng = new google.maps.LatLng(z.latitude, z.longitude);
						if (z.business_id == "VFslQjSgrw4Mu5_Q1xk1KQ") {
							var marker = new google.maps.Marker({
								position: myLatlng,
								map: map,
								title: z.name + " (You!)",
								icon: "http://www.google.com/intl/en_us/mapfiles/ms/micons/blue-dot.png"
							});
						} else {
							var marker = new google.maps.Marker({
								position: myLatlng,
								map: map,
								title: z.name
							});
						}
						(function(marker, z) {
							google.maps.event.addListener(marker, 'click', function(e) {
								var contentString = "<a href='"+z.url+"'><ul style='list-style-type:none; margin:0; padding:0;'>" +
										"<li style='float:left; margin-right:1em;'><img src='"+z.photo_url+"'></li>" +
												"<li style='float:left'><h1>"+z.name+"</h1><p>"+z.full_address+"</p></li></ul></a>";
								infowindow.setContent(contentString);
								infowindow.open(map, marker);
							});
						})(marker, z);
					} 
				}).error(function(data, status, headers, config) {
					alert("failure");
				});
			}
			
			fillMap();
		}
	};
});
<<<<<<< HEAD

app.directive('pieplot', function($http) {
=======
/*
app.directive('piePlot', function($http) {
>>>>>>> 00b251422e75c8a5260f1facfcffc2ce31fecbce
	return {
		restrict: 'EA',
		replace: true,
		template: '<div></div>',
		link: function(scope, element, attrs) {
			console.log(element);
			
			makeChart = function() {
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
			
			makeChart();
		}
	};
});

<<<<<<< HEAD
app.directive('donutplot', function($http) {
	return {
		restrict: 'EA',
		replace: true,
		template: '<div></div>',
		link: function(scope, element, attrs) {
			console.log(element);
			
			makeChart2 = function() {
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
				    .innerRadius(radius - 70);

					var pie = d3.layout.pie()
					    .sort(null)
					    .value(function(d) { return d.value; });

					var svg = d3.select("#sentimentdonut").append("svg")
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
					      .text(function(d) { return d.data.value; });
					  
				}).error(function(data, status, headers, config) {
					alert("failure");
				});
				
			}
			
			makeChart2();
		}
	};
});


=======
*/
>>>>>>> 00b251422e75c8a5260f1facfcffc2ce31fecbce
function MyCtrl($scope) {
	$scope.sentiment = [{
		"sentiment": "neutral",
		"value": "2"
		}, {
		"sentiment": "positive",
		"value": "14"
		}, {
		"sentiment": "negative",
		"value": "5"
		}, {
		"sentiment": "notevaluated",
		"value": "0"
		}];
}


