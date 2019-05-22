package com.signal.demo.common.instance;

import com.signal.demo.common.log.SipLogger;
import com.signal.demo.session.SessionModel;

import javax.sip.ClientTransaction;
import javax.sip.ServerTransaction;
import javax.sip.message.Request;
import java.io.Serializable;
import java.util.HashMap;

public class SessionInstance extends SipLogger implements Serializable {
    private static SessionInstance sessionInstance = null;

    private HashMap<String, SessionModel> hmap;

    public static SessionInstance getInstance() {
        if (sessionInstance == null) sessionInstance = new SessionInstance();
        return sessionInstance;
    }

    public HashMap<String, SessionModel> getMap() {
        if (hmap == null) hmap = new HashMap<>();
        return hmap;
    }

    public void putMap(String k, SessionModel s) {
        HashMap<String, SessionModel> hmap = getMap();
        logger.debug("Put Session Data: " +hmap);
        hmap.put(k,s);
    }


    public void setClientTransactionCallId(String callId1, ClientTransaction clientTransaction) {
        HashMap<String, SessionModel> map = SessionInstance.getInstance().getMap();
        SessionModel sessionModel = map.get(callId1);
        sessionModel.setClientTransaction(clientTransaction);
    }

    public SessionModel findSessionModel(String callId) {
        logger.debug("Find Request CallId: " + callId);
        HashMap<String, SessionModel> map = SessionInstance.getInstance().getMap();
        logger.debug("Find Hashmap Keyset" + map.keySet());
        return map.get(callId);
    }

    public Request findRequest (String callId) {
        logger.debug("Find Request CallId: " + callId);
        HashMap<String, SessionModel> map = SessionInstance.getInstance().getMap();
        return map.get(callId).getRequest();
    }

    public ClientTransaction findClientTransaction (String callId) {
        HashMap<String, SessionModel> map = SessionInstance.getInstance().getMap();
        logger.debug("ClientTransactoin Model " + map);
        SessionModel sessionModel = map.get(callId);
        logger.debug("ClientTransactionCallId SessionModel CallID " + callId);
        return sessionModel.getClientTransaction();
    }

    public ServerTransaction findTransactionId (String callId) {
        logger.debug("FindTransactoinId CallId "+ callId);
        HashMap<String, SessionModel> map = SessionInstance.getInstance().getMap();
        ServerTransaction st = map.get(callId).getServerTransaction();
        return st;
    }

    public void deleteSessionModel (String callId) {
        HashMap<String, SessionModel> map = SessionInstance.getInstance().getMap();
        logger.debug("Delete SessionModel : " +callId);
        map.remove(callId);
    }

    public SessionModel setSession(String callId, String fromip, Integer fromPort, String fromUser, String toip, Integer toPort, String toUser, String type, String sdp, ServerTransaction st, Request request, Long seq) {
        SessionModel sessionModel = new SessionModel();
        sessionModel.setCallId(callId);
        sessionModel.setFromip(fromip);
        sessionModel.setFromPort(fromPort);
        sessionModel.setFromUser(fromUser);
        sessionModel.setToip(toip);
        sessionModel.setToPort(toPort);
        sessionModel.setToUser(toUser);
        sessionModel.setType(type);
        sessionModel.setSdp(sdp);
        sessionModel.setServerTransaction(st);
        sessionModel.setRequest(request);
        sessionModel.setSeq(111L);
        return sessionModel;
    }



}
