package com.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import seguranca.com.entidade.CadastroImportador;

@FacesConverter(value="cadastroImportadorConverter")
public class CadastroImportadorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (CadastroImportador) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof CadastroImportador) {
        	CadastroImportador entity= (CadastroImportador) value;
            if (entity != null && entity instanceof CadastroImportador && entity.getId() != null) {
                uiComponent.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }
}
