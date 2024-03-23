package lk.oxo.urbantraffic.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.oxo.urbantraffic.ejb.remote.TrafficDataStorage;
import lk.oxo.urbantraffic.model.TrafficData;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "TrafficDataServlet",urlPatterns = {"/","/index.jsp"})
public class TrafficDataServlet extends HttpServlet {
    @EJB
    TrafficDataStorage storage;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TrafficData> trafficDataList = storage.getTrafficDataList();
        req.setAttribute("trafficDataList",trafficDataList);
        req.getRequestDispatcher("/data.jsp").forward(req,resp);
    }
}
