package dynamic1;

import javax.faces.application.Application;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class ClipboardInitListener implements SystemEventListener {

	@Override
	public boolean isListenerForSource(Object value) {
		// only for Application
		return (value instanceof Application);
	}

	@Override
	public void processEvent(SystemEvent event) {
		if (event instanceof PostConstructApplicationEvent) {
			DBCleaner.startScheduler();
		}
	}
}
