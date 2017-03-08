package br.com.neotech.framework.faces.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "br.com.neotech.bootstrapfaces.converter.entity")
public class EntityConverter implements Converter {

    private static final String KEY = "entityConverter";

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {

        Map<Object, String> entities = getMap(context);
        synchronized (entities) {
            if (!entities.containsKey(entity)) {
                String uuid = UUID.randomUUID().toString();
                entities.put(entity, uuid);
                return uuid;
            } else {
                return entities.get(entity);
            }
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        Map<Object, String> entities = getMap(context);
        for (Entry<Object, String> entry : entities.entrySet()) {
            if (entry.getValue().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private Map<Object, String> getMap(FacesContext context) {
        Map<String, Object> map = context.getExternalContext().getSessionMap();
        // Map<String, Object> map = context.getViewRoot().getViewMap();

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Map<Object, String> entityMap = (Map) map.get(KEY);
        if (entityMap == null) {
            entityMap = new HashMap<Object, String>();
            map.put(KEY, entityMap);
        }

        return entityMap;
    }

}
