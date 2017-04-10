/**
 *
 */
package gov.hhs.fha.nhin.carequality;

import gov.hhs.fha.nhinc.common.carequality.AccessDenialType;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

/**
 * @author mpnguyen
 *
 */
public class CareQualityDocQuery {
    //private CareQualityDummy careQualitySoapHeader;
    private AccessDenialType careQualitySoapHeader;
    private AdhocQueryResponse adhocQueryResponse;



    /**
     * @return the careQualitySoapHeader
     */
    public AccessDenialType getCareQualitySoapHeader() {
        return careQualitySoapHeader;
    }

    /**
     * @param careQualitySoapHeader the careQualitySoapHeader to set
     */
    public void setCareQualitySoapHeader(AccessDenialType careQualitySoapHeader) {
        this.careQualitySoapHeader = careQualitySoapHeader;
    }

    /**
     * @return the adhocQueryResponse
     */
    public AdhocQueryResponse getAdhocQueryResponse() {
        return adhocQueryResponse;
    }

    /**
     * @param adhocQueryResponse the adhocQueryResponse to set
     */
    public void setAdhocQueryResponse(AdhocQueryResponse adhocQueryResponse) {
        this.adhocQueryResponse = adhocQueryResponse;
    }

}
