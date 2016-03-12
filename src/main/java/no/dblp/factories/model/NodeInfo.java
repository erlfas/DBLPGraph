package no.dblp.factories.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import no.dblp.labels.Labels;

/**
 *
 * @author erlend321
 */
public class NodeInfo {
    
    private final Map<String,String> properties;
    private final Set<Labels> labels;
    private final String indexProperty;
    private final String indexValue;
    
    public NodeInfo(String indexProperty, String indexValue) {
        this.properties = new HashMap<>();
        this.labels = new HashSet<>();
        this.indexProperty = indexProperty;
        this.indexValue = indexValue;
    }

    public String getIndexProperty() {
        return indexProperty;
    }

    public String getIndexValue() {
        return indexValue;
    }
    
    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }
    
    public Map<String,String> getProperties() {
        Map<String,String> copyOfProperties = new HashMap<>();
        for (Entry<String,String> entry : properties.entrySet()) {
            copyOfProperties.put(entry.getKey(), entry.getValue());
        }
        return copyOfProperties;
    }
    
    public void addLabels(Labels label) {
        this.labels.add(label);
    }
    
    public Labels[] getLabels() {
        Labels[] list = new Labels[labels.size()];
        int i = 0;
        for (Labels label : labels) {
            list[i++] = label;
        }
        return list;
    }
    
}
