package lk.oxo.urbantraffic.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataAnalysis;
import lk.oxo.urbantraffic.ejb.util.Efficiency;
import lk.oxo.urbantraffic.model.TrafficZone;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@WebServlet(name = "UrbanEfficiencyServlet",value = "/efficiency")
public class UrbanEfficiencyServlet extends HttpServlet {
    @EJB
    TrafficDataAnalysis dataAnalysis;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<LocalDate, Map<TrafficZone, Efficiency>> urbanEfficiency = dataAnalysis.calculateUrbanMobilityEfficiency();
        req.setAttribute("efficiency",urbanEfficiency);
        req.getRequestDispatcher("/urban_efficiency.jsp").forward(req,resp);
    }
}
