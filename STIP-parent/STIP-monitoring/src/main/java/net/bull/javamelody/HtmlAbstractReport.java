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

import java.io.IOException;
import java.io.Writer;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

/**
 * Parent abstrait des classes de rapport html.
 * @author Emeric Vernat
 */
abstract class HtmlAbstractReport {
	private static final boolean PDF_ENABLED = computePdfEnabled();
	private static final boolean CSRF_PROTECTION_ENABLED = Boolean
			.parseBoolean(Parameters.getParameter(Parameter.CSRF_PROTECTION_ENABLED));
	private final Writer writer;

	class HtmlTable {
		private boolean firstRow = true;
		private boolean oddRow;

		void beginTable(String summary) throws IOException {
			writeDirectly("<table class='sortable' width='100%' border='1' summary='");
			writeDirectly(summary);
			writeDirectly("'>\n");
			writeDirectly("<thead><tr>");
		}

		void nextRow() throws IOException {
			writeDirectly("</tr>");
			if (firstRow) {
				firstRow = false;
				writeDirectly("</thead><tbody>\n");
			}
			if (oddRow) {
				writeDirectly(
						"<tr class='odd' onmouseover=\"this.className='highlight'\" onmouseout=\"this.className='odd'\">\n");
			} else {
				writeDirectly(
						"<tr onmouseover=\"this.className='highlight'\" onmouseout=\"this.className=''\">\n");
			}
			oddRow = !oddRow; // NOPMD
		}

		void endTable() throws IOException {
			writeDirectly("</tr>");
			if (firstRow) {
				firstRow = false;
				writeDirectly("</thead><tbody>\n");
			}
			writeDirectly("</tbody></table>\n");
		}
	}

	HtmlAbstractReport(Writer writer) {
		super();
		assert writer != null;
		this.writer = writer;
	}

	/**
	 * Perform the default html rendering of the report into the writer.
	 * @throws IOException e
	 */
	abstract void toHtml() throws IOException;

	Writer getWriter() {
		return writer;
	}

	void writeDirectly(String html) throws IOException {
		writer.write(html);
	}

	/**
	 * Écrit un texte dans un flux en remplaçant dans le texte les clés entourées de deux '#'
	 * par leurs traductions dans la locale courante.
	 * @param html texte html avec éventuellement des #clé#
	 * @throws IOException e
	 */
	void write(String html) throws IOException {
		I18N.writeTo(html, writer);
	}

	/**
	 * Écrit un texte, puis un retour chariot, dans un flux en remplaçant dans le texte les clés entourées de deux '#'
	 * par leurs traductions dans la locale courante.
	 * @param html texte html avec éventuellement des #clé#
	 * @throws IOException e
	 */
	void writeln(String html) throws IOException {
		I18N.writelnTo(html, writer);
	}

	void writeTitle(String imageFileName, String title) throws IOException {
		writeDirectly("<span class='chapterTitle'><img src='?resource=");
		writeDirectly(imageFileName);
		writeDirectly("' alt=\"");
		writeDirectly(title);
		writeDirectly("\" />&nbsp;");
		writeDirectly("<b>");
		writeDirectly(title);
		writeDirectly("</b></span><br/>\n");
	}

	void writeShowHideLink(String idToShow, String label) throws IOException {
		writeln("<a href=\"javascript:showHide('" + idToShow + "');\" class='noPrint'><img id='"
				+ idToShow + "Img' src='?resource=bullets/plus.png' alt=''/> " + label + "</a>");
	}

	/**
	 * Retourne une traduction dans la locale courante.
	 * @param key clé d'un libellé dans les fichiers de traduction
	 * @return String
	 */
	static String getString(String key) {
		return I18N.getString(key);
	}

	/**
	 * Retourne une traduction dans la locale courante et l'encode pour affichage en javascript.
	 * @param key clé d'un libellé dans les fichiers de traduction
	 * @return String
	 */
	static String getStringForJavascript(String key) {
		return I18N.getStringForJavascript(key);
	}

	/**
	 * Retourne une traduction dans la locale courante et insère les arguments aux positions {i}.
	 * @param key clé d'un libellé dans les fichiers de traduction
	 * @param arguments Valeur à inclure dans le résultat
	 * @return String
	 */
	static String getFormattedString(String key, Object... arguments) {
		return I18N.getFormattedString(key, arguments);
	}

	/**
	 * Encode pour affichage en javascript.
	 * @param text message à encoder
	 * @return String
	 */
	static String javascriptEncode(String text) {
		return I18N.javascriptEncode(text);
	}

	static String urlEncode(String text) {
		return I18N.urlEncode(text);
	}

	/**
	 * Encode pour affichage en html, en encodant les espaces en nbsp (insécables).
	 * @param text message à encoder
	 * @return String
	 */
	static String htmlEncode(String text) {
		return I18N.htmlEncode(text, true);
	}

	/**
	 * Encode pour affichage en html, sans encoder les espaces en nbsp (insécables).
	 * @param text message à encoder
	 * @return String
	 */
	static String htmlEncodeButNotSpace(String text) {
		return I18N.htmlEncode(text, false);
	}

	static String getCsrfTokenUrlPart() {
		if (CSRF_PROTECTION_ENABLED) {
			final HttpSession currentSession = SessionListener.getCurrentSession();
			String csrfToken = (String) currentSession
					.getAttribute(SessionListener.CSRF_TOKEN_SESSION_NAME);
			if (csrfToken == null) {
				final byte[] bytes = new byte[16];
				new SecureRandom().nextBytes(bytes);
				csrfToken = new String(Base64Coder.encode(bytes));
				currentSession.setAttribute(SessionListener.CSRF_TOKEN_SESSION_NAME, csrfToken);
			}
			return "&amp;" + HttpParameters.TOKEN_PARAMETER + '=' + csrfToken;
		}
		return "";
	}

	static boolean isPdfEnabled() {
		return PDF_ENABLED;
	}

	private static boolean computePdfEnabled() {
		try {
			Class.forName("com.lowagie.text.Document");
			return true;
		} catch (final ClassNotFoundException e) {
			return false;
		}
	}
}
