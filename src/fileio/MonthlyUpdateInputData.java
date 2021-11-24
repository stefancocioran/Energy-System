package fileio;

import java.util.ArrayList;
import java.util.List;

public final class MonthlyUpdateInputData {

    private final List<ConsumerInputData> newConsumers;
    private final List<DistributorChangesInputData> distributorChanges;
    private final List<ProducerChangesInputData> producerChanges;

    public MonthlyUpdateInputData(final List<ConsumerInputData> newConsumers,
                                  final ArrayList<DistributorChangesInputData> distributorChanges,
                                  final ArrayList<ProducerChangesInputData> producerChanges) {
        this.newConsumers = newConsumers;
        this.distributorChanges = distributorChanges;
        this.producerChanges = producerChanges;
    }

    public List<ConsumerInputData> getNewConsumers() {
        return newConsumers;
    }

    public List<DistributorChangesInputData> getDistributorChanges() {
        return distributorChanges;
    }

    public List<ProducerChangesInputData> getProducerChanges() {
        return producerChanges;
    }
}
