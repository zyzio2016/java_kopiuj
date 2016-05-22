package dynamic1;

import java.util.concurrent.TimeUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;

@ManagedBean(name = "clipboardText", eager = true)
@ViewScoped
public class ClipboardTextBean {

	private String user;

	public ClipboardTextBean() {
		user = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("_user_");
		if (user == null)
			user = new String("__default__");
		text.setValue(ClipboardDatabase.getInstance().retrieveData(user).getText());
	}

	private HtmlInputTextarea text = new HtmlInputTextarea();

	public HtmlInputTextarea getText() {
		return text;
	}

	public void setText(HtmlInputTextarea text) {
		this.text = text;
	}

	public String save() {
		ClipboardDatabase.getInstance().storeData(new ClipboardData(getText().getValue().toString(), user,
				TimeUnit.MILLISECONDS.toDays (System.currentTimeMillis()) + 30));
		return "OK";
	}

}
