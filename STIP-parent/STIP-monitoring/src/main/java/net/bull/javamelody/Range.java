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
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Cette classe représente une période pour les courbes et les statistiques.
 * Elle contient soit une période fixe (jour, semaine, mois, année, tout, à partir de la date du jour),
 * soit une période personnalisée entre une date de début et une date de fin.
 * @author Emeric Vernat
 */
final class Range implements Serializable {
	static final char CUSTOM_PERIOD_SEPARATOR = '|';

	private static final long serialVersionUID = 4658258882827669495L;

	private static final long MILLISECONDS_PER_DAY = 24L * 60 * 60 * 1000;

	private final Period period;

	private final Date startDate;

	private final Date endDate;

	private Range(Period period, Date startDate, Date endDate) {
		super();
		assert period != null && startDate == null && endDate == null || period == null
				&& startDate != null && endDate != null && startDate.getTime() <= endDate.getTime();
		this.period = period;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	static Range createPeriodRange(Period period) {
		return new Range(period, null, null);
	}

	static Range createCustomRange(Date startDate, Date endDate) {
		Date normalizedStartDate = startDate;
		Date normalizedEndDate = endDate;
		final Calendar minimum = Calendar.getInstance();
		minimum.add(Calendar.YEAR, -2);
		if (normalizedStartDate.getTime() < minimum.getTimeInMillis()) {
			// pour raison de performance, on limite à 2 ans (et non 2000 ans)
			normalizedStartDate = minimum.getTime();
		}
		if (normalizedStartDate.getTime() > System.currentTimeMillis()) {
			// pour raison de performance, on limite à aujourd'hui (et non 2000 ans)
			normalizedStartDate = new Date();
		}

		if (normalizedEndDate.getTime() > System.currentTimeMillis()) {
			// pour raison de performance, on limite à aujourd'hui (et non 2000 ans)
			normalizedEndDate = new Date();
		}
		if (normalizedStartDate.after(normalizedEndDate)) {
			normalizedEndDate = normalizedStartDate;
		}

		// la date de fin est incluse jusqu'à 23h59m59s
		// (et le formatage de cette date reste donc au même jour)
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(normalizedEndDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		normalizedEndDate = calendar.getTime();

		return new Range(null, normalizedStartDate, normalizedEndDate);
	}

	static Range parse(String value) {
		final int index = value.indexOf(CUSTOM_PERIOD_SEPARATOR);
		if (index == -1) {
			try {
				return Period.valueOfIgnoreCase(value).getRange();
			} catch (final IllegalArgumentException e) {
				return Period.JOUR.getRange();
			}
		}
		// rq: on pourrait essayer aussi des dateFormat alternatifs,
		// par exemple même pattern mais sans les slashs ou juste avec jour et mois
		final DateFormat dateFormat = I18N.createDateFormat();
		Date startDate;
		try {
			startDate = dateFormat.parse(value.substring(0, index));
		} catch (final ParseException e) {
			startDate = new Date();
		}

		Date endDate;
		if (index < value.length() - 1) {
			try {
				endDate = dateFormat.parse(value.substring(index + 1));
			} catch (final ParseException e) {
				endDate = new Date();
			}
		} else {
			endDate = new Date();
		}

		return Range.createCustomRange(startDate, endDate);
	}

	Period getPeriod() {
		return period;
	}

	Date getStartDate() {
		return startDate;
	}

	Date getEndDate() {
		return endDate;
	}

	String getValue() {
		if (period == null) {
			final DateFormat dateFormat = I18N.createDateFormat();
			return dateFormat.format(startDate) + CUSTOM_PERIOD_SEPARATOR
					+ dateFormat.format(endDate);
		}
		return period.getCode();
	}

	String getLabel() {
		if (period == null) {
			final DateFormat dateFormat = I18N.createDateFormat();
			return dateFormat.format(startDate) + " - " + dateFormat.format(endDate);
		}
		return period.getLabel();
	}

	int getDurationDays() {
		if (period == null) {
			// attention endDate contient le dernier jour inclus jusqu'à 23h59m59s (cf parse),
			// donc on ajoute 1s pour compter le dernier jour
			return (int) ((endDate.getTime() + 1000 - startDate.getTime()) / MILLISECONDS_PER_DAY);
		}
		return period.getDurationDays();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[period=" + getPeriod() + ", startDate="
				+ getStartDate() + ", endDate=" + getEndDate() + ']';
	}
}
