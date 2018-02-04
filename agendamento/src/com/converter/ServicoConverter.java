package com.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.entidade.Servico;


@FacesConverter(forClass=Servico.class)
public class ServicoConverter implements Converter{
		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
	        if (value != null && !value.isEmpty()) {
	            return (Servico) uiComponent.getAttributes().get(value);
	        }
	        return null;
	    }
	
	    @Override
		public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
	        if (value instanceof Servico) {
	        	Servico entity= (Servico) value;
	            if (entity != null && entity instanceof Servico && entity.getId() != null) {
	                uiComponent.getAttributes().put( entity.getId().toString(), entity);
	                return entity.getId().toString();
	            }
	        }
	        return "";
	    }
}
