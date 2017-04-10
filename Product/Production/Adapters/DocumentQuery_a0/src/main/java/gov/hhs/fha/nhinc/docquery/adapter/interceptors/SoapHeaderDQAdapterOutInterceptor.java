/**
 *
 */
package gov.hhs.fha.nhinc.docquery.adapter.interceptors;

import org.apache.cxf.jaxb.JAXBDataBinding;

import gov.hhs.fha.nhinc.common.carequality.AccessDenialType;
import gov.hhs.fha.nhinc.common.carequality.ObjectFactory;
import gov.hhs.fha.nhinc.common.carequality.ReasonType;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapOutInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dummy Intercept soap message before return response back to clients
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

        LOG.debug("Starting to hit adapter reponse version 2");
        SoapMessage soapMsg = (SoapMessage) message;
        try {
            AccessDenialType careQualityDenial = new AccessDenialType();

            ReasonType reasonType = new ReasonType();
            reasonType.setLang("EN");
            reasonType.setValue("Reason Type Value: hhhhh");
            careQualityDenial.setReason(reasonType);


            JAXBElement<AccessDenialType> accessDeniedElement = new ObjectFactory()
            .createAccessDenial(careQualityDenial);
            Header header = new Header(accessDeniedElement.getName(), accessDeniedElement, new JAXBDataBinding(
                AccessDenialType.class));
            soapMsg.getHeaders().add(header);
        } catch (JAXBException e) {
            LOG.error("Unable to add new dummy header {}", e.getLocalizedMessage(), e);
        }

    }




}
