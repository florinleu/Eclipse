/*  Copyright (C) 2009 Mobile Sorcery AB

    This program is free software; you can redistribute it and/or modify it
    under the terms of the Eclipse Public License v1.0.

    This program is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License v1.0 for
    more details.

    You should have received a copy of the Eclipse Public License v1.0 along
    with this program. It is also available at http://www.eclipse.org/legal/epl-v10.html
*/
package com.mobilesorcery.sdk.finalizer.core;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.mobilesorcery.sdk.finalizer.core.messages"; //$NON-NLS-1$
	public static String FinalizerParser_BuildFailed;
	public static String FinalizerParser_FinalizingProgress;
	public static String FinalizerParser_ParseError_0;
	public static String FinalizerParser_ParseError_3;
	public static String FinalizerParser_ParseError_UnknownProfile;
	public static String FinalizerParser_ParseError_UnknownVender;
	public static String FinalizerParser_ScriptBoilerplate;
	public static String FinalizerParser_ScriptNoProfiles;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
