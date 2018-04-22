package com.zoom.risk.platform.scard.mode;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public class EnginePolices {
    private SCardRouter scardRouter;
    private SCard scard;

    public SCardRouter getScardRouter() {
        return scardRouter;
    }

    public void setScardRouter(SCardRouter scardRouter) {
        this.scardRouter = scardRouter;
    }

    public SCard getScard() {
        return scard;
    }

    public void setScard(SCard scard) {
        this.scard = scard;
    }
}
