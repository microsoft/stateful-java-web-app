/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.webapp.samples;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Tracker Servlet is the main entry page for
 * Cloud Scale Web App Session Management demo
 */
public class TrackerServlet extends HttpServlet {

    public static final Logger LOG = LogManager.getLogger(TrackerServlet.class);

    private String pageTitle = "Cloud Scale Web App Session Management";

    /**
     * Initialize the Servlet
     * @param config the <code>ServletConfig</code> object
     * that contains configuration
     * information for this servlet
     * @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.pageTitle = config.getInitParameter("pageTitle");
    }

    /**
     * Destroys the Servlet
     */
    public void destroy() {

    }

    /**
     * Processes requests for HTTP <code>GET</code> and <code>POST</code> methods
     * @param request Servlet Request
     * @param response Servlet Response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + this.pageTitle + "</title>");
        out.println("</head>");

        out.println("<body>");
        out.println("<font size='12'>");
        out.println(this.pageTitle);
        out.println("</font><br><br>");

        PageVisits pageVisits = getSessionObj(session);
        pageVisits.increment();

        LOG.info("=============================================");
        LOG.info("Page Visits = " + pageVisits.getValue());
        LOG.info("Session ID = " + session.getId());
        LOG.info("=============================================");

        out.println("<hr>");
        out.println("Number of Visits = <font size='14'>" + pageVisits.getValue()
                + "</font><br>");
        out.println("Session ID = " + session.getId() + "<br>");
        out.println("Session Creation Time = " + new Date(session.getCreationTime()) + "<br>");
        out.println("Session Last Access Time = " + new Date(session.getLastAccessedTime())
                + "<br>");
        out.println("Your IP Address = " + request.getRemoteAddr() + "<br>");

        StringBuffer buffer = new StringBuffer();
        buffer.append("<br>");
        buffer.append("<hr>");
        buffer.append("<br><br><br>");

        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            if (envName.startsWith("WEBSITE")) {
                buffer.append(String.format("%s = %s%n",
                        envName,
                        env.get(envName)));
                buffer.append("<br>");
            }
        }

        /**
         * WEBSITE_INSTANCE_ID = 33037ab24fcfe9052fca7b7786dc73bd9e70878172511068a5b343b2afee0f8b
         * WEBSITE_OWNER_NAME = 343129b6-cc9a-49f8-8b05-fbebbc594bd1+e2e-java-se-WestUSwebspace
         * WEBSITE_RESOURCE_GROUP = e2e-java-se
         * WEBSITE_ROLE_INSTANCE_ID = 0
         * WEBSITE_SITE_NAME = spring-todo-app
         */

        out.println(buffer.toString());

        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();

        out.println("<hr>");
        out.println("Java VM Name = " + runtime.getVmName() + "<br>");
        out.println("Java VM Vendor = " + runtime.getVmVendor() + "<br>");
        out.println("Java VM Version = " + runtime.getSpecVersion() + "<br>");
        out.println("Java VM Full Version = " + System.getProperty("java.runtime.version") + "<br>");
        out.println("<hr>");

        /**
        Properties properties = System.getProperties();
        buffer = new StringBuffer();
        for (Object propertyName : properties.keySet()) {
            if (((String)propertyName).startsWith("java")) {
                buffer.append(String.format("%s = %s%n",
                        propertyName,
                        properties.get(propertyName)));
                buffer.append("<br>");
            }
        }

        out.println(buffer.toString());
         */

        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private PageVisits getSessionObj(HttpSession session) {

        PageVisits pageVisits = (PageVisits)session.getAttribute("Analytics");
        if (pageVisits == null) {
            pageVisits = new PageVisits();
            session.setAttribute("Analytics", pageVisits);
        }

        return pageVisits;
    }

    /**
     * Get Servlet info
     * @return servlet info
     */
    public String getServletInfo() {
        return pageTitle;
    }

}
