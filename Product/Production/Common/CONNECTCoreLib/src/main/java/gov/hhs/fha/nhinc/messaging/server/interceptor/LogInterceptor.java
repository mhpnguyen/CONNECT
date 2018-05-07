/*
 * Copyright (c) 2009-2018, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.messaging.server.interceptor;


import java.util.Collections;

import java.io.StringWriter;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mpnguyen
 */
public class LogInterceptor implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);


    /*
     * @Override public void handleMessage(Message message) { LOG.debug("Begin Custom Log Interceptor 2");//
     * LoggingFeature boolean enableHttpDump = true; try { enableHttpDump =
     * getPropAccessor().getPropertyBoolean("gateway", "enableHttpDump"); } catch (PropertyAccessException e) {
     * LOG.error(e.getLocalizedMessage(), e); } LoggingInInterceptor cxfLogging = new LoggingInInterceptor();
     * LoggingOutInterceptor cxfOutLogging = new LoggingOutInterceptor(); if (true) { cxfLogging.setPrettyLogging(true);
     * cxfLogging.setShowBinaryContent(true); cxfLogging.setShowMultipartContent(true); cxfLogging.setLimit(-1);
     * cxfLogging.handleMessage(message); // LoggingFeature cxfOutLogging.setPrettyLogging(true);
     * cxfOutLogging.setShowBinaryContent(true); cxfOutLogging.setShowMultipartContent(true);
     * cxfOutLogging.setLimit(-1); cxfOutLogging.handleMessage(message);
     *
     * } // LoggingInInterceptor // LoggingOutInterceptor }
     */



    /*
     * (non-Javadoc)
     *
     * @see javax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler.MessageContext)
     */
    @Override
    public boolean handleMessage(SOAPMessageContext messageContext) {
        // LoggingOutInterceptor
        // LoggingInInterceptor
        LOG.debug("Custom LogInterceptor Minh 2 ");
        Boolean isOutboundMessage = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        String soapMessageStr = convertDomToString(messageContext.getMessage().getSOAPPart());
        String soapActionURI = ((QName) messageContext.get(MessageContext.WSDL_OPERATION)).getLocalPart();
        LOG.debug("Direction: " + (isOutboundMessage ? "outbound" : "inbound"));
        // loop up use case before we log
        LOG.debug(soapMessageStr);
        LOG.debug("End of direction");
        LOG.debug(soapActionURI);

        return true;
    }

    /**
     * @param request
     * @return
     */
    private String convertDomToString(SOAPPart request) {
        final StringWriter sw = new StringWriter();
        try {
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(request), new StreamResult(sw));
        } catch (TransformerException e) {
            // do something
        }
        return sw.toString();

    }

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.ws.handler.Handler#handleFault(javax.xml.ws.handler.MessageContext)
     */
    @Override
    public boolean handleFault(SOAPMessageContext context) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.ws.handler.Handler#close(javax.xml.ws.handler.MessageContext)
     */
    @Override
    public void close(MessageContext context) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see javax.xml.ws.handler.soap.SOAPHandler#getHeaders()
     */
    @Override
    public Set<QName> getHeaders() {
        LOG.debug("LogInterceptor.getHeaders");
        return Collections.emptySet();
    }

}
