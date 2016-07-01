package net.cloudkit.experiment.infrastructure.support;

import java.io.Serializable;
import java.util.*;

public class MetaData implements Map<String, Object>, Serializable {

    private static final long serialVersionUID = -7892913866303912970L;
    private static final MetaData EMPTY_META_DATA = new MetaData();
    private static final String UNSUPPORTED_MUTATION_MSG = "Event meta-data is immutable.";
    private final Map<String, Object> values;

    public static MetaData emptyInstance() {
        return EMPTY_META_DATA;
    }

    private MetaData() {
        this.values = Collections.emptyMap();
    }

    @SuppressWarnings("unchecked")
    public MetaData(Map<String, ?> items) {
        this.values = Collections.unmodifiableMap(new HashMap(items));
    }

    public static MetaData from(Map<String, ?> metaDataEntries) {
        return metaDataEntries instanceof MetaData?(MetaData)metaDataEntries:(metaDataEntries != null && !metaDataEntries.isEmpty()?new MetaData(metaDataEntries):emptyInstance());
    }

    public Object get(Object key) {
        return this.values.get(key);
    }

    public Object put(String key, Object value) {
        throw new UnsupportedOperationException("Event meta-data is immutable.");
    }

    public Object remove(Object key) {
        throw new UnsupportedOperationException("Event meta-data is immutable.");
    }

    public void putAll(Map<? extends String, ?> m) {
        throw new UnsupportedOperationException("Event meta-data is immutable.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Event meta-data is immutable.");
    }

    public boolean containsKey(Object key) {
        return this.values.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.values.containsValue(value);
    }

    public Set<String> keySet() {
        return this.values.keySet();
    }

    public Collection<Object> values() {
        return this.values.values();
    }

    public Set<Entry<String, Object>> entrySet() {
        return this.values.entrySet();
    }

    public int size() {
        return this.values.size();
    }

    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(!(o instanceof Map)) {
            return false;
        } else {
            Map that = (Map)o;
            return this.values.equals(that);
        }
    }

    public int hashCode() {
        return this.values.hashCode();
    }

    @SuppressWarnings("unchecked")
    public MetaData mergedWith(Map<String, ?> additionalEntries) {
        if(additionalEntries.isEmpty()) {
            return this;
        } else {
            HashMap merged = new HashMap(this.values);
            merged.putAll(additionalEntries);
            return new MetaData(merged);
        }
    }

    @SuppressWarnings("unchecked")
    public MetaData withoutKeys(Set<String> keys) {
        if(keys.isEmpty()) {
            return this;
        } else {
            HashMap modified = new HashMap(this.values);
            Iterator i$ = keys.iterator();

            while(i$.hasNext()) {
                String key = (String)i$.next();
                modified.remove(key);
            }

            return new MetaData(modified);
        }
    }

    protected Object readResolve() {
        return this.isEmpty()?emptyInstance():this;
    }
}
