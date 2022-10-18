package project.dailynail.models.dtos.json;

import com.google.gson.annotations.Expose;

public class StatsEntityExportDto {
    @Expose
    private int authorizedRequests;
    @Expose
    private int unauthorizedRequests;

    public StatsEntityExportDto() {
    }

    public int getAuthorizedRequests() {
        return authorizedRequests;
    }

    public StatsEntityExportDto setAuthorizedRequests(int authorizedRequests) {
        this.authorizedRequests = authorizedRequests;
        return this;
    }

    public int getUnauthorizedRequests() {
        return unauthorizedRequests;
    }

    public StatsEntityExportDto setUnauthorizedRequests(int unauthorizedRequests) {
        this.unauthorizedRequests = unauthorizedRequests;
        return this;
    }
}
