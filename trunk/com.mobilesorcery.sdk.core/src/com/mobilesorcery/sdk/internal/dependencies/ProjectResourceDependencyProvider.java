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
package com.mobilesorcery.sdk.internal.dependencies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>A dependency provider for resource files (*.lst) in
 * mosync projects -- touching those files should trigger
 * a change in maheaders.h</p>
 * <p>
 * <b>Exception:</b> The STABS file generated by pipe tool 
 * </p>
 * @author Mattias Bybro, mattias.bybro@purplescout.se
 *
 */
public class ProjectResourceDependencyProvider implements IDependencyProvider<IResource> {

	public final static String RESOURCE_EXT = "lst";
	
	public final static String STABS_FILE_NAME = "stabs.lst";
	
	public final static IPath MA_HEADER_PATH = new Path("MAHeaders.h");
	
	private final static Map<IResource, Collection<IResource>> EMPTY_MAP = Collections.unmodifiableMap(new HashMap<IResource, Collection<IResource>>());
	
	public Map<IResource, Collection<IResource>> computeDependenciesOf(IResource resource)
			throws CoreException {
		if (resource.getType() == IResource.FILE) {
			String ext = resource.getFileExtension();
			if (RESOURCE_EXT.equals(ext) && !STABS_FILE_NAME.equalsIgnoreCase(resource.getName())) {
				IResource maheaderFile = resource.getParent().getFile(MA_HEADER_PATH);
				HashMap<IResource, Collection<IResource>> result = new HashMap<IResource, Collection<IResource>>();
				ArrayList<IResource> resourceList = new ArrayList<IResource>();
				resourceList.add(resource);
				result.put(maheaderFile, resourceList);
				return result;
			}
		}
		
		return EMPTY_MAP;
	}

}
