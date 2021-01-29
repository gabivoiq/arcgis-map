<template>
  <div id="viewDiv"><NavigationBar/></div>
</template>

<script>
import { loadModules } from "esri-loader";
import NavigationBar from '@/components/NavigationBar';

export default {
  components: {
    NavigationBar
  },
  mounted() {
    this.loadMap();
  },
  methods: {
    loadMap() {
      loadModules([
        "esri/Map",
        "esri/views/MapView",
        "esri/Graphic",
        "esri/layers/FeatureLayer",
        "esri/widgets/Track",
        "esri/widgets/Directions",
        "esri/widgets/Search",
        "esri/symbols/SimpleMarkerSymbol",
      ]).then(
          ([
             Map,
             MapView,
             Graphic,
             FeatureLayer,
             Track,
             Directions,
             Search,
             SimpleMarkerSymbol,
           ]) => {
            var map = new Map({
              basemap: "streets-navigation-vector",
            });
            var view = new MapView({
              container: "viewDiv",
              map: map,
              center: [23.733, 46.728],
              zoom: 11,
            });
            var popupTuristic = {
              title: "{Objective_Name}",
              content: "<b>Descriere:</b> {Descriere}",
            };
            var turisticLayer = new FeatureLayer({
              url:
                  "https://services3.arcgis.com/U26uBjSD32d7xvm2/arcgis/rest/services/Obiective_turistice_ClujNapoca/FeatureServer",
              outFields: ["*"],
              popupTemplate: popupTuristic,
            });
            map.add(turisticLayer);
            var track = new Track({
              view: view,
              graphic: new Graphic({
                symbol: {
                  type: "simple-marker",
                  size: "12px",
                  color: "green",
                  outline: {
                    color: "#efefef",
                    width: "1.5px",
                  },
                },
              }),
              useHeadingEnabled: false, // Don't change orientation of the map
            });
            view.ui.add(track, "top-left");
            //  var sqlExpressions = [
            //  "Objective_Type = 'Culturale'",
            //  "Objective_Type = 'Distractie'",
            //  "Varsta < 10",
            //  "Varsta < 14"
            //];
            //  var sqlNames = [
            //  "Obiective Culturale",
            //  "Obiective Recreationale",
            //  "Varsta mai mica de 10 ani",
            //  "Varsta mai mica de 14 ani"
            //];
            //var i = 0;
            //var selectFilter = document.createElement("select");
            //selectFilter.setAttribute("class", "esri-widget esri-select");
            //selectFilter.setAttribute(
            //  "style",
            //  "width: 220px; font-family: Avenir Next W00; font-size: 1em;"
            //);
            //sqlExpressions.forEach(function (sql) {
            //  var option = document.createElement("option");
            //  option.value = sql;
            //  option.innerHTML = sqlNames[i];
            //  i++;
            //  selectFilter.appendChild(option);
            //});
            // view.ui.add(selectFilter, "top-left");
            var objectiveType = "Objective_Type = '";
            var quotation = "'";
            var age = " and Varsta <= ";
            var userInterests = localStorage.getItem("userInterests").split(",");
            if(userInterests[0] === "Cultural") {
              userInterests = "Culturale";
            } else if(userInterests[0] === "Entertainment") {
              userInterests = "Distractie";
            }
            console.log(userInterests);
            var variabilaType = userInterests; // SHI AICI BAGA
            var variabilaAge = localStorage.getItem("age"); /////// BAGA AICI VOIQ-LEEEEEEEEEEEEEEEEEEE

            var typeHalf = objectiveType.concat(variabilaType);
            var typeFull = typeHalf.concat(quotation);
            var ageFull = age.concat(variabilaAge);
            var filter = typeFull.concat(ageFull);
            //selectFilter.addEventListener("online", function (event) {
            setFeatureLayerViewFilter(filter);
            //});
            function setFeatureLayerViewFilter(expression) {
              view.whenLayerView(turisticLayer).then(function (featureLayerView) {
                featureLayerView.filter = {
                  where: expression,
                };
              });
            }
            view.on("double-click", function (evt) {
              evt.stopPropagation();
            });
            var directions = new Directions({
              view: view,
              routeServiceUrl:
                  "https://utility.arcgis.com/usrsvcs/appservices/GVlbfdiBqPHQnD2a/rest/services/World/Route/NAServer/Route_World",
            });
            function createStopSymbol(color, size) {
              return new SimpleMarkerSymbol({
                style: "simple-marker",
                size: size,
                outline: {
                  width: "2px",
                  color: "white",
                },
                color: color,
              });
            }
            // Stop symbols
            directions.stopSymbols.first = createStopSymbol("green", "14px");
            directions.stopSymbols.middle = createStopSymbol("purple", "14px");
            directions.stopSymbols.last = createStopSymbol("red", "14px");
            // Route symbol
            directions.routeSymbol.width = "2";
            directions.routeSymbol.color = [0, 0, 0, 0.75];
            directions.routeSymbol.style = "short-dot";
            view.ui.add(directions, "top-right");
            var search = new Search({
              view: view,
            });
            var search1 = new Search({
              view: view,
            });
            view.ui.add(search, "top-left");
            view.on("double-click", function (evt) {
              search1.clear();
              view.popup.clear();
              if (search1.activeSource) {
                var geocoder = search1.activeSource.locator; // World geocode service
                var params = {
                  location: evt.mapPoint,
                };
                geocoder.locationToAddress(params).then(
                    function (response) {
                      // Show the address found
                      var address = response.address;
                      showPopup(address, evt.mapPoint);
                    }
                );
              }
            });
            function showPopup(address, pt) {
              view.popup.open({
                title: "Adresa exacta",
                content:
                    "<b>Coordonate: </b>" +
                    Math.round(pt.longitude * 100000) / 100000 +
                    ", " +
                    Math.round(pt.latitude * 100000) / 100000 +
                    "</br>" +
                    "<b>Adresa: </b>" +
                    address,
                location: pt,
              });
            }
            search.sources.push({
              layer: turisticLayer,
              searchFields: ["Objective_Name"],
              displayField: "Objective_Name",
              exactMatch: false,
              outFields: ["Objective_Name", "Descriere"],
              resultGraphicEnabled: true,
              name: "Obiective Turistice Cluj",
              placeholder: "Example: Cluj Arena",
            });
          }
      );
    },
  },
};
</script>

<style scoped>
@import url("https://js.arcgis.com/4.18/esri/themes/light/main.css");

html,
body,
#viewDiv {
  padding: 0;
  margin: 0;
  height: 100%;
  width: 100%;
}
.esri-view .esri-directions {
  position: fixed;
  right: 15px;
}
.esri-view .esri-component.esri-attribution {
  position: fixed;
}
</style>
