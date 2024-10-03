package andrewkassab.jsf_login.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

@FacesComponent(value = "customToolbar")
public class CustomToolbar extends UIComponentBase {

    @Override
    public String getFamily() {
        return "javax.faces.Output";
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement("div", this);
        writer.writeAttribute("class", "ui-toolbar ui-widget-header", null);

        writer.startElement("button", this);
        writer.writeAttribute("class", "ui-button ui-widget", null);
        writer.writeText("Home", null);
        writer.endElement("button");

        writer.startElement("button", this);
        writer.writeAttribute("class", "ui-button ui-widget", null);
        writer.writeText("Settings", null);
        writer.endElement("button");

        writer.endElement("div");
    }

}
