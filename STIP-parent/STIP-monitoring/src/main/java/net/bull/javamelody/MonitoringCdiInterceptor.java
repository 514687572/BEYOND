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

import javax.interceptor.Interceptor;

/**
 * Intercepteur pour CDI & pour EJB 3.1 (Java EE 6+).
 * Il est destiné à un compteur pour les statistiques d'exécutions de méthodes @RequestScoped, @SessionScoped, @ApplicationScoped
 * ( ainsi que @Stateless, @Stateful ou @MessageDriven ).
 * Il peut être paramétré par l'annotation @Monitored dans les sources java des classes d'implémentations de beans CDI ou d'ejb.
 * (ou alors par l'annotation @javax.interceptor.Interceptors dans les mêmes classes).
 * @author Emeric Vernat
 */
@Interceptor
@Monitored
public class MonitoringCdiInterceptor extends MonitoringInterceptor {
	private static final long serialVersionUID = 1L;
}
