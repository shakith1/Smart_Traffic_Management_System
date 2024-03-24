<%@ page import="lk.oxo.urbantraffic.model.TrafficData" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Urban Traffic Monitor</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="row text-center">
        <div class="col mt-3 mb-4">
            <h2>Traffic Data Dashboard</h2>
        </div>
    </div>

    <%
        List<TrafficData> trafficDataList = (List<TrafficData>) request.getAttribute("trafficDataList");
        if (trafficDataList.isEmpty()) {
    %>
    <div class="row text-center mt-5">
        <h3>Please Run Client Application For Data Manipulation</h3>
    </div>
    <%
    } else {
    %>
    <div class="row text-center mb-4">
        <div class="col">
            <a class="btn btn-primary mr-2" href="traffic_pattern">Traffic Patterns</a>
            <button class="btn btn-primary">Urban Efficiency</button>
        </div>
    </div>

    <div class="row">
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>Date</th>
                <th>Time</th>
                <th>Vehicle Speed (km/h)</th>
                <th>Traffic Light Status</th>
                <th>Latitude (°)</th>
                <th>Longitude (°)</th>
                <th>Traffic Zone</th>
            </tr>
            </thead>
            <tbody>
            <%

                for (TrafficData trafficData : trafficDataList) { %>
            <tr>
                <td><%= trafficData.getTimeStamp().toLocalDate() %>
                </td>
                <td><%= trafficData.getTimeStamp().toLocalTime() %>
                </td>
                <td><%= trafficData.getVehicleSpeed() %>
                </td>
                <td><%= trafficData.getLightStatus() %>
                </td>
                <td><%= trafficData.getLatitude() %>
                </td>
                <td><%= trafficData.getLongitude() %>
                </td>
                <td><%= trafficData.getTrafficZone() %>
                </td>
            </tr>
            <%
                }
            }%>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"/>
<script src="js/script.js"/>
</body>
</html>
