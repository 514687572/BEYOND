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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.CodeSource;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Rapport html pour afficher un fichier source java.
 * @author Emeric Vernat
 */
class HtmlSourceReport extends HtmlAbstractReport {
	private static final File JDK_SRC_FILE = getJdkSrcFile();

	private final String source;

	HtmlSourceReport(String className, Writer writer) throws IOException {
		super(writer);
		if (className.indexOf('$') == -1) {
			this.source = getSource(className);
		} else {
			this.source = getSource(className.substring(0, className.indexOf('$')));
		}
	}

	private String getSource(String className) throws IOException {
		final Class<?> clazz;
		try {
			clazz = Class.forName(className);
		} catch (final ClassNotFoundException e) {
			return null;
		}

		final CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
		if (clazz.getName().startsWith("java.")
				|| clazz.getName().startsWith("javax.") && codeSource == null) {
			if (JDK_SRC_FILE != null) {
				return getSourceFromJar(clazz, JDK_SRC_FILE);
			}
		} else if (codeSource != null) {
			final File sourceJarFile = MavenArtifact.getSourceJarFile(codeSource.getLocation());
			if (sourceJarFile != null) {
				return getSourceFromJar(clazz, sourceJarFile);
			}
		}
		return null;
	}

	private static File getJdkSrcFile() {
		File file = new File(System.getProperty("java.home"));
		if ("jre".equalsIgnoreCase(file.getName())) {
			file = file.getParentFile();
		}
		final File srcZipFile = new File(file, "src.zip");
		if (srcZipFile.exists()) {
			return srcZipFile;
		}
		return null;
	}

	private String getSourceFromJar(Class<?> clazz, File srcJarFile)
			throws ZipException, IOException {
		final ZipFile zipFile = new ZipFile(srcJarFile);
		try {
			final ZipEntry entry = zipFile.getEntry(clazz.getName().replace('.', '/') + ".java");
			if (entry == null) {
				return null;
			}
			final StringWriter writer = new StringWriter();
			final InputStream inputStream = zipFile.getInputStream(entry);
			try {
				final Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
				try {
					final char[] chars = new char[1024];
					int read = reader.read(chars);
					while (read != -1) {
						writer.write(chars, 0, read);
						read = reader.read(chars);
					}
				} finally {
					reader.close();
				}
			} finally {
				inputStream.close();
			}
			return writer.toString();
		} finally {
			zipFile.close();
		}
	}

	@Override
	void toHtml() throws IOException {
		if (source != null) {
			String html = JavaHTMLizer.htmlize(source);
			html = JavaHTMLizer.addLineNumbers(html);
			writeDirectly("<code>");
			writeDirectly(html);
			writeDirectly("</code>");
		} else {
			write("#source_not_found#");
		}
	}

	static String htmlEncodeStackTraceElement(String element) {
		if (element.endsWith(")") && !element.endsWith("(Native Method)")
				&& !element.endsWith("(Unknown Source)")) {
			final int index3 = element.lastIndexOf(':');
			final int index2 = element.lastIndexOf('(');
			final int index1 = element.lastIndexOf('.', index2);
			final int index0 = element.lastIndexOf(' ', index1);
			if (index1 > index0 && index2 != -1 && index3 > index2) {
				final String classNameEncoded = urlEncode(element.substring(index0 + 1, index1));
				return htmlEncodeButNotSpace(element.substring(0, index2 + 1)) + "<a href='?part="
						+ HttpParameters.SOURCE_PART + "&amp;class=" + classNameEncoded + '#'
						+ urlEncode(element.substring(index3 + 1, element.length() - 1))
						+ "' class='lightwindow' type='external' title='" + classNameEncoded + "'>"
						+ htmlEncode(element.substring(index2 + 1, element.length() - 1)) + "</a>)";
			}
		}
		return htmlEncodeButNotSpace(element);
	}

	static String addLinkToClassName(String className) {
		String cleanClassName = className;
		if (cleanClassName.endsWith("[]")) {
			cleanClassName = cleanClassName.substring(0, cleanClassName.length() - 2);
		}
		return "<a href='?part=" + HttpParameters.SOURCE_PART + "&amp;class=" + cleanClassName
				+ "' class='lightwindow' type='external' title='" + cleanClassName + "'>"
				+ className + "</a>";
	}
}
