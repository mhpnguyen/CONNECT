/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.asyncmsgs.model;

import java.sql.Blob;
import java.util.Date;

/**
 *
 * @author JHOPPESC
 */
public class AsyncMsgRecord {
    private String MessageId = null;
    private Date CreationTime = null;
    private String ServiceName = null;
    private Blob MsgData = null;

    public AsyncMsgRecord() {}
    
    public String getMessageId () {
        return MessageId;
    }

    public void setMessageId (String MessageId) {
        this.MessageId = MessageId;
    }

    public Date getCreationTime () {
        return CreationTime;
    }

    public void setCreationTime (Date CreationTime) {
        this.CreationTime = CreationTime;
    }

    public String getServiceName () {
        return ServiceName;
    }

    public void setServiceName (String ServiceName) {
        this.ServiceName = ServiceName;
    }

    public Blob getMsgData () {
        return MsgData;
    }

    public void setMsgData (Blob MsgData) {
        this.MsgData = MsgData;
    }

}
