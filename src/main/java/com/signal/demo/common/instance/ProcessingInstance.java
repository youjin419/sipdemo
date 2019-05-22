package com.signal.demo.common.instance;

import com.signal.demo.session.SessionModel;
import com.signal.demo.sip.SipSignal;

import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.header.*;
import java.util.ArrayList;

public class ProcessingInstance {
    private static ProcessingInstance processingInstance = null;
    private AddressFactory addressFactory;
    private HeaderFactory headerFactory;
    private SessionModel sessionModel;
    private String fromTags;
    private String toTags;

    private SipSignal sipSignal = SipSignal.getInstance();

    public String getFromTags() {
        return fromTags;
    }

    public void setFromTags(String fromTags) {
        this.fromTags = fromTags;
    }

    public String getToTags() {
        return toTags;
    }

    public void setToTags(String toTags) {
        this.toTags = toTags;
    }

    public static ProcessingInstance getProcessingInstance() {
        if (processingInstance == null) processingInstance = new ProcessingInstance();

        return processingInstance;
    }

    public SessionModel getSessionModel() {
        return sessionModel;
    }

    public void setSessionModel(SessionModel sessionModel) {
        this.sessionModel = sessionModel;
    }

    public void setAddressFactory(AddressFactory addressFactory) {
        this.addressFactory = addressFactory;
    }

    public void setHeaderFactory(HeaderFactory headerFactory) {
        this.headerFactory = headerFactory;
    }

    public ContactHeader createContactHeader(String user, String ip, Integer port) throws Exception {

        SipURI contactURI = this.addressFactory.createSipURI(user, ip);
        contactURI.setPort(port);
        Address contactAddress = this.addressFactory.createAddress(contactURI);

        contactAddress.setDisplayName(user);
        return this.headerFactory.createContactHeader(contactAddress);
    }

    public SipURI createSipAddress(String name, String ip) throws Exception{
        SipURI sipURI = this.addressFactory.createSipURI(name,ip);
        return sipURI;
    }

    public SipURI createSipUri(String ip, Integer port, String user) throws Exception {
        String host = ip + ":" + port;
        return this.addressFactory.createSipURI(user,host);
    }

    public ToHeader createToHeader(SipURI sipURI, String tags) throws Exception {
        Address address = this.addressFactory.createAddress(sipURI);
        ToHeader toHeader = this.headerFactory.createToHeader(address,tags);
        return toHeader;
    }

    public FromHeader createFromHeader(SipURI sipURI, String tags) throws Exception {
        Address address = this.addressFactory.createAddress(sipURI);
        FromHeader fromHeader = this.headerFactory.createFromHeader(address, tags);
        return fromHeader;
    }

    public CSeqHeader createCSeqHeader(long seq, String method) throws Exception {
        CSeqHeader cSeqHeader = this.headerFactory.createCSeqHeader(seq, method);
        return cSeqHeader;
    }

    public MaxForwardsHeader createMaxForwardsHeader() throws Exception {
        MaxForwardsHeader maxForwardsHeader = this.headerFactory.createMaxForwardsHeader(70);
        return maxForwardsHeader;
    }

    public ArrayList createViaHeaders() throws Exception {
        ArrayList viaHeaders = new ArrayList();
        String address = this.sipSignal.getSipProvider().getListeningPoint().getIPAddress();
        ViaHeader viaHeader = this.headerFactory.createViaHeader(address, this.sipSignal.getSipProvider().getListeningPoint("udp").getPort(), "udp", null);
        viaHeaders.add(viaHeader);

        return viaHeaders;
    }



}
