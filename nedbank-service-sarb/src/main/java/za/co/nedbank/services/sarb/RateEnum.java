package za.co.nedbank.services.sarb;

public enum RateEnum {
    PPI("sarb.ppi", "PPI"), CPI("sarb.cpi", "CPI"), REPO("sarb.repo", "Repo rate"),
    PRIME("sarb.prime", "Prime lending rate (predominant rate)");

    final String cacheKey;
    final String jsonKey;

    private RateEnum(final String cacheKey, final String jsonKey) {
        this.cacheKey = cacheKey;
        this.jsonKey = jsonKey;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public String getJsonKey() {
        return jsonKey;
    }
}
