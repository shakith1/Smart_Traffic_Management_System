<%@ page import="java.util.Map" %>
<%@ page import="lk.oxo.urbantraffic.ejb.util.AnalyzedLevel" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="lk.oxo.urbantraffic.ejb.util.RushHour" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Traffic Patterns</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="row text-center">
        <div class="col mt-3 mb-4">
            <h2>Traffic Patterns</h2>
        </div>
    </div>
    <div class="row text-center mb-4">
        <div class="col">
            <a class="btn btn-primary mr-2" href="index.jsp">Home Page</a>
            <a class="btn btn-primary" href="efficiency">Urban Efficiency</a>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <ul>
                <li>Morning Rush Hour: 07:00 - 10:00</li>
                <li>Evening Rush Hour: 16:00 - 19:00</li>
            </ul>
        </div>
    </div>

    <div class="row mt-3">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Date</th>
                <th>Morning Rush Hour Avg Speed (km/h)</th>
                <th>Traffic Level</th>
                <th>Evening Rush Hour Avg Speed (km/h)</th>
                <th>Traffic Level</th>
            </tr>
            </thead>
            <tbody>
            <%
                Map<LocalDate, Map<RushHour, AnalyzedLevel>> trafficLevel =
                        (Map<LocalDate, Map<RushHour, AnalyzedLevel>>) request.getAttribute("trafficLevel");

                for (LocalDate date : trafficLevel.keySet()) {
                    Map<RushHour, AnalyzedLevel> rushHourPairMap = trafficLevel.get(date);
                    AnalyzedLevel morningData = rushHourPairMap.get(RushHour.MORNING);
                    AnalyzedLevel eveningData = rushHourPairMap.get(RushHour.EVENING);
            %>
            <tr>
                <td><%= date%>
                </td>
                <td><%= morningData.getAverageSpeed() %>
                </td>
                <td><%= morningData.getTrafficLevel() %>
                </td>
                <td><%= eveningData.getAverageSpeed() %>
                </td>
                <td><%= eveningData.getTrafficLevel() %>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"/>
</body>
</html>
