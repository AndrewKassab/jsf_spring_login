package andrewkassab.jsf_login.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import java.io.IOException;

@FacesComponent("andrewkassab.jsf_login.ToolbarComponent")
public class ToolbarComponent extends UIComponentBase {

    @Override
    public String getFamily() {
        return "custom.components";
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        context.getResponseWriter().startElement("div", this);
        context.getResponseWriter().writeAttribute("class", "toolbar", null);

        for (ToolbarAction action: ToolbarAction.values()) {
            addButton(context, action);
        }

        context.getResponseWriter().endElement("div");
    }

    public void addButton(FacesContext context, ToolbarAction action) throws IOException {

    }
}
