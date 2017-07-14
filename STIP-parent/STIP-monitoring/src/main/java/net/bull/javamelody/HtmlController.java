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

import static net.bull.javamelody.HttpParameters.CACHE_ID_PARAMETER;
import static net.bull.javamelody.HttpParameters.CACHE_KEYS_PART;
import static net.bull.javamelody.HttpParameters.CLASS_PARAMETER;
import static net.bull.javamelody.HttpParameters.CONNECTIONS_PART;
import static net.bull.javamelody.HttpParameters.COUNTER_PARAMETER;
import static net.bull.javamelody.HttpParameters.COUNTER_SUMMARY_PER_CLASS_PART;
import static net.bull.javamelody.HttpParameters.CURRENT_REQUESTS_PART;
import static net.bull.javamelody.HttpParameters.DATABASE_PART;
import static net.bull.javamelody.HttpParameters.DEPENDENCIES_PART;
import static net.bull.javamelody.HttpParameters.FORMAT_PARAMETER;
import static net.bull.javamelody.HttpParameters.GRAPH_PARAMETER;
import static net.bull.javamelody.HttpParameters.GRAPH_PART;
import static net.bull.javamelody.HttpParameters.HEAP_HISTO_PART;
import static net.bull.javamelody.HttpParameters.HOTSPOTS_PART;
import static net.bull.javamelody.HttpParameters.HTML_BODY_FORMAT;
import static net.bull.javamelody.HttpParameters.HTML_CHARSET;
import static net.bull.javamelody.HttpParameters.HTML_CONTENT_TYPE;
import static net.bull.javamelody.HttpParameters.JNDI_PART;
import static net.bull.javamelody.HttpParameters.MBEANS_PART;
import static net.bull.javamelody.HttpParameters.PART_PARAMETER;
import static net.bull.javamelody.HttpParameters.PATH_PARAMETER;
import static net.bull.javamelody.HttpParameters.PROCESSES_PART;
import static net.bull.javamelody.HttpParameters.REQUEST_PARAMETER;
import static net.bull.javamelody.HttpParameters.SESSIONS_PART;
import static net.bull.javamelody.HttpParameters.SESSION_ID_PARAMETER;
import static net.bull.javamelody.HttpParameters.SOURCE_PART;
import static net.bull.javamelody.HttpParameters.SPRING_BEANS_PART;
import static net.bull.javamelody.HttpParameters.TEXT_CONTENT_TYPE;
import static net.bull.javamelody.HttpParameters.THREADS_DUMP_PART;
import static net.bull.javamelody.HttpParameters.THREADS_PART;
import static net.bull.javamelody.HttpParameters.USAGES_PART;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bull.javamelody.SamplingProfiler.SampledMethod;

/**
 * Contrôleur au sens MVC de l'ihm de monitoring pour la partie html.
 * @author Emeric Vernat
 */
class HtmlController {
	private final HttpCookieManager httpCookieManager = new HttpCookieManager();
	private final Collector collector;
	private final CollectorServer collectorServer;
	private final String messageForReport;
	private final String anchorNameForRedirect;

	HtmlController(Collector collector, CollectorServer collectorServer, String messageForReport,
			String anchorNameForRedirect) {
		super();
		assert collector != null;
		this.collector = collector;
		this.collectorServer = collectorServer;
		this.messageForReport = messageForReport;
		this.anchorNameForRedirect = anchorNameForRedirect;
	}

	void doHtml(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			List<JavaInformations> javaInformationsList) throws IOException {
		final String part = httpRequest.getParameter(PART_PARAMETER);
		if (!isFromCollectorServer() && isLocalCollectNeeded(part) && !collector.isStopped()) {
			// avant de faire l'affichage on fait une collecte, pour que les courbes
			// et les compteurs par jour soit à jour avec les dernières requêtes,
			// sauf si c'est un serveur de collecte
			// ou si la page de monitoring d'une webapp monitorée par un serveur de collecte est appelée par erreur
			collector.collectLocalContextWithoutErrors();
		}

		// simple appel de monitoring sans format
		httpResponse.setContentType(HTML_CONTENT_TYPE);
		final BufferedWriter writer = getWriter(httpResponse);
		try {
			final Range range = httpCookieManager.getRange(httpRequest, httpResponse);
			final HtmlReport htmlReport = new HtmlReport(collector, collectorServer,
					javaInformationsList, range, writer);
			if (part == null) {
				htmlReport.toHtml(messageForReport, anchorNameForRedirect);
			} else if (THREADS_DUMP_PART.equalsIgnoreCase(part)) {
				httpResponse.setContentType(TEXT_CONTENT_TYPE);
				htmlReport.writeThreadsDump();
			} else {
				doHtmlPart(httpRequest, part, htmlReport);
			}
		} finally {
			writer.close();
		}
	}

	static boolean isLocalCollectNeeded(final String part) {
		return part == null || CURRENT_REQUESTS_PART.equalsIgnoreCase(part)
				|| GRAPH_PART.equalsIgnoreCase(part)
				|| COUNTER_SUMMARY_PER_CLASS_PART.equalsIgnoreCase(part);
	}

	static BufferedWriter getWriter(HttpServletResponse httpResponse) throws IOException {
		return new BufferedWriter(
				new OutputStreamWriter(httpResponse.getOutputStream(), HTML_CHARSET));
	}

	private void doHtmlPart(HttpServletRequest httpRequest, String part, HtmlReport htmlReport)
			throws IOException {
		if (GRAPH_PART.equalsIgnoreCase(part)) {
			final String graphName = httpRequest.getParameter(GRAPH_PARAMETER);
			htmlReport.writeRequestAndGraphDetail(graphName);
		} else if (USAGES_PART.equalsIgnoreCase(part)) {
			final String graphName = httpRequest.getParameter(GRAPH_PARAMETER);
			htmlReport.writeRequestUsages(graphName);
		} else if (CURRENT_REQUESTS_PART.equalsIgnoreCase(part)) {
			htmlReport.writeAllCurrentRequestsAsPart();
		} else if (THREADS_PART.equalsIgnoreCase(part)) {
			htmlReport.writeAllThreadsAsPart();
		} else if (COUNTER_SUMMARY_PER_CLASS_PART.equalsIgnoreCase(part)) {
			final String counterName = httpRequest.getParameter(COUNTER_PARAMETER);
			final String requestId = httpRequest.getParameter(GRAPH_PARAMETER);
			htmlReport.writeCounterSummaryPerClass(counterName, requestId);
		} else if (SOURCE_PART.equalsIgnoreCase(part)) {
			final String className = httpRequest.getParameter(CLASS_PARAMETER);
			htmlReport.writeSource(className);
		} else if (DEPENDENCIES_PART.equalsIgnoreCase(part)) {
			htmlReport.writeDependencies();
		} else if (CACHE_KEYS_PART.equalsIgnoreCase(part)) {
			final boolean withoutHeaders = HTML_BODY_FORMAT
					.equalsIgnoreCase(httpRequest.getParameter(FORMAT_PARAMETER));
			doCacheKeys(htmlReport, httpRequest.getParameter(CACHE_ID_PARAMETER), withoutHeaders);
		} else {
			doHtmlPartForSystemActions(httpRequest, part, htmlReport);
		}
	}

	private void doHtmlPartForSystemActions(HttpServletRequest httpRequest, String part,
			HtmlReport htmlReport) throws IOException {
		if (SESSIONS_PART.equalsIgnoreCase(part)) {
			doSessions(htmlReport, httpRequest.getParameter(SESSION_ID_PARAMETER));
		} else if (HOTSPOTS_PART.equalsIgnoreCase(part)) {
			doHotspots(htmlReport);
		} else if (HEAP_HISTO_PART.equalsIgnoreCase(part)) {
			doHeapHisto(htmlReport);
		} else if (PROCESSES_PART.equalsIgnoreCase(part)) {
			doProcesses(htmlReport);
		} else if (DATABASE_PART.equalsIgnoreCase(part)) {
			final int requestIndex = DatabaseInformations
					.parseRequestIndex(httpRequest.getParameter(REQUEST_PARAMETER));
			doDatabase(htmlReport, requestIndex);
		} else if (CONNECTIONS_PART.equalsIgnoreCase(part)) {
			final boolean withoutHeaders = HTML_BODY_FORMAT
					.equalsIgnoreCase(httpRequest.getParameter(FORMAT_PARAMETER));
			doConnections(htmlReport, withoutHeaders);
		} else if (JNDI_PART.equalsIgnoreCase(part)) {
			doJndi(htmlReport, httpRequest.getParameter(PATH_PARAMETER));
		} else if (MBEANS_PART.equalsIgnoreCase(part)) {
			doMBeans(htmlReport);
		} else if (SPRING_BEANS_PART.equalsIgnoreCase(part)) {
			htmlReport.writeSpringContext();
		} else {
			throw new IllegalArgumentException(part);
		}
	}

	private void doSessions(HtmlReport htmlReport, String sessionId) throws IOException {
		// par sécurité
		Action.checkSystemActionsEnabled();
		final List<SessionInformations> sessionsInformations;
		if (!isFromCollectorServer()) {
			if (sessionId == null) {
				sessionsInformations = SessionListener.getAllSessionsInformations();
			} else {
				sessionsInformations = Collections.singletonList(
						SessionListener.getSessionInformationsBySessionId(sessionId));
			}
		} else {
			sessionsInformations = collectorServer.collectSessionInformations(getApplication(),
					sessionId);
		}
		if (sessionId == null || sessionsInformations.isEmpty()) {
			htmlReport.writeSessions(sessionsInformations, messageForReport, SESSIONS_PART);
		} else {
			final SessionInformations sessionInformation = sessionsInformations.get(0);
			htmlReport.writeSessionDetail(sessionId, sessionInformation);
		}
	}

	private void doHotspots(HtmlReport htmlReport) throws IOException {
		// par sécurité
		Action.checkSystemActionsEnabled();
		if (!isFromCollectorServer()) {
			final List<SampledMethod> hotspots = collector.getHotspots();
			htmlReport.writeHotspots(hotspots);
		} else {
			final List<SampledMethod> hotspots = collectorServer.collectHotspots(getApplication());
			htmlReport.writeHotspots(hotspots);
		}
	}

	private void doHeapHisto(HtmlReport htmlReport) throws IOException {
		// par sécurité
		Action.checkSystemActionsEnabled();
		final HeapHistogram heapHistogram;
		try {
			if (!isFromCollectorServer()) {
				heapHistogram = VirtualMachine.createHeapHistogram();
			} else {
				heapHistogram = collectorServer.collectHeapHistogram(getApplication());
			}
		} catch (final Exception e) {
			LOG.warn("heaphisto report failed", e);
			htmlReport.writeMessageIfNotNull(String.valueOf(e.getMessage()), null);
			return;
		}
		htmlReport.writeHeapHistogram(heapHistogram, messageForReport, HEAP_HISTO_PART);
	}

	private void doProcesses(HtmlReport htmlReport) throws IOException {
		// par sécurité
		Action.checkSystemActionsEnabled();
		try {
			if (!isFromCollectorServer()) {
				final List<ProcessInformations> processInformationsList = ProcessInformations
						.buildProcessInformations();
				htmlReport.writeProcesses(processInformationsList);
			} else {
				final Map<String, List<ProcessInformations>> processInformationsByTitle = collectorServer
						.collectProcessInformations(getApplication());
				htmlReport.writeProcesses(processInformationsByTitle);
			}
		} catch (final Exception e) {
			LOG.warn("processes report failed", e);
			htmlReport.writeMessageIfNotNull(String.valueOf(e.getMessage()), null);
		}
	}

	private void doDatabase(HtmlReport htmlReport, int index) throws IOException {
		// par sécurité
		Action.checkSystemActionsEnabled();
		try {
			final DatabaseInformations databaseInformations;
			if (!isFromCollectorServer()) {
				databaseInformations = new DatabaseInformations(index);
			} else {
				databaseInformations = collectorServer.collectDatabaseInformations(getApplication(),
						index);
			}
			htmlReport.writeDatabase(databaseInformations);
		} catch (final Exception e) {
			LOG.warn("database report failed", e);
			htmlReport.writeMessageIfNotNull(String.valueOf(e.getMessage()), null);
		}
	}

	private void doConnections(HtmlReport htmlReport, boolean withoutHeaders) throws IOException {
		assert !isFromCollectorServer();
		// par sécurité
		Action.checkSystemActionsEnabled();
		htmlReport.writeConnections(JdbcWrapper.getConnectionInformationsList(), withoutHeaders);
	}

	private void doJndi(HtmlReport htmlReport, String path) throws IOException {
		// par sécurité
		Action.checkSystemActionsEnabled();
		try {
			final List<JndiBinding> jndiBindings;
			if (!isFromCollectorServer()) {
				jndiBindings = JndiBinding.listBindings(path);
			} else {
				jndiBindings = collectorServer.collectJndiBindings(getApplication(), path);
			}
			htmlReport.writeJndi(jndiBindings, path);
		} catch (final Exception e) {
			LOG.warn("jndi report failed", e);
			htmlReport.writeMessageIfNotNull(String.valueOf(e.getMessage()), null);
		}
	}

	private void doMBeans(HtmlReport htmlReport) throws IOException {
		// par sécurité
		Action.checkSystemActionsEnabled();
		try {
			if (!isFromCollectorServer()) {
				final List<MBeanNode> nodes = MBeans.getAllMBeanNodes();
				htmlReport.writeMBeans(nodes);
			} else {
				final Map<String, List<MBeanNode>> allMBeans = collectorServer
						.collectMBeans(getApplication());
				htmlReport.writeMBeans(allMBeans);
			}
		} catch (final Exception e) {
			LOG.warn("mbeans report failed", e);
			htmlReport.writeMessageIfNotNull(String.valueOf(e.getMessage()), null);
		}
	}

	private void doCacheKeys(HtmlReport htmlReport, String cacheId, boolean withoutHeaders)
			throws IOException {
		assert !isFromCollectorServer();
		final CacheInformations cacheInfo = CacheInformations
				.buildCacheInformationsWithKeys(cacheId);
		htmlReport.writeCacheWithKeys(cacheId, cacheInfo, messageForReport,
				CACHE_KEYS_PART + '&' + CACHE_ID_PARAMETER + '=' + I18N.urlEncode(cacheId),
				withoutHeaders);
	}

	void writeHtmlToLastShutdownFile() {
		try {
			final File dir = Parameters.getStorageDirectory(getApplication());
			if (!dir.mkdirs() && !dir.exists()) {
				throw new IOException("JavaMelody directory can't be created: " + dir.getPath());
			}
			final File lastShutdownFile = new File(dir, "last_shutdown.html");
			final BufferedWriter writer = new BufferedWriter(new FileWriter(lastShutdownFile));
			try {
				final JavaInformations javaInformations = new JavaInformations(
						Parameters.getServletContext(), true);
				// on pourrait faire I18N.bindLocale(Locale.getDefault()), mais cela se fera tout seul
				final HtmlReport htmlReport = new HtmlReport(collector, collectorServer,
						Collections.singletonList(javaInformations), Period.JOUR, writer);
				htmlReport.writeLastShutdown();
			} finally {
				writer.close();
			}
		} catch (final IOException e) {
			LOG.warn("exception while writing the last shutdown report", e);
		}
	}

	private String getApplication() {
		return collector.getApplication();
	}

	private boolean isFromCollectorServer() {
		return collectorServer != null;
	}
}
