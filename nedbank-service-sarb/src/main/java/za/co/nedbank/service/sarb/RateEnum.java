package za.co.nedbank.service.sarb;

public enum RateEnum {
    PPI("sarb.ppi", "PPI"),
    CPI("sarb.cpi", "CPI"),
    REPO("sarb.repo", "Repo rate"),
    PRIME("sarb.prime", "Prime lending rate (predominant rate)");

    private final String cacheKey;
    private final String jsonKey;

    /**
     * @param cache Key to look up rate from the cache.
     * @param json Key to find the required rate from the SARB in the JSON payload
     */
    RateEnum(final String cache, final String json) {
        this.cacheKey = cache;
        this.jsonKey = json;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public String getJsonKey() {
        return jsonKey;
    }
}
