package org.blitzortung.android.jsonrpc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.experimental.Delegate;


public class JsonRpcClient {

    @Delegate(types = HttpServiceClient.class)
    private final HttpServiceClient client;

    private final int id = 0;

    private int lastNumberOfTransferredBytes;

    public JsonRpcClient(String uri, String agentSuffix) {
        client = new HttpServiceClient(uri, agentSuffix);
    }

    // VisibleForTesting
    protected JSONArray buildParameters(Object[] parameters) {
        JSONArray parameterArray = new JSONArray();
        for (Object parameter : parameters) {
            parameterArray.put(parameter);
        }
        return parameterArray;
    }

    // VisibleForTesting
    protected String buildRequest(String methodName, Object[] parameters) {
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("id", id);
            requestObject.put("method", methodName);
            requestObject.put("params", buildParameters(parameters));
        } catch (JSONException e) {
            throw new JsonRpcException("invalid JSON request", e);
        }

        return requestObject.toString();
    }

    public JSONObject call(String methodName, Object... parameters) {
        String response = doRequest(buildRequest(methodName, parameters));

        lastNumberOfTransferredBytes = response.length();

        try {
            if (response.startsWith("[")) {

                JSONArray responseArray = new JSONArray(response);
                return responseArray.getJSONObject(0);
            } else {

                JSONObject responseObject = new JSONObject(response);

                if (responseObject.has("fault")) {
                    throw new JsonRpcException(String.format("remote Exception '%s' #%s ", responseObject.get("faultString"),
                            responseObject.get("faultCode")));
                }
                return responseObject;
            }
        } catch (JSONException e) {
            throw new JsonRpcException("response not in JSON format", e);
        }
    }

    public int getLastNumberOfTransferredBytes() {
        return lastNumberOfTransferredBytes;
    }
}