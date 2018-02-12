package com.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import seguranca.com.entidade.UserComissario;

@FacesConverter(value="userComissarioConverter")
public class UserComissarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (UserComissario) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof UserComissario) {
        	UserComissario entity= (UserComissario) value;
            if (entity != null && entity instanceof UserComissario && entity.getId() != null) {
                uiComponent.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }
}
