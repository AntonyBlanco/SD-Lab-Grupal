/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package compra.productos;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author patit
 */
@WebServlet(name = "BuyProducts", urlPatterns = {"/BuyProducts"})
public class BuyProducts extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String aceite = request.getParameter("aceite");
        String arroz = request.getParameter("arroz");
        String manzana = request.getParameter("manzana");
        String lenteja = request.getParameter("lenteja");
        int numAceite = Integer.parseInt(aceite);
        int numArroz = Integer.parseInt(arroz);
        int numManzana = Integer.parseInt(manzana);
        int numLenteja= Integer.parseInt(lenteja);
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BuyProducts</title>");     
            out.println("<style>");
            out.println("    table {");
            out.println("        border-collapse: collapse;");
            out.println("        width: 50%;");
            out.println("        margin: auto;");
            out.println("    }");
            out.println("    th, td {");
            out.println("        border: 1px solid black;");
            out.println("        padding: 8px;");
            out.println("        text-align: center;");
            out.println("    }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Informe de la compra realizada:</h1>");
            if(ingresoValido(numAceite, numArroz, numManzana, numLenteja)){
                double subtotalAceite = numAceite*9.5;
                double subtotalArroz = numArroz*5.5;
                double subtotalManzana = numManzana*6;
                double subtotalLenteja = numLenteja*7.5;
                out.println(crearTabla(numAceite, numArroz, numManzana, numLenteja, subtotalAceite, subtotalArroz, subtotalManzana, subtotalLenteja));
            }else{
                out.println("INGRESO INVÁLIDO" );
            }
           
            //out.println("<h1>Servlet BuyProducts at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    private boolean ingresoValido(int aceite, int arroz, int manzana, int lenteja) {
        if(aceite < 1 || arroz < 1 || manzana < 1 || lenteja < 1)
            return false;
        return true;
    }
    private double calcularTotal(double aceite, double arroz, double manzana, double lenteja) {
        double total=aceite+arroz+manzana+lenteja;
        return total;
    }
    private String crearTabla(int numAceite, int numArroz, int numManzana, int numLenteja, double subtotalAceite, 
            double subtotalArroz, double subtotalManzana, double subtotalLenteja){
        String rpta="";
        rpta+="<table>";
        rpta+="    <tr>";
        rpta+="        <th>Producto</th>";
        rpta+="        <th>Cantidad</th>";
        rpta+="        <th>Precio Unitario</th>";
        rpta+="        <th>Subtotal</th>";
        rpta+="    </tr>";
        rpta+="    <tr>";
        rpta+="        <td>Aceite</td>";
        rpta+="        <td>"+numAceite+"</td>";
        rpta+="        <td>9.5</td>";
        rpta+="        <td>"+subtotalAceite+"</td>";
        rpta+="    </tr>";
        rpta+="    <tr>";
        rpta+="        <td>Arroz</td>";
        rpta+="        <td>"+numArroz+"</td>";
        rpta+="        <td>5.5</td>";
        rpta+="        <td>"+subtotalArroz+"</td>";
        rpta+="    </tr>";
        rpta+="    <tr>";
        rpta+="        <td>Manzana</td>";
        rpta+="        <td>"+numManzana+"</td>";
        rpta+="        <td>6.0</td>";
        rpta+="        <td>"+subtotalManzana+"</td>";
        rpta+="    </tr>";
        rpta+="    <tr>";
        rpta+="        <td>Lenteja</td>";
        rpta+="        <td>"+numLenteja+"</td>";
        rpta+="        <td>7.5</td>";
        rpta+="        <td>"+subtotalLenteja+"</td>";
        rpta+="    </tr>";
        rpta+="    <tr>";
        rpta+="        <td></td>";
        rpta+="        <td></td>";
        rpta+="        <td></td>";
        rpta+="        <td>"+calcularTotal(subtotalAceite, subtotalArroz, subtotalManzana, subtotalLenteja)+"</td>";
        rpta+="    </tr>";
        rpta+="</table>";
        return rpta;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
