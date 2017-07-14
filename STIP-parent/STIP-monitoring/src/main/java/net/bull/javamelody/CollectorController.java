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
package net.bull.javamelody; // NOPMD

import static net.bull.javamelody.HttpParameters.ACTION_PARAMETER;
import static net.bull.javamelody.HttpParameters.APPLICATIONS_PART;
import static net.bull.javamelody.HttpParameters.CACHE_ID_PARAMETER;
import static net.bull.javamelody.HttpParameters.CACHE_KEYS_PART;
import static net.bull.javamelody.HttpParameters.CACHE_KEY_PARAMETER;
import static net.bull.javamelody.HttpParameters.CLASS_PARAMETER;
import static net.bull.javamelody.HttpParameters.CONNECTIONS_PART;
import static net.bull.javamelody.HttpParameters.COUNTER_PARAMETER;
import static net.bull.javamelody.HttpParameters.COUNTER_SUMMARY_PER_CLASS_PART;
import static net.bull.javamelody.HttpParameters.CURRENT_REQUESTS_PART;
import static net.bull.javamelody.HttpParameters.DATABASE_PART;
import static net.bull.javamelody.HttpParameters.DEPENDENCIES_PART;
import static net.bull.javamelody.HttpParameters.EXPLAIN_PLAN_PART;
import static net.bull.javamelody.HttpParameters.FORMAT_PARAMETER;
import static net.bull.javamelody.HttpParameters.GRAPH_PARAMETER;
import static net.bull.javamelody.HttpParameters.HEAP_HISTO_PART;
import static net.bull.javamelody.HttpParameters.HOTSPOTS_PART;
import static net.bull.javamelody.HttpParameters.HTML_BODY_FORMAT;
import static net.bull.javamelody.HttpParameters.HTML_CONTENT_TYPE;
import static net.bull.javamelody.HttpParameters.JMX_VALUE;
import static net.bull.javamelody.HttpParameters.JNDI_PART;
import static net.bull.javamelody.HttpParameters.JOB_ID_PARAMETER;
import static net.bull.javamelody.HttpParameters.JROBINS_PART;
import static net.bull.javamelody.HttpParameters.JVM_PART;
import static net.bull.javamelody.HttpParameters.MBEANS_PART;
import static net.bull.javamelody.HttpParameters.OTHER_JROBINS_PART;
import static net.bull.javamelody.HttpParameters.PART_PARAMETER;
import static net.bull.javamelody.HttpParameters.PATH_PARAMETER;
import static net.bull.javamelody.HttpParameters.POM_XML_PART;
import static net.bull.javamelody.HttpParameters.PROCESSES_PART;
import static net.bull.javamelody.HttpParameters.REQUEST_PARAMETER;
import static net.bull.javamelody.HttpParameters.SESSIONS_PART;
import static net.bull.javamelody.HttpParameters.SESSION_ID_PARAMETER;
import static net.bull.javamelody.HttpParameters.SOURCE_PART;
import static net.bull.javamelody.HttpParameters.SPRING_BEANS_PART;
import static net.bull.javamelody.HttpParameters.THREADS_PART;
import static net.bull.javamelody.HttpParameters.THREAD_ID_PARAMETER;
import static net.bull.javamelody.HttpParameters.WEB_XML_PART;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bull.javamelody.SamplingProfiler.SampledMethod;

import org.apache.log4j.Logger;

/**
 * Contrôleur au sens MVC de l'ihm de monitoring dans le serveur collecte.
 * @author Emeric Vernat
 */
class CollectorController { // NOPMD
	private static final Logger LOGGER = Logger.getLogger("javamelody");

	private static final String COOKIE_NAME = "javamelody.application";

	private static final boolean CSRF_PROTECTION_ENABLED = Boolean
			.parseBoolean(Parameters.getParameter(Parameter.CSRF_PROTECTION_ENABLED));

	private final HttpCookieManager httpCookieManager = new HttpCookieManager();

	private final CollectorServer collectorServer;

	CollectorController(CollectorServer collectorServer) {
		super();
		assert collectorServer != null;
		this.collectorServer = collectorServer;
	}

	void addCollectorApplication(String appName, String appUrls) throws IOException {
		final File file = Parameters.getCollectorApplicationsFile();
		if (file.exists() && !file.canWrite()) {
			throw new IllegalStateException(
					"applications should be added or removed in the applications.properties file, because the user is not allowed to write: "
							+ file);
		}
		final List<URL> urls = Parameters.parseUrl(appUrls);
		collectorServer.addCollectorApplication(appName, urls);
	}

	void doMonitoring(HttpServletRequest req, HttpServletResponse resp, String application)
			throws IOException {
		try {
			final String actionParameter = req.getParameter(ACTION_PARAMETER);
			if (actionParameter != null) {
				if (CSRF_PROTECTION_ENABLED) {
					MonitoringController.checkCsrfToken(req);
				}
				final String messageForReport;
				if ("remove_application".equalsIgnoreCase(actionParameter)) {
					collectorServer.removeCollectorApplication(application);
					LOGGER.info("monitored application removed: " + application);
					messageForReport = I18N.getFormattedString("application_enlevee", application);
					showAlertAndRedirectTo(resp, messageForReport, "?");
					return;
				}

				final Collector collector = getCollectorByApplication(application);
				final MonitoringController monitoringController = new MonitoringController(
						collector, collectorServer);
				final Action action = Action.valueOfIgnoreCase(actionParameter);
				if (action != Action.CLEAR_COUNTER && action != Action.MAIL_TEST
						&& action != Action.PURGE_OBSOLETE_FILES) {
					// on forwarde l'action (gc, invalidate session(s) ou heap dump) sur l'application monitorée
					// et on récupère les informations à jour (notamment mémoire et nb de sessions)
					messageForReport = forwardActionAndUpdateData(req, application);
				} else {
					// nécessaire si action clear_counter
					messageForReport = monitoringController.executeActionIfNeeded(req);
				}

				if (TransportFormat.isATransportFormat(req.getParameter(FORMAT_PARAMETER))) {
					final SerializableController serializableController = new SerializableController(
							collector);
					final Range range = serializableController.getRangeForSerializable(req);
					final List<Object> serializable = new ArrayList<Object>();
					final List<JavaInformations> javaInformationsList = getJavaInformationsByApplication(
							application);
					serializable.addAll((List<?>) serializableController.createDefaultSerializable(
							javaInformationsList, range, messageForReport));
					monitoringController.doCompressedSerializable(req, resp,
							(Serializable) serializable);
				} else {
					writeMessage(req, resp, application, messageForReport);
				}
				return;
			}

			doReport(req, resp, application);
		} catch (final RuntimeException e) {
			// catch RuntimeException pour éviter warning exception
			writeMessage(req, resp, application, e.getMessage());
		} catch (final Exception e) {
			writeMessage(req, resp, application, e.getMessage());
		}
	}

	private void doReport(HttpServletRequest req, HttpServletResponse resp, String application)
			throws IOException, ServletException {
		final Collector collector = getCollectorByApplication(application);
		final MonitoringController monitoringController = new MonitoringController(collector,
				collectorServer);
		final String partParameter = req.getParameter(PART_PARAMETER);
		final String formatParameter = req.getParameter(FORMAT_PARAMETER);
		if (req.getParameter(JMX_VALUE) != null) {
			doJmxValue(req, resp, application, req.getParameter(JMX_VALUE));
		} else if (TransportFormat.isATransportFormat(formatParameter)) {
			doCompressedSerializable(req, resp, application, monitoringController);
		} else if (partParameter == null || "pdf".equalsIgnoreCase(formatParameter)) {
			// la récupération de javaInformationsList doit être après forwardActionAndUpdateData
			// pour être à jour
			final List<JavaInformations> javaInformationsList = getJavaInformationsByApplication(
					application);
			monitoringController.doReport(req, resp, javaInformationsList);
		} else {
			doCompressedPart(req, resp, application, monitoringController, partParameter);
		}
	}

	private void doCompressedPart(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String application, MonitoringController monitoringController, String partParameter)
			throws IOException, ServletException {
		if (MonitoringController.isCompressionSupported(httpRequest)) {
			// comme la page html peut être volumineuse
			// on compresse le flux de réponse en gzip à partir de 4 Ko
			// (à moins que la compression http ne soit pas supportée
			// comme par ex s'il y a un proxy squid qui ne supporte que http 1.0)
			final CompressionServletResponseWrapper wrappedResponse = new CompressionServletResponseWrapper(
					httpResponse, 4096);
			try {
				doPart(httpRequest, wrappedResponse, application, monitoringController,
						partParameter);
			} finally {
				wrappedResponse.finishResponse();
			}
		} else {
			doPart(httpRequest, httpResponse, application, monitoringController, partParameter);
		}
	}

	private void doPart(HttpServletRequest req, HttpServletResponse resp, String application,
			MonitoringController monitoringController, String partParameter)
			throws IOException, ServletException {
		if (WEB_XML_PART.equalsIgnoreCase(partParameter)
				|| POM_XML_PART.equalsIgnoreCase(partParameter)
				|| DEPENDENCIES_PART.equalsIgnoreCase(partParameter)
				|| SPRING_BEANS_PART.equalsIgnoreCase(partParameter)) {
			noCache(resp);
			doProxy(req, resp, application, partParameter);
		} else if (SOURCE_PART.equalsIgnoreCase(partParameter)) {
			noCache(resp);
			doProxy(req, resp, application, partParameter + '&' + CLASS_PARAMETER + '='
					+ req.getParameter(CLASS_PARAMETER));
		} else if (CONNECTIONS_PART.equalsIgnoreCase(partParameter)) {
			doMultiHtmlProxy(req, resp, application, CONNECTIONS_PART,
					I18N.getString("Connexions_jdbc_ouvertes"), I18N.getString("connexions_intro"),
					"db.png");
		} else if (CACHE_KEYS_PART.equalsIgnoreCase(partParameter)) {
			// note: cache keys may not be serializable, so we do not try to serialize them
			final String cacheId = req.getParameter(CACHE_ID_PARAMETER);
			doMultiHtmlProxy(req, resp, application,
					CACHE_KEYS_PART + '&' + CACHE_ID_PARAMETER + '=' + cacheId,
					I18N.getFormattedString("Keys_cache", cacheId), null, "caches.png");
		} else {
			final List<JavaInformations> javaInformationsList = getJavaInformationsByApplication(
					application);
			monitoringController.doReport(req, resp, javaInformationsList);
		}
	}

	private void doJmxValue(HttpServletRequest req, HttpServletResponse resp, String application,
			String jmxValueParameter) throws IOException {
		noCache(resp);
		resp.setContentType("text/plain");
		boolean first = true;
		for (final URL url : getUrlsByApplication(application)) {
			if (first) {
				first = false;
			} else {
				resp.getOutputStream().write('|');
				resp.getOutputStream().write('|');
			}
			final URL proxyUrl = new URL(
					url.toString().replace(TransportFormat.SERIALIZED.getCode(), "")
							.replace(TransportFormat.XML.getCode(), "") + '&' + JMX_VALUE + '='
							+ jmxValueParameter);
			new LabradorRetriever(proxyUrl).copyTo(req, resp);
		}
		resp.getOutputStream().close();
	}

	private void doProxy(HttpServletRequest req, HttpServletResponse resp, String application,
			String partParameter) throws IOException {
		// récupération à la demande du contenu du web.xml de la webapp monitorée
		// (et non celui du serveur de collecte),
		// on prend la 1ère url puisque le contenu de web.xml est censé être le même
		// dans tout l'éventuel cluster
		final URL url = getUrlsByApplication(application).get(0);
		// on récupère le contenu du web.xml sur la webapp et on transfert ce contenu
		final URL proxyUrl = new URL(
				url.toString().replace(TransportFormat.SERIALIZED.getCode(), HTML_BODY_FORMAT)
						.replace(TransportFormat.XML.getCode(), HTML_BODY_FORMAT) + '&'
						+ PART_PARAMETER + '=' + partParameter);
		new LabradorRetriever(proxyUrl).copyTo(req, resp);
	}

	private void doMultiHtmlProxy(HttpServletRequest req, HttpServletResponse resp,
			String application, String partParameter, String title, String introduction,
			String iconName) throws IOException {
		final PrintWriter writer = createWriterFromOutputStream(resp);
		final HtmlReport htmlReport = createHtmlReport(req, resp, writer, application);
		htmlReport.writeHtmlHeader();
		writer.write("<div class='noPrint'>");
		I18N.writelnTo(
				"<a href='javascript:history.back()'><img src='?resource=action_back.png' alt='#Retour#'/> #Retour#</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
				writer);
		writer.write("<a href='?part=");
		writer.write(partParameter);
		writer.write("'>");
		I18N.writelnTo("<img src='?resource=action_refresh.png' alt='#Actualiser#'/> #Actualiser#",
				writer);
		writer.write("</a></div>");
		if (introduction != null) {
			writer.write("<br/>");
			writer.write(I18N.htmlEncode(introduction, false));
		}
		for (final URL url : getUrlsByApplication(application)) {
			final String htmlTitle = "<h3 class='chapterTitle'><img src='?resource=" + iconName
					+ "' alt='" + I18N.urlEncode(title) + "'/>&nbsp;"
					+ I18N.htmlEncode(title, false) + " (" + getHostAndPort(url) + ")</h3>";
			writer.write(htmlTitle);
			writer.flush(); // flush du buffer de writer, sinon le copyTo passera avant dans l'outputStream
			final URL proxyUrl = new URL(
					url.toString().replace(TransportFormat.SERIALIZED.getCode(), HTML_BODY_FORMAT)
							.replace(TransportFormat.XML.getCode(), HTML_BODY_FORMAT) + '&'
							+ PART_PARAMETER + '=' + partParameter);
			new LabradorRetriever(proxyUrl).copyTo(req, resp);
		}
		htmlReport.writeHtmlFooter();
		writer.close();
	}

	private void doCompressedSerializable(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, String application,
			MonitoringController monitoringController) throws IOException {
		Serializable serializable;
		try {
			serializable = createSerializable(httpRequest, application);
		} catch (final Exception e) {
			serializable = e;
		}
		monitoringController.doCompressedSerializable(httpRequest, httpResponse, serializable);
	}

	private Serializable createSerializable(HttpServletRequest httpRequest, String application)
			throws Exception { // NOPMD
		final Serializable resultForSystemActions = createSerializableForSystemActions(httpRequest,
				application);
		if (resultForSystemActions != null) {
			return resultForSystemActions;
		}

		final Collector collector = getCollectorByApplication(application);
		final SerializableController serializableController = new SerializableController(collector);
		final Range range = serializableController.getRangeForSerializable(httpRequest);
		final String part = httpRequest.getParameter(PART_PARAMETER);
		if (THREADS_PART.equalsIgnoreCase(part)) {
			return new ArrayList<List<ThreadInformations>>(
					collectorServer.getThreadInformationsLists(application));
		} else if (CURRENT_REQUESTS_PART.equalsIgnoreCase(part)) {
			return new LinkedHashMap<JavaInformations, List<CounterRequestContext>>(
					collectorServer.collectCurrentRequests(application));
		} else if (EXPLAIN_PLAN_PART.equalsIgnoreCase(part)) {
			final String sqlRequest = httpRequest.getHeader(REQUEST_PARAMETER);
			return collectorServer.collectSqlRequestExplainPlan(application, sqlRequest);
		} else if (COUNTER_SUMMARY_PER_CLASS_PART.equalsIgnoreCase(part)) {
			final String counterName = httpRequest.getParameter(COUNTER_PARAMETER);
			final String requestId = httpRequest.getParameter(GRAPH_PARAMETER);
			final Counter counter = collector.getRangeCounter(range, counterName);
			final List<CounterRequest> requestList = new CounterRequestAggregation(counter)
					.getRequestsAggregatedOrFilteredByClassName(requestId);
			return new ArrayList<CounterRequest>(requestList);
		} else if (APPLICATIONS_PART.equalsIgnoreCase(part)) {
			// list all applications, with last exceptions if not available,
			// use ?part=applications&format=json for example
			final Map<String, Throwable> applications = new HashMap<String, Throwable>();
			for (final String app : Parameters.getCollectorUrlsByApplications().keySet()) {
				applications.put(app, null);
			}
			applications.putAll(collectorServer.getLastCollectExceptionsByApplication());
			return new HashMap<String, Throwable>(applications);
		} else if (JROBINS_PART.equalsIgnoreCase(part)
				|| OTHER_JROBINS_PART.equalsIgnoreCase(part)) {
			// pour UI Swing
			return serializableController.createSerializable(httpRequest, null, null);
		}

		final List<JavaInformations> javaInformationsList = getJavaInformationsByApplication(
				application);
		return serializableController.createDefaultSerializable(javaInformationsList, range, null);
	}

	// CHECKSTYLE:OFF
	private Serializable createSerializableForSystemActions(HttpServletRequest httpRequest,
			String application) throws IOException {
		// CHECKSTYLE:ON
		final String part = httpRequest.getParameter(PART_PARAMETER);
		if (JVM_PART.equalsIgnoreCase(part)) {
			final List<JavaInformations> javaInformationsList = getJavaInformationsByApplication(
					application);
			return new ArrayList<JavaInformations>(javaInformationsList);
		} else if (HEAP_HISTO_PART.equalsIgnoreCase(part)) {
			// par sécurité
			Action.checkSystemActionsEnabled();
			return collectorServer.collectHeapHistogram(application);
		} else if (SESSIONS_PART.equalsIgnoreCase(part)) {
			// par sécurité
			Action.checkSystemActionsEnabled();
			final String sessionId = httpRequest.getParameter(SESSION_ID_PARAMETER);
			final List<SessionInformations> sessionInformations = collectorServer
					.collectSessionInformations(application, sessionId);
			if (sessionId != null && !sessionInformations.isEmpty()) {
				return sessionInformations.get(0);
			}
			return new ArrayList<SessionInformations>(sessionInformations);
		} else if (HOTSPOTS_PART.equalsIgnoreCase(part)) {
			// par sécurité
			Action.checkSystemActionsEnabled();
			return new ArrayList<SampledMethod>(collectorServer.collectHotspots(application));
		} else if (PROCESSES_PART.equalsIgnoreCase(part)) {
			// par sécurité
			Action.checkSystemActionsEnabled();
			return new LinkedHashMap<String, List<ProcessInformations>>(
					collectorServer.collectProcessInformations(application));
		} else if (JNDI_PART.equalsIgnoreCase(part)) {
			// par sécurité
			Action.checkSystemActionsEnabled();
			final String path = httpRequest.getParameter(PATH_PARAMETER);
			return new ArrayList<JndiBinding>(
					collectorServer.collectJndiBindings(application, path));
		} else if (MBEANS_PART.equalsIgnoreCase(part)) {
			// par sécurité
			Action.checkSystemActionsEnabled();
			return new LinkedHashMap<String, List<MBeanNode>>(
					collectorServer.collectMBeans(application));
		} else if (DATABASE_PART.equalsIgnoreCase(part)) {
			// par sécurité
			Action.checkSystemActionsEnabled();
			final int requestIndex = DatabaseInformations
					.parseRequestIndex(httpRequest.getParameter(REQUEST_PARAMETER));
			return collectorServer.collectDatabaseInformations(application, requestIndex);
		} else if (CONNECTIONS_PART.equalsIgnoreCase(part)) {
			// par sécurité
			Action.checkSystemActionsEnabled();
			return new ArrayList<List<ConnectionInformations>>(
					collectorServer.collectConnectionInformations(application));
		}
		return null;
	}

	private HtmlReport createHtmlReport(HttpServletRequest req, HttpServletResponse resp,
			PrintWriter writer, String application) {
		final Range range = httpCookieManager.getRange(req, resp);
		final Collector collector = getCollectorByApplication(application);
		final List<JavaInformations> javaInformationsList = getJavaInformationsByApplication(
				application);
		return new HtmlReport(collector, collectorServer, javaInformationsList, range, writer);
	}

	private static String getHostAndPort(URL url) {
		return RemoteCollector.getHostAndPort(url);
	}

	void writeMessage(HttpServletRequest req, HttpServletResponse resp, String application,
			String message) throws IOException {
		noCache(resp);
		final Collector collector = getCollectorByApplication(application);
		final List<JavaInformations> javaInformationsList = getJavaInformationsByApplication(
				application);
		if (application == null || collector == null || javaInformationsList == null) {
			showAlertAndRedirectTo(resp, message, "?");
		} else {
			final PrintWriter writer = createWriterFromOutputStream(resp);
			final String partToRedirectTo;
			if (req.getParameter(CACHE_ID_PARAMETER) == null) {
				partToRedirectTo = req.getParameter(PART_PARAMETER);
			} else {
				partToRedirectTo = req.getParameter(PART_PARAMETER) + '&' + CACHE_ID_PARAMETER + '='
						+ req.getParameter(CACHE_ID_PARAMETER);
			}
			// la période n'a pas d'importance pour writeMessageIfNotNull
			new HtmlReport(collector, collectorServer, javaInformationsList, Period.TOUT, writer)
					.writeMessageIfNotNull(message, partToRedirectTo);
			writer.close();
		}
	}

	private static PrintWriter createWriterFromOutputStream(HttpServletResponse httpResponse)
			throws IOException {
		noCache(httpResponse);
		httpResponse.setContentType(HTML_CONTENT_TYPE);
		return new PrintWriter(MonitoringController.getWriter(httpResponse));
	}

	static void writeOnlyAddApplication(HttpServletResponse resp) throws IOException {
		noCache(resp);
		resp.setContentType(HTML_CONTENT_TYPE);
		final PrintWriter writer = createWriterFromOutputStream(resp);
		writer.write("<html lang='" + I18N.getCurrentLocale().getLanguage()
				+ "'><head><title>Monitoring</title></head><body>");
		HtmlReport.writeAddAndRemoveApplicationLinks(null, writer);
		writer.write("</body></html>");
		writer.close();
	}

	static void writeDataUnavailableForApplication(String application, HttpServletResponse resp)
			throws IOException {
		noCache(resp);
		resp.setContentType(HTML_CONTENT_TYPE);
		final PrintWriter writer = createWriterFromOutputStream(resp);
		writer.write("<html lang='" + I18N.getCurrentLocale().getLanguage()
				+ "'><head><title>Monitoring</title></head><body>");
		writer.write(
				I18N.htmlEncode(I18N.getFormattedString("data_unavailable", application), false));
		writer.write("<br/><br/>");
		writer.write("<a href='?'><img src='?resource=action_back.png' alt=\""
				+ I18N.getString("Retour") + "\"/> " + I18N.getString("Retour") + "</a>");
		if (Parameters.getCollectorApplicationsFile().canWrite()) {
			writer.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			writer.write("<a href='?action=remove_application&amp;application="
					+ I18N.urlEncode(application) + HtmlAbstractReport.getCsrfTokenUrlPart()
					+ "' ");
			final String messageConfirmation = I18N.getFormattedString("confirm_remove_application",
					application);
			writer.write("onclick=\"javascript:return confirm('"
					+ I18N.javascriptEncode(messageConfirmation) + "');\">");
			final String removeApplicationLabel = I18N.getFormattedString("remove_application",
					application);
			writer.write("<img src='?resource=action_delete.png' alt=\"" + removeApplicationLabel
					+ "\"/> " + removeApplicationLabel + "</a>");
		}
		writer.write("</body></html>");
		writer.close();
	}

	static void showAlertAndRedirectTo(HttpServletResponse resp, String message, String redirectTo)
			throws IOException {
		resp.setContentType(HTML_CONTENT_TYPE);
		final PrintWriter writer = createWriterFromOutputStream(resp);
		writer.write("<script type='text/javascript'>alert('");
		writer.write(I18N.javascriptEncode(message));
		writer.write("');location.href='");
		writer.write(redirectTo);
		writer.write("';</script>");
		writer.close();
	}

	private static void noCache(HttpServletResponse httpResponse) {
		MonitoringController.noCache(httpResponse);
	}

	private String forwardActionAndUpdateData(HttpServletRequest req, String application)
			throws IOException {
		final String actionParameter = req.getParameter(ACTION_PARAMETER);
		final String sessionIdParameter = req.getParameter(SESSION_ID_PARAMETER);
		final String threadIdParameter = req.getParameter(THREAD_ID_PARAMETER);
		final String jobIdParameter = req.getParameter(JOB_ID_PARAMETER);
		final String cacheIdParameter = req.getParameter(CACHE_ID_PARAMETER);
		final String cacheKeyParameter = req.getParameter(CACHE_KEY_PARAMETER);
		final List<URL> urls = getUrlsByApplication(application);
		final List<URL> actionUrls = new ArrayList<URL>(urls.size());
		for (final URL url : urls) {
			final StringBuilder actionUrl = new StringBuilder(url.toString());
			actionUrl.append("&action=").append(actionParameter);
			if (sessionIdParameter != null) {
				actionUrl.append("&sessionId=").append(sessionIdParameter);
			}
			if (threadIdParameter != null) {
				actionUrl.append("&threadId=").append(threadIdParameter);
			}
			if (jobIdParameter != null) {
				actionUrl.append("&jobId=").append(jobIdParameter);
			}
			if (cacheIdParameter != null) {
				actionUrl.append("&cacheId=").append(cacheIdParameter);
			}
			if (cacheKeyParameter != null) {
				actionUrl.append("&cacheKey=").append(cacheKeyParameter);
			}
			actionUrls.add(new URL(actionUrl.toString()));
		}
		return collectorServer.collectForApplicationForAction(application, actionUrls);
	}

	String getApplication(HttpServletRequest req, HttpServletResponse resp) {
		// on utilise un cookie client pour stocker l'application
		// car la page html est faite pour une seule application sans passer son nom en paramètre des requêtes
		// et pour ne pas perdre l'application choisie entre les reconnexions
		String application = req.getParameter("application");
		if (application == null) {
			// pas de paramètre application dans la requête, on cherche le cookie
			final Cookie cookie = httpCookieManager.getCookieByName(req, COOKIE_NAME);
			if (cookie != null) {
				application = cookie.getValue();
				if (!collectorServer.isApplicationDataAvailable(application)) {
					cookie.setMaxAge(-1);
					resp.addCookie(cookie);
					application = null;
				}
			}
			if (application == null) {
				// pas de cookie, on prend la première application si elle existe
				application = collectorServer.getFirstApplication();
			}
		} else if (collectorServer.isApplicationDataAvailable(application)) {
			// un paramètre application est présent dans la requête: l'utilisateur a choisi une application,
			// donc on fixe le cookie
			httpCookieManager.addCookie(req, resp, COOKIE_NAME, String.valueOf(application));
		}
		return application;
	}

	private Collector getCollectorByApplication(String application) {
		return collectorServer.getCollectorByApplication(application);
	}

	private List<JavaInformations> getJavaInformationsByApplication(String application) {
		return collectorServer.getJavaInformationsByApplication(application);
	}

	private static List<URL> getUrlsByApplication(String application) throws IOException {
		return CollectorServer.getUrlsByApplication(application);
	}
}
