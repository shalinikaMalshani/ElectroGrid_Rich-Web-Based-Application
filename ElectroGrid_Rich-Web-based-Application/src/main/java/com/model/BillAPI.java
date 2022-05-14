package com.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BillAPI
 */
@WebServlet("/BillAPI")
public class BillAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Bill billObj=new Bill();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BillAPI() {
        super();
        
    }

	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String output = billObj.insertBill(request.getParameter("billCode"),
				request.getParameter("customerID"),
				request.getParameter("month"),
				request.getParameter("units"),
				request.getParameter("KWHCharge"),
				request.getParameter("fixedCharge"),
				request.getParameter("rebate"),
				request.getParameter("total"));
				response.getWriter().write(output);
	
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map paras = getParasMap(request);
		String output = billObj.updateBill(paras.get("hidItemIDSave").toString(),
		paras.get("billCode").toString(),
		paras.get("customerID").toString(),
		paras.get("month").toString(),
		paras.get("units").toString(),
		paras.get("KWHCharge").toString(),
		paras.get("fixedCharge").toString(),
		paras.get("rebate").toString(),
		paras.get("total").toString());
		response.getWriter().write(output);
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map paras = getParasMap(request);
		String output = billObj.deleteBill(paras.get("billId").toString());
		response.getWriter().write(output);
	}
	
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	Map paras = getParasMap(request);
	String output = billObj.getBill(request.getParameter("billCode"));
	response.getWriter().write(output);

	}

	
		// Convert request parameters to a Map
		private static Map getParasMap(HttpServletRequest request)
		{
			Map<String, String> map = new HashMap<String, String>();
			try
			{
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
				String queryString = scanner.hasNext() ?
				scanner.useDelimiter("\\A").next() : "";
				scanner.close();
				String[] params = queryString.split("&");
				for (String param : params)
				{
					String[] p = param.split("=");
					map.put(p[0], p[1]);
				}
			}
			catch (Exception e)
			{
			}
			return map;
		}
		

}
