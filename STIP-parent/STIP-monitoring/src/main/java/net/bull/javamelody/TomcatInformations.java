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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * Informations sur Tomcat, sans code html de présentation.
 * L'état d'une instance est initialisé à son instanciation et non mutable;
 * il est donc de fait thread-safe.
 * Cet état est celui d'un serveur Tomcat, similaire à celui fourni dans le manager de Tomcat.
 * Les instances sont sérialisables pour pouvoir être transmises au serveur de collecte.
 * @author Emeric Vernat
 */
final class TomcatInformations implements Serializable {
	// cette classe utilise la même technique avec les MBeans Tomcat que la webapp manager de Tomcat
	// http://svn.apache.org/repos/asf/tomcat/trunk/java/org/apache/catalina/manager/StatusManagerServlet.java
	// http://svn.apache.org/repos/asf/tomcat/trunk/java/org/apache/catalina/manager/StatusTransformer.java
	// http://svn.apache.org/repos/asf/tomcat/trunk/webapps/manager/xform.xsl

	private static final boolean TOMCAT_USED = System.getProperty("catalina.home") != null;

	private static final long serialVersionUID = -6145865427461051370L;

	@SuppressWarnings("all")
	private static final List<ObjectName> THREAD_POOLS = new ArrayList<ObjectName>();
	@SuppressWarnings("all")
	private static final List<ObjectName> GLOBAL_REQUEST_PROCESSORS = new ArrayList<ObjectName>();

	private static int mbeansInitAttemps;

	private final String name;
	private final int maxThreads;
	private final int currentThreadCount;
	private final int currentThreadsBusy;
	private final long bytesReceived;
	private final long bytesSent;
	private final int requestCount;
	private final int errorCount;
	private final long processingTime;
	private final long maxTime;

	private TomcatInformations(MBeans mBeans, ObjectName threadPool) throws JMException {
		super();
		name = threadPool.getKeyProperty("name");
		maxThreads = (Integer) mBeans.getAttribute(threadPool, "maxThreads");
		currentThreadCount = (Integer) mBeans.getAttribute(threadPool, "currentThreadCount");
		currentThreadsBusy = (Integer) mBeans.getAttribute(threadPool, "currentThreadsBusy");
		ObjectName grp = null;
		for (final ObjectName globalRequestProcessor : GLOBAL_REQUEST_PROCESSORS) {
			if (name.equals(globalRequestProcessor.getKeyProperty("name"))) {
				grp = globalRequestProcessor;
				break;
			}
		}
		if (grp != null) {
			bytesReceived = (Long) mBeans.getAttribute(grp, "bytesReceived");
			bytesSent = (Long) mBeans.getAttribute(grp, "bytesSent");
			requestCount = (Integer) mBeans.getAttribute(grp, "requestCount");
			errorCount = (Integer) mBeans.getAttribute(grp, "errorCount");
			processingTime = (Long) mBeans.getAttribute(grp, "processingTime");
			maxTime = (Long) mBeans.getAttribute(grp, "maxTime");
		} else {
			bytesReceived = 0;
			bytesSent = 0;
			requestCount = 0;
			errorCount = 0;
			processingTime = 0;
			maxTime = 0;
		}
	}

	static List<TomcatInformations> buildTomcatInformationsList() {
		if (!TOMCAT_USED) {
			return Collections.emptyList();
		}
		try {
			synchronized (THREAD_POOLS) {
				if ((THREAD_POOLS.isEmpty() || GLOBAL_REQUEST_PROCESSORS.isEmpty())
						&& mbeansInitAttemps < 10) {
					// lors du premier appel dans Tomcat lors du déploiement de la webapp,
					// ce initMBeans ne fonctionne pas car les MBeans n'existent pas encore,
					// donc il faut réessayer plus tard
					initMBeans();

					// issue 406, Tomcat mbeans never found in jboss eap 6.2,
					// we must stop initMBeans at some point
					mbeansInitAttemps++;
				}
			}
			final MBeans mBeans = new MBeans();
			final List<TomcatInformations> tomcatInformationsList = new ArrayList<TomcatInformations>(
					THREAD_POOLS.size());
			// rq: le processor correspondant au threadPool peut se retrouver selon
			// threadPool.getKeyProperty("name").equals(globalRequestProcessor.getKeyProperty("name"))
			for (final ObjectName threadPool : THREAD_POOLS) {
				tomcatInformationsList.add(new TomcatInformations(mBeans, threadPool));
			}
			return tomcatInformationsList;
		} catch (final InstanceNotFoundException e) {
			// nécessaire pour JBoss 6.0 quand appelé depuis MonitoringFilter.destroy via
			// writeHtmlToLastShutdownFile
			return Collections.emptyList();
		} catch (final AttributeNotFoundException e) {
			// issue 220 and end of issue 133:
			// AttributeNotFoundException: No attribute called maxThreads (in some JBossAS or JBossWeb)
			return Collections.emptyList();
		} catch (final JMException e) {
			// n'est pas censé arriver
			throw new IllegalStateException(e);
		}
	}

	// visibilité package pour réinitialisation en test unitaire
	static void initMBeans() throws MalformedObjectNameException {
		// rq: en général, il y a 2 connecteurs (http et ajp 1.3) définis dans server.xml et donc
		// 2 threadPools et 2 globalRequestProcessors de même nom : http-8080 et jk-8009 (ajp13)
		final MBeans mBeans = new MBeans();
		THREAD_POOLS.clear();
		GLOBAL_REQUEST_PROCESSORS.clear();
		THREAD_POOLS.addAll(mBeans.getTomcatThreadPools());
		GLOBAL_REQUEST_PROCESSORS.addAll(mBeans.getTomcatGlobalRequestProcessors());
	}

	String getName() {
		return name;
	}

	int getMaxThreads() {
		return maxThreads;
	}

	int getCurrentThreadCount() {
		return currentThreadCount;
	}

	int getCurrentThreadsBusy() {
		return currentThreadsBusy;
	}

	long getBytesReceived() {
		return bytesReceived;
	}

	long getBytesSent() {
		return bytesSent;
	}

	int getRequestCount() {
		return requestCount;
	}

	int getErrorCount() {
		return errorCount;
	}

	long getProcessingTime() {
		return processingTime;
	}

	long getMaxTime() {
		return maxTime;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[name=" + getName() + ", maxThreads=" + getMaxThreads()
				+ ", currentThreadCount=" + getCurrentThreadCount() + ", currentThreadsBusy="
				+ getCurrentThreadsBusy() + ", bytesReceived=" + getBytesReceived() + ", bytesSent="
				+ getBytesSent() + ", requestCount=" + getRequestCount() + ", errorCount="
				+ getErrorCount() + ", processingTime=" + getProcessingTime() + ", maxTime="
				+ getMaxTime() + ']';
	}
}
