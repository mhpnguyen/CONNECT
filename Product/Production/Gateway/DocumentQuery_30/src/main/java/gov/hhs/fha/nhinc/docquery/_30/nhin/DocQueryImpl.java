/*
 * Copyright (c) 2009-2017, United States Government, as represented by the Secretary of Health and Human Services.
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
package gov.hhs.fha.nhinc.docquery._30.nhin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import org.apache.cxf.jaxb.JAXBDataBinding;
import gov.hhs.fha.nhin.carequality.CareQualityDummy;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.docquery.inbound.InboundDocQuery;
import gov.hhs.fha.nhinc.messaging.server.BaseService;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants.UDDI_SPEC_VERSION;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.xml.ws.WebServiceContext;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import org.apache.cxf.headers.Header;

public class DocQueryImpl extends BaseService {

    private InboundDocQuery inboundDocQuery;
    private static final Logger LOG = LoggerFactory.getLogger(DocQueryImpl.class);

    public DocQueryImpl(InboundDocQuery inboundDocQuery) {
        this.inboundDocQuery = inboundDocQuery;
    }

    public AdhocQueryResponse respondingGatewayCrossGatewayQuery(AdhocQueryRequest body, WebServiceContext context) {
        AssertionType assertion = getAssertion(context, null);
        if (assertion != null) {
            assertion.setImplementsSpecVersion(UDDI_SPEC_VERSION.SPEC_3_0.toString());
        }
        // This property also hold carequality from adapter level
        Properties properties = getWebContextProperties(context);
        AdhocQueryResponse response = inboundDocQuery.respondingGatewayCrossGatewayQuery(body, assertion, properties);
        /*
         * context.getMessageContext().put(NhincConstants.CARE_QUALITY_KEY,
         * properties.get(NhincConstants.CARE_QUALITY_KEY));
         */
        setHeaderForContext(context, properties);
        return response;
    }

    private void setHeaderForContext(WebServiceContext context, Properties properties) {

        List<Header> headers = (List<Header>) context.getMessageContext().get(Header.HEADER_LIST);
        if (headers == null) {
            headers = new ArrayList<>();
        }
        // retrieve careQuality soapheader from adapter level. We can move into common class to detect from all adapters
        // need better way to cast this object since it fails when null is return
        CareQualityDummy careQualityHeader = (CareQualityDummy) properties.get(NhincConstants.CARE_QUALITY_KEY);
        try {
            Header adapterResult = new Header(new QName("uri:carequalityResultFromAdapter", "dummy"),
                careQualityHeader.getReason(), new JAXBDataBinding(String.class));
            headers.add(adapterResult);
            LOG.debug("Result comming from adapter level {}", careQualityHeader.getReason());
            context.getMessageContext().put(Header.HEADER_LIST, headers);
        } catch (JAXBException e) {
            LOG.error("uanble to parse result {}", e.getLocalizedMessage(), e);

        }


    }


}
