package project.dailynail.models.entities;


import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "stats")
public class StatsEntity extends BaseEntity{
    private int authorizedRequests;
    private int unauthorizedRequests;
    private Map<CategoryEntity, Integer> categoryCounts;

    public StatsEntity() {
    }

    @Column
    public int getAuthorizedRequests() {
        return authorizedRequests;
    }

    public StatsEntity setAuthorizedRequests(int authorizedRequests) {
        this.authorizedRequests = authorizedRequests;
        return this;
    }

    @Column
    public int getUnauthorizedRequests() {
        return unauthorizedRequests;
    }

    public StatsEntity setUnauthorizedRequests(int unauthorizedRequests) {
        this.unauthorizedRequests = unauthorizedRequests;
        return this;
    }

    @ElementCollection
    @MapKeyColumn
    public Map<CategoryEntity, Integer> getCategoryCounts() {
        return categoryCounts;
    }

    public StatsEntity setCategoryCounts(Map<CategoryEntity, Integer> categoryCounts) {
        this.categoryCounts = categoryCounts;
        return this;
    }
}
