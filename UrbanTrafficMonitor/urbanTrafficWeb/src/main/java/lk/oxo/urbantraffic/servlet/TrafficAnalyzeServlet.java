package lk.oxo.urbantraffic.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataAnalysis;
import lk.oxo.urbantraffic.ejb.util.AnalyzedLevel;
import lk.oxo.urbantraffic.ejb.util.RushHour;
import lk.oxo.urbantraffic.ejb.util.TrafficLevel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@WebServlet(name = "TrafficAnalyzeServlet",value = "/traffic_pattern")
public class TrafficAnalyzeServlet extends HttpServlet {
    @EJB
    TrafficDataAnalysis dataAnalysis;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<LocalDate, Map<RushHour, AnalyzedLevel>> trafficLevelOnRushHour = dataAnalysis.analyzeTrafficLevelOnRushHour();
        req.setAttribute("trafficLevel",trafficLevelOnRushHour);
        req.getRequestDispatcher("/traffic_patterns.jsp").forward(req,resp);
    }
}
