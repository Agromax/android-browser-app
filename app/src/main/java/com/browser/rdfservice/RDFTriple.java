package com.browser.rdfservice;

import com.browser.rdfservice.spi.ITriple;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 10-06-2016.
 */
public class RDFTriple implements ITriple {
    protected final String sub;
    protected final String pre;
    protected final String obj;

    public RDFTriple(JSONObject triple) {
        String s = null, p = null, o = null;
        try {
            s = (String) triple.get("sub");
            p = (String) triple.get("pre");
            o = (String) triple.get("obj");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sub = s;
        pre = p;
        obj = o;
    }

    @Override
    public String sub() {
        return sub;
    }

    @Override
    public String pre() {
        return pre;
    }

    @Override
    public String obj() {
        return obj;
    }

    @Override
    public String asCSV() {
        return String.format("%s,%s,%s", sub(), pre(), obj());
    }

    @Override
    public String asTSV() {
        return String.format("%s\t%s\t%s", sub(), pre(), obj());
    }

    @Override
    public JSONObject asJSON() {
        JSONObject o = null;
        try {
            o = new JSONObject()
                    .put("sub", sub())
                    .put("pre", pre())
                    .put("obj", obj());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }
}
