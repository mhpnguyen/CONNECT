/**
 *
 */
package gov.hhs.fha.nhinc.docquery.adapter.interceptors;

import javax.xml.bind.JAXBException;

import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapOutInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Intercept soap message before return response back to clients
 *
 * @author mpnguyen
 *
 */
public class SoapHeaderDQAdapterOutInterceptor extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(SoapHeaderDQAdapterOutInterceptor.class);
    /**
     * @param phase
     */
    public SoapHeaderDQAdapterOutInterceptor() {
        super(Phase.WRITE);
        addBefore(SoapOutInterceptor.class.getName());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
     */
    @Override
    public void handleMessage(Message message) throws Fault {

        LOG.debug("Starting to hit adapter reponse version 1");
        SoapMessage soapMsg = (SoapMessage) message;
        try {
            Header dummyHeader = new Header(new QName("uri:carequality", "dummy"), "decapitated",
                new JAXBDataBinding(String.class));
            soapMsg.getHeaders().add(dummyHeader);
        } catch (JAXBException e) {
            LOG.error("Unable to add new dummy header {}", e.getLocalizedMessage(), e);
        }

    }




}
