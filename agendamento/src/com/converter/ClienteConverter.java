package com.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import seguranca.com.entidade.User;



@FacesConverter(value="clienteConverter")
public class ClienteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (User) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof User) {
        	User entity= (User) value;
            if (entity != null && entity instanceof User && entity.getId() != 0) {
                uiComponent.getAttributes().put(String.valueOf(entity.getId()), entity);
                return Integer.toString(entity.getId());
            }
        }
        return "";
    }

}
