/*
 * Copyright 2008-2017 by Emeric Vernat
 *
 *     This file is part of Java Melody.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.bull.javamelody;

import static net.bull.javamelody.HttpParameters.ACTION_PARAMETER;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Servlet de collecte utilisée uniquement pour serveur de collecte séparé de l'application monitorée.
 * @author Emeric Vernat
 */
public class CollectorServlet extends HttpServlet {
	private static final long serialVersionUID = -2070469677921953224L;

	@SuppressWarnings("all")
	private static final Logger LOGGER = Logger.getLogger("javamelody");

	@SuppressWarnings("all")
	private transient HttpAuth httpAuth;

	@SuppressWarnings("all")
	private transient CollectorServer collectorServer;

	/** {@inheritDoc} */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Parameters.initialize(config.getServletContext());
		if (!Boolean.parseBoolean(Parameters.getParameter(Parameter.LOG))) {
			// si log désactivé dans serveur de collecte,
			// alors pas de log, comme dans webapp
			LOGGER.setLevel(Level.WARN);
		}
		// dans le serveur de collecte, on est sûr que log4j est disponible
		LOGGER.info("initialization of the collector servlet of the monitoring");

		httpAuth = new HttpAuth();

		try {
			collectorServer = new CollectorServer();
		} catch (final IOException e) {
			throw new ServletException(e.getMessage(), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (!httpAuth.isAllowed(req, resp)) {
			return;
		}

		final long start = System.currentTimeMillis();
		final CollectorController collectorController = new CollectorController(collectorServer);
		final String application = collectorController.getApplication(req, resp);
		I18N.bindLocale(req.getLocale());
		try {
			if (application == null) {
				CollectorController.writeOnlyAddApplication(resp);
				return;
			}
			if (!collectorServer.isApplicationDataAvailable(application)
					&& req.getParameter(ACTION_PARAMETER) == null) {
				CollectorController.writeDataUnavailableForApplication(application, resp);
				return;
			}
			collectorController.doMonitoring(req, resp, application);
		} finally {
			I18N.unbindLocale();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("monitoring from " + req.getRemoteAddr() + ", request="
						+ req.getRequestURI()
						+ (req.getQueryString() != null ? '?' + req.getQueryString() : "")
						+ ", application=" + application + " in "
						+ (System.currentTimeMillis() - start) + "ms");
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (!httpAuth.isAllowed(req, resp)) {
			return;
		}

		// post du formulaire d'ajout d'application à monitorer
		final String appName = req.getParameter("appName");
		final String appUrls = req.getParameter("appUrls");
		I18N.bindLocale(req.getLocale());
		final CollectorController collectorController = new CollectorController(collectorServer);
		try {
			if (appName == null || appUrls == null) {
				writeMessage(req, resp, collectorController, I18N.getString("donnees_manquantes"));
				return;
			}
			if (!appUrls.startsWith("http://") && !appUrls.startsWith("https://")) {
				writeMessage(req, resp, collectorController, I18N.getString("urls_format"));
				return;
			}
			collectorController.addCollectorApplication(appName, appUrls);
			LOGGER.info("monitored application added: " + appName);
			LOGGER.info("urls of the monitored application: " + appUrls);
			CollectorController.showAlertAndRedirectTo(resp,
					I18N.getFormattedString("application_ajoutee", appName),
					"?application=" + appName);
		} catch (final FileNotFoundException e) {
			final String message = I18N.getString("monitoring_configure");
			LOGGER.warn(message, e);
			writeMessage(req, resp, collectorController, message + '\n' + e.toString());
		} catch (final StreamCorruptedException e) {
			final String message = I18N.getFormattedString("reponse_non_comprise", appUrls);
			LOGGER.warn(message, e);
			writeMessage(req, resp, collectorController, message + '\n' + e.toString());
		} catch (final Exception e) {
			LOGGER.warn(e.toString(), e);
			writeMessage(req, resp, collectorController, e.toString());
		} finally {
			I18N.unbindLocale();
		}
	}

	private void writeMessage(HttpServletRequest req, HttpServletResponse resp,
			CollectorController collectorController, String message) throws IOException {
		collectorController.writeMessage(req, resp, collectorController.getApplication(req, resp),
				message);
	}

	/** {@inheritDoc} */
	@Override
	public void destroy() {
		LOGGER.info("collector servlet stopping");
		if (collectorServer != null) {
			collectorServer.stop();
		}
		Collector.stopJRobin();
		LOGGER.info("collector servlet stopped");
		super.destroy();
	}
}
