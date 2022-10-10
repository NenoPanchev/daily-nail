package project.dailynail.models.view;

public class StatsViewModel {
    private int authorizedRequests;
    private int unauthorizedRequests;

    public StatsViewModel() {
    }

    public int getAuthorizedRequests() {
        return authorizedRequests;
    }

    public StatsViewModel setAuthorizedRequests(int authorizedRequests) {
        this.authorizedRequests = authorizedRequests;
        return this;
    }

    public int getUnauthorizedRequests() {
        return unauthorizedRequests;
    }

    public StatsViewModel setUnauthorizedRequests(int unauthorizedRequests) {
        this.unauthorizedRequests = unauthorizedRequests;
        return this;
    }

    public String getWidthPercentage(int requestsNumber) {
        double percentage = 100.0 * requestsNumber / (authorizedRequests + unauthorizedRequests);
        return String.format("width: %.2f%%", percentage).replace(",", ".");
    }
}
