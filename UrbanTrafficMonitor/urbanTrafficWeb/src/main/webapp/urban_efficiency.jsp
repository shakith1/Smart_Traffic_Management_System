<%@ page import="java.time.LocalDate" %>
<%@ page import="lk.oxo.urbantraffic.model.TrafficZone" %>
<%@ page import="lk.oxo.urbantraffic.ejb.util.Efficiency" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Overall Urban Mobility Efficiency</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="row text-center">
        <div class="col mt-3 mb-4">
            <h2>Overall Urban Mobility Efficiency</h2>
        </div>
    </div>
    <div class="row text-center mb-4">
        <div class="col">
            <a class="btn btn-primary mr-2" href="index.jsp">Home Page</a>
            <a class="btn btn-primary" href="traffic_pattern">Traffic Patterns</a>
        </div>
    </div>

    <div class="row mt-3">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Date</th>
                <th>Traffic Zone</th>
                <th>Efficiency</th>
            </tr>
            </thead>
            <tbody>
            <%
                Map<LocalDate, Map<TrafficZone, Efficiency>> efficiency = (Map<LocalDate, Map<TrafficZone, Efficiency>>) request.getAttribute("efficiency");
                for (LocalDate date : efficiency.keySet()) {
                    Map<TrafficZone, Efficiency> efficiencyMap = efficiency.get(date);
                    int firstDate = 0;

                    for (TrafficZone trafficZone : efficiencyMap.keySet()) {
                        Efficiency efficiencyData = efficiencyMap.get(trafficZone);
                        firstDate++;
            %>
            <tr>
                <%
                    if (firstDate == 1) {
                        out.println("<td>" + date + "</td>");
                    } else {
                        out.println("<td>");
                    }
                %>
                <td><%= trafficZone %>
                </td>
                <td><%= efficiencyData %>
                </td>
                <%
                    }
                %>
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
