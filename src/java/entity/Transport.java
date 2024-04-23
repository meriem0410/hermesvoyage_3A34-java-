package java.entity;

public class Transport {
    private String transport;
    private String description;

    public Transport(String transport, String description) {
        this.transport = transport;
        this.description = description;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "transport='" + transport + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
