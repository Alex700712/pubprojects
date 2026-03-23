package Database.Entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "keys")
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "key")
    @GeneratedValue
    private UUID key;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Column(name = "is_blocked")
    private boolean is_blocked = false;

    public void setId(long id) { this.id = id; }

    public void setKey(UUID key) { this.key = key; }

    public void setStart_date(Date start_date) { this.start_date = start_date; }

    public void setEnd_date(Date end_date) { this.end_date = end_date; }

    public void setOrganization(Organization organization) { this.organization = organization; }

    public void setBlockState(boolean is_blocked) { this.is_blocked = is_blocked; }

    public long getId() { return id; }

    public UUID getKey() { return key; }

    public Date getStart_date() { return start_date; }

    public Date getEnd_date() { return end_date; }

    public Organization getOrganization() { return organization; }

    public boolean GetBlockState() { return is_blocked; }
}
