package andrewkassab.jsf_login.components;

import org.primefaces.component.commandbutton.CommandButton;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import java.io.IOException;

@FacesComponent(createTag = true, namespace= "http://andrewkassab.com/custom", tagName = "toolbar", value = "custom.ToolbarComponent")
public class ToolbarComponent extends UIComponentBase {

    @Override
    public String getFamily() {
        return "custom.components";
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        context.getResponseWriter().startElement("div", this);
        context.getResponseWriter().writeAttribute("class", "toolbar", null);

        for (ToolbarAction action : ToolbarAction.values()) {
            renderButton(context, action.getLabel(), action.getAction());
        }

        context.getResponseWriter().endElement("div");
    }

    private void renderButton(FacesContext context, String label, String action) throws IOException {
        CommandButton button = (CommandButton) context.getApplication().createComponent(CommandButton.COMPONENT_TYPE);
        button.setValue(label);
        button.setActionExpression(context.getApplication()
                .getExpressionFactory()
                .createMethodExpression(context.getELContext(), action, String.class, new Class[0]));

        button.setStyleClass("toolbar-button");

        this.getChildren().add(button);
    }


}
