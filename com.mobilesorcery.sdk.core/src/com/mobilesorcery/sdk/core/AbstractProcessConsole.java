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
package com.mobilesorcery.sdk.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.mobilesorcery.sdk.core.LineReader.ILineHandler;

public abstract class AbstractProcessConsole implements IProcessConsole {

	protected final static int OUT = 0;
	protected final static int ERR = 1;
	protected final static int MESSAGE = 2;
	
	/* (non-Javadoc)
	 * @see com.mobilesorcery.sdk.core.api.IProcessConsole#attachProcess(java.lang.Process)
	 */
	public void attachProcess(Process process) {
        attachProcess(process, null);
    }

    /* (non-Javadoc)
	 * @see com.mobilesorcery.sdk.core.api.IProcessConsole#attachProcess(java.lang.Process, com.mobilesorcery.sdk.core.api.LineReader.LineHandler)
	 */
    public void attachProcess(Process process, final ILineHandler delegate) {
    	attachProcess(process, delegate, delegate);
    }
    
    /* (non-Javadoc)
	 * @see com.mobilesorcery.sdk.core.api.IProcessConsole#attachProcess(java.lang.Process, com.mobilesorcery.sdk.core.api.LineReader.LineHandler, com.mobilesorcery.sdk.core.api.LineReader.LineHandler)
	 */
    public void attachProcess(Process process, final ILineHandler stdoutDelegate, final ILineHandler stderrDelegate) {
        final InputStream input = process.getInputStream();
        final InputStream error = process.getErrorStream();

        Reader inputReader = new InputStreamReader(input);
        Reader errorReader = new InputStreamReader(error);

        LineReader inputPump = new LineReader(inputReader, new ILineHandler() {
        	public void start(Process process) {
            	if (stdoutDelegate != null) {
                	stdoutDelegate.start(process);
                }
            }
            
        	public void newLine(String line) {
        		if (stdoutDelegate != null) {
                	stdoutDelegate.newLine(line);
                }
                writeLine(OUT, line);
            }

            public void stop(IOException e) {
                if (stdoutDelegate != null) {
                    stdoutDelegate.stop(e);
                }
            }
        });
        
        LineReader errorPump = new LineReader(errorReader, new ILineHandler() {
        	public void start(Process process) {
            	if (stderrDelegate != null) {
                	stderrDelegate.start(process);
                }
            }
        	
        	public void newLine(String line) {
                if (stderrDelegate != null) {
                    stderrDelegate.newLine(line);
                }
                writeLine(ERR, line);
            }
            
            public void stop(IOException e) {
                if (stderrDelegate != null) {
                    stderrDelegate.stop(e);
                }
            }

        });

        inputPump.start();
        errorPump.start();
    }

    /**
     * The default implementation is equivalent to 
     * to writeLine(MESSAGE, line)
     */
    public void addMessage(String line) {
    	writeLine(MESSAGE, line);
    }
    
    /**
     * Clients must implement this method
     * @param type OUT, ERR or MESSAGE
     * @param line
     */
    protected abstract void writeLine(int type, String line);
       

    /**
     * The default implementation does nothing.
     */
	public void clear() {
	}

    /**
     * The default implementation does nothing.
     */
	public void prepare() {
	}
}
