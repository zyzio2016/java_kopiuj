package dynamic1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class DBCleaner {
	private static DBCleaner singleton = new DBCleaner();

	public static void startScheduler() {
		singleton.initScheduler();
	}

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private volatile AtomicBoolean initialized = new AtomicBoolean();

	private DBCleaner() {
	}

	private void initScheduler() {
		if (initialized.getAndSet(true))
			return;
		ClipboardDatabase.getInstance().removeOlderThan(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()) + 30);
		scheduler.scheduleAtFixedRate(
				() -> ClipboardDatabase.getInstance()
						.removeOlderThan(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis())),
				0, 1, TimeUnit.DAYS);
	}
}
